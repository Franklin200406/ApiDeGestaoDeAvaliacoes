# API de Gestão de Avaliações

API REST para gestão de avaliações acadêmicas: usuários, cursos, disciplinas, questões, avaliações e templates de geração de PDF.

Todos os endpoints exigem autenticação JWT, exceto `POST /api/auth/login`. Todas as listagens são paginadas em banco, e todo o tráfego é JSON (o PDF gerado é a única exceção, que sai como `application/pdf`).

- **Stack:** Java 23, Spring Boot 4.1, Spring Security (JWT), Spring Data JPA, PostgreSQL 17
- **Geração de PDF:** template HTML com placeholders Thymeleaf, renderizado para PDF via openhtmltopdf
- **Documentação interativa:** Swagger UI

---

## 1. Pré-requisitos

- JDK 23
- PostgreSQL 17 rodando em `localhost:5432`
- Maven Wrapper (já incluído: `mvnw` / `mvnw.cmd`)

## 2. Preparando o banco

> **Importante:** a aplicação usa `spring.jpa.hibernate.ddl-auto=validate`, ou seja, ela **não cria nem altera tabelas**. O schema precisa existir antes de subir, senão o boot falha com `Schema validation: missing table/column`.

Crie o banco e rode o script de schema + dados de exemplo:

```bash
createdb -U postgres assesment_managment
psql -U postgres -d assesment_managment -f database/tde1.sql
```

No Windows, se o `psql` não estiver no PATH:

```powershell
& 'C:\Program Files\PostgreSQL\17\bin\psql.exe' -U postgres -d assesment_managment -f database/tde1.sql
```

O script cria as 11 tabelas e insere dados de exemplo: 1 curso, 1 disciplina, 2 usuários, 2 questões, 1 avaliação e 1 template de PDF.

## 3. Configurando as variáveis de ambiente

Copie `.env.example` para `.env` e ajuste as credenciais:

```properties
DB_URL=jdbc:postgresql://localhost:5432/assesment_managment
DB_USERNAME=postgres
DB_PASSWORD=sua-senha
JWT_SECRET=uma-chave-secreta-com-no-minimo-32-caracteres
JWT_EXPIRATION_HOURS=24
```

O `JWT_SECRET` precisa ter **pelo menos 32 caracteres** (HMAC-SHA256).

## 4. Subindo a aplicação

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. Para apenas compilar, use `./mvnw clean compile` (ou `.\build.ps1` no Windows, que aponta o `JAVA_HOME` para o JDK 23 antes de compilar).

---

## 5. Usuários de teste

O `database/tde1.sql` já cria dois usuários, ambos com a senha **`password123`**:

| E-mail | Tipo | Observação |
|---|---|---|
| `admin@example.com` | `ADMIN` | Gerencia usuários, cursos, disciplinas e templates |
| `author@example.com` | `AUTHOR` | Já associado ao curso 1 e à disciplina 1, pode criar questões e avaliações |

## 6. Obtendo o token JWT

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","password":"password123"}'
```

Resposta:

```json
{ "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsIn..." }
```

Envie esse token no header `Authorization` das demais chamadas:

```bash
curl http://localhost:8080/api/courses \
  -H "Authorization: Bearer SEU_TOKEN"
```

O token expira conforme `JWT_EXPIRATION_HOURS` (24h por padrão).

---

## 7. Testando pelo Swagger UI

1. Suba a aplicação e acesse **http://localhost:8080/swagger-ui/index.html**
2. Expanda `POST /api/auth/login`, clique em **Try it out** e envie:
   ```json
   { "email": "admin@example.com", "password": "password123" }
   ```
3. Copie o valor de `token` da resposta
4. Clique em **Authorize** (cadeado no topo da página) e cole **apenas o token**, sem o prefixo `Bearer` — o Swagger adiciona sozinho
5. Clique em **Authorize** e depois em **Close**

Pronto: a partir daí todas as chamadas do "Try it out" já vão com o header `Authorization`. A spec OpenAPI declara `bearerAuth` como requisito global, então o cadeado aparece em todos os endpoints — inclusive no `/api/auth/login`, que na prática continua público.

Para trocar de usuário (ex.: testar as regras do autor), refaça o login com `author@example.com` e reautorize com o novo token.

A spec crua fica em **http://localhost:8080/v3/api-docs**.

---

## 8. Fluxo completo de teste via curl

Este roteiro cobre o caminho principal do sistema: criar a estrutura acadêmica como admin, elaborar conteúdo como autor e gerar o PDF da avaliação.

> **Atenção com acentos em terminais Windows:** o cmd, o PowerShell e o Git Bash costumam enviar texto em Windows-1252, e a API rejeita o corpo com `400` e `JSON parse error: Invalid UTF-8 middle byte`. Se acontecer, salve o JSON em um arquivo UTF-8 e envie com `--data-binary`:
>
> ```bash
> curl -X POST http://localhost:8080/api/questions \
>   -H "Authorization: Bearer $AUTOR" -H "Content-Type: application/json" \
>   --data-binary @questao.json
> ```
>
> Pelo Swagger UI isso não acontece — o navegador já envia UTF-8.

### 8.1 Autenticar os dois perfis

```bash
ADMIN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","password":"password123"}' \
  | sed -E 's/.*"token":"([^"]+)".*/\1/')

AUTOR=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"author@example.com","password":"password123"}' \
  | sed -E 's/.*"token":"([^"]+)".*/\1/')
```

No PowerShell:

```powershell
$admin = (Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/auth/login `
  -ContentType 'application/json' `
  -Body '{"email":"admin@example.com","password":"password123"}').token
```

### 8.2 Como admin: criar curso e disciplina

```bash
# Cria o curso
curl -X POST http://localhost:8080/api/courses \
  -H "Authorization: Bearer $ADMIN" -H "Content-Type: application/json" \
  -d '{"name":"Ciência da Computação","description":"Curso de CC","classFormat":"PRESENTIAL"}'

# Cria a disciplina no curso 2 (id retornado acima)
curl -X POST http://localhost:8080/api/disciplines \
  -H "Authorization: Bearer $ADMIN" -H "Content-Type: application/json" \
  -d '{"name":"Estrutura de Dados","description":"Listas, árvores e grafos","workloadHours":80,"shift":"NIGHT","courseId":2}'
```

### 8.3 Como admin: criar um autor e associá-lo à disciplina

Um autor só consegue criar questões e avaliações para disciplinas às quais **está associado**.

```bash
# Cria o usuário autor
curl -X POST http://localhost:8080/api/users \
  -H "Authorization: Bearer $ADMIN" -H "Content-Type: application/json" \
  -d '{"email":"maria@example.com","name":"Maria Souza","type":"AUTHOR","password":"senha123"}'

# Associa ao curso e à disciplina (usuário 3, curso 2, disciplina 2)
curl -X POST http://localhost:8080/api/users/3/courses \
  -H "Authorization: Bearer $ADMIN" -H "Content-Type: application/json" \
  -d '{"courseId":2}'

curl -X POST http://localhost:8080/api/users/3/disciplines \
  -H "Authorization: Bearer $ADMIN" -H "Content-Type: application/json" \
  -d '{"disciplineId":2}'
```

### 8.4 Como autor: criar questões

Questão aberta:

```bash
curl -X POST http://localhost:8080/api/questions \
  -H "Authorization: Bearer $AUTOR" -H "Content-Type: application/json" \
  -d '{
    "type":"OPEN",
    "difficulty":"MEDIUM",
    "description":"Explique a diferença entre lista encadeada e vetor.",
    "disciplineId":1
  }'
```

Questão de múltipla escolha — `optionCount` precisa ser igual ao número de itens em `options`, e exatamente **uma** opção deve ter `"correct": true`:

```bash
curl -X POST http://localhost:8080/api/questions \
  -H "Authorization: Bearer $AUTOR" -H "Content-Type: application/json" \
  -d '{
    "type":"MULTIPLE_CHOICE",
    "difficulty":"EASY",
    "description":"Qual estrutura segue a política FIFO?",
    "disciplineId":1,
    "optionCount":4,
    "options":[
      {"text":"Pilha","correct":false},
      {"text":"Fila","correct":true},
      {"text":"Árvore binária","correct":false},
      {"text":"Grafo","correct":false}
    ]
  }'
```

Listar as questões de uma disciplina (exige estar associado a ela, ou ser admin):

```bash
curl "http://localhost:8080/api/questions?disciplineId=1&page=0&size=10" \
  -H "Authorization: Bearer $AUTOR"
```

### 8.5 Como autor: criar a avaliação

`multipleChoiceQuestionCount` e `openQuestionCount` declaram quantas questões de cada tipo a avaliação terá — são o limite das associações do próximo passo. A disciplina precisa pertencer ao curso informado.

```bash
curl -X POST http://localhost:8080/api/assessments \
  -H "Authorization: Bearer $AUTOR" -H "Content-Type: application/json" \
  -d '{
    "courseId":1,
    "disciplineId":1,
    "semester":"2026.2",
    "teacher":"Prof. Exemplo",
    "assessmentDate":"2026-11-20",
    "elaborationDate":"2026-10-15",
    "type":"AV2",
    "value":10.00,
    "multipleChoiceQuestionCount":1,
    "openQuestionCount":1
  }'
```

### 8.6 Como autor: associar as questões à avaliação

Cada associação informa o valor daquela questão na prova. A questão precisa ser da mesma disciplina da avaliação e do tipo correspondente ao endpoint.

> Os IDs abaixo assumem que a avaliação criada em 8.5 recebeu o id `2` e que as questões criadas em 8.4 receberam os ids `3` (aberta) e `4` (múltipla escolha) — o seed já ocupa os primeiros IDs. Use os valores que as respostas anteriores retornaram.

```bash
# Questão de múltipla escolha (valendo 4.00)
curl -X POST http://localhost:8080/api/assessments/2/multiple-choice-questions \
  -H "Authorization: Bearer $AUTOR" -H "Content-Type: application/json" \
  -d '{"questionId":4,"value":4.00}'

# Questão aberta (valendo 6.00)
curl -X POST http://localhost:8080/api/assessments/2/open-questions \
  -H "Authorization: Bearer $AUTOR" -H "Content-Type: application/json" \
  -d '{"questionId":3,"value":6.00}'
```

A resposta traz a avaliação com as associações já refletidas. Para remover uma associação:

```bash
curl -X DELETE http://localhost:8080/api/assessments/2/open-questions/3 \
  -H "Authorization: Bearer $AUTOR"
```

### 8.7 Listar as próprias avaliações

```bash
# Todas as avaliações do autor autenticado
curl http://localhost:8080/api/assessments/mine -H "Authorization: Bearer $AUTOR"

# Filtrando por disciplina
curl "http://localhost:8080/api/assessments/mine?disciplineId=1" -H "Authorization: Bearer $AUTOR"

# Listagem geral (somente admin)
curl http://localhost:8080/api/assessments -H "Authorization: Bearer $ADMIN"
```

### 8.8 Gerar o PDF da avaliação

O `templateId` precisa apontar para um template já cadastrado (o seed cria o template 1, `Template Padrão`).

```bash
curl "http://localhost:8080/api/assessments/1/pdf?templateId=1" \
  -H "Authorization: Bearer $AUTOR" \
  --output avaliacao.pdf
```

A resposta vem como `application/pdf`. O conteúdo é gerado a partir do HTML do template, processado com os dados reais da avaliação (curso, disciplina, docente, questões e alternativas).

### 8.9 Como admin: cadastrar um novo template

O campo `content` recebe o HTML do template, com placeholders Thymeleaf. A variável disponível é `assessment`:

```bash
curl -X POST http://localhost:8080/api/templates \
  -H "Authorization: Bearer $ADMIN" -H "Content-Type: application/json" \
  -d '{
    "name":"Template Simples",
    "fileName":"simples.html",
    "content":"<html xmlns:th=\"http://www.thymeleaf.org\"><body><h1 th:text=\"${assessment.type}\"></h1><p th:text=\"${assessment.discipline.name}\"></p><p th:text=\"${assessment.teacher}\"></p><ol><li th:each=\"q : ${assessment.openQuestions}\" th:text=\"${q.question.description}\"></li></ol></body></html>"
  }'
```

O template completo que o seed cadastra (com cabeçalho, questões de múltipla escolha e alternativas) está em `database/tde1.sql` e serve como ponto de partida para criar os seus.

Placeholders úteis: `${assessment.type}`, `${assessment.course.name}`, `${assessment.discipline.name}`, `${assessment.teacher}`, `${assessment.semester}`, `${assessment.assessmentDate}`, `${assessment.value}`, e as listas `${assessment.multipleChoiceQuestions}` e `${assessment.openQuestions}` (cada item tem `.value` e `.question.description`; nas de múltipla escolha, `.question.options` com `.text`).

### 8.10 Gestão da própria conta

```bash
# Dados do usuário autenticado
curl http://localhost:8080/api/users/me -H "Authorization: Bearer $AUTOR"

# Atualizar os próprios dados
curl -X PUT http://localhost:8080/api/users/me \
  -H "Authorization: Bearer $AUTOR" -H "Content-Type: application/json" \
  -d '{"email":"author@example.com","name":"Autor de Conteúdo II"}'

# Trocar a própria senha
curl -X POST http://localhost:8080/api/users/me/change-password \
  -H "Authorization: Bearer $AUTOR" -H "Content-Type: application/json" \
  -d '{"oldPassword":"password123","newPassword":"novaSenha123"}'

# Admin reseta a senha de outro usuário
curl -X POST http://localhost:8080/api/users/3/reset-password \
  -H "Authorization: Bearer $ADMIN" -H "Content-Type: application/json" \
  -d '{"newPassword":"senhaPadrao123"}'
```

---

## 9. Testando as regras de negócio (casos de erro)

Vale testar também os caminhos que devem falhar — é onde as regras do sistema aparecem:

| Teste | Chamada | Esperado |
|---|---|---|
| Autor tentando listar usuários | `GET /api/users` com token de autor | `403` |
| Autor tentando criar curso | `POST /api/courses` com token de autor | `403` |
| Admin tentando criar questão | `POST /api/questions` com token de admin | `403` (só autor elabora) |
| Autor criando questão em disciplina não associada | `POST /api/questions` com `disciplineId` sem vínculo | `403` |
| Múltipla escolha com contagem errada | `optionCount: 4` e 3 opções | `400` |
| Múltipla escolha sem resposta correta | nenhuma opção com `correct: true` | `400` |
| Remover curso com disciplina vinculada | `DELETE /api/courses/1` | `409` |
| Remover disciplina com questão vinculada | `DELETE /api/disciplines/1` | `409` |
| Remover usuário autor de questões | `DELETE /api/users/2` | `409` |
| Remover questão já usada em avaliação | `DELETE /api/questions/1` | `409` |
| Associar questão além da quantidade declarada | repetir o `POST` de associação | `409` |
| Associar a mesma questão duas vezes | repetir com o mesmo `questionId` | `409` |
| Associar questão de outra disciplina | `questionId` de disciplina diferente | `400` |
| Disciplina que não pertence ao curso | `POST /api/assessments` com par incompatível | `400` |
| E-mail duplicado | `POST /api/users` com e-mail existente | `409` |
| Senha atual errada | `POST /api/users/me/change-password` | `400` |
| Template inexistente na geração de PDF | `?templateId=999` | `404` |
| Sem token / token inválido | qualquer rota protegida | `403` |

Erros de validação de payload retornam `400` com os campos que falharam:

```json
{
  "timestamp": "2026-09-17T23:53:35.507Z",
  "status": 400,
  "message": "Erro de validação",
  "errors": {
    "email": "E-mail inválido",
    "password": "Senha é obrigatória"
  }
}
```

---

## 10. Referência de endpoints

Legenda de acesso: **Público** · **Autenticado** (qualquer usuário logado) · **Admin** · **Autor** · **Admin ou autor do recurso**

### Autenticação

| Método | Rota | Acesso |
|---|---|---|
| `POST` | `/api/auth/login` | Público |

### Usuários

| Método | Rota | Acesso |
|---|---|---|
| `GET` | `/api/users` | Admin |
| `GET` | `/api/users/{id}` | Admin |
| `GET` | `/api/users/me` | Autenticado |
| `POST` | `/api/users` | Admin |
| `PUT` | `/api/users/me` | Autenticado (só a si mesmo) |
| `POST` | `/api/users/me/change-password` | Autenticado (exige senha atual) |
| `POST` | `/api/users/{id}/reset-password` | Admin |
| `DELETE` | `/api/users/{id}` | Admin (sem questões/avaliações) |
| `POST` | `/api/users/{id}/courses` | Admin |
| `DELETE` | `/api/users/{id}/courses/{courseId}` | Admin |
| `POST` | `/api/users/{id}/disciplines` | Admin |
| `DELETE` | `/api/users/{id}/disciplines/{disciplineId}` | Admin |

### Cursos

| Método | Rota | Acesso |
|---|---|---|
| `GET` | `/api/courses` | Autenticado |
| `GET` | `/api/courses/{id}` | Autenticado |
| `GET` | `/api/courses/{id}/disciplines` | Autenticado |
| `POST` | `/api/courses` | Admin |
| `PUT` | `/api/courses/{id}` | Admin |
| `DELETE` | `/api/courses/{id}` | Admin (sem disciplinas/avaliações/usuários) |

### Disciplinas

| Método | Rota | Acesso |
|---|---|---|
| `GET` | `/api/disciplines` | Autenticado |
| `GET` | `/api/disciplines/{id}` | Autenticado |
| `POST` | `/api/disciplines` | Admin |
| `PUT` | `/api/disciplines/{id}` | Admin |
| `DELETE` | `/api/disciplines/{id}` | Admin (sem questões/avaliações/usuários) |

### Questões

| Método | Rota | Acesso |
|---|---|---|
| `GET` | `/api/questions?disciplineId={id}` | Autenticado e associado à disciplina (ou admin) |
| `GET` | `/api/questions/{id}` | Autenticado e associado à disciplina (ou admin) |
| `POST` | `/api/questions` | Autor associado à disciplina |
| `PUT` | `/api/questions/{id}` | Admin ou autor da questão |
| `DELETE` | `/api/questions/{id}` | Admin ou autor da questão (se não usada em avaliação) |

### Avaliações

| Método | Rota | Acesso |
|---|---|---|
| `GET` | `/api/assessments?disciplineId={id}` | Admin |
| `GET` | `/api/assessments/mine?disciplineId={id}` | Autenticado (retorna só as próprias) |
| `GET` | `/api/assessments/{id}` | Admin ou autor da avaliação |
| `POST` | `/api/assessments` | Autor associado à disciplina |
| `PUT` | `/api/assessments/{id}` | Admin ou autor da avaliação |
| `DELETE` | `/api/assessments/{id}` | Admin ou autor da avaliação |
| `POST` | `/api/assessments/{id}/multiple-choice-questions` | Admin ou autor da avaliação |
| `DELETE` | `/api/assessments/{id}/multiple-choice-questions/{questionId}` | Admin ou autor da avaliação |
| `POST` | `/api/assessments/{id}/open-questions` | Admin ou autor da avaliação |
| `DELETE` | `/api/assessments/{id}/open-questions/{questionId}` | Admin ou autor da avaliação |
| `GET` | `/api/assessments/{id}/pdf?templateId={id}` | Admin ou autor da avaliação |

### Templates

| Método | Rota | Acesso |
|---|---|---|
| `GET` | `/api/templates` | Autenticado |
| `GET` | `/api/templates/{id}` | Autenticado |
| `POST` | `/api/templates` | Admin |
| `PUT` | `/api/templates/{id}` | Admin |
| `DELETE` | `/api/templates/{id}` | Admin |

---

## 11. Paginação

Toda listagem aceita `page` (base 0), `size` e `sort`:

```bash
curl "http://localhost:8080/api/courses?page=0&size=5&sort=name,asc" \
  -H "Authorization: Bearer $ADMIN"
```

A resposta segue o formato `Page` do Spring Data:

```json
{
  "content": [ { "id": 1, "name": "Engenharia de Software", "...": "..." } ],
  "number": 0,
  "size": 5,
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true,
  "empty": false
}
```

## 12. Valores aceitos nos enums

| Campo | Valores |
|---|---|
| `type` (usuário) | `ADMIN`, `AUTHOR` |
| `classFormat` (curso) | `PRESENTIAL`, `EAD`, `HYBRID` |
| `shift` (disciplina) | `AFTERNOON`, `NIGHT` |
| `type` (questão) | `OPEN`, `MULTIPLE_CHOICE` |
| `difficulty` (questão) | `EASY`, `MEDIUM`, `HARD` |
| `type` (avaliação) | `AV1`, `AV1_SECOND_CALL`, `AV1_ADAPTED`, `AV1_ADAPTED_SECOND_CALL`, `AV2`, `AV2_SECOND_CALL`, `AV2_ADAPTED`, `AV2_ADAPTED_SECOND_CALL`, `FINAL`, `FINAL_SECOND_CALL`, `FINAL_ADAPTED`, `FINAL_ADAPTED_SECOND_CALL` |

## 13. Testes automatizados

```bash
./mvnw test
```

> O teste `GestaoAvaliacoesApplicationTests` carrega o contexto completo e por isso precisa do PostgreSQL disponível e do schema já criado.

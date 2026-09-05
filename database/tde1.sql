-- ============================================================
-- TDE1 - Sistema de Gestão de Avaliações
-- Banco de dados: PostgreSQL
-- ============================================================


-- ============================================================
-- TABELA: usuarios
-- ============================================================

CREATE TABLE usuarios (
                          id BIGSERIAL PRIMARY KEY,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          nome VARCHAR(150) NOT NULL,
                          tipo VARCHAR(20) NOT NULL,
                          senha VARCHAR(255) NOT NULL,

                          CONSTRAINT ck_usuarios_tipo
                              CHECK (tipo IN ('admin', 'autor'))
);


-- ============================================================
-- TABELA: cursos
-- ============================================================

CREATE TABLE cursos (
                        id BIGSERIAL PRIMARY KEY,
                        nome VARCHAR(150) NOT NULL,
                        descricao TEXT,
                        formato_aula VARCHAR(20) NOT NULL,

                        CONSTRAINT ck_cursos_formato_aula
                            CHECK (formato_aula IN ('presencial', 'EAD', 'semi-presencial'))
);


-- ============================================================
-- TABELA: disciplinas
-- ============================================================

CREATE TABLE disciplinas (
                             id BIGSERIAL PRIMARY KEY,
                             nome VARCHAR(150) NOT NULL,
                             descricao TEXT,
                             carga_horaria INTEGER NOT NULL,
                             turno VARCHAR(20) NOT NULL,
                             curso_id BIGINT NOT NULL,

                             CONSTRAINT ck_disciplinas_carga_horaria
                                 CHECK (carga_horaria > 0),

                             CONSTRAINT ck_disciplinas_turno
                                 CHECK (turno IN ('Vespertino', 'Noturno')),

                             CONSTRAINT fk_disciplinas_curso
                                 FOREIGN KEY (curso_id)
                                     REFERENCES cursos(id)
);


-- ============================================================
-- TABELA: usuario_cursos
-- Associação entre usuários e cursos
-- ============================================================

CREATE TABLE usuario_cursos (
                                usuario_id BIGINT NOT NULL,
                                curso_id BIGINT NOT NULL,

                                PRIMARY KEY (usuario_id, curso_id),

                                CONSTRAINT fk_usuario_cursos_usuario
                                    FOREIGN KEY (usuario_id)
                                        REFERENCES usuarios(id),

                                CONSTRAINT fk_usuario_cursos_curso
                                    FOREIGN KEY (curso_id)
                                        REFERENCES cursos(id)
);


-- ============================================================
-- TABELA: usuario_disciplinas
-- Associação entre usuários e disciplinas
-- ============================================================

CREATE TABLE usuario_disciplinas (
                                     usuario_id BIGINT NOT NULL,
                                     disciplina_id BIGINT NOT NULL,

                                     PRIMARY KEY (usuario_id, disciplina_id),

                                     CONSTRAINT fk_usuario_disciplinas_usuario
                                         FOREIGN KEY (usuario_id)
                                             REFERENCES usuarios(id),

                                     CONSTRAINT fk_usuario_disciplinas_disciplina
                                         FOREIGN KEY (disciplina_id)
                                             REFERENCES disciplinas(id)
);


-- ============================================================
-- DADOS DE EXEMPLO
-- ============================================================

INSERT INTO usuarios (
    email,
    nome,
    tipo,
    senha
)
VALUES (
           'admin@gestaoavaliacoes.com',
           'Administrador',
           'admin',
           'senha-exemplo'
       );


INSERT INTO cursos (
    nome,
    descricao,
    formato_aula
)
VALUES (
           'Engenharia de Software',
           'Curso voltado ao desenvolvimento e gerenciamento de sistemas de software.',
           'presencial'
       );


INSERT INTO disciplinas (
    nome,
    descricao,
    carga_horaria,
    turno,
    curso_id
)
VALUES (
           'Programação Orientada a Objetos',
           'Disciplina sobre conceitos e práticas de programação orientada a objetos.',
           80,
           'Noturno',
           1
       );


-- Associação do usuário ao curso

INSERT INTO usuario_cursos (
    usuario_id,
    curso_id
)
VALUES (
           1,
           1
       );


-- Associação do usuário à disciplina

INSERT INTO usuario_disciplinas (
    usuario_id,
    disciplina_id
)
VALUES (
           1,
           1
       );
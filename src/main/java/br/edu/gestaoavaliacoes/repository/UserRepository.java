package br.edu.gestaoavaliacoes.repository;

import br.edu.gestaoavaliacoes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCourses_Id(Long courseId);

    boolean existsByDisciplines_Id(Long disciplineId);

    boolean existsByIdAndDisciplines_Id(Long userId, Long disciplineId);
}
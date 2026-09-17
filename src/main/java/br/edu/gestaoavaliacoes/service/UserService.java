package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.presentation.dto.request.ChangePasswordRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.ResetPasswordRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserCourseRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserCreateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserDisciplineRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserUpdateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public Page<UserResponse> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    public UserResponse findById(Long id) {
        throw new UnsupportedOperationException();
    }

    public UserResponse findMe() {
        throw new UnsupportedOperationException();
    }

    public UserResponse create(UserCreateRequest request) {
        throw new UnsupportedOperationException();
    }

    public UserResponse updateMe(UserUpdateRequest request) {
        throw new UnsupportedOperationException();
    }

    public void changePassword(ChangePasswordRequest request) {
        throw new UnsupportedOperationException();
    }

    public void resetPassword(Long id, ResetPasswordRequest request) {
        throw new UnsupportedOperationException();
    }

    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    public UserResponse addCourse(Long id, UserCourseRequest request) {
        throw new UnsupportedOperationException();
    }

    public UserResponse removeCourse(Long id, Long courseId) {
        throw new UnsupportedOperationException();
    }

    public UserResponse addDiscipline(Long id, UserDisciplineRequest request) {
        throw new UnsupportedOperationException();
    }

    public UserResponse removeDiscipline(Long id, Long disciplineId) {
        throw new UnsupportedOperationException();
    }
}
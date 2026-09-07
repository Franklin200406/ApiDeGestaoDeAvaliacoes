package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.ChangePasswordRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.ResetPasswordRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserCourseRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserCreateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserDisciplineRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserUpdateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public Page<UserResponse> findAll(@PageableDefault(size = 20) Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/me")
    public UserResponse findMe() {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public UserResponse create(@RequestBody UserCreateRequest request) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/me")
    public UserResponse updateMe(@RequestBody UserUpdateRequest request) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/me/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{id}/reset-password")
    public void resetPassword(@PathVariable Long id, @RequestBody ResetPasswordRequest request) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{id}/courses")
    public UserResponse addCourse(@PathVariable Long id, @RequestBody UserCourseRequest request) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}/courses/{courseId}")
    public UserResponse removeCourse(@PathVariable Long id, @PathVariable Long courseId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{id}/disciplines")
    public UserResponse addDiscipline(@PathVariable Long id, @RequestBody UserDisciplineRequest request) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}/disciplines/{disciplineId}")
    public UserResponse removeDiscipline(@PathVariable Long id, @PathVariable Long disciplineId) {
        throw new UnsupportedOperationException();
    }
}
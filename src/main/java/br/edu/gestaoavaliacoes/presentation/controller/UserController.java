package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.ChangePasswordRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.ResetPasswordRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserCourseRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserCreateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserDisciplineRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserUpdateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.UserResponse;
import br.edu.gestaoavaliacoes.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponse> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/me")
    public UserResponse findMe() {
        return userService.findMe();
    }

    @PostMapping
    public UserResponse create(@RequestBody UserCreateRequest request) {
        return userService.create(request);
    }

    @PutMapping("/me")
    public UserResponse updateMe(@RequestBody UserUpdateRequest request) {
        return userService.updateMe(request);
    }

    @PostMapping("/me/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
    }

    @PostMapping("/{id}/reset-password")
    public void resetPassword(@PathVariable Long id, @RequestBody ResetPasswordRequest request) {
        userService.resetPassword(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping("/{id}/courses")
    public UserResponse addCourse(@PathVariable Long id, @RequestBody UserCourseRequest request) {
        return userService.addCourse(id, request);
    }

    @DeleteMapping("/{id}/courses/{courseId}")
    public UserResponse removeCourse(@PathVariable Long id, @PathVariable Long courseId) {
        return userService.removeCourse(id, courseId);
    }

    @PostMapping("/{id}/disciplines")
    public UserResponse addDiscipline(@PathVariable Long id, @RequestBody UserDisciplineRequest request) {
        return userService.addDiscipline(id, request);
    }

    @DeleteMapping("/{id}/disciplines/{disciplineId}")
    public UserResponse removeDiscipline(@PathVariable Long id, @PathVariable Long disciplineId) {
        return userService.removeDiscipline(id, disciplineId);
    }
}

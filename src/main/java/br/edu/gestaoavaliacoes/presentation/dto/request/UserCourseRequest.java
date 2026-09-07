package br.edu.gestaoavaliacoes.presentation.dto.request;

public class UserCourseRequest {

    private Long courseId;

    public UserCourseRequest() {
    }

    public UserCourseRequest(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
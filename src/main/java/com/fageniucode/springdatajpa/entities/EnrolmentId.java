package com.fageniucode.springdatajpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EnrolmentId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    public EnrolmentId(Long student_id, Long courseId) {
        this.studentId = student_id;
        this.courseId = courseId;
    }

    public EnrolmentId() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long student_id) {
        this.studentId = student_id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrolmentId that = (EnrolmentId) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}

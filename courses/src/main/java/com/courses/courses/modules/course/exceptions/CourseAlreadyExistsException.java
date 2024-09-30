package com.courses.courses.modules.course.exceptions;

public class CourseAlreadyExistsException extends RuntimeException {
    public CourseAlreadyExistsException() {
        super("Course already exists!");
    }
}

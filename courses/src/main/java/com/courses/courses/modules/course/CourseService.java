package com.courses.courses.modules.course;

import com.courses.courses.modules.course.exceptions.CourseAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public CourseEntity createCourse(CourseEntity newCourse) throws CourseAlreadyExistsException {

        courseRepository.findByName(newCourse.getName()).ifPresent(course -> {
            throw new CourseAlreadyExistsException();
        });

        return courseRepository.save(newCourse);
    }

    public List<CourseEntity> findAll() {
        return this.courseRepository.findAll();
    }
}

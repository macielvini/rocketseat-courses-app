package com.courses.courses.modules.course;

import com.courses.courses.modules.course.exceptions.CourseAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/")
    public ResponseEntity<Object> createCourse(@Valid @RequestBody CourseEntity newCourse) {
        try {
            var result = this.courseService.createCourse(newCourse);
            return ResponseEntity.ok().body(result);
        } catch (CourseAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<CourseEntity>> findAllCourses() {
        var result = this.courseService.findAll();
        return ResponseEntity.ok().body(result);
    }
}

package com.courses.courses.modules.course;

import com.courses.courses.modules.course.dto.CreateCourseDto;
import com.courses.courses.modules.course.exceptions.CourseAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createCourse(@Valid @RequestBody CreateCourseDto courseDto, HttpServletRequest http) {
        try {
            var userId = http.getAttribute("userId");

            var courseEntity = CourseEntity.builder()
                    .categoryId(courseDto.getCategoryId())
                    .userId(UUID.fromString(userId.toString()))
                    .name(courseDto.getName())
                    .build();

            var result = this.courseService.createCourse(courseEntity);
            return ResponseEntity.ok().body(result);
        } catch (CourseAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CourseEntity>> findAllCourses() {
        var result = this.courseService.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminTest() {
        return "You have admin permissions!";
    }
}

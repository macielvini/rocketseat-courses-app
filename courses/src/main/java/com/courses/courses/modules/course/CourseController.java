package com.courses.courses.modules.course;

import com.courses.courses.modules.course.dto.CreateCourseDto;
import com.courses.courses.modules.course.exceptions.CourseAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CourseEntity.class))
            })
    })
    @Operation(summary = "Create a course")
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
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "List all courses")
    public ResponseEntity<List<CourseEntity>> findAllCourses() {
        var result = this.courseService.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(example = "You have admin permissions!"))
            }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(schema = @Schema(example = "{}", description = "You DO NOT have admin permissions!"))
            }),
    })
    @Operation(summary = "Just to check if user has ADMIN role permissions")
    public String adminTest() {
        return "You have admin permissions!";
    }
}

package com.courses.courses.modules.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class CreateCourseDto {
    @NotBlank
    @Length(min = 2, max = 100)
    private String name;

    @NotNull
    private UUID categoryId;
}

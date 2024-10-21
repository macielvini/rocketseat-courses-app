package com.courses.courses.modules.category;

import com.courses.courses.modules.category.exceptions.CategoryAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/categories")
@Tag(name = "Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    @Operation(summary = "Create a category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CategoryEntity.class))
            }),
            @ApiResponse(responseCode = "409", content = {
                    @Content(schema = @Schema(example = "Category already exists"))
            })
    })
    public ResponseEntity<Object> createCategory(@Valid @RequestBody CategoryEntity categoryBody) {
        try {
            var result = this.categoryService.createCategory(categoryBody);
            return ResponseEntity.ok().body(result);
        } catch (CategoryAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @Operation(summary = "List all categories")
    public ResponseEntity<List<CategoryEntity>> findAll() {
        var result = this.categoryService.findAll();

        return ResponseEntity.ok().body(result);
    }
}

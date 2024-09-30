package com.courses.courses.modules.category;

import com.courses.courses.modules.category.exceptions.CategoryAlreadyExistsException;
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
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/")
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
    public ResponseEntity<List<CategoryEntity>> findAll() {
        var result = this.categoryService.findAll();

        return ResponseEntity.ok().body(result);
    }
}

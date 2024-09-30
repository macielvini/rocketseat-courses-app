package com.courses.courses.modules.category.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException() {
        super("Category already exists!");
    }
}

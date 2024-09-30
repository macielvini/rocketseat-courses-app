package com.courses.courses.modules.category;

import com.courses.courses.modules.category.exceptions.CategoryAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity createCategory(CategoryEntity newCategory) throws CategoryAlreadyExistsException {
        this.categoryRepository.findByName(newCategory.getName()).ifPresent(
                (category) -> {
                    throw new CategoryAlreadyExistsException();
                }
        );

        return this.categoryRepository.save(newCategory);
    }

    public List<CategoryEntity> findAll() {
        return this.categoryRepository.findAll();
    }
}

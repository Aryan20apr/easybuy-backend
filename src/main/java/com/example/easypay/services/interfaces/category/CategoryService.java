package com.example.easypay.services.interfaces.category;

import com.example.easypay.modals.dtos.category.CategoryDto;
import com.example.easypay.modals.projections.CategoryProjection;

import java.util.List;

public interface CategoryService {

    public Boolean createCategory(CategoryDto categoryDto);

    public Boolean editCategory(CategoryDto category);

    public Boolean removeCategory(Long categoryId);

    public List<CategoryProjection> getAllCategories();

}

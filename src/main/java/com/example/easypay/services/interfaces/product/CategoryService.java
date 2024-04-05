package com.example.easypay.services.interfaces.product;

import com.example.easypay.modals.dtos.category.CategoryDto;
import com.example.easypay.modals.dtos.projections.CategoryProjection;
import com.example.easypay.modals.entities.category.Category;

import java.util.List;

public interface CategoryService {

    public Boolean createCategory(CategoryDto categoryDto);

    public Boolean editCategory(CategoryDto category);

    public Boolean removeCategory(Long categoryId);

    public List<CategoryProjection> getAllCategories();

}

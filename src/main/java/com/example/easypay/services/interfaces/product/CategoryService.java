package com.example.easypay.services.interfaces.product;

import com.example.easypay.modals.dtos.category.CategoryDto;
import com.example.easypay.modals.entities.category.Category;

public interface CategoryService {

    public Boolean createCategory(CategoryDto categoryDto);

    public Boolean editCategory(CategoryDto category);

    public Boolean removeCategory(Long categoryId);

}

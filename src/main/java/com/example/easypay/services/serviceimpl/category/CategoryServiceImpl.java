package com.example.easypay.services.serviceimpl.category;

import com.example.easypay.modals.dtos.category.CategoryDto;
import com.example.easypay.modals.projections.CategoryProjection;
import com.example.easypay.modals.entities.admins.Admin;
import com.example.easypay.modals.entities.category.Category;
import com.example.easypay.repository.admin.AdminRepository;
import com.example.easypay.repository.category.CategoryRepository;
import com.example.easypay.services.interfaces.category.CategoryService;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    AdminRepository adminRepository;

    @Override
    public Boolean createCategory(CategoryDto categoryDto) {
        String adminToken=categoryDto.getAdminToken();
        Optional<Admin> admin=adminRepository.findByAdminToken(adminToken);
        if(!admin.isPresent())
        {
            throw new ApiException("Admin with token "+adminToken+" is not present");
        }
        else
        {
            Category category=Category.builder().name(categoryDto.getName()).enabled(categoryDto.isEnabled()).build();
            admin.get().addCategory(category);
        }
        adminRepository.save(admin.get());

        return true;

    }

    @Override
    public Boolean editCategory(CategoryDto categoryDto) {

        Optional<Category> category =categoryRepository.findById(categoryDto.getCategoryId());

        if(category.isPresent())
        {
            category.get().setName(categoryDto.getName());
            category.get().setEnabled(categoryDto.isEnabled());
            categoryRepository.save(category.get());
        }
        else
        {
            throw new ApiException("No category found with this Id");
        }
        return true;
    }

    @Override
    public Boolean removeCategory(Long categoryId) {

        try
        {
            categoryRepository.deleteById(categoryId);
        }
        catch (Exception e)
        {
            throw
                     new ApiException(e.getMessage());
        }
        return true;


    }

    @Override
    public List<CategoryProjection> getAllCategories()

    {
        return categoryRepository.getAllCategories();
    }
}

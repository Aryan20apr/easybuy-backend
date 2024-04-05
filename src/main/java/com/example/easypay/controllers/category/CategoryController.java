package com.example.easypay.controllers.category;

import com.example.easypay.modals.dtos.category.CategoryDto;
import com.example.easypay.modals.projections.CategoryProjection;
import com.example.easypay.modals.dtos.shared.ApiResponse;
import com.example.easypay.services.interfaces.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/easybuy/api/v1/category")
public class CategoryController {

    private CategoryService categoryService ;
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createCategory(@RequestBody CategoryDto categoryDto)
    {
        categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(new ApiResponse<>("Category added succsessfully"), HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateCtageory(@RequestBody CategoryDto categoryDto)
    {
        categoryService.editCategory(categoryDto);
        return new ResponseEntity<>(new ApiResponse<>("Category updated !"), HttpStatus.OK);
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> removeCateory(@RequestParam Long id)
    {
        categoryService.removeCategory(id);
        return new ResponseEntity<>(new ApiResponse<>("Category removed!"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryProjection>>> getAllCategories()
    {
        return new ResponseEntity<>(new ApiResponse<>(categoryService.getAllCategories()), HttpStatus.OK);
    }
}

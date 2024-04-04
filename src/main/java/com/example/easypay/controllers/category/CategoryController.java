package com.example.easypay.controllers.category;

import com.example.easypay.modals.dtos.category.CategoryDto;
import com.example.easypay.modals.dtos.shared.ApiResponse;
import com.example.easypay.services.interfaces.product.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/easybuy/api/v1/category")
public class CategoryController {

    private CategoryService categoryService ;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createCtageory(@RequestBody CategoryDto categoryDto)
    {
        categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(new ApiResponse<>("Category added succsessfully"), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateCtageory(@RequestBody CategoryDto categoryDto)
    {
        categoryService.editCategory(categoryDto);
        return new ResponseEntity<>(new ApiResponse<>("Category updated !"), HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> createCtageory(@RequestParam Long id)
    {
        categoryService.removeCategory(id);
        return new ResponseEntity<>(new ApiResponse<>("Category removed!"), HttpStatus.OK);
    }

}

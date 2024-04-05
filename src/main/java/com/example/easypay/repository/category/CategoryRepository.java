package com.example.easypay.repository.category;

import com.example.easypay.modals.projections.CategoryProjection;
import com.example.easypay.modals.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("""
            SELECT
            c.id as id,
            c.name as name
            FROM
            Category c where c.enabled=true
            """)
    List<CategoryProjection> getAllCategories();


}

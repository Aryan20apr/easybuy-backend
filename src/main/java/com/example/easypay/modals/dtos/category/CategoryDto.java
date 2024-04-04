package com.example.easypay.modals.dtos.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Long categoryId;

   private String name;

    private String adminToken;

    private boolean enabled;;

}

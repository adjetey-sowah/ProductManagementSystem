package com.juls.lab.productmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO{

    @NotNull
    @Size(min = 1, max = 100)
    private String categoryName;
    @Size(max = 500)
    private String description;
    private Long parentId;

}

package io.dksifoua.eshop.catalog.mapper;

import io.dksifoua.eshop.catalog.dto.CategoryDTO;
import io.dksifoua.eshop.catalog.entity.Category;

public class CategoryMapper {

    public static Category from(CategoryDTO categoryDTO) {
        return Category
                .builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .createdDate(categoryDTO.getCreatedDate())
                .lastUpdatedDate(categoryDTO.getLastUpdatedDate())
                .build();
    }

    public static CategoryDTO from(Category category) {
        return CategoryDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdDate(category.getCreatedDate())
                .lastUpdatedDate(category.getLastUpdatedDate())
                .build();
    }
}

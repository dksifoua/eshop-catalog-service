package io.dksifoua.eshop.catalog.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CategoryDTO {
    private Integer id;
    private String name;
    private String description;
    protected LocalDateTime createdDate;
    protected LocalDateTime lastUpdatedDate;
}

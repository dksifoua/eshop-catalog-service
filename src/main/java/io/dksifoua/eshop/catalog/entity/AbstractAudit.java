package io.dksifoua.eshop.catalog.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@SuperBuilder
@Data
public class AbstractAudit {

    @Column("CREATED_DATE")
    protected LocalDateTime createdDate;

    @Column("LAST_UPDATED_DATE")
    protected LocalDateTime lastUpdatedDate;
}

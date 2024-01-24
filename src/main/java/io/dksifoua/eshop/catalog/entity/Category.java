package io.dksifoua.eshop.catalog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@Table("CATEGORY")
public class Category extends AbstractAudit {

    @Id
    @Column("ID")
    private Integer id;

    @Column("NAME")
    private String name;

    @Column("DESCRIPTION")
    private String description;
}

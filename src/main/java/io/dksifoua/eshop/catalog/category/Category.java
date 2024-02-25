package io.dksifoua.eshop.catalog.category;

import io.dksifoua.eshop.catalog.utility.Active;
import io.dksifoua.eshop.catalog.utility.Audit;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document(collection = "categories")
public class Category {

    @Id
    private String id;
    private String name;
    private String description;
    private String parentId;
    private Active active;
    private Audit audit;
}

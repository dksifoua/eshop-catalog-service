package io.dksifoua.eshop.catalog.entity;

import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.time.LocalDateTime;

@Component
public class AuditBeforeConvertCallback<T extends AbstractAudit> implements BeforeConvertCallback<T> {

    @Override
    public @NonNull Publisher<T> onBeforeConvert(T entity, @NonNull SqlIdentifier table) {
        System.out.println(entity.getCreatedDate() + " " + entity.getLastUpdatedDate());
        LocalDateTime now = LocalDateTime.now();
        if (entity.getCreatedDate() == null) {
            entity.setCreatedDate(now);
        }
        entity.setLastUpdatedDate(now);
        System.out.println(entity.getCreatedDate() + " " + entity.getLastUpdatedDate());
        return Mono.just(entity);
    }
}

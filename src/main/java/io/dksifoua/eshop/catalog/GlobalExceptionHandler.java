package io.dksifoua.eshop.catalog;

import com.mongodb.MongoWriteException;
import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@Order(-2)
public class GlobalExceptionHandler implements WebExceptionHandler {

    public Mono<Void> handleDuplicateKeyException(ServerWebExchange exchange, MongoWriteException cause) {
        String type = cause.getError().getCategory().name();
        Matcher matcher = Pattern.compile("dup key: \\{.+}'").matcher(cause.getLocalizedMessage());
        String payload = "Duplicate entry!!";
        if (matcher.find()) {
            payload = matcher.group(0)
                    .replace("dup key: ", "")
                    .replace("'", "");
        }
        byte[] responseBody = String.format("{'type': %s, 'payload': %s}", type, payload).getBytes();
        exchange.getResponse().setStatusCode(HttpStatus.CONFLICT);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(responseBody)));
    }

    @Override
    public @Nonnull Mono<Void> handle(@Nonnull ServerWebExchange exchange, Throwable ex) {
        return handleDuplicateKeyException(exchange, (MongoWriteException) ex.getCause());
    }
}

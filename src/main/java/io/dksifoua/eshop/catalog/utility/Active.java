package io.dksifoua.eshop.catalog.utility;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Active {

    private LocalDateTime from;
    private LocalDateTime to;
}

package org.example.shixi.tables.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseQuery {
    @Min(value =1,message="当前页码不能小于1")
    @NotNull(message = "当前页码不能为空")
    @Schema(description = "当前页码")
    private Integer current;

    @Max(value =40,message = "每页条数不能大于40")
    @Min(value = 1,message = "每页显示条数不能小于1")
    @NotNull(message = "每页显示条数不能为空")
    @Schema(description = "每页显示条数")
    private Integer size;
}

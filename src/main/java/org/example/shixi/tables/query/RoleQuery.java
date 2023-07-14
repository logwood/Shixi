package org.example.shixi.tables.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询条件")
public class RoleQuery extends BaseQuery{
    @Size(max = 20, message = "角色长度范围2-20")
    @Schema(description = "角色名称")
    private String name;
}

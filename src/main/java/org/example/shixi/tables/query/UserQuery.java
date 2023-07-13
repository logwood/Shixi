package org.example.shixi.tables.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询条件")
@Data
public class UserQuery extends BaseQuery{
    @Size(max = 30, message = "用户名长度不能超过30")
    @Schema(description = "用户名")
    private String username;

    @Size(max = 20, message = "姓名长度不能超过20")
    @Schema(description = "姓名")
    private String name;

    @Schema(description = "角色主键")
    private String roleId;
}

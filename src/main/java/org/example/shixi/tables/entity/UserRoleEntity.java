package org.example.shixi.tables.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("user_role")
@Schema(name = "UserRoleEntity",description = "用户角色关系")
public class UserRoleEntity {
    @Schema(description = "用户主键")
    private Integer userId;
    @Schema(description = "角色主键")
    private Integer roleId;

}

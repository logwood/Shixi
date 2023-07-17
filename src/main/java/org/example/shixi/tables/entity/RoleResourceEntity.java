package org.example.shixi.tables.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@TableName("role_rescource")
@Schema(name="RoleResourceEntity",description = "角色资源关系")
@EqualsAndHashCode(callSuper = true)
public class RoleResourceEntity extends BaseEntity{
    @Schema(description = "角色主键")
    @Getter private Integer roleId;
    @Schema(description = "资源主键")
    @Getter private Integer resourceId;
    @Schema(description = "post|新增,put|修改,delete|删除,page|查询等")
    @Getter private String authority;

}
package org.example.shixi.tables.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shixi.tables.entity.RoleEntity;
import org.example.shixi.tables.entity.RoleResourceEntity;
import org.example.shixi.tables.entity.UserRoleEntity;

import java.util.List;

@Schema(description = "角色资源信息")
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDTO extends RoleEntity {

    @Schema(description = "修改标识，0(角色资源列表配置)，1(用户角色列表配置)")
    private Integer updateFlag;

    @Schema(description = "角色列表配置")
    private List<RoleResourceEntity> roleResourceEntityList;

    @Schema(description = "角色用户列表配置")
    private List<UserRoleEntity> userRoleEntityList;

}

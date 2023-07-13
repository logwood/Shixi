package org.example.shixi.tables.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shixi.tables.entity.ResourceEntity;

import java.util.List;

@Schema(description = "资源树")
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceTreeDTO extends ResourceEntity {

    @Schema(description = "页面权限列表")
    private String authority;

    @Schema(description = "子树")
    private List<ResourceTreeDTO> children;

}

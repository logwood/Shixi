package org.example.shixi.tables.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@TableName("resource")
@Schema(name="ResourceEntity",description = "资源信息")
@EqualsAndHashCode(callSuper = true)
public class ResourceEntity extends BaseEntity{
    @Schema(description = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @Schema(description = "父亲的主键")
    private Integer parentId;
    @Size(min=2,max=20,message = "资源名称长度范围2-20")
    @NotBlank(message = "资源名称不能为空")
    @Schema(description = "资源名称")
    private String name;
    @Size(min = 2, max = 100, message = "路径长度范围2-100")
    @Schema(description = "路径")
    private String path;

    @Size(min = 2, max = 100, message = "资源地址长度范围2-100")
    @Schema(description = "资源地址")
    private String uri;

    @Schema(description = "post|新增,put|修改,delete|删除,page|查询等")
    private String authority;

    @Schema(description = "0(未删除)，1(已删除)")
    private String deleted;
}

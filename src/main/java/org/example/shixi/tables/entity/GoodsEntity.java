package org.example.shixi.tables.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("goods")
@Schema(name="ResourceEntity",description = "资源信息")
@EqualsAndHashCode(callSuper = true)
public class GoodsEntity extends BaseEntity{
    @Schema(description = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @Schema(description = "父亲的主键")
    private Integer parentId;
    @Size(min=2,max=20,message = "资源名称长度范围2-20")
    @NotBlank(message = "资源名称不能为空")
    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "0(未删除)，1(已删除)")
    private String deleted;
}

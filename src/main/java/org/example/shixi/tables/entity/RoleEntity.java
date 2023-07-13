package org.example.shixi.tables.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//EqualsAndHashCode是Data的平替，在父类的支持上显著好于Data
@EqualsAndHashCode(callSuper = true)
@TableName("role")
@Schema(name = "RoleEntity" )
public class RoleEntity extends BaseEntity{
    @Schema(description = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @Size(min = 2, max = 20, message = "角色长度范围2-20")
    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "0(未删除)，1(已删除)")
    private String deleted;
}

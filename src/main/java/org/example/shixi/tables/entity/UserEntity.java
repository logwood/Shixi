package org.example.shixi.tables.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user")
@Schema(name = "UserEntity",description = "用户信息")
public class UserEntity extends BaseEntity{
    @ExcelProperty({"登录信息","用户id"})
    @Schema(description = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @ExcelProperty({"登录信息","用户名称"})
    @NotBlank(message = "用户名不为空")
    @Size(min =4,max=20,message = "用户名长度范围4-20")
    private String username;
    @ExcelProperty({"登录信息","用户密码"})
    @NotBlank(message = "密码不为空")
    @Schema(description = "密码")
    private String password;
    @ExcelProperty({"单值属性","locked属性"})
    @Size(max = 1,message = "locked属性只能为0-1")
    @Schema(description = "0(未锁定)，1(已锁定)")
    private String locked;
    @ExcelProperty({"单值属性","deleted属性"})
    @Size(max = 1,message = "deleted属性只能为0-1")
    @Schema(description = "0(未删除)，1(已删除)")
    private String deleted;
    @ExcelProperty("用户姓名")
    @NotBlank(message = "姓名不能为空")
    @Size(min = 2, max = 20, message = "姓名长度范围2-20")
    @Schema(description = "姓名")
    private String name;


}

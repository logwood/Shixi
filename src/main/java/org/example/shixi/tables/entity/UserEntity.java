package org.example.shixi.tables.entity;

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
    @Schema(description = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "用户名不为空")
    @Size(min =4,max=20,message = "用户名长度范围4-20")
    private String username;
    @NotBlank(message = "密码不为空")
    @Schema(description = "密码")
    private String password;
    @Size(max = 1,message = "locked属性只能为0-1")
    @Schema(description = "0(未锁定)，1(已锁定)")
    private String locked;
    @Size(max = 1,message = "deleted属性只能为0-1")
    @Schema(description = "0(未删除)，1(已删除)")
    private String deleted;
    @NotBlank(message = "姓名不能为空")
    @Size(min = 2, max = 20, message = "姓名长度范围2-20")
    @Schema(description = "姓名")
    private String name;


}

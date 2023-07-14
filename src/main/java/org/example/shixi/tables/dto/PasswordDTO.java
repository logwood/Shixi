package org.example.shixi.tables.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Schema(description = "密码信息")
@Data
public class PasswordDTO {
    @Size(max=90,message="当前密码超长")
    @NotBlank(message = "当前密码不为空")
    @Schema(description = "当前密码")
    private String password;
    @Size(max = 90, message = "新密码超长")
    @NotBlank(message = "新密码不能为空")
    @Schema(description = "新密码")
    private String newPassword;
}

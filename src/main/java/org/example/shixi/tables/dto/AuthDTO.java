package org.example.shixi.tables.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "认证信息")
@Data
public class AuthDTO {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "token")
    private String token;
    @Schema(description = "资源")
    private List<ResourceTreeDTO> resourceTreeDTOList;
}

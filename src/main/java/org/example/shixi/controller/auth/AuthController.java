package org.example.shixi.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.shixi.constant.MessageConstant;
import org.example.shixi.controller.RestResponse;
import org.example.shixi.service.auth.AuthenticationService;
import org.example.shixi.tables.dto.LoginDTO;
import org.example.shixi.tables.dto.PasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证鉴权")
@RestController
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @Operation(summary="登陆")
    @PostMapping("/login")
    public RestResponse<Void> login(@RequestBody @Validated LoginDTO loginDTO) {
        return RestResponse.ok(MessageConstant.LOGIN_OK);
    }
    @Operation(summary="修改密码")
    @PostMapping("/changePassword")
    public RestResponse<Boolean> changePassword(@RequestBody @Validated PasswordDTO passwordDTO) {
        return RestResponse.updateResponse(authenticationService.changePassword(passwordDTO));
    }
}

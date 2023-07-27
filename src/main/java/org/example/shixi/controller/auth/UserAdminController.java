package org.example.shixi.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.shixi.controller.RestResponse;
import org.example.shixi.service.base.UserInfoService;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/balance")
@Tag(name="负载均衡接口")
public class UserAdminController {

    @Resource
    UserInfoService userInfoService;

    @GetMapping("/sayHi")
    @Operation(summary = "你好")
    public RestResponse<String> sayHello(){
        return RestResponse.ok(userInfoService.sayHi());
    }

    @Operation(summary = "不好")
    @PostMapping(value = "/pictures",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RestResponse<Boolean> sendpictures(@RequestPart("file") MultipartFile[] files){
        Assert.notNull(files,"文件不能为空");
        return RestResponse.queryResponse(userInfoService.upload(files));
    }
}
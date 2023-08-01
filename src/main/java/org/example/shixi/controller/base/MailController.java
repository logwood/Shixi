package org.example.shixi.controller.base;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shixi.controller.RestResponse;
import org.example.shixi.service.base.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/email")
@RequiredArgsConstructor
@Tag(name = "邮箱接口")
public class MailController {
    @Autowired
    private PostService service;
    @GetMapping("/sendSimpleMail")
    @Operation(summary = "邮箱发送码")
    public RestResponse<String> sendSimpleMail() {
        return service.sendSimpleMail(
                "9990928@qq.com",
                "欢迎关注微信公众号「武培轩」",
                "感谢你这么可爱，这么优秀，还来关注我，关注了就要一起成长哦~~回复【资料】领取优质资源！");
    }
    @PostMapping(value = "/sendMineMail")
    @Operation(summary = "发送特殊码")
    public ResponseEntity<RestResponse<String>> sendMineMail(@RequestPart("file") MultipartFile[] files) {
        return service.sendMimeMail(
                files,
                "9990928@qq.com",
                "欢迎关注微信公众号「武培轩」",
                "<h3>感谢你这么可爱，这么优秀，还来关注我，关注了就要一起成长哦~~</h3><br>" +
                        "回复【资料】领取优质资源！<br>" +
                        "<img src='cid:logo'>");

    }

}

package org.example.shixi.service.base;

import org.example.shixi.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Component
@FeignClient(value = "CLOUD-EUREKA-PROVIDE-USERINFO-SERVICE",configuration = OpenFeignConfig.class)
public interface UserInfoService {

    @GetMapping("/balance/sayHi")
    String sayHi();
    @PostMapping(value = "/balance/pictures",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    boolean upload(@RequestPart("file") MultipartFile[] files);

}
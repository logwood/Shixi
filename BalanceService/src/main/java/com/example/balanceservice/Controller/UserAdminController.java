package com.example.balanceservice.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/balance")
public class UserAdminController {


    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/sayHi")
    public String sayHi(){
        String number= UUID.randomUUID().toString();
        return "service,port:"+serverPort+",number:"+ number;
    }
    @PostMapping(value = "/pictures",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean upload(@RequestPart("file") MultipartFile[] files)
            throws Exception{
        for(MultipartFile file:files){
            if(!file.isEmpty() && file.getSize() > 0){
                String filename =file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                file.transferTo(new File("D:\\"+uuid+filename));

            }else {
                return false;
            }
        }
        return true;
    }
}
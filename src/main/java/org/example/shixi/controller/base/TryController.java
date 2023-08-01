package org.example.shixi.controller.base;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthFeishuRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.example.shixi.controller.RestResponse;
import org.example.shixi.service.base.UserParseService;
import org.example.shixi.service.base.UserService;
import org.example.shixi.tables.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Tag(name = "简单尝试")
@RestController
@RequestMapping("/trying")
public class TryController {
    // 创建授权request
    @Autowired
    UserParseService userParseService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @GetMapping
    @Operation(summary = "实现树形结构")
    public RestResponse<String> callgitee(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = new AuthFeishuRequest(AuthConfig.builder()
            .clientId("cli_a44a68a897fbd013")
            .clientSecret("5na1uIPeVGgtpe0pG51UNqgH8dCeuVKQ")
            .redirectUri("http://127.0.0.1:9999/trying/outh")
            .build());
        // 生成授权页面
        // 授权登录后会返回code（auth_code（仅限支付宝））、state，1.8.0版本后，可以用AuthCallback类作为回调接口的参数
        // 注：JustAuth默认保存state的时效为3分钟，3分钟内未使用则会自动清除过期的state
        log.info(authRequest.authorize(AuthStateUtils.createState()));
        return RestResponse.ok(authRequest.authorize(AuthStateUtils.createState()));
        //response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }
    @GetMapping("/outh")
    public Object callup(AuthCallback callback){
        log.info(" callback params：" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = new AuthFeishuRequest(AuthConfig.builder()
                .clientId("cli_a44a68a897fbd013")
                .clientSecret("5na1uIPeVGgtpe0pG51UNqgH8dCeuVKQ")
                .redirectUri("http://127.0.0.1:9999/trying/outh")
                .build());
        AuthResponse<AuthUser> response = authRequest.login(callback);
        log.info(JSONObject.toJSONString(response));

        if (response.ok()) {
            userParseService.save(response.getData());
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(response.getData().getUsername());

            userEntity.setName(response.getData().getNickname());
            userService.add(userEntity);
            return RestResponse.ok("发送成功");
        }

        Map<String, Object> map = new HashMap<>(1);
        map.put("errorMsg", response.getMsg());
        return RestResponse.fail("error:"+map);
    }
    @RequestMapping("/revoke/{source}/{uuid}")
    @ResponseBody
    public RestResponse revokeAuth(@PathVariable("source") String source, @PathVariable("uuid") String uuid) throws IOException {
        AuthRequest authRequest = new AuthFeishuRequest(AuthConfig.builder()
                .clientId("cli_a44a68a897fbd013")
                .clientSecret("5na1uIPeVGgtpe0pG51UNqgH8dCeuVKQ")
                .redirectUri("http://127.0.0.1:9999/trying/outh")
                .build());

        AuthUser user = userParseService.getByUuid(uuid);
        if (null == user) {
            return RestResponse.fail("用户不存在");
        }
        AuthResponse<AuthToken> response = null;
        try {
            response = authRequest.revoke(user.getToken());
            if (response.ok()) {
                userParseService.remove(user.getUuid());
                return RestResponse.ok("用户 [" + user.getUsername() + "] 的 授权状态 已收回！");
            }
            return RestResponse.fail("用户 [" + user.getUsername() + "] 的 授权状态 收回失败！" + response.getMsg());
        } catch (AuthException e) {
            return RestResponse.badRequest(e.getErrorMsg());
        }
    }

}

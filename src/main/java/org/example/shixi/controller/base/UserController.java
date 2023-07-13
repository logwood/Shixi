package org.example.shixi.controller.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.shixi.controller.RestResponse;
import org.example.shixi.service.auth.AuthenticationService;
import org.example.shixi.service.base.UserService;
import org.example.shixi.tables.entity.UserEntity;
import org.example.shixi.tables.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息 前端控制器
 * @author liulinchuan
 * @since 2023/1/11 18:25
 */
@Tag(name = "用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary="新增")
    @PostMapping
    public RestResponse<Boolean> add(@RequestBody @Validated UserEntity userEntity) {
        log.info("/user#post:{}", userEntity);
        return RestResponse.addResponse(userService.add(userEntity));
    }

    @Operation(summary="删除")
    @DeleteMapping
    public RestResponse<Boolean> delete(@RequestParam Integer id) {
        log.info("/user#delete:{}", id);
        return RestResponse.deleteResponse(userService.removeById(id));
    }

    @Operation(summary="修改")
    @PutMapping
    public RestResponse<Boolean> update(@RequestBody @Validated UserEntity userEntity) {
        log.info("/user#put:{}", userEntity);
        return RestResponse.updateResponse(userService.update(userEntity));
    }

    @Operation(summary="分页查询")
    @PostMapping("/page")
    public RestResponse<IPage<UserEntity>> page(@RequestBody @Validated UserQuery query) {
        log.info("/user/page#post:{}", query);
        return RestResponse.queryResponse(userService.page(query));
    }

}

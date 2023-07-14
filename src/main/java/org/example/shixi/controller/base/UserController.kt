package org.example.shixi.controller.base

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.extern.slf4j.Slf4j
import org.example.shixi.controller.RestResponse
import org.example.shixi.service.auth.AuthenticationService
import org.example.shixi.service.base.UserService
import org.example.shixi.tables.entity.UserEntity
import org.example.shixi.tables.query.UserQuery
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(name = "用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
class UserController {

    private val log = LoggerFactory.getLogger(ResourceController::class.java)
    //Postmapping默认是("/post")
    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val authenticationService: AuthenticationService? = null

    @Operation(summary = "新增")
    @PostMapping
    fun add(@RequestBody @Validated userEntity: UserEntity?): RestResponse<Boolean?>? {
        log.info("/user#post:{}", userEntity)
        return RestResponse.addResponse(userService!!.add(userEntity))
    }

    @Operation(summary = "删除")
    @DeleteMapping
    fun delete(@RequestParam id: Int?): RestResponse<Boolean?>? {
        log.info("/user#delete:{}", id)
        return RestResponse.deleteResponse(userService!!.removeById(id))
    }

    @Operation(summary = "修改")
    @PutMapping
    fun update(@RequestBody @Validated userEntity: UserEntity?): RestResponse<Boolean?>? {
        log.info("/user#put:{}", userEntity)
        return RestResponse.updateResponse(userService!!.update(userEntity))
    }

    @Operation(summary = "分页查询")
    @PostMapping("/page")
    fun page(@RequestBody @Validated query: UserQuery?): RestResponse<IPage<UserEntity?>?>? {
        log.info("/user/page#post:{}", query)
        return RestResponse.queryResponse(userService!!.page(query))
    }
}
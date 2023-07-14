package org.example.shixi.controller.base

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.extern.slf4j.Slf4j
import org.example.shixi.controller.RestResponse
import org.example.shixi.service.base.RoleService
import org.example.shixi.tables.dto.RoleDTO
import org.example.shixi.tables.entity.RoleEntity
import org.example.shixi.tables.query.RoleQuery
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(name = "角色管理")
@Slf4j
@RestController
@RequestMapping("/role")
class RoleController {
    @Autowired
    private val roleService: RoleService? = null

    private val log = LoggerFactory.getLogger(ResourceController::class.java)

    @Operation(summary = "新增")
    @PostMapping
    fun add(@RequestBody @Validated roleDTO:RoleDTO?):RestResponse<Boolean>{
        log.info("/role#post:{}",roleDTO)
        return RestResponse.addResponse(roleService?.add(roleDTO) ?: false)
    }
    @Operation(summary = "删除")
    @DeleteMapping
    fun delete(@RequestBody @Validated id:Int):RestResponse<Boolean>{
        log.info("/role#delete:{}", id)
        return RestResponse.deleteResponse(roleService!!.delete(id))
    }
    @Operation(summary = "修改")
    @PutMapping
    fun update(@RequestBody @Validated roleDTO:RoleDTO):RestResponse<Boolean>{
        log.info("/role#put:{}",roleDTO)
        return RestResponse.updateResponse(roleService?.update(roleDTO) ?:false)
    }
    @Operation(summary = "分页查询")
    @PostMapping("/page")
    fun page(@RequestBody @Validated roleQuery: RoleQuery):RestResponse<IPage<RoleEntity>> {
        log.info("/role#put:{}",roleQuery)
        return RestResponse.queryResponse(roleService?.page(roleQuery))
    }
}
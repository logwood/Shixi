package org.example.shixi.controller.base

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.extern.slf4j.Slf4j
import org.example.shixi.controller.RestResponse
import org.example.shixi.service.auth.AuthenticationService
import org.example.shixi.service.base.ResourceService
import org.example.shixi.service.base.RoleResourceService
import org.example.shixi.tables.UserInfo
import org.example.shixi.tables.dto.ResourceTreeDTO
import org.example.shixi.tables.entity.ResourceEntity
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Slf4j
@Tag(name = "资源管理")
@RestController
@RequestMapping("/resource")
class ResourceController {
    @Autowired
    private val resourceService: ResourceService? = null

    @Autowired
    private val authenticationService: AuthenticationService? = null
    @Autowired
    private val roleResourceService: RoleResourceService? = null

    private val log = LoggerFactory.getLogger(ResourceController::class.java)
    @Operation(summary = "新增")
    @PostMapping
    fun add(@RequestBody @Validated resourceEntity: ResourceEntity):
            RestResponse<Boolean>{
        log.info("/resource#post:{}",resourceEntity)
        return RestResponse.addResponse(resourceService!!.add(resourceEntity))
    }
    @Operation(summary = "删除")
    @DeleteMapping
    fun delete(@RequestParam id: Int):RestResponse<Boolean>{
        log.info("resource#delete:{}",id)
        return RestResponse.deleteResponse(resourceService!!.delete(id))
    }
    @Operation(summary = "修改")
    @PutMapping
    fun update(@RequestBody @Validated resourceEntity:ResourceEntity):RestResponse<Boolean>{
        log.info("/resource#put:{}",resourceEntity)
        return RestResponse.updateResponse(resourceService!!.updateById(resourceEntity))
    }
    @Operation(summary = "资源树")
    @GetMapping
    fun tree():RestResponse<List<ResourceTreeDTO>>{
        val authentication = SecurityContextHolder.getContext().authentication
        val userInfo = authentication.principal as UserInfo
        log.info("/resource#get")
        return RestResponse.queryResponse(resourceService!!.tree(userInfo.roleIdList))
    }
    @Operation(summary = "角色资源列表")
    @GetMapping("/list")
    fun list(@RequestParam roleId:Int):RestResponse<List<ResourceEntity>>{
        log.info("/resource/list#get:{}",roleId)
        val roleIdList: MutableList<Int> = ArrayList()
        roleIdList += roleId
        return RestResponse.queryResponse(roleResourceService!!.listResource(roleIdList))
    }
}
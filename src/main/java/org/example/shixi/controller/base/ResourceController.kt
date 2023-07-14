package org.example.shixi.controller.base

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.extern.slf4j.Slf4j
import org.example.shixi.controller.RestResponse
import org.example.shixi.service.base.ResourceService
import org.example.shixi.service.base.RoleResourseService
import org.example.shixi.tables.entity.ResourceEntity
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@Tag(name = "资源管理")
@RestController
@RequestMapping("/resource")
class ResourceController {
    @Autowired
    private val resourceService: ResourceService? = null

    @Autowired
    private val roleResourceService: RoleResourseService? = null

    private val log = LoggerFactory.getLogger(ResourceController::class.java)
    @Operation(summary = "新增")
    @PostMapping
    fun add(@RequestBody @Validated resourceEntity: ResourceEntity):
            RestResponse<Boolean>{
        log.info("/resource#post:{}",resourceEntity)
        return RestResponse.addResponse(resourceService!!.add(resourceEntity))
    }
}
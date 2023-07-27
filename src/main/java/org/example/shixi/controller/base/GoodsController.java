package org.example.shixi.controller.base;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.shixi.controller.RestResponse;
import org.example.shixi.service.base.GoodsService;
import org.example.shixi.tables.dto.GoodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "商品列表管理")
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @GetMapping
    @Operation(summary = "实现树形结构")
    public RestResponse<List<GoodsDTO>> tree(){
        log.info("/goods#get");
        return RestResponse.queryResponse(goodsService.tree());
    }
}
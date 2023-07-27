package org.example.shixi.tables.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shixi.tables.entity.GoodsEntity;

import java.util.List;

@Schema(description = "商品信息")
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsDTO extends GoodsEntity {
    @Schema(description = "商品列表")
    private String goods;

    @Schema(description = "子树")
    private List<GoodsDTO> children;

}

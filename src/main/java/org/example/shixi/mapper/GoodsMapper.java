package org.example.shixi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.shixi.tables.entity.GoodsEntity;
import org.example.shixi.tables.entity.ResourceEntity;

@Mapper
public interface GoodsMapper extends BaseMapper<GoodsEntity> {

}

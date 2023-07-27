package org.example.shixi.service.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.shixi.mapper.GoodsMapper;
import org.example.shixi.tables.dto.GoodsDTO;
import org.example.shixi.tables.entity.GoodsEntity;
import org.example.shixi.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GoodsService extends ServiceImpl<GoodsMapper, GoodsEntity> {
    public List<GoodsDTO> tree(){
        List<GoodsEntity> EntityList=this.list();
        List<GoodsDTO>goodsDTOS= BeanUtil.INSTAMCE.copyGoodsEntity(EntityList);
        return goodsDTOS.stream().filter(goodsDTO -> Objects.isNull(goodsDTO.getGoods()))
                .peek(resourceEntity -> resourceEntity.setChildren(getChildrenTree(resourceEntity,goodsDTOS)))
                .collect(Collectors.toList());
    }
    public List<GoodsDTO> getChildrenTree(GoodsDTO goodsDTO, List<GoodsDTO>goodsDTOList){
        return goodsDTOList.stream()
                .filter(goodsDTO1 -> goodsDTO1.getParentId().equals(goodsDTO.getId()))
                .peek(goodsDTO1 -> getChildrenTree(goodsDTO1,goodsDTOList))
                .collect(Collectors.toList());
    }
}

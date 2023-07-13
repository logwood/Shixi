package org.example.shixi.util;

import org.example.shixi.tables.dto.ResourceTreeDTO;
import org.example.shixi.tables.entity.ResourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
//不用BeanUtils是因为他不好，通过这种方式可以将他效果变好。
@Mapper
public interface BeanUtil {
    BeanUtil INSTAMCE = Mappers.getMapper(BeanUtil.class);
    //获取BeanUtil的Mapper

    List<ResourceTreeDTO> copyResourceEntity(List<ResourceEntity> list);
}

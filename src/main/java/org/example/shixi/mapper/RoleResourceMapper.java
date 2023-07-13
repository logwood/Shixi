package org.example.shixi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.shixi.tables.entity.ResourceEntity;
import org.example.shixi.tables.entity.RoleResourceEntity;

import java.util.List;

@Mapper
public interface RoleResourceMapper extends BaseMapper<RoleResourceEntity> {
    List<ResourceEntity> listResource(@Param("roleIdList")List<Integer>roleIdList);
}

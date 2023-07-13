package org.example.shixi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.shixi.tables.entity.RoleEntity;
import org.example.shixi.tables.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {
    List<RoleEntity> listByUserId(Integer userId);
}

package org.example.shixi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.example.shixi.tables.entity.UserEntity;
import org.example.shixi.tables.query.UserQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    IPage<UserEntity> page(@Param("page") Page<UserEntity> page,@Param("query") UserQuery query);
}

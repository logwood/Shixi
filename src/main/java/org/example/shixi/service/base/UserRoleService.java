package org.example.shixi.service.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.shixi.constant.MessageConstant;
import org.example.shixi.mapper.UserRoleMapper;
import org.example.shixi.tables.entity.RoleEntity;
import org.example.shixi.tables.entity.UserRoleEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRoleEntity> {
    public boolean deleteByUserId(Integer userId){
        Assert.notNull(userId, MessageConstant.ID_NOT_NULL);
        Wrapper<UserRoleEntity> condition = new LambdaQueryWrapper<UserRoleEntity>()
                .eq(UserRoleEntity::getUserId,userId);
        return super.remove(condition);
    }
    public boolean deleteByRoleId(Integer roldId){
        Assert.notNull(roldId,MessageConstant.ID_NOT_NULL);
        Wrapper<UserRoleEntity> condition = new LambdaQueryWrapper<UserRoleEntity>()
                .eq(UserRoleEntity::getRoleId,roldId);
        return super.remove(condition);
    }
    public List<RoleEntity> listByUserId(Integer userId) {
        Assert.notNull(userId, MessageConstant.ID_NOT_NULL);
        return super.baseMapper.listByUserId(userId);
    }
    public boolean exists(Integer roleId) {
        Assert.notNull(roleId, MessageConstant.ID_NOT_NULL);
        return super.lambdaQuery()
                .eq(UserRoleEntity::getRoleId, roleId)
                .exists();
    }
}

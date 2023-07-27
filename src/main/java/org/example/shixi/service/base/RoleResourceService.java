package org.example.shixi.service.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.shixi.constant.MessageConstant;
import org.example.shixi.mapper.RoleResourceMapper;
import org.example.shixi.tables.entity.ResourceEntity;
import org.example.shixi.tables.entity.RoleResourceEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;


@Service
public class RoleResourceService extends ServiceImpl<RoleResourceMapper, RoleResourceEntity> {
    public boolean delete(Integer roleId){
        Assert.notNull(roleId, MessageConstant.ID_NOT_NULL);
        //roleId不能为空，为空抛出异常。
        Wrapper<RoleResourceEntity> conditions =new LambdaQueryWrapper<RoleResourceEntity>()
                .eq(RoleResourceEntity::getRoleId,roleId);
        return super.remove(conditions);
    }
    public List<ResourceEntity> listResource(List<Integer> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Collections.emptyList();
        }
        return super.baseMapper.listResource(roleIdList);
    }

    public boolean exists(Integer resourceId) {
        Assert.notNull(resourceId, MessageConstant.ID_NOT_NULL);
        return super.lambdaQuery()
                .eq(RoleResourceEntity::getResourceId, resourceId)
                .exists();
    }
}

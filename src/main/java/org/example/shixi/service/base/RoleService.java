package org.example.shixi.service.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.shixi.constant.MessageConstant;
import org.example.shixi.exception.ServiceException;
import org.example.shixi.mapper.RoleMapper;
import org.example.shixi.tables.dto.RoleDTO;
import org.example.shixi.tables.entity.RoleEntity;
import org.example.shixi.tables.entity.RoleResourceEntity;
import org.example.shixi.tables.entity.UserRoleEntity;
import org.example.shixi.tables.query.RoleQuery;
import org.example.shixi.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RoleService extends ServiceImpl<RoleMapper, RoleEntity> {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleResourseService roleResourseService;
    @Transactional(rollbackFor = Exception.class)
    public boolean add(RoleDTO roleDTO){
        Assert.notNull(roleDTO, MessageConstant.ROLE_NOT_NULL);
        if(exists(roleDTO.getName())){
            throw new ServiceException(MessageConstant.ROLE_EXISTS);
        }
        super.save(roleDTO);
        List<RoleResourceEntity> roleResourceEntityList= roleDTO.getRoleResourceEntityList();
        List<UserRoleEntity> userRoleEntityList = roleDTO.getUserRoleEntityList();
        if(!CollectionUtils.isEmpty(roleResourceEntityList)){
            roleResourceEntityList.forEach(roleResourceEntity -> roleResourceEntity.setRoleId(roleDTO.getId()));
            roleResourseService.saveBatch(roleResourceEntityList);
        }
        if(!CollectionUtils.isEmpty(userRoleEntityList)){
            userRoleEntityList.forEach(userRoleEntity -> userRoleEntity.setRoleId(roleDTO.getId()));
            userRoleService.saveBatch(userRoleEntityList);
        }
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean update(RoleDTO roleDTO){
        Assert.notNull(roleDTO, MessageConstant.ROLE_NOT_NULL);
        Integer updateFlag = roleDTO.getUpdateFlag();
        if (updateFlag == null || updateFlag < 0 || updateFlag > 2) {
            throw new ServiceException(MessageConstant.PARAM_ERROR);
        }
        Integer roleId = roleDTO.getId();
        RoleEntity roleEntityExist =get(roleDTO.getName());
        if(roleEntityExist != null && !roleEntityExist.getId().equals(roleId)){
            throw new ServiceException(MessageConstant.ROLE_EXISTS);
        }
        super.updateById(BeanUtil.INSTAMCE.copy(roleDTO));
        if (updateFlag == 0){
            roleResourseService.delete(roleId);
            roleResourseService.saveBatch(roleDTO.getRoleResourceEntityList());
        }
        else if(updateFlag == 1){
            userRoleService.deleteByRoleId(roleId);
            userRoleService.saveBatch(roleDTO.getUserRoleEntityList());
        } else {
            roleResourseService.delete(roleId);
            userRoleService.deleteByRoleId(roleId);
            roleResourseService.saveBatch(roleDTO.getRoleResourceEntityList());
            userRoleService.saveBatch(roleDTO.getUserRoleEntityList());

        }
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer id){
        Assert.notNull(id, MessageConstant.ROLE_NOT_NULL);
        if(userRoleService.exists(id)){
            throw new ServiceException(MessageConstant.ROLE_EXISTS);
        }
        roleResourseService.delete(id);
        super.removeById(id);
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    public RoleEntity get(String roleName){
        Assert.notNull(roleName,MessageConstant.ROLE_NAME_NOT_NULL);
        return super.lambdaQuery()
                .eq(RoleEntity::getName,roleName)
                .one();
    }
    public IPage<RoleEntity> page(RoleQuery query) {
        Assert.notNull(query, MessageConstant.QUERY_CONDITION_NOT_NULL);
        return super.lambdaQuery()
                .like(StringUtils.hasText(query.getName()), RoleEntity::getName, query.getName())
                .page(Page.of(query.getCurrent(), query.getSize()));
    }
    public boolean exists(String roleName){
        Assert.notNull(roleName, MessageConstant.ROLE_NOT_NULL);
        return super.lambdaQuery()
                .eq(RoleEntity::getName,roleName)
                .exists();
    }
}

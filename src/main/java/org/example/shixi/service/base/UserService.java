package org.example.shixi.service.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.shixi.constant.MessageConstant;
import org.example.shixi.exception.ServiceException;
import org.example.shixi.mapper.UserMapper;
import org.example.shixi.tables.entity.UserEntity;
import org.example.shixi.tables.query.UserQuery;
import org.example.shixi.util.CryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

//使用分层结构，最好使用@Service @Controller进行标识。
@Service
public class UserService extends ServiceImpl<UserMapper,UserEntity> {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;
    public boolean add(UserEntity userEntity){
        Assert.notNull(userEntity, MessageConstant.USER_NOT_NULL);
        if(exists(userEntity.getUsername())){
            throw new ServiceException(MessageConstant.USER_EXISTS);
        }
        userEntity.setPassword(passwordEncoder.encode("user@Thinking"));
        return super.save(userEntity);
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer id){
        Assert.notNull(id,MessageConstant.ID_NOT_NULL);
        userRoleService.deleteByUserId(id);
        super.removeById(id);
        return true;
    }

    public IPage<UserEntity> page(UserQuery query){
        Assert.notNull(query,MessageConstant.QUERY_CONDITION_NOT_NULL);
        if(StringUtils.hasText(query.getRoleId())){
            return super.baseMapper.page(Page.of(query.getCurrent(), query.getSize()),query);
        }
        return this.lambdaQuerySelect()
                .like(StringUtils.hasText(query.getUsername()),UserEntity::getUsername,query.getUsername())
                .like(StringUtils.hasText(query.getName()),UserEntity::getName,query.getName())
                .page(Page.of(query.getCurrent(), query.getSize()));
    }

    private LambdaQueryChainWrapper<UserEntity> lambdaQuerySelect() {
        return super.lambdaQuery()
                .select(UserEntity::getId,
                        UserEntity::getUsername,
                        UserEntity::getName,
                        UserEntity::getLocked,
                        UserEntity::getDeleted,
                        UserEntity::getCreateTime,
                        UserEntity::getUpdateTime);
    }

    public boolean update(UserEntity userEntity){
        Assert.notNull(userEntity,MessageConstant.USER_NOT_NULL);
        UserEntity userEntityExists = get(userEntity.getUsername());
        if(userEntityExists != null && !userEntityExists.getId().equals(userEntity.getId())){
            throw new ServiceException(MessageConstant.USER_EXISTS);
        };
        userEntity.setPassword(null);
        return super.updateById(userEntity);
    }


    public UserEntity get(String username) {
        Assert.hasText(username, MessageConstant.USERNAME_NOT_BLANK);
        return super.lambdaQuery()
                .eq(UserEntity::getUsername, username)
                .one();
    }
    private boolean exists(String username) {
        Assert.hasText(username,MessageConstant.USER_EXISTS);
        return super.lambdaQuery()
                .eq(UserEntity::getUsername,username)
                .exists();
    }
}

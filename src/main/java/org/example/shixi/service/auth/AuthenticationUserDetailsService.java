package org.example.shixi.service.auth;

import org.example.shixi.constant.MessageConstant;
import org.example.shixi.service.base.UserRoleService;
import org.example.shixi.service.base.UserService;
import org.example.shixi.tables.UserInfo;
import org.example.shixi.tables.entity.RoleEntity;
import org.example.shixi.tables.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthenticationUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 重写loadUserByUserName
     * @param username 用户名称
     * @return UserDetails 验证信息
     * @throws UsernameNotFoundException 没找到用户名
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.get(username);
        //通过UserService获得Username
        if(Objects.isNull(userEntity)){
            throw new UsernameNotFoundException(MessageConstant.USER_NOT_EXISTS);
        }
        List<RoleEntity> roleEntityList = userRoleService
                .listByUserId(userEntity.getId());
        List<Integer> roleIdLost = new ArrayList<>(roleEntityList
                .size());
        roleEntityList.forEach(
                roleEntity -> roleIdLost.add(roleEntity.getId())
        );
        List<GrantedAuthority> authorities = new ArrayList<>(roleIdLost.size());
        roleIdLost.forEach(roleId -> authorities.add(new SimpleGrantedAuthority("ROLE_"+roleId)));
        UserInfo userInfo =new UserInfo(userEntity.getUsername(),userEntity.getPassword(),authorities);
        userInfo.setUserId(userEntity.getId());
        userInfo.setName(userEntity.getName());
        userInfo.setRoleIdList(roleIdLost);
        return userInfo;
    }
}

package org.example.shixi.service.auth;

import org.example.shixi.constant.MessageConstant;
import org.example.shixi.exception.ServiceException;
import org.example.shixi.service.base.ResourceService;
import org.example.shixi.service.base.UserService;
import org.example.shixi.tables.UserInfo;
import org.example.shixi.tables.dto.AuthDTO;
import org.example.shixi.tables.dto.PasswordDTO;
import org.example.shixi.tables.entity.UserEntity;
import org.example.shixi.util.JWTUtil;
import org.example.shixi.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 获取认证信息
     * @author wangsang
     * @since 2023/7/16 14:15
     * @return 认证信息
     */
    public AuthDTO authInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername(userInfo.getUsername());
        authDTO.setName(userInfo.getName());
        authDTO.setToken(JWTUtil.encodeToken(authentication));
        if ("root".equals(userInfo.getUsername())) {
            authDTO.setResourceTreeDTOList(resourceService.tree());
        } else {
            authDTO.setResourceTreeDTOList(resourceService.tree(userInfo.getRoleIdList()));
        }
        return authDTO;
    }

    /**
     * 修改密码
     * @author wangsang
     * @since 2023/7/26 11:03
     * @param passwordDTO 密码信息
     * @return 是否修改成功
     */
    public boolean changePassword(PasswordDTO passwordDTO) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(userInfo.getUsername(), passwordDTO.getPassword());
        try {
            authenticationManager.authenticate(authRequest);
        }catch (BadCredentialsException e) {
            throw new ServiceException(MessageConstant.PASSWORD_ERROR);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userInfo.getUserId());
        userEntity.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        boolean result = userService.updateById(userEntity);
        userEntity.setPassword(null);
        return result;
    }
}

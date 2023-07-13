package org.example.shixi.util;

import org.example.shixi.tables.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserInfoUtil {

    private UserInfoUtil() {
    }

    public static UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserInfo) authentication.getPrincipal();
    }

}
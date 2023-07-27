package org.example.shixi.service.auth;

import org.springframework.util.PathMatcher;
import org.springframework.util.AntPathMatcher;
import org.example.shixi.properties.WhiteListProperties;
import org.example.shixi.service.base.RoleResourceService;
import org.example.shixi.tables.UserInfo;
import org.example.shixi.tables.entity.ResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class AuthorizationAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private WhiteListProperties whiteListProperties;
    //AntPathMatcher是java中判断路径是否匹配的重要工具，Spring中的路径匹配都是使用的Ant风格。
    private final PathMatcher pathMatcher = new AntPathMatcher();
    @Override
    public boolean supports(ConfigAttribute attribute){return true;}
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute>attributes){
     String requestUrl = object.getHttpRequest().getServletPath();
     if(isWhite(requestUrl)){
         return ACCESS_ABSTAIN;
     }
     //getPrincipal可以获取用户的各种信息。
        UserInfo userInfo=(UserInfo)authentication.getPrincipal();
        if("root".equals(userInfo.getUsername())){
            return ACCESS_GRANTED;
        }
        String[] pathArr = requestUrl.split("/");
        List<Integer> roleIdList = userInfo.getRoleIdList();
        List<ResourceEntity> resourceEntityList = roleResourceService.listResource(roleIdList);
        //利用object.getHttpRequest().getMethod()匹配鉴权，
        for(ResourceEntity resourceEntity:resourceEntityList){
            if(pathMatcher.match(resourceEntity.getUri(), requestUrl)){
                if(pathArr.length<=2&&resourceEntity.getAuthority().contains(object.getHttpRequest().getMethod().toLowerCase())){
                    return ACCESS_GRANTED;
                }
                if(resourceEntity.getAuthority().contains(pathArr[pathArr.length-1])){
                    return ACCESS_GRANTED;
                }
            }
        }
        return ACCESS_DENIED;
    }
    private boolean isWhite(String uri) {
        return whiteListProperties.getWhiteList().stream().anyMatch(s -> pathMatcher.match(s,uri));
    }
}

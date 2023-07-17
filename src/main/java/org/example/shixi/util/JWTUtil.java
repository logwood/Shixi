package org.example.shixi.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.el.parser.Token;
import org.example.shixi.tables.UserInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class JWTUtil {
    private static final String SECRET = "ec818a93Dbcb4d7f92Befc62F27aA89a";

    private JWTUtil() {
    }

    /**
     * 生成 token
     * @author liulinchuan
     * @since 2023/1/16 14:09
     * @param authentication 认证信息
     * @return token
     */
    public static String encodeToken(Authentication authentication) {
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        String token = JWT.create()
                .withExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS))
                .withClaim("userId", userInfo.getUserId())
                .withClaim("username", userInfo.getUsername())
                .withClaim("name", userInfo.getName())
                .withClaim("roles", userInfo.getRoleIdList())
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }
    /**
     * 解析 token
     * @author liulinchuan
     * @since 2023/1/29 14:22
     * @param token token 信息
     * @return 认证信息
     */
    public static Authentication decodeToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        decodedJWT = verifier.verify(decodedJWT);

        List<Integer> roleIdList = decodedJWT.getClaim("roles").asList(Integer.class);
        String username = decodedJWT.getClaim("username").asString();
        Integer userId = decodedJWT.getClaim("userId").asInt();
        String name = decodedJWT.getClaim("name").asString();
        List<GrantedAuthority> authorities = new ArrayList<>(roleIdList.size());
        roleIdList.forEach(roleId -> authorities.add(new SimpleGrantedAuthority("ROLE_" + roleId)));
        UserInfo userInfo = new UserInfo(username, "", authorities);
        userInfo.setUserId(userId);
        userInfo.setName(name);
        userInfo.setRoleIdList(roleIdList);
        return UsernamePasswordAuthenticationToken.authenticated(userInfo, null, authorities);
    }

}

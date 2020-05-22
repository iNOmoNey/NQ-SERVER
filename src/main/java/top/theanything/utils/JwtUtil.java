package top.theanything.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import top.theanything.config.BasicConfig;

import java.util.Date;

public class JwtUtil {
    private static final String signingKey = BasicConfig.signingKey;
    /**
     * 签发token
     * @param userName 用户名
     * @return token
     */
    public static String create(String userName) {
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
        return token;
    }

    /**
     * 解析token
     * @param token token
     * @return 用户名
     */
    public static String parse(String token) throws Exception{
        String username = Jwts.parser()
                            .setSigningKey(signingKey)
                            .parseClaimsJws(token.replace("Bearer ", ""))
                            .getBody()
                            .getSubject();
        return username;
    }

    @Test
    public void test() throws Exception {
        String name = "zhou";
        String s = JwtUtil.create(name);
        System.out.println(s);
        String parse = JwtUtil.parse(s);
        System.out.println(parse);
    }
}

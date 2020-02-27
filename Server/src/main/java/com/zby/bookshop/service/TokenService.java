package com.zby.bookshop.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
//    设置Token有效时间
    public static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;
//    Token私钥 uuid生成
    public static final String TOKEN_SECRET ="b68d826e961d4c3a91a1a82a5a86c203";
    public String sign(String username,int uid){
        try {
//            过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
//             私钥与加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
//            设置头部信息
            Map<String,Object> header = new HashMap<>(2);
            header.put("typ","JWT");
            header.put("alg","HS256");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("loginName",username)
                    .withClaim("userId",uid)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (UnsupportedEncodingException e){
            return null;

        }
    }
}

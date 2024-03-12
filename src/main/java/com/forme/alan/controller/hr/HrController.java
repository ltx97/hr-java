package com.forme.alan.controller.hr;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HrController {

    @PostMapping("/hr/login")
    public Map login(@RequestBody String req){
        Date expireTime = DateUtils.addSeconds(new Date(), 60);
        String token = JWT.create()
                .withExpiresAt(expireTime)  //设置过期时间
                .withAudience("ltx") //设置接受方信息，一般时登录用户
                .sign(Algorithm.HMAC256("111111"));  //使用HMAC算法，111111作为密钥加密
        Map map = new HashMap();
        map.put("success", true);
        map.put("message", "登录成功");
        map.put("data", token);
        return map;
    }

    @PostMapping("/userInfo")
    public Map userInfo(){
        Map userMap = new HashMap();
//        userMap.put("headImg", "https://img.pconline.com.cn/images/upload/upc/tx/photoblog/1812/26/c7/125539584_1545834974811_mthumb.jpg");
        userMap.put("headImg", "22");
        userMap.put("username", "蓝天翔");

        Map map = new HashMap();
        map.put("success", true);
        map.put("message", "访问接口成功");
        map.put("data", userMap);
        return map;
    }


    public static void main(String[] args) throws InterruptedException {
        Date expireTime = DateUtils.addSeconds(new Date(), 2);
        String token = JWT.create()
                .withExpiresAt(expireTime)  //设置过期时间
                .withAudience("ltx") //设置接受方信息，一般时登录用户
                .sign(Algorithm.HMAC256("111111"));  //使用HMAC算法，111111作为密钥加密
        System.out.println(token);

        Thread.sleep(3000);

        String userId = JWT.decode(token).getAudience().get(0);
        System.out.println(userId);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("111111")).build();
        jwtVerifier.verify(token);
    }

}

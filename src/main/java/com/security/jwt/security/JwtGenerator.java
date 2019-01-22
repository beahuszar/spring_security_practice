package com.security.jwt.security;

import com.security.jwt.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

  //this funtion returns the JSON web token for us
  public String generate(JwtUser jwtUser) {

    //set expiry date can be used, but this one is an infinite token
    Claims claims = Jwts.claims()
        .setSubject(jwtUser.getUserName());
    claims.put("userId", String.valueOf(jwtUser.getId()));
    claims.put("role", jwtUser.getRole());



        Jwts.builder()
        .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, "youtube")
            .compact(); //to avoid lengthy token
    return null;
  }
}

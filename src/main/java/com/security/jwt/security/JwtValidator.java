package com.security.jwt.security;

import com.security.jwt.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {
//io.jsonwebtoken dependecy is added for this class in pom

  private String secrect = "youtube"; //user should create the token using this particular secret
                                      // we are decoding with the same secret

  /**
   * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
   * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
   *
   * @param token the JWT token to parse
   * @return the User object extracted from specified token or null if a token is invalid.
   */

  public JwtUser validate(String token) {
    JwtUser jwtUser = null;

    try {
      Claims body = Jwts.parser()
          .setSigningKey(secrect)
          .parseClaimsJws(token)
          .getBody(); //to convert it to the claims, thus we need the body of the message

      jwtUser = new JwtUser(); //POJO

      //all these details are extracted from the token to set the model accordingly
      jwtUser.setUserName(body.getSubject());
      jwtUser.setId(Long.parseLong((String)body.get("userId")));
      jwtUser.setRole((String)body.get("role"));
    } catch (Exception e) {
      System.out.println(e);
    }
    return jwtUser;
  }
}

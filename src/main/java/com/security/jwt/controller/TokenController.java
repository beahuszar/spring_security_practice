package com.security.jwt.controller;

import com.security.jwt.model.JwtUser;
import com.security.jwt.security.JwtGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

  @PostMapping //whoever hits this rest endpoint, with the same token & username, it will generate a new token
  public String generate(@RequestBody final JwtUser jwtUser) {
    JwtGenerator jwtGenerator = new JwtGenerator();
    jwtGenerator.generate(jwtUser);
  }
}

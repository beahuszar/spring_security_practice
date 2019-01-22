package com.security.jwt.controller;

import com.security.jwt.model.JwtUser;
import com.security.jwt.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

  private JwtGenerator jwtGenerator;

  @Autowired
  public TokenController(JwtGenerator jwtGenerator) {
    this.jwtGenerator = jwtGenerator;
  }

  @PostMapping //whoever hits this rest endpoint, with the same token & username, it will generate a new token
  public String generate(@RequestBody final JwtUser jwtUser) {
    return jwtGenerator.generate(jwtUser);
  }
}

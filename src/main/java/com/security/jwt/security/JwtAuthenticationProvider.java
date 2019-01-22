package com.security.jwt.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationProvider implements AuthenticationProvider {
  //all the major processing is executed here
  //this is where the class is authenticated
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    return null;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    //checks whether the particular is is of correct type
    return JwtAuthenticationToken.class.isAssignableFrom(aClass); //is it an implementation of this particular class which we are getting
  }
}

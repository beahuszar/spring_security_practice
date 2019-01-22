package com.security.jwt.security;

import com.security.jwt.model.JwtAuthenticationToken;
import com.security.jwt.model.JwtUser;
import com.security.jwt.model.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
  //all the major processing is executed here
  //this is where the class is authenticated
  private JwtValidator validator;

  @Autowired
  public JwtAuthenticationProvider(JwtValidator validator) {
    this.validator = validator;
  }

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

  }

  @Override
  protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    //username is not always required like in this case
    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;

    String token = jwtAuthenticationToken.getToken();

    JwtUser jwtUser =  validator.validate(token); //to decode

    if (jwtUser == null) { //validator prints exception and returns null if 'try' is unsuccessful
      throw new RuntimeException("JWT Token is incorrect"); //custom exceptions can be thrown too
    }

    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
        .commaSeparatedStringToAuthorityList(jwtUser.getRole());

    return new JwtUserDetails(jwtUser.getUserName(), jwtUser.getId(),
        token,
        grantedAuthorities); //POJO
  }

  @Override
  public boolean supports(Class<?> aClass) {
    //checks whether the particular is is of correct type
    return JwtAuthenticationToken.class.isAssignableFrom(aClass); //is it an implementation of this particular class which we are getting
  }
}

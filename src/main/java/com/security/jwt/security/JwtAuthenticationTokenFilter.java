package com.security.jwt.security;

import com.security.jwt.model.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
//will be filtering out the URLs
  public JwtAuthenticationTokenFilter( ) {
    super("/rest/**"); //to make it applicable for all the endpoints
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
    //this is where the request is handled and validated
    //the tokens are used and decoded here
    //extract the information from the header
    String header = httpServletRequest.getHeader("Authorisation");
    if (header == null || !header.startsWith("Token ")) {
      throw new RuntimeException("JWT Token is missing");
    }

    String authenticationToken = header.substring(6);

    JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);

    return getAuthenticationManager().authenticate(token); //it is authenticated using this particular request
  }

  @Override //proceed with the next chain because we have a different filter
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
    chain.doFilter(request, response); //go to the next filter so that we dont have to block any request
  }
}

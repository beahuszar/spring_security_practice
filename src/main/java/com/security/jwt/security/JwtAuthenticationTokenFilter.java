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
//This class is the entry point of our JWT authentication process
//extracts the JWT token from the request headers and delegates authentication to the injected AuthenticationManager
// If the token is not found, an exception is thrown that stops the request from processing.
//successful auth - to override default spring flow not to stop the filter

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

    // As this authentication is in HTTP header, after success we need to continue the request normally
    // and return the response as if the resource was not secured at all
    chain.doFilter(request, response);
  }

  @Override
  protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
    return true;
  }
}

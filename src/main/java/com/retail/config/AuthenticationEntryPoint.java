package com.retail.config;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  /* (non-Javadoc)
   * @see org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    response.addHeader("WWW-Authenticate", "Basic realm - " + getRealmName());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    PrintWriter writer = response.getWriter();
    writer.println("Http Status 401 " + authException.getMessage());
  }

  /* (non-Javadoc)
   * @see org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint#afterPropertiesSet()
   */
  @Override
  public void afterPropertiesSet() {
    setRealmName("MandeepKumawat");
    super.afterPropertiesSet();
  }

}

package tw.com.ispan.ted;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@EnableWebSecurity
public class LoginConfig extends WebSecurityConfigurerAdapter{
    
     @Override
     protected void configure(HttpSecurity http) throws Exception {
        http
                // .authorizeRequests()
                // // .antMatchers(HttpMethod.GET, "/users/**").authenticated()
                // .antMatchers(HttpMethod.GET, "/**").permitAll()
                // .antMatchers(HttpMethod.POST, "./**").permitAll()
                // ;
               .csrf().disable()
               .cors()
               .and()
               .authorizeRequests()
               .anyRequest().permitAll()
               .and()
               .oauth2Login().defaultSuccessUrl("/loginCheck")
               .and()
               .logout()
               
               // .logout().clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))                                        
               // //.logoutUrl("/logout")                    
               .logoutSuccessUrl("/")              // 3.
               // //.logoutSuccessHandler(logoutSuccessHandler) 
               // .deleteCookies("JSESSIONID")
               // .invalidateHttpSession(true)                // 5.
               // //.addLogoutHandler(logoutHandler)           
               
                
                ;
     }

    //  @Override
    //  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //      auth.inMemoryAuthentication()
    //      .withUser("user")
    //      .password("123")
    //      .roles("USER");
    //  }
 }


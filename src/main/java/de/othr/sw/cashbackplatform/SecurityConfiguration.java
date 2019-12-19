package de.othr.sw.cashbackplatform;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userSecurityService;
	@Autowired
	private SecurityUtilities securityUtilities;
	
	private BCryptPasswordEncoder passwordEncoder() {
		return securityUtilities.passwordEncoder();
	}
	
	private static final String[] ALLOW_ACCESS_WITHOUT_AUTHENTICATION = {
		"/css/**", "/image/**", "/fonts/**", "/", "/customers/login", "/customers/new", "/customers/new/**", "/customers/new/privatecustomer/**"
	};

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		
		http
			.authorizeRequests()
			.antMatchers(ALLOW_ACCESS_WITHOUT_AUTHENTICATION)
			.permitAll().anyRequest().authenticated();
		
		http
			.formLogin()
				.loginPage("/customers/login").permitAll()
				.defaultSuccessUrl("/")
				.failureUrl("/customers/login?error")
			.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/customers/logout"))
				.logoutSuccessUrl("/customers/login?logout")
				.deleteCookies("remember-me")
				.permitAll()
			.and()
				.rememberMe();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
}

package eu.balev.pushme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests().
				antMatchers("/mycontainers/**").authenticated().
				antMatchers("/**").permitAll().
				and().
				formLogin().
					loginPage("/login").
					failureUrl("/login?error").
					successForwardUrl("/home").
					permitAll().and().
				logout().
					logoutUrl("/logout").
					logoutSuccessUrl("/home")
				.permitAll();
		
		http.csrf().ignoringAntMatchers("/container/**");
		
		//TODO: should be changed on released vers.
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				new BCryptPasswordEncoder());
	}

}

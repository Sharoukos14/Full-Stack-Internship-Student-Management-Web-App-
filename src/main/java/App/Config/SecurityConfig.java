package App.Config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/register", "/error","/login","/uoi-logo.png").permitAll()
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	        	.loginPage("/login")
	            .defaultSuccessUrl("/success", true)
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutSuccessUrl("/login")
	            .permitAll()
	        );

	    return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}


    @Bean
    public AuthenticationManager authManager(
        AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}

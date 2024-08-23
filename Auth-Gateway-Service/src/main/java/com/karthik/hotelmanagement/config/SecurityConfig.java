package com.karthik.hotelmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.karthik.hotelmanagement.filter.JwtFilter;
import com.karthik.hotelmanagement.service.UserInfoService;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserInfoService userService;

	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final String[] AUTH_WHITELIST = { 
			"/getToken", 
			"/register", 
			"/userservice/guest/**",
			"/hotelservice/guest/**",
			"/ratingservice/guest/**",
			"/paymentservice/guest/**",
			"/bookingservice/guest/**"};
	
	private static final String[] AUTH_USER = { 
			"/usersTest", 
			"/userservice/user/**",
			"/hotelservice/user/**",
			"/ratingservice/user/**",
			"/paymentservice/user/**",
			"/bookingservice/user/**"};
	
	private static final String[] AUTH_USER_ADMIN = { 			
			"/userservice/useradmin/**",
			"/hotelservice/useradmin/**",
			"/ratingservice/useradmin/**",
			"/paymentservice/useradmin/**",
			"/bookingservice/useradmin/**"};

	private static final String[] AUTH_ADMIN = { 
			"/adminsTest", 
			"/getUsersCredentials", 
			"/getUserRoles", 
			"/userservice/admin/**",
			"/hotelservice/admin/**",
			"/ratingservice/admin/**",
			"/paymentservice/admin/**",
			"/bookingservice/admin/**"};	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> 
				auth.requestMatchers(AUTH_WHITELIST).permitAll()
					.requestMatchers(AUTH_USER).hasAuthority("USER")
					.requestMatchers(AUTH_USER_ADMIN).hasAnyAuthority("USER", "ADMIN")
					.requestMatchers(AUTH_ADMIN).hasAuthority("ADMIN")
					.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder);
		return auth;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		return config.getAuthenticationManager();
	}

//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().requestMatchers("/ignore");
//	}
}

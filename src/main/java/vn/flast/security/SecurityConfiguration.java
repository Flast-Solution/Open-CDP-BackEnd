package vn.flast.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.flast.entities.MyResponse;
import vn.flast.jwt.JwtAuthTokenFilter;
import vn.flast.models.UserPermission;
import vn.flast.repositories.UserPermissionRepository;
import vn.flast.utils.JsonUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
   
	private final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private UserPermissionRepository permissionRepository;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] ignore = { "/auth/**", "/uploads/**", "/junit/**" };
        List<UserPermission> allPermission = permissionRepository.findAll();
        var itemIgnore = allPermission.stream().filter(
            f -> "any".equals(f.getRoles())).map(UserPermission::getAction).toList();
        String[] notRqAuth = ArrayUtils.addAll(ignore, itemIgnore.toArray(new String[0]));
        var permissions = allPermission.stream().filter(f -> !"any".equals(f.getRoles())).toList();

        http.cors().and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeHttpRequests(request -> {
            request.requestMatchers(notRqAuth).permitAll();
            permissions.forEach(permission -> request.requestMatchers(permission.getAction()).hasRole(permission.getRoles()));
            request.anyRequest().authenticated();
        })
        .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint());

        return http.build();
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            var ret = MyResponse.response(403, "Request is not permit");
            response.getWriter().write(JsonUtils.toJson(ret));
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers","Access-Control-Allow-Origin","Access-Control-Request-Method", "Access-Control-Request-Headers","Origin","Cache-Control", "Content-Type", "Authorization"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
	    return (request, response, ex) -> {
	    	logger.info(ex.getMessage());
	        response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            var ret = MyResponse.response(403, "Request Denied Handler");
            response.getWriter().write(JsonUtils.toJson(ret));
	    };
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

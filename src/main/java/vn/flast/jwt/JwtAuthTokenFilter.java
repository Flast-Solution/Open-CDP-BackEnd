package vn.flast.jwt;

import org.springframework.stereotype.Component;
import vn.flast.entities.MyResponse;
import vn.flast.user.MyUserDetailsService;
import vn.flast.utils.JsonUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component("jwtAuthTokenFilter")
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            String jwt = getJwt(request);
            if (jwt != null) {
                if(tokenProvider.validateJwtToken(jwt)){
                    Claims claims = tokenProvider.getClaims(jwt);
                    int id = Integer.parseInt(claims.getId());
                    /* Load Rule from DataBase */
                    UserDetails userDetails = myUserDetailsService.loadUserById(id);
                    var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    var ret = MyResponse.response(500, "validateJwtToken Error");
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(JsonUtils.toJson(ret));
                    return;
                }
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}

package com.example.rate_limiter.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.rate_limiter.service.RateLimiterService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RateLimiterFilter extends OncePerRequestFilter {
	
	@Autowired
	private RateLimiterService rateLimiterServivce;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException {
		
		String path = request.getRequestURI();
		if(!path.startsWith("/api")){
			log.info("The request is from Swagger.So it has not been rate limited...");
			filterChain.doFilter(request, response);
			return;
		}
		String clientIp = request.getRemoteAddr();
		boolean isAllowed = rateLimiterServivce.allowRequest(clientIp);
		if(!isAllowed) {
			log.info("Requst blocked for " +clientIp + ".Exceeded too many attempts");
			response.setStatus(429);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Map<String,Object> rateLimiterResponse = new HashMap<>();
			rateLimiterResponse.put("status", 429);
		    rateLimiterResponse.put("message", "Rate limit exceeded");
			ObjectMapper objectmapper = new ObjectMapper();
			String jsonResponse = objectmapper.writeValueAsString(rateLimiterResponse);
			response.getWriter().write(jsonResponse);
			return;
			
		}
		filterChain.doFilter(request, response);
	}

}

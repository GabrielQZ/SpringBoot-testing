package com.microserve.authService.validator;

import com.microserve.authService.controller.UserController;
import com.microserve.authService.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class UserJWT {

    public static Object createJWT(User foundUser, String jwtSecret) {
        try {
            Instant now = Instant.now();

            Date issuedAt = Date.from(now);
            Date expiresAt = Date.from(now.plus(2, ChronoUnit.HOURS));

            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

            String jwt = Jwts
                    .builder()
                    .setSubject("user-auth")
                    .setIssuedAt(issuedAt)
                    .setExpiration(expiresAt)
                    .claim("username", foundUser.username)
                    .claim("id", foundUser.id)
                    .signWith(key)
                    .compact();

            JSONObject loginData = new JSONObject();
            loginData.put("token", jwt);

            return loginData.toString();

        } catch (JwtException e) {
            System.out.println(e.getMessage());
            return UserValidationErrors.unhandledError(e.getMessage());
        } catch ( Exception e) {
            System.out.println(e.getMessage());
            return UserValidationErrors.loggingInServerError();
        }
    }
}

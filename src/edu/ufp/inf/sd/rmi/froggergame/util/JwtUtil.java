package edu.ufp.inf.sd.rmi.froggergame.util;

import edu.ufp.inf.sd.rmi.froggergame.server.models.User;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {/*
    private String SECRET_KEY = "secret";


    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(subject)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                    .sign(algorithm);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean validateToken(String token, User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .withSubject(user.getEmail())
                    .acceptExpiresAt(System.currentTimeMillis())
                    .build(); //Reusable verifier instance

            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}

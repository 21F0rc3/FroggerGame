package edu.ufp.inf.sd.rmi.froggergame.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.ufp.inf.sd.rmi.froggergame.server.models.User;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static String SECRET_KEY = "secret";

    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getEmail());
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(subject)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 minutos
                    .sign(algorithm);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean validateToken(String token, User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .withSubject(user.getEmail())
                    .acceptExpiresAt(0)
                    .build(); //Reusable verifier instance

            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (TokenExpiredException e) {
            System.out.println(TerminalColors.ANSI_RED + "[ERROR]" + TerminalColors.ANSI_RESET + " Jwt token is not valid. Please authenticate again");
            return false;
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

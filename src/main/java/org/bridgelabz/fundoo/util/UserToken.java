package org.bridgelabz.fundoo.util;

import reactor.core.publisher.Mono;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


@Component
public class UserToken {
    private static String token;

    public static String generateToken(int id) {
        token = "Yash";
        return JWT.create().withClaim("id", id).sign(Algorithm.HMAC256(token));
    }

    public static int tokenVerify(String tok) {
        token = "Yash";
        int userid;
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(UserToken.token));
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        JWTVerifier jwtverifier=verification.build();
        DecodedJWT decodedjwt=jwtverifier.verify(tok);
        Claim claim=decodedjwt.getClaim("id");
        userid=claim.asInt();
        return userid;
    }
}
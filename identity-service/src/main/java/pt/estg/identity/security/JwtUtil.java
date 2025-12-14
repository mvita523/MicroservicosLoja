package pt.estg.identity.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;
import java.util.Date;
public class JwtUtil {
  private final String secret = System.getenv().getOrDefault("JWT_SECRET","change_this_secret");
  private final Algorithm alg = Algorithm.HMAC256(secret);
  private final JWTVerifier verifier = JWT.require(alg).build();
  public String generateToken(String username, Long userId){
    return JWT.create().withSubject(username).withClaim("userId", userId)
      .withIssuedAt(new Date()).withExpiresAt(new Date(System.currentTimeMillis()+3600_000)).sign(alg);
  }
  public DecodedJWT verify(String token){ return verifier.verify(token); }
}

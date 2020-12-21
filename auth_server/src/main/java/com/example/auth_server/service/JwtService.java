package com.example.auth_server.service;

import java.io.FileNotFoundException;
import java.security.KeyPair;
import java.util.Date;
import java.util.List;

import com.example.auth_server.config.JwtConfig;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;

@Service
public class JwtService {

    private final KeyPair keyPair;
    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) throws FileNotFoundException {
        this.jwtConfig = jwtConfig;
        this.keyPair = JwkUtil.getKeyPairFromKeyStore(jwtConfig.getKeyStorePath(), jwtConfig.getKeyStorePassword(), jwtConfig.getKeyAlias());
    }

    public String generateJwtToken(String username, List<String> authorities) throws JOSEException {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .claim("authorities", authorities)
                    .subject(username)
                    .issueTime(new Date())
                    .issuer("http://www.ynu.edu.cn/sunxp")
                    .expirationTime(DateUtil.offsetMinute(new Date(), jwtConfig.getExpiration()))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
            RSASSASigner rsassaSigner = new RSASSASigner(this.keyPair.getPrivate());
            signedJWT.sign(rsassaSigner);

            return signedJWT.serialize();
    }

    public JWKSet GetJwkSet(){
        return JwkUtil.publicKeyToJwkSet(this.keyPair.getPublic());
    }

}

package com.example.auth_server.service;

import cn.hutool.crypto.KeyUtil;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

public class JwkUtil {
    /**
     * 获取秘钥对
     *
     * @return KeyPair
     * @throws FileNotFoundException
     */
    public static KeyPair getKeyPairFromKeyStore(
            String keyStorePath,
            String keyStorePassword,
            String keyAlias
    ) throws FileNotFoundException {

        File keyStoreFile = new File(keyStorePath);
        if (!keyStoreFile.exists()) {
            throw new FileNotFoundException("未找到秘钥库文件：" + keyStorePath);
        }

        char[] passwordChars = keyStorePassword.toCharArray();

        KeyStore keyStore = KeyUtil.readKeyStore(KeyUtil.KEY_TYPE_JKS, keyStoreFile, passwordChars);
        return KeyUtil.getKeyPair(keyStore, passwordChars, keyAlias);
    }

    public static JWKSet publicKeyToJwkSet(PublicKey publicKey) {
        JWK jwk = new RSAKey.Builder((RSAPublicKey) publicKey)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .build();
        return new JWKSet(jwk);
    }

}

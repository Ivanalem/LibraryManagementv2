package com.academy.LibraryManagementSystem.component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@ConfigurationProperties(prefix = "rsa")
@Setter
@Getter
public class RsaKeyProperties {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    @PostConstruct
    public void init() {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // Декодируем Base64
            byte[] decodedPublicKey = Base64.getDecoder().decode(publicKey.getEncoded());
            byte[] decodedPrivateKey = Base64.getDecoder().decode(privateKey.getEncoded());

            X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decodedPublicKey);
            PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decodedPrivateKey);

            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpecPublic);
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpecPrivate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA keys", e);
        }
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }
}

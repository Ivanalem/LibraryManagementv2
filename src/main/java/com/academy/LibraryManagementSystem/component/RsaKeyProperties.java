package com.academy.LibraryManagementSystem.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Getter
@Component
@ConfigurationProperties(prefix = "rsa")
@Setter
public class RsaKeyProperties {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}

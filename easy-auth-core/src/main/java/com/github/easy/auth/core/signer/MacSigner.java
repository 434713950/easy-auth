package com.github.easy.auth.core.signer;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

/**
 * java mac签名生成器
 *
 * @author cheng.peng
 * @date 2019/8/28
 */
public class MacSigner implements Signer {

    private static final String DEFAULT_ALGORITHM = "HMACSHA256";
    //算法
    private final String algorithm;
    //密钥
    private final SecretKey key;

    public MacSigner(String key) {
        this(new SecretKeySpec(key.getBytes(), DEFAULT_ALGORITHM));
    }

    public MacSigner(SecretKey key) {
        this(DEFAULT_ALGORITHM, key);
    }

    public MacSigner(String algorithm, SecretKey key) {
        this.algorithm = algorithm;
        this.key = key;
    }

    @Override
    public byte[] sign(byte[] content) {
        try {
            Mac mac = Mac.getInstance(this.algorithm);
            mac.init(this.key);
            return mac.doFinal(content);
        } catch (GeneralSecurityException var3) {
            throw new RuntimeException(var3);
        }
    }

    @Override
    public boolean verify(byte[] content, byte[] signature) {
        byte[] signed = this.sign(content);
        return this.isEqual(signed, signature);
    }

    private boolean isEqual(byte[] b1, byte[] b2) {
        if (b1.length != b2.length) {
            return false;
        } else {
            int xor = 0;
            for(int i = 0; i < b1.length; ++i) {
                xor |= b1[i] ^ b2[i];
            }
            return xor == 0;
        }
    }

    @Override
    public String algorithm() {
        return this.algorithm;
    }

    public String toString() {
        return "MacSigner: " + this.algorithm;
    }
}

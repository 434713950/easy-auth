package com.github.easy.auth.core.signer;

/**
 * @author cheng.peng
 * @date 2019/8/28
 */
public interface Signer {

    /**
     * 签名生成
     * @param content
     * @return
     */
    byte[] sign(byte[] content);

    /**
     * 签名算法
     * @return
     */
    String algorithm();

    boolean verify(byte[] content, byte[] sig);
}

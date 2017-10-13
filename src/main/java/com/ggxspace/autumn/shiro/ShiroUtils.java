package com.ggxspace.autumn.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Random;

/**
 * Created by ganguixiang on 2017/10/13.
 */
public class ShiroUtils {

    /**
     * 加密算法
     */
    public static final String HASH_ALGORITHM = "MD5";

    /**
     * salt长度
     */
    public static final int SALT_LENGTH = 6;

    /**
     * 加密循环次数
     */
    public static final int HASH_ITERATIONS = 1024;

    /**
     * 密码加密
     * @param credentials
     * @param saltSource
     * @return
     */
    public static String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash(HASH_ALGORITHM, credentials, salt, HASH_ITERATIONS).toString();
    }

    /**
     * 获取随机salt
     * @return
     */
    public static String getRandomSalt() {
        return getRandomString(SALT_LENGTH);
    }


    /**
     * 获取随机数字和字母
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}

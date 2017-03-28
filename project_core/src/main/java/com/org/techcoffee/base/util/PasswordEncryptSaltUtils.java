package com.org.techcoffee.base.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 基于shiro做的MD5加盐算法
 * @author Ron.Lin
 *
 */
public class PasswordEncryptSaltUtils {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private static String algorithmName = "md5";

    private static int hashIterations = 2;
    
    public static PasswordEncryptSalt encryptPassword(String originPassword) {
        PasswordEncryptSalt returnEntity = new PasswordEncryptSalt();
        String salt = randomNumberGenerator.nextBytes().toHex();
        returnEntity.setSalt(salt);
        String newPassword = new SimpleHash(
                algorithmName,
                originPassword,
                ByteSource.Util.bytes(originPassword + salt),
                hashIterations).toHex();
        returnEntity.setNewPassword(newPassword);
        return returnEntity;
    }
    
    
    public static boolean checkIsSame(String inputPassword, String salt, String dbPassword) {
        String newPassword = new SimpleHash(
                algorithmName,
                inputPassword,
                ByteSource.Util.bytes(inputPassword + salt),
                hashIterations).toHex();
        return newPassword.equals(dbPassword);
    }
}

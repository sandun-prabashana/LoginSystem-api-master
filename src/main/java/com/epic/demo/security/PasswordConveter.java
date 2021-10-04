package com.epic.demo.security;

/**
 * @author Sandun Prabashana <sandunprabashana@gmail.com> (prabashana.tk/)
 * @since 10/4/2021
 */
public class PasswordConveter {

    public static String convertPassword(String password) throws Exception{
        AES aes = new AES("gtevdywoap12gryd");
        String encdata =aes.encrypt(password);
        return encdata;
    }

    public static String decryptPassword(String password) throws Exception{
        AES aes = new AES("gtevdywoap12gryd");
        String decrypt =aes.decrypt(password);
        return decrypt;
    }

}

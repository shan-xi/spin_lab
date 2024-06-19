package org.example;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ParseEcdata {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String payId = "1001340526093022";
        String salt = "473531b173db4371";
        String keySalt = "cd9ee8b0395f4177";

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update((keySalt + payId).getBytes());
        String response = new String(Hex.encodeHex(messageDigest.digest())).toUpperCase();
        String generatedKey = response.substring(0, 32);

        String encdata = "pbwChL2nJZ95/rtqifZsFP+mZRRbldTMouds5HKkSuxJYZxXIZq5rTPCkBrhW+6/UjktUBYhPIOrsbtshaylb7MFwRP/+ctsQLEhtTKSp/LOdwPk75863uAay5JnaYOZqZdM7jSU8UM2r+qKNxzdN9tT0IDRLNSvFL2JFdO7YrNiQj3mFruMlrdXUS5egyDBAfmMZpIK8KQCqWpCJ60V92nCcWpU7NJwzPR0nKRnTWYTWJMt2tf64z4mP8AhfmJVNarzP9hBa6mz/QGmeOROAQLJrkQQT4WjC9tFEZ57HhWzP2UnnESobOxCaiXf8+lmxCt5eJhfyfP/Rv7toBsLkvghmciFyvx81HMjuUbN38W3LB5GADVS+5XpK4xHG2DhJzri+fMAD/Do0XftYHvuWkYHdlcrRAAymnC3rC68xoJ97Ksin3otWavXVZqqkuXqMbVc2+GHPfa0gnWlX7hVmSfHEqECqlt8Zhx4Noshp/7VwNlUkZpwsWZGJeE+qwtxa5pu4lB8il/RAIWGk7bBO4RtiGSRH1QF1Rcfba/GDSNo76P0fhxD4nhaboD5Si5B54wxXHXWtG7I4v7eTbJlHDdlflEXXX2bAskJJ8vA/xST4wN5146qhim7SdolT7orS95vzfKsstmA4SjSmc6AXzJOP5woi36oeqomaleqpfkQfno7k5gFS8Qr7g1C17J6APvt4qeG4xn4hCXMVQelzsr5EH5etE0W6oHqATiAkHM";
        String ivString = generatedKey.substring(0,16);
        IvParameterSpec iv = new IvParameterSpec(ivString.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(generatedKey.getBytes(), "AES"), iv);

        byte[] decodedData = Base64.getDecoder().decode(encdata);
        byte[] decValue = cipher.doFinal(decodedData);

        System.out.println(new String(decValue));
    }
}

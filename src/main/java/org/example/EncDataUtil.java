package org.example;

import org.apache.commons.codec.binary.Hex;

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

public class EncDataUtil {
    static String payId = "1001340526093022";
    static String salt = "473531b173db4371";
    static String keySalt = "cd9ee8b0395f4177";

    public static void main(String[] args) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        System.out.println(decrypt("pbwChL2nJZ95/rtqifZsFP+mZRRbldTMouds5HKkSuxJYZxXIZq5rTPCkBrhW+6/UjktUBYhPIOrsbtshaylb7MFwRP/+ctsQLEhtTKSp/LOdwPk75863uAay5JnaYOZqZdM7jSU8UM2r+qKNxzdN9tT0IDRLNSvFL2JFdO7YrNiQj3mFruMlrdXUS5egyDBAfmMZpIK8KQCqWpCJ60V92nCcWpU7NJwzPR0nKRnTWYTWJMt2tf64z4mP8AhfmJVKW3ANgSBna6krwnjqVKH/LYw9cxK3cxkHO4TNjRQJVGUcB+oxIRJTDpYFDofkkdCNHDG3x/YkQtkxUlHMfovpndm3Fk4wcCoFh8Kb+E698YeAZD6Q0xyT38AGI7DW0qGu3kHDjnD6svZri7gZDK/uClX3PMXS6NW/aooImA3lrnKHL1HeyJpqu2GzBirdLEtfRN/HVlSFRgYfmruvKi2Ue3v/Ov4wgsQDYh2vArDwLh1OZy6p517O5W7SCURq47C//wpMZZyHHf5G5KwLsl9YOuddZVTkTTmw4Qzi/Q48RQ88a41eXy/pVpPp+1B7Km8dh1va3SsoX1RYo1Lq/FCcaNzuBfsa7cFY9uBfHO3ZuceAS5HonIP4pGbA6/LVACifvkgXidUq7TXgrRXQm9DvB4koGhceSIWEOsQdKc63QJNweaaaYVaPOmo2jgyyR4FEnREDWqZ+kfHCUPV1gZtGxvU6xPn34QHeJn3XS0qHLA"));
    }

    public static String decrypt(String encData) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {


        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update((keySalt + payId).getBytes());
        String response = new String(Hex.encodeHex(messageDigest.digest(), false));
        String generatedKey = response.substring(0, 32);

        String ivString = generatedKey.substring(0, 16);
        IvParameterSpec iv = new IvParameterSpec(ivString.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(generatedKey.getBytes(), "AES"), iv);

        byte[] decodedData = Base64.getDecoder().decode(encData);
        byte[] decValue = cipher.doFinal(decodedData);
        return new String(decValue);
    }

    public static String encrypt(String encString) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update((keySalt + payId).getBytes());
        String response = new String(Hex.encodeHex(messageDigest.digest(), false));
        String generatedKey = response.substring(0, 32);

        String ivString = generatedKey.substring(0, 16);
        IvParameterSpec iv = new IvParameterSpec(ivString.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(generatedKey.getBytes(), "AES"), iv);

        byte[] encValue = cipher.doFinal(encString.getBytes("UTF-8"));

        Base64.Encoder base64Encoder = Base64.getEncoder().withoutPadding();
        return base64Encoder.encodeToString(encValue);
    }
}

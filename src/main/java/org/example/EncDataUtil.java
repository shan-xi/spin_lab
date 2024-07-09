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
    static String payId = "1000140627123003";
    static String salt = "bf0e59087c50487d";
    static String keySalt = "2c6ece3ac5364224";

    public static void main(String[] args) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        System.out.println(decrypt("htbars3iOyAhF6HXMDJXN0Dpr21/15AciHLgPA8NdKRS39ASefd4Fl58KD4lClrOXIjPKTfjJQIYyuOcLoKNQFdFU06UKZHXiMQviNxi2epd2uxtyXBId51qdPCA/B/8jEISVJ05L7vnTBWAUDnJ+VIK02VWwtxZw+F/IvItRR/FQGaFriEEz0Om9D2AcIFr26rxp1L2nfDvXtDfG25B3I+Fj1lk5BfKUa75rfjvpHjYcT9msAzVefnv45sghYP45VDtv5v1nEQhB3/HzZNU+V2y77pZ7JzTA3/MgHTJohawXxDO4vv8n0k03qPkeHCYOSfOvoJ1ef//F0SGJD/SDKQxq/hGvFJiLEHdRSr9V6Au3qT4fWKoguo7ZxoaMbta5sV3iDMX9KMidyil+e6RzcOInowlZs1O1VEIHXCua6/gijf1IAPaV07kGt2/4kRDwykONnjJMKCL3UkkEvFQ5IoGKBcmUakBAqyONNkDl+0GSUMKw7REU8vZI7T0WjnfUzR99jVKIKY8muobVCtFJBL6b7lkoFT8PcbpXDh//u0kY2kWl/dAwi2xkp6UoOUjon79Zq4eDkFAYX9hHgaIz2Pyz8NC34UtPI212xtcDpG3OvDvdOTpfVkx2y5phfsYUvP1VLepQVYIWTQhqYfY89pf7jvScJVXcPAglDzMbA1DmjtPI0S6UFYUBWW5HhF4mxY+1mzV92SF1u5D8B2qqI2jp9fCKu3/MCw6usD3riI"));
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

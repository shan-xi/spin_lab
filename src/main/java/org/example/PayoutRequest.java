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
import java.util.Map;
import java.util.TreeMap;

public class PayoutRequest {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String payId = "1001340526093022";
        String salt = "473531b173db4371";
        String keySalt = "cd9ee8b0395f4177";
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("ACC_NO", "123456");
        treeMap.put("PAY_TYPE", "FIAT");
        treeMap.put("ACC_CITY_NAME", "TPE");
        treeMap.put("CUST_PHONE", "12345678");
        treeMap.put("ACC_NAME", "ICIC BANK");
        treeMap.put("REMARKS", "test");
        treeMap.put("CURRENCY_CODE", "356");
        treeMap.put("IFSC", "SBIN0016605");
        treeMap.put("ACC_PROVINCE", "UP");
        treeMap.put("PAY_ID", payId);
        treeMap.put("ORDER_ID", "SPIN20240619P1");
        treeMap.put("UDF13", "PO");
        treeMap.put("AMOUNT", "1500");
        treeMap.put("TOTAL_AMOUNT", "1500");
        treeMap.put("BANK_BRANCH", "DELHI");
        treeMap.put("CUST_EMAIL", "spin.liao@btse.com");
        treeMap.put("BANK_CODE", "1013");
        treeMap.put("CUST_NAME", "Spin");
        treeMap.put("TRANSFER_TYPE", "IMPS");

        StringBuilder createHashString = new StringBuilder();
        for (String key : treeMap.keySet()) {
            createHashString.append(key);
            createHashString.append("=");
            createHashString.append(treeMap.get(key));
            createHashString.append("~");
        }

        String encStringWoHash = createHashString.toString();
        int tildeLastCount = createHashString.lastIndexOf("~");
        createHashString.replace(tildeLastCount, tildeLastCount + 1, "");
        createHashString.append(salt);

        String createHash = createHashString.toString();
        MessageDigest messageDigest2 = MessageDigest.getInstance("SHA-256");
        messageDigest2.update(createHash.getBytes());
        String generatedHash = new String(Hex.encodeHex(messageDigest2.digest())).toUpperCase();

        String encString = encStringWoHash + "HASH=" + generatedHash;
        String encData = "";

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update((keySalt + payId).getBytes());
        String response = new String(Hex.encodeHex(messageDigest.digest())).toUpperCase();
        String generatedKey = response.substring(0, 32);

        String ivString = generatedKey.substring(0, 16);
        IvParameterSpec iv = new IvParameterSpec(ivString.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(generatedKey.getBytes(), "AES"), iv);

        byte[] encValue = cipher.doFinal(encString.getBytes("UTF-8"));

        Base64.Encoder base64Encoder = Base64.getEncoder().withoutPadding();
        encData = base64Encoder.encodeToString(encValue);
        System.out.println("ENCDATA : " + encData);
        System.out.println("STRING : " + encString);
    }
}

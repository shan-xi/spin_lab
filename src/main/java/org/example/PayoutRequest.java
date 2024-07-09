package org.example;

import com.google.gson.Gson;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

public class PayoutRequest {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String payId = "1000140627123003";
        String salt = "bf0e59087c50487d";
//        String keySalt = "cd9ee8b0395f4177";
        Map<String, String> treeMap = getParameterMap(payId);
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
        String encData = EncDataUtil.encrypt(encString);
        Gson gson = new Gson();
        Map<String, String> jsonMap = new TreeMap<>();
        jsonMap.put("PAY_ID", payId);
        jsonMap.put("ENCDATA", encData);
        String json = gson.toJson(jsonMap);
        System.out.println(json);
    }

    private static Map<String, String> getParameterMap(String payId) {
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("ACC_NO", "1234567890");
        treeMap.put("PAY_TYPE", "FIAT");
        treeMap.put("ACC_CITY_NAME", "Delhi");
        treeMap.put("CUST_PHONE", "1234567890");
        treeMap.put("ACC_NAME", "Spin");
        treeMap.put("REMARKS", "test");
        treeMap.put("CURRENCY_CODE", "356");
        treeMap.put("IFSC", "ICIC0003428");
        treeMap.put("ACC_PROVINCE", "Delhi");
        treeMap.put("PAY_ID", payId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        treeMap.put("ORDER_ID", "PO"+currentDateTime.format(formatter));
        treeMap.put("UDF13", "PO");
        treeMap.put("AMOUNT", "1500");
        treeMap.put("TOTAL_AMOUNT", "1500");
        treeMap.put("BANK_BRANCH", "Delhi");
        treeMap.put("CUST_EMAIL", "spin.liao@btse.com");
        treeMap.put("BANK_CODE", "1013");
        treeMap.put("CUST_NAME", "Spin");
        treeMap.put("TRANSFER_TYPE", "IMPS");
        return treeMap;
    }
}

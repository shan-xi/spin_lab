package org.example;

import com.google.gson.Gson;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class PayInRequest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String payId = "1001340526093022";
        String salt = "473531b173db4371";
        Map<String, String> treeMap = getParameterMap(payId);
        StringBuilder allFields = new StringBuilder();
        for (String key : treeMap.keySet()) {
            allFields.append("~");
            allFields.append(key);
            allFields.append("=");
            allFields.append(treeMap.get(key));
        }
        allFields.deleteCharAt(0); // Remove first FIELD_SEPARATOR
        allFields.append(salt);

        String input  = allFields.toString();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(input.getBytes());
        String hash = new String(Hex.encodeHex(digest.digest(), false));

        treeMap.put("HASH", hash);

        Gson gson = new Gson();
        String json = gson.toJson(treeMap);
        System.out.println(json);
    }

    private static Map<String, String> getParameterMap(String payId) {
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("PAY_ID", payId);
        treeMap.put("PAY_TYPE", "FIAT");
        treeMap.put("CUST_NAME", "spin");
        treeMap.put("CUST_FIRST_NAME", "spin");
        treeMap.put("CUST_LAST_NAME", "liao");
        treeMap.put("CUST_STREET_ADDRESS1", "taiwan");
        treeMap.put("CUST_CITY", "taipei");
        treeMap.put("CUST_STATE", "taipei");
        treeMap.put("CUST_COUNTRY", "taiwan");
        treeMap.put("CUST_ZIP", "110");
        treeMap.put("CUST_PHONE", "12345678");
        treeMap.put("CUST_EMAIL", "spin.liso@btse.com");
        treeMap.put("AMOUNT", "10000");
        treeMap.put("TXNTYPE", "SALE");
        treeMap.put("CURRENCY_CODE", "356");
        treeMap.put("PRODUCT_DESC", "test");
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        treeMap.put("ORDER_ID", "SPINPI"+currentDateTime.format(formatter));
        treeMap.put("RETURN_URL", "https://localhost:8443/payInCallBack");
        treeMap.put("PAYMENT_TYPE", "UP");
        return treeMap;
    }
}

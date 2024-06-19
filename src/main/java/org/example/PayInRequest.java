package org.example;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class PayInRequest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String payId = "1001340526093022";
        String salt = "473531b173db4371";
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
        treeMap.put("AMOUNT", "30000");
        treeMap.put("TXNTYPE", "SALE");
        treeMap.put("CURRENCY_CODE", "356");
        treeMap.put("PRODUCT_DESC", "test");
        treeMap.put("ORDER_ID", "SPIN020406172");
        treeMap.put("RETURN_URL", "https://www.google.com");
        treeMap.put("PAYMENT_TYPE", "UP");
        StringBuilder allFields = new StringBuilder();
        for (String key : treeMap.keySet()) {
            allFields.append("~");
            allFields.append(key);
            allFields.append("=");
            allFields.append(treeMap.get(key));
        }
        allFields.deleteCharAt(0); // Remove first FIELD_SEPARATOR
        allFields.append(salt);
        System.out.println(allFields);
        String input  = allFields.toString();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(input.getBytes());
        String response = new String(Hex.encodeHex(digest.digest()));
        System.out.println(response.toUpperCase());
    }
}

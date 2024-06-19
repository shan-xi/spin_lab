package org.example;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class PayOutTransactionStatusQueryRequestHash {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String payId = "1001340526093022";
        String salt = "473531b173db4371";
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("PAY_ID", payId);
        treeMap.put("ORDER_ID", "SPIN20240619P1");
        treeMap.put("TXNTYPE", "PO_STATUS");
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

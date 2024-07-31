package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
        Map<String, String> treeMap = getParameterMap(Config.payId);
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
        createHashString.append(Config.salt);

        String createHash = createHashString.toString();
        MessageDigest messageDigest2 = MessageDigest.getInstance("SHA-256");
        messageDigest2.update(createHash.getBytes());
        String generatedHash = new String(Hex.encodeHex(messageDigest2.digest())).toUpperCase();

        String encString = encStringWoHash + "HASH=" + generatedHash;
        String encData = EncDataUtil.encrypt(encString);
        Gson gson = new Gson();
        Map<String, String> jsonMap = new TreeMap<>();
        jsonMap.put("PAY_ID", Config.payId);
        jsonMap.put("ENCDATA", encData);
        String json = gson.toJson(jsonMap);
        System.out.println(json);
        sendRequest(json);
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

    private static void sendRequest(String json) {
        try {
            // Create the URL and open a connection
            URL url = new URL("http://localhost:8080/pgws/api/v1/rest/transaction/payout");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            conn.setRequestMethod("POST");

            // Set the request headers
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");

            // Enable the output stream to send the request body
            conn.setDoOutput(true);

            // Write the JSON input string to the output stream
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = conn.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                System.out.println("POST request worked");
            } else {
                System.out.println("POST request did not work");
            }
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    System.out.println(response.toString());
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        @SuppressWarnings("rawtypes")
                        Map map = objectMapper.readValue(response.toString(), Map.class);
                        System.out.println(map.get("PAY_ID"));
                        System.out.println(map.get("ENCDATA"));
                        System.out.println(EncDataUtil.decrypt((String) map.get("ENCDATA")));;
                    } catch (Exception e) {
                        //noinspection CallToPrintStackTrace
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("POST request did not work");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

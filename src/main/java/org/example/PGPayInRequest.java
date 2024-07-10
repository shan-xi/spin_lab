package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Hex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class PGPayInRequest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String payId = Config.payId;
        String salt = Config.salt;
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

        String input = allFields.toString();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(input.getBytes());
        String hash = new String(Hex.encodeHex(digest.digest(), false));

        treeMap.put("HASH", hash);

        Gson gson = new Gson();
        String json = gson.toJson(treeMap);
        System.out.println(json);

        sendRequest(json);
    }

    private static void sendRequest(String json) {
        try {
            // Create the URL and open a connection
            URL url = new URL("http://localhost:8080/crmws/api/v1/payin/s2s/payment");
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
                        System.out.println();
                        System.out.println(map.get("PAY_URL"));
                        openChrome((String) map.get("PAY_URL"));
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
        treeMap.put("ORDER_ID", "SPINPI" + currentDateTime.format(formatter));
        treeMap.put("RETURN_URL", "https://localhost:8443/payInCallBack");
        treeMap.put("PAYMENT_TYPE", "UP"); // UP NB
        return treeMap;
    }

    public static void openChrome(String url) {
        String os = System.getProperty("os.name").toLowerCase();

        Runtime rt = Runtime.getRuntime();
        try {
            if (os.contains("win")) {
                // Windows command to open Chrome
                rt.exec(new String[]{"cmd", "/c", "start chrome " + url});
            } else if (os.contains("mac")) {
                // Mac command to open Chrome
                rt.exec(new String[]{"/usr/bin/open", "-a", "Google Chrome", url});
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux command to open Chrome
                rt.exec(new String[]{"google-chrome", url});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
 * Copyright (c) ICANIO
 */

package com.SalesForce.services;
import java.net.*;


import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.json.JSONObject;

@Service
public class SalesforceService {

    private static final Logger logger = LoggerFactory.getLogger(SalesforceService.class);

    @Value("${salesforce.username}")
    private String username;

    @Value("${salesforce.password}")
    private String password;

    @Value("${salesforce.securityToken}")
    private String securityToken;

    @Value("${salesforce.authEndpoint}")
    private String authEndpoint;


    private static final String TOKEN_URL = "https://dream-force-2896.my.salesforce.com/services/oauth2/token";


//    private static final String TOKEN_URL = "https://superkeyinsurance.my.salesforce.com/services/oauth2/token";

    private static final String CLIENT_ID = "3MVG9GBhY6wQjl2vLS.SZ89FXpIlJpPsWM55SLvawLtF8xILvdsmp9CvtGPKpohwOH8kYXNejmVZaE1JrFW1.";
    private static final String CLIENT_SECRET = "0E73CE8F5D1D996C3524EB2238E9C029D5FA725BF7E10BF4FBDE4E2A9537CEFB";

    private static final String REDIRECT_URI = "https://localhost/callback";
    private static final String CODE = "aPrxzWsWjEBtZ3WUGzPKKrmu0u99L6lYocZCk2dBHygaapgoa3V7LxKeOVLph6R6ympGlf8LGQ";
    private static final String CODE_VERIFIER = "kGcc7XLHmAQWU18AHha4jLwFggwN9ol7zupUvcdYyJE";


    private static final String USER_NAME = "rajaselvamala.a-ulth@force.com";
    private static final String PASSWORD_CODE = "Password123jV2pgsEpSzff11eclQ5kdPj2q";


//    private static final String TOKEN_URL = "https://speed-customer-3504.my.salesforce.com/services/oauth2/token";
//    private static final String CLIENT_ID = "3MVG9bZIBBVRES4EoUUvMxBH054ts5ofgYCN1iSfOZAxq8KeJXOpOKls2VsXUog3lX6ruDDz4i0NH1sIS4H2T";
//    private static final String CLIENT_SECRET = "05F7E388B91891FE599A71B53CCBBC4D79D5D5019F4B72D59D0048CA334D6BA3";
//    private static final String REDIRECT_URI = "http://localhost:3000/callback";
//    private static final String CODE = "aPrxaAR4fymj92QPoft5ilTAD2sfoQg9CZ2IBAkatXC95FCRmwwX3A_ey3jFtvGF_1w7wwtO9A==";
//    private static final String CODE_VERIFIER = "1VkHjEvJzAZeGto3cnmaMc3-WjohUN8spfuJ8ZQC_hE";


    public static Map<String, String> getAccessTokenCode() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", CLIENT_ID);
        map.add("client_secret", CLIENT_SECRET);
        map.add("redirect_uri", REDIRECT_URI);
        map.add("code", CODE);
        map.add("code_verifier", CODE_VERIFIER);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(TOKEN_URL, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {

            // Parse the response body to extract the access_token and instance_url
            JSONObject responseJson = new JSONObject(response.getBody());

            String accessToken = responseJson.getString("access_token");
            String instanceUrl = responseJson.getString("instance_url");

            // Return both access token and instance URL
            Map<String, String> tokenData = new HashMap<>();
            tokenData.put("access_token", accessToken);
            tokenData.put("instance_url", instanceUrl);
            System.out.println(tokenData + "token1111111111111111111111111");
            return tokenData;
        } else {
            throw new Exception("Error fetching access token: " + response.getBody());
        }
    }


    public static Map<String, String> getAccessToken() {
        System.out.println("entered the line  could you check");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

//        map.add("grant_type", "password");
//        map.add("client_id", "3MVG9wfACBgtCq9GRV4xhFgWyn_rFUQP6OWeZ9o7fV6JjOYf.j_Hi0p4gjTFhBBKPkuu_StT8h4LpR7Wa5QWz");
//        map.add("client_secret", "F673DB6A4E3C918395D9FB8509EBE6ED19289D545C1A2867006FEA538807F67C");
//        map.add("username", "jebastin.prabaharan@ideas2it.vrna.com");
//        map.add("password", "Ideas2IT2024vGlfbqIQhJgxNvvD7KG0Ch9UQ");


        map.add("grant_type", "password");
        map.add("client_id", "3MVG9GBhY6wQjl2vLS.SZ89FXpIlJpPsWM55SLvawLtF8xILvdsmp9CvtGPKpohwOH8kYXNejmVZaE1JrFW1.");
        map.add("client_secret", "0E73CE8F5D1D996C3524EB2238E9C029D5FA725BF7E10BF4FBDE4E2A9537CEFB");
        map.add("username", "rajaselvamala.a-kluz@force.com");
        map.add("password", "Password12366ykhgyuXXlRkSi63uYTwS0V");


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            logger.info("Sending request to token URL: {}", TOKEN_URL);
            ResponseEntity<String> response = restTemplate.postForEntity(TOKEN_URL, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseJson = new JSONObject(response.getBody());
                String accessToken = responseJson.getString("access_token");
                String instanceUrl = responseJson.getString("instance_url");

                Map<String, String> tokenData = new HashMap<>();
                tokenData.put("access_token", accessToken);
                tokenData.put("instance_url", instanceUrl);

                System.out.println(tokenData + "token");
                logger.info("Successfully retrieved access token");
                return tokenData;
            } else {
                logger.error("Unexpected response status: {}", response.getStatusCode());
                logger.error("Response body: {}", response.getBody());
                throw new RuntimeException("Error fetching access token: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Client Error: {}", e.getStatusCode());
            logger.error("Error response body: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error fetching access token: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            throw new RuntimeException("Error fetching access token", e);
        }
    }


    public static void createUsers(String name, String email) throws Exception {

        // Get access token and instance URL dynamically
        Map<String, String> tokenData = getAccessToken();
        String accessToken = tokenData.get("access_token");
        String instanceUrl = tokenData.get("instance_url");

        String endpoint = instanceUrl + "/services/data/v58.0/sobjects/User/";

        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-Type", "application/json");  // Use JSON content type
        conn.setDoOutput(true);

        // Create JSON payload
        JSONObject userJson = new JSONObject();
        userJson.put("Username", email);
        userJson.put("Email", email);
        userJson.put("LastName", name);
        userJson.put("Alias", name.substring(0, Math.min(name.length(), 8)));
        userJson.put("TimeZoneSidKey", "America/Los_Angeles");
        userJson.put("LocaleSidKey", "en_US");
        userJson.put("EmailEncodingKey", "UTF-8");
        userJson.put("LanguageLocaleKey", "en_US");
        userJson.put("ProfileId", "00ebn000004Po7ZAAS"); // Replace with actual Profile ID


        System.out.println("Enter the " + userJson.put("Alias", name.substring(0, Math.min(name.length(), 8))));
        // Send JSON data
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = userJson.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Read response
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) { // 201 Created
            System.out.println("Enter the ");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {

                System.out.println("Enter the line ");

                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("User created successfully. Response: " + response);
            }
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                System.out.println("Enter the line else");
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.err.println("Failed to create user. Response code: " + responseCode);
                System.err.println("Response: " + response);
            }
        }
    }

    // Get All Users:

    public String getAllUsers() throws Exception {
       code();

        Map<String, String> tokenData = getAccessToken();
        String accessToken = tokenData.get("access_token");
        String instanceUrl = tokenData.get("instance_url");

        // SOQL query to get all users
        String query = "SELECT Id, Name, Email, ProfileId FROM User";

        String endpoint = instanceUrl + "/services/data/v58.0/query/?q=" + query.replace(" ", "%20");

        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-Type", "application/json");

        // Read the response
        int responseCode = conn.getResponseCode();

        System.out.println("Enter the " + responseCode + " responseCode");

        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Enter the " + responseCode + " responseCode");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();  // This will contain all users as JSON
            }
        } else {
            throw new RuntimeException("Failed to get users: HTTP error code " + responseCode);
        }
    }

    //Create User API:

    public static String createUser(String name, String email) throws Exception {
        // Get access token and instance URL dynamically
        Map<String, String> tokenData = getAccessToken();
        String accessToken = tokenData.get("access_token");
        String instanceUrl = tokenData.get("instance_url");

        String endpoint = instanceUrl + "/services/data/v58.0/sobjects/User/";

        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Create JSON payload
        JSONObject userJson = new JSONObject();
        userJson.put("Username", email);
        userJson.put("Email", email);
        userJson.put("LastName", name);
        userJson.put("Alias", name.substring(0, Math.min(name.length(), 8)));
        userJson.put("TimeZoneSidKey", "America/Los_Angeles");
        userJson.put("LocaleSidKey", "en_US");
        userJson.put("EmailEncodingKey", "UTF-8");
        userJson.put("LanguageLocaleKey", "en_US");
        userJson.put("ProfileId", "00ebn000004Po7ZAAS"); // Replace with actual Profile ID

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = userJson.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) { // 201 Created
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Parse the response to get the Salesforce ID
                JSONObject jsonResponse = new JSONObject(response.toString());
                String salesforceId = jsonResponse.getString("id");
                System.out.println("User created successfully in Salesforce. Salesforce ID: " + salesforceId);
                return salesforceId; // Return the Salesforce ID
            }
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                throw new Exception("Failed to create user. Response code: " + responseCode + ". Response: " + response);
            }
        }
    }




// Get User Using Id:
    public String getUser(String userId) throws Exception {
        // Get access token and instance URL dynamically
        Map<String, String> tokenData = getAccessToken();
        String accessToken = tokenData.get("access_token");
        String instanceUrl = tokenData.get("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new Exception("Access token or instance URL is null");
        }

        // Salesforce User retrieval endpoint with the user ID
        String endpoint = instanceUrl + "/services/data/v58.0/sobjects/User/" + userId;
        System.out.println(endpoint+"endpoint");
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString(); // Return the raw JSON string
            }
        } else {
            throw new Exception("Failed to retrieve user. Response code: " + responseCode);
        }
    }


    // Delete Method:
    public static String deleteUser(String userId) throws Exception {
        System.out.println("Starting the delete user process...");

        // Get access token and instance URL dynamically
        Map<String, String> tokenData = getAccessToken();
        String accessToken = tokenData.get("access_token");
        String instanceUrl = tokenData.get("instance_url");

        String endpoint = instanceUrl + "/services/data/v58.0/sobjects/User/" + userId;
        System.out.println("Endpoint: " + endpoint);

        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-Type", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_NO_CONTENT || responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("User deleted successfully in Salesforce. User ID: " + userId);
            return userId;
        } else {
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            throw new Exception("Failed to delete user. Response code: " + responseCode + ". Response: " + response);
        }
    }







    public static String updateUser(String userId, String name, String email) throws Exception {
        // Get access token and instance URL dynamically
        Map<String, String> tokenData = getAccessToken();
        String accessToken = tokenData.get("access_token");
        String instanceUrl = tokenData.get("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new Exception("Access token or instance URL is null");
        }

        // Debugging logs to check the values
        System.out.println("Access Token: " + accessToken);
        System.out.println("Instance URL: " + instanceUrl);
        System.out.println("User ID: " + userId);

        // Salesforce User update endpoint with the user ID
        String endpoint = instanceUrl + "/services/data/v58.0/sobjects/User/" + userId;
        System.out.println("Endpoint URL: " + endpoint);  // Debugging line to check the endpoint URL

        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set the request method to PATCH
        conn.setRequestMethod("PATCH");  // Corrected to PATCH instead of POST
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Create JSON payload for the fields you want to update
        JSONObject userJson = new JSONObject();
        userJson.put("Email", email);
        userJson.put("LastName", name);

        // Send JSON data
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = userJson.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error writing the request body: " + e.getMessage());
        }

        // Get response code and process response
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) { // 204 No Content means success
            System.out.println("User updated successfully.");
            return "User updated successfully with ID: " + userId;
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Error response: " + response.toString());
                throw new Exception("Failed to update user. Response code: " + responseCode + ". Response: " + response.toString());
            }
        }
    }






















    private static HttpServer server;
    private static final int PORT = 8080;
    private static final String AUTHORIZATION_ENDPOINT = "https://dream-force-2896.my.salesforce.com/services/oauth2/authorize";
    private static final String CLIENT_IDs = "3MVG9GBhY6wQjl2vLS.SZ89FXpIlJpPsWM55SLvawLtF8xILvdsmp9CvtGPKpohwOH8kYXNejmVZaE1JrFW1.";
    private static final String REDIRECT_URIs = "https://localhost/callback";
    private static String authorizationCode;

    public static void code() throws Exception {
        startCallbackServer();  // Start a simple local server to handle the redirect

        // Generate and print the authorization URL
        String authUrl = AUTHORIZATION_ENDPOINT
                + "?response_type=code"
                + "&client_id=" + URLEncoder.encode(CLIENT_IDs, StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URIs, StandardCharsets.UTF_8)
                + "&scope=id";
        System.out.println("Go to this URL to authorize: " + authUrl);

        // Wait until authorizationCode is set by the callback server
        while (authorizationCode == null) {
            Thread.sleep(1000); // Poll every second
        }
        System.out.println("Authorization Code: " + authorizationCode);
    }

    private static synchronized void startCallbackServer() throws Exception {
        if (server == null) { // Ensure server is only initialized once
            server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
            server.createContext("/callback", exchange -> {
                authorizationCode = exchange.getRequestURI().getQuery().split("=")[1];  // Extract code from query
                String response = "Authorization successful! You can close this window.";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            });
            server.setExecutor(Executors.newSingleThreadExecutor());
            server.start();
            System.out.println("Callback server started on port " + PORT);
        } else {
            System.out.println("Callback server is already running.");
        }
    }

}




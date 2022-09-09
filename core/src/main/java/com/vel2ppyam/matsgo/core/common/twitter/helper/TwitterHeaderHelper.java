package com.vel2ppyam.matsgo.core.common.twitter.helper;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class TwitterHeaderHelper {
    @Value("${twitter.api-key}")
    private static String oAuthConsumerKey;

    @Value("${twitter.secret-key}")
    private static String secretKey;

    @Value("${twitter.bearer-token}")
    private static String bearerToken;

    @Value("${twitter.access-token}")
    private static String accessToken;

    @Value("${twitter.secret-token}")
    private static String secretToken;
    private static final String oauthSignatureMethod = "HMAC-SHA1";
    private static final String oauthVersion = "1.0";

    private static final String oauth_consumer_key = "oauth_consumer_key";
    private static final String oauth_token = "oauth_token";
    private static final String oauth_signature_method = "oauth_signature_method";
    private static final String oauth_timestamp = "oauth_timestamp";
    private static final String oauth_nonce = "oauth_nonce";
    private static final String oauth_version = "oauth_version";
    private static final String oauth_signature = "oauth_signature";
    private static final String HMAC_SHA1 = "HmacSHA1";


    public static String getAuthorization(String httpMethod, String url, Map<String, String> requestParams) {
        StringBuilder base = new StringBuilder();
        String nonce = getNonce();
        String timestamp = getTimestamp();
        String baseSignatureString = generateSignatureBaseString(httpMethod, url, requestParams, nonce, timestamp);
        String signature = encryptUsingHmacSHA1(baseSignatureString);
        base.append("OAuth ");
        append(base, oauth_consumer_key, oAuthConsumerKey);
        append(base, oauth_token, accessToken);
        append(base, oauth_signature_method, oauthSignatureMethod);
        append(base, oauth_timestamp, timestamp);
        append(base, oauth_nonce, nonce);
        append(base, oauth_version, oauthVersion);
        append(base, oauth_signature, signature);
        base.deleteCharAt(base.length() - 1);
        return base.toString();
    }

    private static String encryptUsingHmacSHA1(String input) {
        String secret = new StringBuilder().append(encode(secretKey)).append("&").append(encode(secretToken)).toString();
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            mac.init(key);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
        byte[] signatureBytes = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(signatureBytes));
    }

    private static String generateSignatureBaseString(String httpMethod, String url, Map<String, String> requestParams, String nonce, String timestamp) {
        Map<String, String> params = new HashMap<>();
        requestParams.entrySet().forEach(entry -> {
            put(params, entry.getKey(), entry.getValue());
        });
        put(params, oauth_consumer_key, oAuthConsumerKey);
        put(params, oauth_nonce, nonce);
        put(params, oauth_signature_method, oauthSignatureMethod);
        put(params, oauth_timestamp, timestamp);
        put(params, oauth_token, accessToken);
        put(params, oauth_version, oauthVersion);
        Map<String, String> sortedParams = params.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        StringBuilder base = new StringBuilder();
        sortedParams.entrySet().forEach(entry -> {
            base.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        });
        base.deleteCharAt(base.length() - 1);
        String baseString = httpMethod.toUpperCase() + "&" + encode(url) + "&" + encode(base.toString());
        return baseString;
    }

    private static void append(StringBuilder builder, String key, String value) {
        builder.append(encode(key)).append("=\"").append(encode(value)).append("\",");
    }

    private static void put(Map<String, String> map, String key, String value) {
        map.put(encode(key), encode(value));
    }

    private static String encode(String value) {
        String encoded = "";
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sb = "";
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                sb += "%2A";
            } else if (focus == '+') {
                sb += "%20";
            } else if (focus == '%' && i + 1 < encoded.length() && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                sb += '~';
                i += 2;
            } else {
                sb += focus;
            }
        }
        return sb.toString();
    }


    private static String getNonce() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        return generatedString;

    }

    private static String getTimestamp() {
        return Math.round((new Date()).getTime() / 1000.0) + "";
    }


}

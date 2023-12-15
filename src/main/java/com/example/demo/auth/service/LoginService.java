package com.example.demo.auth.service;

import com.example.demo.auth.dto.RegistrationDTO;
import com.example.demo.user.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {

    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserService userService;

    @Autowired
    public LoginService(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    public void socialLogin(String code, String registrationId) {
        String accessToken = getAccessToken(code, registrationId);
        System.out.println("accessToken = " + accessToken);

        JsonNode userInfo = getUserInfo(accessToken, registrationId);
        System.out.println("userInfo = " + userInfo);

        String id = userInfo.get("id").asText();
        String name = userInfo.get("name").asText();
        String email = userInfo.get("email").asText();
        RegistrationDTO registrationDTO = new RegistrationDTO(registrationId, id, name, email);

        userService.updateUser(registrationDTO);
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        String prefix = "spring.security.oauth2.client.registration." + registrationId;
        String clientId = env.getProperty(prefix + ".client-id");
        String clientSecret = env.getProperty(prefix + ".client-secret");
        String redirectUri = env.getProperty(prefix + ".redirect-uri");
        String tokenUri = env.getProperty(prefix + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserInfo(String accessToken, String registrationId) {
        String resourceUri = env.getProperty("spring.security.oauth2.client.registration."+registrationId+".userinfo-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}

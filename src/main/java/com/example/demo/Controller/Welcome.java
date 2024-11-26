package com.example.demo.Controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.awt.*;

@Controller
public class Welcome {
    private final OAuth2AuthorizedClientRepository authorizedClientRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    public Welcome(OAuth2AuthorizedClientRepository authorizedClientRepository, ClientRegistrationRepository clientRegistrationRepository) {
        this.authorizedClientRepository = authorizedClientRepository;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/welcome")
    public String welcomePage() {
        return "books/welcome";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request) {
        if (authentication != null) {
            OAuth2AuthorizedClient authorizedClient = authorizedClientRepository.loadAuthorizedClient("google", authentication, request);
            if (authorizedClient != null) {
                String accessToken = authorizedClient.getAccessToken().getTokenValue();
                revokeToken(accessToken);
            }
            SecurityContextHolder.clearContext();
        }
        return "redirect:/login";
    }

    private void revokeToken(String accessToken) {
        String revokeUrl = "https://oauth2.googleapis.com/revoke";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(revokeUrl, request, String.class);
    }
}

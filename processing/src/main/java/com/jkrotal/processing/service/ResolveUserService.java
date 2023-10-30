package com.jkrotal.processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResolveUserService {

    private final UserInfoRestTemplateFactory restTemplateFactory;

    private final ResourceServerProperties resource;

    public Long resolveUserId() {
        OAuth2RestTemplate userInfoRestTemplate = restTemplateFactory.getUserInfoRestTemplate();
        Map<String, Object> response = userInfoRestTemplate.getForEntity(resource.getUserInfoUri(), Map.class).getBody();
        return Optional.ofNullable(response)
                .map(r -> r.get("principal"))
                .map(Map.class::cast)
                .map(v -> (Integer) v.get("userId"))
                .map(Long::valueOf)
                .orElse(null);
    }
}
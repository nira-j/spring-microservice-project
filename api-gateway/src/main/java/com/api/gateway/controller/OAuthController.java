package com.api.gateway.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class OAuthController {

    @GetMapping("/profile")
    public Mono<Map<String, Object>> profile(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) return Mono.just(Map.of("authenticated", false));

        return Mono.just(Map.of(
            "name", principal.getAttribute("name"),
            "login", principal.getAttribute("login"),
            "id", principal.getAttribute("id"),
            "attributes", principal.getAttributes()
        ));
    }
}

package com.ecommerce.user.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @GetMapping("/homepage")
    public Map<String,Object> me(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) return Map.of("authenticated", false);
        String res = userService.addUser(principal,"github");
        
        return Map.of(
            "name", principal.getAttribute("name"),
            "login", principal.getAttribute("login"),
            "id", principal.getAttribute("id"),
            "attrs", principal.getAttributes(),
            "status",res
        );
    }
    
    @GetMapping("/login/oauth2/code/github")
    public Map<String,Object> mlogin(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) return Map.of("authenticated", false);
        String res = userService.addUser(principal,"github");
        
        return Map.of(
            "name", principal.getAttribute("name"),
            "login", principal.getAttribute("login"),
            "id", principal.getAttribute("id"),
            "attrs", principal.getAttributes(),
            "status",res
        );
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(),
                                    HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID", description = "Returns user details")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id){
        log.info("Request received for user: {}", id);

        log.trace("This is TRACE level - Very detailed logs");
        log.debug("This is DEBUG level - Used for development debugging");
        log.info("This is INFO level - General system information");
        log.warn("This is WARN level - Something might be wrong");
        log.error("This is ERROR level - Something failed");

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @RequestBody UserRequest updateUserRequest){
        boolean updated = userService.updateUser(id, updateUserRequest);
        if (updated)
            return ResponseEntity.ok("User updated successfully");
        return ResponseEntity.notFound().build();
    }
}

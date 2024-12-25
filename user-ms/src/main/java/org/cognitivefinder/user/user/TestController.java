package org.cognitivefinder.user.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // Public endpoint accessible to everyone
    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint accessible by anyone.";
    }

    // Client-specific endpoint
    @GetMapping("/client")
    public String clientEndpoint() {
        return "This is a client endpoint accessible by users with the CLIENT role.";
    }

    // Admin-specific endpoint
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is an admin endpoint accessible by users with the ADMIN role.";
    }
}

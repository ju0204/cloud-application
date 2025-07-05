package io.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.test.entity.User;
import io.test.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserTestController {
    private final UserService userService;
    
    // 초기 데이터 세팅
    @GetMapping("/users/init-data")
    public ResponseEntity<String> initUsers() {

    	userService.initUsers();
        
    	return ResponseEntity.ok("complete set user data");
    }
    
    // 단일 사용자 조회 API
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // CPU 과부화 API
    @GetMapping("/calculate")
    public ResponseEntity<Map<String, Object>> calculate(@RequestParam int complexity) {
        long start = System.currentTimeMillis();
        double result = 0;
        for (int i = 0; i < complexity * 1_000_000; i++) {
            result += Math.sqrt(i) * Math.sin(i);
        }
        long duration = System.currentTimeMillis() - start;
        
        Map<String, Object> response = new HashMap<>();
        response.put("result", result);
        response.put("processingTimeMs", duration);
        return ResponseEntity.ok(response);
    }

    // 대량 사용자 생성 API
    @PostMapping("/users/bulk")
    public ResponseEntity<List<User>> createBulkUsers(@RequestBody List<User> users) {
        List<User> created = userService.createBulk(users);
        return ResponseEntity.ok(created);
    }

    // 헬스 체크
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
    
}

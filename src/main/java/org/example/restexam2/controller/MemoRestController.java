package org.example.restexam2.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/memos")
public class MemoRestController {

    private final Map<Long, String> memos = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @PostMapping
    public ResponseEntity<Map<String, Long>> createMemo(@RequestBody Map<String, String> payload) {
        String content = payload.get("memo");
        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Long id = counter.incrementAndGet();
        memos.put(id, content);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping
    public ResponseEntity<Map<Long, String>> getMemos() {
        return ResponseEntity.ok(memos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getMemo(@PathVariable("id") Long id) {
        String memo = memos.get(id);
        if (memo != null) {
            return ResponseEntity.ok(memo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


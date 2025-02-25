package org.example.restexam2.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api2/memos")
public class MemoRest2Controller {

    private final Map<Long, String> memos = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();
    // AtomicLong은 카운터, 시퀀스 생성기, 동시성이 높은 환경에서의
    // ID 생성 등에 주로 사용됩니다. 멀티스레드 애플리케이션에서 동기화
    // 오버헤드를 줄이면서 안전한 숫자 연산을 수행할 때 유용


    // curl -X POST -H "Content-type: text/plain" -d "첫번째 메모" http://localhost:8080/api2/memos
    @PostMapping
    public Long createMemo(@RequestBody String content) {
        Long id = counter.incrementAndGet(); // 값을 1 증가시키고 반환
        memos.put(id, content);

        return id;
    }

    // curl -X GET http://localhost:8080/api2/memos
    @GetMapping
    public Map<Long, String> getMemos() {
        return memos;
    }


    // curl -X GET http://localhost:8080/api2/memos/1
    @GetMapping("/{id}")
    public String getMemos2(@PathVariable(name = "id") Long id) {
        return memos.getOrDefault(id, "해당 메모를 찾을수 없어요");
    }


    // curl -X PUT -H "Content-type: text/plain" -d "수정된 메모" http://localhost:8080/api2/memos/1
    @PutMapping("/{id}")
    public String updateMemo(@PathVariable(name = "id") Long id, @RequestBody String content) {
        if (memos.containsKey(id)) {
            memos.put(id, content);
            return "메모 수정 성공";
        }
        return "메모를 찾을 수 없습니다";
    }

    // curl -X DELETE http://localhost:8080/api2/memos/1
    @DeleteMapping("/{id}")
    public String deleteMemo(@PathVariable(name = "id") Long id) {
        if (memos.remove(id) != null) {
            return "메모 삭제 성공";
        }
        return "메모를 찾을 수 없습니다";
    }







}

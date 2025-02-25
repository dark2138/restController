package org.example.restexam2.controller;

import org.example.restexam2.domain.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api3/memos")
public class MemoRest3Controller {

    private final Map<Long, Memo> memos = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();
    // AtomicLong은 카운터, 시퀀스 생성기, 동시성이 높은 환경에서의
    // ID 생성 등에 주로 사용됩니다. 멀티스레드 애플리케이션에서 동기화
    // 오버헤드를 줄이면서 안전한 숫자 연산을 수행할 때 유용


    // curl -X POST -H "Content-type: application/json" -d "{\"content\" : \"첫번째 메모\"}" http://localhost:8080/api3/memos
    @PostMapping
    public Long createMemo(@RequestBody Memo memo) {
        Long id = counter.incrementAndGet(); // 값을 1 증가시키고 반환
        memo.setId(id);
        memos.put(id, memo);
        return id;
    }

    // curl -X GET http://localhost:8080/api3/memos
    @GetMapping
    public Map<Long, Memo> getMemos() {
        return memos;
    }


    // curl -X GET http://localhost:8080/api3/memos/1
    @GetMapping("/{id}")
    public Memo getMemos2(@PathVariable(name = "id") Long id) {
        return memos.getOrDefault(id, null );
    }



    // curl -X PUT -H "Content-type: application/json" -d "{\"content\" : \"수정된 메모\"}"  http://localhost:8080/api3/memos/1
    @PutMapping("/{id}")
    public String updateMemo(@PathVariable(name = "id") Long id, @RequestBody Memo memo) {
        if (memos.containsKey(id)) {
            memo.setId(id);
            memos.put(id, memo);
            return "메모 수정 성공";
        }
        return "메모를 찾을 수 없습니다";
    }

    // curl -X DELETE http://localhost:8080/api3/memos/1
    @DeleteMapping("/{id}")
    public String deleteMemo(@PathVariable(name = "id") Long id) {
        if (memos.remove(id) != null) {
            return "메모 삭제 성공";
        }
        return "메모를 찾을 수 없습니다";
    }







}

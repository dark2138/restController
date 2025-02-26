package org.example.restexam2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.restexam2.domain.UploadInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@Slf4j
public class FileController {

    // 파일 다운로드
    @GetMapping("/file/download")
    public void downloadfile(HttpServletResponse response) {
       Path path =  Paths.get("C:\\d\\download\\12.jpg");

       response.setContentType("image/jpeg");

       try(InputStream inputStream = Files.newInputStream(path)){
           // try-with-resources 구문을 사용하여 InputStream을 생성합니다.
           StreamUtils.copy(inputStream, response.getOutputStream());
           //  입력 스트림의 내용을 응답의 출력 스트림으로 복사합니다.
           response.flushBuffer();
           // 버퍼에 남아있는 데이터를 모두 클라이언트로 전송합니다.
       }catch (IOException e){
           log.error("파일 다운로드 중 오류 : "+e.getMessage());
           e.printStackTrace();
       }
       /*
try-with-resources 구문은 주로 다음과 같은 상황에서 사용합니다:

자원 관리가 필요한 경우: 파일 입출력, 데이터베이스 연결, 네트워크 소켓 등 사용 후 반드시 닫아야 하는 리소스를 다룰 때 유용합니다24.

AutoCloseable 인터페이스 구현 객체: try-with-resources는 AutoCloseable 인터페이스를 구현한 객체에 대해 사용할 수 있습니다24.

자동 자원 해제: 명시적으로 close() 메서드를 호출하지 않아도 자동으로 리소스를 해제하고 싶을 때 사용합니다24.

예외 처리와 함께: 리소스 사용 중 발생할 수 있는 예외를 처리하면서 동시에 안전하게 리소스를 닫고 싶을 때 적합합니다34.

코드 간결성 향상: 기존의 try-catch-finally 구문보다 더 간결하고 가독성 있는 코드를 작성하고자 할 때 사용합니다28.

메모리 누수 방지: 리소스를 명시적으로 닫지 않아 발생할 수 있는 메모리 누수를 방지하고자 할 때 효과적입니다28.

다중 리소스 관리: 여러 개의 리소스를 동시에 관리해야 할 때, 세미콜론으로 구분하여 한 번에 여러 리소스를 선언하고 관리할 수 있습니다45.

try-with-resources 구문을 사용하면 코드가 더 안전하고 간결해지며, 자원 관리에 대한 실수를 줄일 수 있습니다.
*/
    }




    // 파일 업로드
    @PostMapping("/file/upload")
    public ResponseEntity<String> uploadfile(
              @RequestParam(name = "file" )MultipartFile file
           // , @RequestParam(name = "info", required = false) String infoJson
            , @RequestPart(name = "info", required = false) UploadInfo uploadInfo) {
        log.info("파일 이름 ::: "+file.getOriginalFilename());
        log.info("파일 설명 ::: " + uploadInfo.getTag() + " :: " + uploadInfo.getDescription());
/*
        if (infoJson != null && !infoJson.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                UploadInfo uploadInfo = objectMapper.readValue(infoJson, UploadInfo.class);
                log.info("파일 설명 ::: " + uploadInfo.getTag() + " :: " + uploadInfo.getDescription());
            } catch (JsonProcessingException e) {
                log.error("JSON 파싱 오류", e);
            }
        }

 */

        String originalFilename = file.getOriginalFilename();
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String newFilename = timestamp + "_" + randomString + "_" + originalFilename;


        try (InputStream inputStream =  file.getInputStream() ) {
            StreamUtils.copy(inputStream,
                    new FileOutputStream("C:\\d\\upload\\" + newFilename));

            return ResponseEntity.ok().body("파일저장 성공 ::: " + newFilename);



        }catch (IOException e){
         return ResponseEntity.badRequest().body("파일압로드 실패 :: "+ file.getOriginalFilename());
        }
    }

    // curl -X POST http://localhost:8080/file/upload -F "file=@C:/d/12.jpg" -F "info={\"tag\":\"example\",\"description\":\"test upload\"}"


    //curl -X POST http://localhost:8080/upload -H "Content-Type: multipart/form-data" -F "file=@C:/temp/dog.jpg"  -F "info=@C:/temp/info.json;type=application/json"
}

package com.spvm.gemini_chat;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/qna")
@AllArgsConstructor
public class AIController {

    private final QnAService qnAService;

    /*JSON object is converted to Map with the help of @RequestBody*/
    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String,String> playload){
         //What is the question?
           String question = playload.get("question");
        //What is the answer?
           String answer = qnAService.getAnswer(question);
        //Return the answer
           return ResponseEntity.ok(answer);
  }
}

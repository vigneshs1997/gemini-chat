package com.spvm.gemini_chat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class QnAService {
    //Access to APIkey and URL [Gemini]
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    /*WebClient Object is automatically injected from the dependency of WebClient*/
    public QnAService(WebClient.Builder webClient) {
        /*Builder is used to create design of WebClient like request
        * build() is used to create actual object from the design*/
        this.webClient = webClient.build();
    }

    public String getAnswer(String question) {
        //Construct the request payload
        Map<String,Object> requestBody = Map.of(
                "contents",new Object[]{
                        Map.of(
                            "parts",new Object[]{
                                    Map.of("text",question)
                                }
                        )
                }
        );
        //Make API Call
        String response = webClient.post()
                .uri(geminiApiUrl)
                .header("x-goog-api-key",geminiApiKey)
                .header("Content-Type","application/json")
                .bodyValue(requestBody)//Payload
                .retrieve()//Expecting response from respective request
                .bodyToMono(String.class)//Convert response into mono which is a reactive wrapper(String)
                .block();//Convert reactive mono into actual response
        //Return response
        return response;
    }
}

package com.hellFire.SpringAi;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getResponse(String prompt){
        String response =  chatModel.call(prompt);
        System.out.println(response);

        return response;
    }
    public String getResponseOptions(String prompt){

        String response =  chatModel.call(
                new Prompt(
                        prompt,
                        // Start from the configured model's own defaults (keeps the provider's
                        // options type and model name) and override only the temperature.
                        // Works with any provider: Gemini, OpenAI, Ollama...
                        chatModel.getOptions().mutate()
                                .temperature(0.4)
                                .build()
                )).getResult().getOutput().getText();

        System.out.println(response);

        return response;
    }


}

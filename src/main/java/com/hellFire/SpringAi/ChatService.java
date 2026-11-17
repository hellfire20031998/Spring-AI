package com.hellFire.SpringAi;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public List<String> getList(String subject) {
        ListOutputConverter converter = new ListOutputConverter();

        // 1. Put the converter's format instructions into the prompt
        PromptTemplate template = new PromptTemplate("""
            List five {subject}.
            {format}
            """);
        Prompt prompt = template.create(Map.of(
                "subject", subject,
                "format", converter.getFormat()));

        // 2. Call Gemini, then 3. turn the text reply into a List
        String text = chatModel.call(prompt).getResult().getOutput().getText();
        return converter.convert(text);
    }


}

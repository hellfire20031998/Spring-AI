package com.hellFire.SpringAi;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {
    private final ChatModel chatModel;


    public RecipeService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients,
                               String cuisine,
                               String dietaryRestrictions){
        var template= """
                I want to create a recipe using the following ingredients:{ingredients}.
                The cuisine type i prefer is{cuisine}.
                Please consider the following dietary restrictions: {dietaryRestrictions}.
                Please provide me with a detailed recipe including title,list of ingredients, and cooking instructions
             
                """;


        Map<String,Object> params= Map.of(
                "ingredients",ingredients,
                "cuisine",cuisine,
                "dietaryRestrictions",dietaryRestrictions
        );
        PromptTemplate promptTemplate = new PromptTemplate(template);

        Prompt prompt = promptTemplate.create(params);
       return chatModel.call(prompt).getResult().getOutput().getText();
    }

    /**
     * Same idea as createRecipe, but returns a typed Recipe instead of free text.
     * BeanOutputConverter adds a JSON Schema of Recipe to the prompt (getFormat)
     * and parses the model's JSON reply into a Recipe (convert).
     */
    public Recipe createRecipeStructured(String ingredients,
                                         String cuisine,
                                         String dietaryRestrictions) {
        BeanOutputConverter<Recipe> converter = new BeanOutputConverter<>(Recipe.class);

        PromptTemplate promptTemplate = new PromptTemplate("""
                Create a recipe using the following ingredients: {ingredients}.
                The cuisine type I prefer is: {cuisine}.
                Please consider the following dietary restrictions: {dietaryRestrictions}.
                {format}
                """);

        Prompt prompt = promptTemplate.create(Map.of(
                "ingredients", ingredients,
                "cuisine", cuisine,
                "dietaryRestrictions", dietaryRestrictions.isBlank() ? "none" : dietaryRestrictions,
                "format", converter.getFormat()));

        String text = chatModel.call(prompt).getResult().getOutput().getText();
        return converter.convert(text);
    }
}

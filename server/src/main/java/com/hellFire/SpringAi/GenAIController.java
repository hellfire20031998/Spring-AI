package com.hellFire.SpringAi;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin()
public class GenAIController {
    ChatService chatService;
    ImageService imageService;
    RecipeService recipeService;

    public GenAIController(ImageService imageService,ChatService chatService,RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService=recipeService;
        this.chatService = chatService;
    }

    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello";
    }

    @GetMapping("ask-ai")
    public String getResponse(@RequestParam String prompt){
        System.out.println(prompt);
        return chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt){
        return chatService.getResponseOptions(prompt);
    }

    @GetMapping("generate-image")
    public void generateImage(HttpServletResponse response, @RequestParam String prompt) throws IOException {
         ImageResponse imageResponse= imageService.generateImage(prompt);
         String url=  imageResponse.getResult().getOutput().getUrl();
         response.sendRedirect(url);
    }

    @GetMapping("generate-image-options")
    public List<String> generateImageOptions(HttpServletResponse response,
                                             @RequestParam String prompt,
                                             @RequestParam(value = "quality",defaultValue = "hd") String quality,
                                             @RequestParam(defaultValue = "1") int n,
                                             @RequestParam(defaultValue = "1024") int width,
                                             @RequestParam(defaultValue = "1024") int height) throws IOException {
        ImageResponse imageResponse= imageService.generateImageOptions(prompt,quality,n,width,height);
        List<String>imageUrls = imageResponse.getResults().stream()
                .map(result->result.getOutput().getUrl())
                .collect(Collectors.toList());
        System.out.println(prompt);
        return imageUrls;

    }

    @GetMapping("recipe-creator")
    public String recipeCreator(@RequestParam String ingredients,
                                      @RequestParam(defaultValue = "any") String cuisine,
                                      @RequestParam(defaultValue = "")String dietaryRestriction){

        System.out.println(ingredients);
        return recipeService.createRecipe(ingredients,cuisine,dietaryRestriction);
    }




}

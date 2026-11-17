package com.hellFire.SpringAi;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;

/**
 * Structured recipe returned by the AI.
 * BeanOutputConverter turns this record into a JSON Schema that is sent to the model,
 * then parses the model's JSON reply back into an instance of it.
 * The descriptions below end up in that schema and guide what the model writes.
 */
public record Recipe(
        @JsonPropertyDescription("Short, appetising name of the dish")
        String title,

        @JsonPropertyDescription("Cuisine style, e.g. Indian, Italian")
        String cuisine,

        @JsonPropertyDescription("Number of people the recipe serves")
        int servings,

        @JsonPropertyDescription("Total time in minutes, including preparation")
        int cookingTimeMinutes,

        @JsonPropertyDescription("Each ingredient with its quantity, e.g. '1 cup basmati rice'")
        List<String> ingredients,

        @JsonPropertyDescription("Cooking instructions, one step per item, in order, without step numbers")
        List<String> steps) {
}

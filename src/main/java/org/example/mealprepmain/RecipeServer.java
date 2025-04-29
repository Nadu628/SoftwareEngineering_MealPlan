package org.example.mealprepmain;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class RecipeServer {

    private static final boolean MOCK_MODE = false;
    private static final String API_KEY = "340efd72260a4e5f8777eda177c0e8c6";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    public String searchRecipe(String search, User user) throws Exception {
        if (MOCK_MODE) {
            return getMockSearchResponse();
        }

        StringBuilder urlBuilder = new StringBuilder(BASE_URL + "recipes/complexSearch?query=" + search + "&apiKey=" + API_KEY);
        urlBuilder.append("&addRecipeInformation=true");

        if (user.getPreferences() != null && !user.getPreferences().isEmpty()) {
            urlBuilder.append("&diet=").append(String.join(",", user.getPreferences()));
        }

        String url = urlBuilder.toString();
        System.out.println("constructed api request: " + url);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request)) {
                System.out.println("http response: " + response.getCode());
                if (response.getCode() != 200) {
                    throw new Exception("Error: " + response.getCode() + " " + response.getReasonPhrase());
                }
                String result = EntityUtils.toString(response.getEntity());
                System.out.println("raw api response: " + result);
                return result;
            }
        }
    }

    public String getRecipeInfo(int recipeId) throws Exception {
        if (MOCK_MODE) {
            return getMockRecipeInfo(recipeId);
        }

        String url = BASE_URL + "recipes/" + recipeId + "/information?apiKey=" + API_KEY;
        System.out.println("fetching details for recipe id: " + recipeId);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request)) {
                System.out.println("http response: " + response.getCode());
                if (response.getCode() != 200) {
                    throw new Exception("Error: " + response.getCode() + " " + response.getReasonPhrase());
                }
                String result = EntityUtils.toString(response.getEntity());
                System.out.println("raw api response: " + result);
                return result;
            }
        }
    }

    private String getMockSearchResponse() {
        return """
    {
        "results": [
            { "id": 123, "title": "Mock Pizza", "image": "https://spoonacular.com/recipeImages/123-556x370.jpg" },
            { "id": 124, "title": "Mock Salad", "image": "https://spoonacular.com/recipeImages/124-556x370.jpg" },
            { "id": 125, "title": "Mock Burger", "image": "https://spoonacular.com/recipeImages/125-556x370.jpg" },
            { "id": 126, "title": "Mock Pasta", "image": "https://spoonacular.com/recipeImages/126-556x370.jpg" },
            { "id": 127, "title": "Mock Tacos", "image": "https://spoonacular.com/recipeImages/127-556x370.jpg" },
            { "id": 128, "title": "Mock Sushi", "image": "https://spoonacular.com/recipeImages/128-556x370.jpg" },
            { "id": 129, "title": "Mock Fried Rice", "image": "https://spoonacular.com/recipeImages/129-556x370.jpg" },
            { "id": 130, "title": "Mock Ice Cream", "image": "https://spoonacular.com/recipeImages/130-556x370.jpg" },
            { "id": 131, "title": "Mock Steak", "image": "https://spoonacular.com/recipeImages/131-556x370.jpg" },
            { "id": 132, "title": "Mock Pancakes", "image": "https://spoonacular.com/recipeImages/132-556x370.jpg" }
        ]
    }
    """;
    }

    private String getMockRecipeInfo(int recipeId) {
        return switch (recipeId) {
            case 123 -> """
                {
                    "id": 123,
                    "title": "Mock Pizza",
                    "image": "https://spoonacular.com/recipeImages/123-556x370.jpg",
                    "instructions": [
                        { "steps": [
                            { "number": 1, "step": "Preheat oven to 400F" },
                            { "number": 2, "step": "Spread tomato sauce on dough" },
                            { "number": 3, "step": "Add cheese and toppings" },
                            { "number": 4, "step": "Bake for 15 minutes" }
                        ]}
                    ],
                    "extendedIngredients": [
                        { "id": 1, "name": "cheese", "image": "cheddar.jpg" },
                        { "id": 2, "name": "pizza dough", "image": "pizza-dough.jpg" },
                        { "id": 3, "name": "tomato sauce", "image": "tomato-sauce.jpg" }
                    ]
                }
            """;
            case 124 -> """
                {
                    "id": 124,
                    "title": "Mock Salad",
                    "image": "https://spoonacular.com/recipeImages/124-556x370.jpg",
                    "instructions": [
                        { "steps": [
                            { "number": 1, "step": "Chop lettuce and veggies" },
                            { "number": 2, "step": "Mix in bowl with dressing" }
                        ]}
                    ],
                    "extendedIngredients": [
                        { "id": 4, "name": "lettuce", "image": "lettuce.jpg" },
                        { "id": 5, "name": "tomatoes", "image": "tomato.jpg" },
                        { "id": 6, "name": "cucumber", "image": "cucumber.jpg" }
                    ]
                }
            """;
            case 125 -> """
                {
                    "id": 125,
                    "title": "Mock Burger",
                    "image": "https://spoonacular.com/recipeImages/125-556x370.jpg",
                    "instructions": [
                        { "steps": [
                            { "number": 1, "step": "Grill beef patty" },
                            { "number": 2, "step": "Assemble burger with bun and toppings" }
                        ]}
                    ],
                    "extendedIngredients": [
                        { "id": 7, "name": "ground beef", "image": "ground-beef.jpg" },
                        { "id": 8, "name": "burger bun", "image": "bun.jpg" },
                        { "id": 9, "name": "lettuce", "image": "lettuce.jpg" }
                    ]
                }
            """;
            case 126 -> """
                {
                    "id": 126,
                    "title": "Mock Pasta",
                    "image": "https://spoonacular.com/recipeImages/126-556x370.jpg",
                    "instructions": [
                        { "steps": [
                            { "number": 1, "step": "Boil pasta" },
                            { "number": 2, "step": "Mix pasta with sauce" }
                        ]}
                    ],
                    "extendedIngredients": [
                        { "id": 10, "name": "spaghetti", "image": "spaghetti.jpg" },
                        { "id": 11, "name": "pasta sauce", "image": "pasta-sauce.jpg" }
                    ]
                }
            """;
            case 127 -> """
                {
                    "id": 127,
                    "title": "Mock Tacos",
                    "image": "https://spoonacular.com/recipeImages/127-556x370.jpg",
                    "instructions": [
                        { "steps": [
                            { "number": 1, "step": "Cook ground beef" },
                            { "number": 2, "step": "Fill tortillas with beef and toppings" }
                        ]}
                    ],
                    "extendedIngredients": [
                        { "id": 12, "name": "tortillas", "image": "tortilla.jpg" },
                        { "id": 13, "name": "ground beef", "image": "ground-beef.jpg" },
                        { "id": 14, "name": "cheddar cheese", "image": "cheddar.jpg" }
                    ]
                }
            """;
            default -> """
                {
                    "id": -1,
                    "title": "Unknown Recipe",
                    "image": "",
                    "instructions": [],
                    "extendedIngredients": []
                }
            """;
        };
    }
}

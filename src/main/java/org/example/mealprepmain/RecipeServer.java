package org.example.mealprepmain;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class RecipeServer {

    private static final boolean MOCK_MODE = true; // <<< switch to false for real API
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
            return getMockRecipeInfo();
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
                {
                    "id": 123,
                    "title": "Mock Pizza",
                    "image": "https://spoonacular.com/recipeImages/123-556x370.jpg"
                },
                {
                    "id": 124,
                    "title": "Mock Salad",
                    "image": "https://spoonacular.com/recipeImages/124-556x370.jpg"
                }
            ]
        }
        """;
    }

    private String getMockRecipeInfo() {
        return """
        {
            "id": 123,
            "title": "Mock Pizza",
            "image": "https://spoonacular.com/recipeImages/123-556x370.jpg",
            "instructions": [
                {
                    "steps": [
                        { "number": 1, "step": "Mock Step 1" },
                        { "number": 2, "step": "Mock Step 2" }
                    ]
                }
            ],
            "extendedIngredients": [
                {
                    "id": 1001,
                    "name": "cheese",
                    "image": "cheddar.jpg"
                },
                {
                    "id": 1002,
                    "name": "tomato sauce",
                    "image": "tomato-sauce.jpg"
                }
            ]
        }
        """;
    }
}

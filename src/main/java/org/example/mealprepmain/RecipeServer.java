package org.example.mealprepmain;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;

import java.util.List;

public class RecipeServer {

    private static final String API_KEY = "340efd72260a4e5f8777eda177c0e8c6";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    //get recipes from search
    public String searchRecipe(String search, User user) throws Exception{
        //construct url for complex search
        StringBuilder urlBuilder = new StringBuilder(BASE_URL + "recipes/complexSearch?query=" + search + "&apiKey=" + API_KEY);
        urlBuilder.append("&addRecipeInformation=true");
        //dietray preferences if specified
        if(user.getPreferences() != null && !user.getPreferences().isEmpty()){
            urlBuilder.append("&diet=").append(String.join(",", user.getPreferences()));
        }

        //final constructed url
        String url = urlBuilder.toString();
        System.out.println("constructed api request: " + url);

        //create http client
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpGet request = new HttpGet(url);
            //execute request
            try (CloseableHttpResponse response = client.execute(request)) {
                //check response status
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

    public String getRecipeInfo(int recipeId) throws Exception{
        String url = BASE_URL + "recipes/" + recipeId + "/information?apiKey=" + API_KEY;
        System.out.println("fetching details for recipe id: " + recipeId);

        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpGet request = new HttpGet(url);
            try(CloseableHttpResponse response = client.execute(request)) {
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

}

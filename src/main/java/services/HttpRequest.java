package services;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class HttpRequest {
    private static HttpRequest instance;

    private HttpRequest() {}

    public synchronized static HttpRequest getInstance(){
        if(instance == null){ instance = new HttpRequest(); }
        return instance;
    }

    public String sendGetRequest(String api){
        try {
            URL url = new URL(api);
            URLConnection connection = url.openConnection();
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}

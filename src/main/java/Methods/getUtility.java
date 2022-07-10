package Methods;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getUtility {

    private HttpURLConnection connection;
    private Headers headers;

    public String run(String link) throws IOException, ParseException {

        startConnection(link);

        int responseCode = getResponseCode();
        getHeaders();
        String contentType = headers.getContentType();

        // If the response content is JSON:
        if(contentType != null) {
            if (contentType.equals("application/json")) {
                String response = jsonResponse();
                return response;
            }
        }
        return "";
    }

    public void startConnection(String link) throws IOException {
        URL url = new URL(link);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
    }

    public int getResponseCode() throws IOException {
        return connection.getResponseCode();
    }

    public void getHeaders() {

        headers = new Headers();

        headers.setContentLength(connection.getHeaderField("Content-Length"));
        headers.setContentType(connection.getHeaderField("Content-Type"));
        headers.setKeepAlive(connection.getHeaderField("Keep-Alive"));
        headers.setDate(connection.getHeaderField("Date"));
        headers.setConnection(connection.getHeaderField("Connection"));
    }

    public String jsonResponse() throws IOException, ParseException {

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer response = new StringBuffer();
        String inputLine;
        while((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return beautify(response.toString());
    }

    String beautify(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(json));
    }

    public String htmlFormattedHeaders() {

        String result = "<b>Content-Type: </b>" + headers.getContentType() + "<br><b>Content-Length: </b>" +
                headers.getContentLength() + "<br><b>Connection: </b>" + headers.getConnection() +
                "<br><b>Date: </b>" + headers.getDate() + "<br><b>Keep-Alive: </b>" + headers.getKeepAlive();

        return result;
    }

}

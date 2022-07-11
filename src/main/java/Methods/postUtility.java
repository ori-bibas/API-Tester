package Methods;

import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class postUtility extends getUtility {

    private HttpURLConnection connection;
    public Headers headers = new Headers();

    public String run(String link, String content) throws IOException, ParseException {

        startConnection(link);
        StringBuilder response = new StringBuilder();

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = content.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        getHeaders(headers, connection);

        int responseCode = getResponseCode();
        if(responseCode < 299) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            return beautify(response.toString());
        }

        return null;
    }

    public void startConnection(String link) throws IOException {
        URL url = new URL(link);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "Chrome");
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    public int getResponseCode() throws IOException {
        return connection.getResponseCode();
    }

}

package Methods;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class postUtility extends getUtility {

    private HttpURLConnection connection;
    private Headers headers;
    

    public void startConnection(String link) throws IOException {

        URL url = new URL(link);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

    }
}

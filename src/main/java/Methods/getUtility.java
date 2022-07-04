package Methods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class getUtility {

    private static HttpURLConnection connection;
    public Headers headers;

    public String retrieveResponse(String link) {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if(status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return responseContent.toString();
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

    public String buildFormattedString() {

        String result = "Content-Type: " + headers.getContentType() + "\n\n"
                        + "Content-Length: " + headers.getContentLength() + "\n\n"
                        + "Date: " + headers.getDate() + "\n\n"
                        + "Keep-Alive: " + headers.getKeepAlive() + "\n\n"
                        + "Connection: " + headers.getConnection();

        return result;
    }


}

package services.apis.lol;



import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Client {


    private URL url;
    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;

    private Client(URL url){
        this.url = url;
        this.httpClient = HttpClients.createDefault();
    }

    public Client(String url) throws MalformedURLException {
        this(new URL(url));
    }

    public void execute() throws IOException {
        try {
            HttpGet httpGet = new HttpGet(this.url.toString());
            this.response = this.httpClient.execute(httpGet);
        } catch (IOException ioexcepcion){
            this.response = null;
        }
    }

    public String getStringResponse() throws IOException {
        if(this.response == null) return null;
        return IOUtils.toString(this.response.getEntity().getContent(),"UTF-8");
    }



}

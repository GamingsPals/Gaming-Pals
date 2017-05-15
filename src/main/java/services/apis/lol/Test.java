package services.apis.lol;



import domain.Summoner;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;
import services.apis.lol.Builder.LeagueInfoBuilder;
import services.apis.lol.Builder.MatchBuilder;
import services.apis.lol.Builder.MatchDetailsBuilder;
import services.apis.lol.Builder.SummonerBuilder;
import services.apis.steam.Builder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class Test {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Builder builder = new Builder("76561197960434622");
        builder.load();

    }
}

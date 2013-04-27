import com.swtorserversstatus.model.Server;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 15/11/12
 * Time: 23:32
 * Purpose:
 */
public class test {

    public static void main(String[] args) {
        try{
            HashSet<Server> serverList = new HashSet<Server>();
            Document doc = Jsoup.connect("http://www.swtor.com/server-status").get();
            Elements euList = doc.select("#serverListEU");
            Elements euServers = euList.select("[data-status]");
            Elements usList = doc.select("#serverListUS");
            Elements usServers = usList.select("[data-status]");
            euServers.addAll(usServers);
                    for(Element e : euServers)
                    {
                        Server server = new Server();
                        server.setServerStatus(e.attr("data-status"));
                        server.setServerName(e.attr("data-name"));
                        server.setServerPopulation(Integer.parseInt(e.attr("data-population")));
                        server.setServerType(e.attr("data-type"));
                        server.setServerTimezone(e.attr("data-timezone"));
                        server.setServerLanguage(e.attr("data-language"));
                        serverList.add(server);
                    }
            System.out.println(serverList.size());







//            Server server = new Server();
//                            for (int i = 0; i < parser.getAttributeCount(); i++) {
//                                // System.out.println(parser.getAttributeName(i)+":"+parser.getAttributeValue(i));
//
//                                if (parser.getAttributeName(i).equals("data-status"))
//                                    server.setServerStatus(parser.getAttributeValue(i));
//                                if (parser.getAttributeName(i).equals("data-name"))
//                                    server.setServerName(parser.getAttributeValue(i).toUpperCase());
//                                if (parser.getAttributeName(i).equals("data-population"))
//                                    server.setServerPopulation(Integer.parseInt(parser.getAttributeValue(i)));
//                                if (parser.getAttributeName(i).equals("data-type"))
//                                    server.setServerType(parser.getAttributeValue(i));
//                                if (parser.getAttributeName(i).equals("data-timezone"))
//                                    server.setServerTimezone(parser.getAttributeValue(i));
//                                if (parser.getAttributeName(i).equals("data-language"))
//                                    server.setServerLanguage(parser.getAttributeValue(i));
//                            }
//                            serverList.add(server);




        }catch (Exception e){e.printStackTrace();}


    }
}

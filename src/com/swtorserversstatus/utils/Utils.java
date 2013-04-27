package com.swtorserversstatus.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.widget.Toast;
import com.swtorserversstatus.ServerStatus;
import com.swtorserversstatus.model.Server;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 22/12/11
 * Time: 16:49
 * Purpose:
 */
public class Utils {

   public static class ServerCompare implements Comparator<Server> {

        public int compare(Server one, Server two){

        return one.getServerName().compareTo(two.getServerName());

        }

    }


      public static HashSet<Server> getServers()
      {
          HashSet<Server> serverList = new HashSet<Server>();
          Document doc = null;
          try {
              doc = Jsoup.connect("http://www.swtor.com/server-status").get();

          Elements euList = doc.select("#serverListEU");
                      Elements euServers = euList.select("[data-status]");
                      Elements usList = doc.select("#serverListUS");
                      Elements usServers = usList.select("[data-status]");
                      euServers.addAll(usServers);
                              for(Element e : euServers)
                              {
                                  Server server = new Server();
                                  server.setServerStatus(e.attr("data-status").toUpperCase());
                                  server.setServerName(e.attr("data-name").toUpperCase());
                                  server.setServerPopulation(Integer.parseInt(e.attr("data-population")));
                                  server.setServerType(e.attr("data-type"));
                                  server.setServerTimezone(e.attr("data-timezone"));
                                  server.setServerLanguage(e.attr("data-language"));
                                  serverList.add(server);
                              }
          } catch (Exception e) {
                        Log.e("e",e.getMessage());
          }
        return serverList;
      }


//
//
//
//     public static HashSet<Server> parseServers(String l) throws XmlPullParserException, IOException {
//        HashSet<Server> serverList = new HashSet<Server>();
//        InputStream bais = new ByteArrayInputStream(l.getBytes());
//        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//        factory.setNamespaceAware(false);
//        XmlPullParser parser = factory.newPullParser();
//        parser.setInput(new InputStreamReader(bais));
//
//        parser.getLineNumber();
//        int eventType = parser.getEventType();
//        do {
//
//            parser.next();
//            if (parser.getAttributeCount() > 1) {Server server = new Server();
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
//
//            }
//
//            eventType = parser.getEventType();
//            if (eventType == XmlPullParser.TEXT) {
//                Log.d("test", parser.getText());
//            }
//        } while (eventType != XmlPullParser.END_DOCUMENT);
//
//     //  if(serverList.size()>0)serverList.remove(0);
//
//        return serverList;
//    }


    public static HashSet<Server> makeZonedList(HashSet<Server> list, boolean zone) {
        HashSet<Server> serverList = new HashSet<Server>();

        for (Server server : list) {
            if (zone) {
                if (server.getServerTimezone().length()>1) {

                    serverList.add(server);
                }
            } else {
                if (server.getServerLanguage().length()>1) {
                    serverList.add(server);
                }
            }
        }

          ServerCompare serverCompare = new ServerCompare();
          Collections.sort(new ArrayList(serverList),serverCompare);

        return serverList;

    }

    public InputStream readFile(Context context) {
        InputStream raw = null;
       // raw = context.getResources().openRawResource(R.raw.ahtml);
        return raw;
    }

    public static void setProxy (DefaultHttpClient httpClient,String proxy,int port)
    {
        HttpParams params = httpClient.getParams();
        params.setParameter(ConnRoutePNames.DEFAULT_PROXY,
                new HttpHost( proxy,port));
        httpClient.setParams(params);
    }

    public static String readInputStream(InputStream in) throws UnsupportedEncodingException {

          if(in==null){return null;}

    //    List<String> dd = new ArrayList<String>();
        StringBuilder str = new StringBuilder();

        String line = null;

        try {
             BufferedReader reader = new BufferedReader(new
                InputStreamReader(in));
           // reader.skip(8000);
            while ((line = reader.readLine()) != null) {
                line=line.trim();
                if (line.contains("serverBody row odd") || line.contains("serverBody row even")) {
                    String s = new StringBuffer(line).insert(line.length() - 1, "/").toString();
                    str.append(s);
                }
                else if(line.contains("<h1>MAINTENANCE</h1>")||
                        line.contains("<p align=\"center\""))
                {
                   str.append(line);
                }
            }

            //reader.close();
//             str.insert(0,"<test>");
//             str.append("</test>");
//            in.close();
//          l.add(0,"<test>");
//          l.add(0,"<?xml version=\"1.0\"?>");

            //l.add(l.size(),"</test>");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(l.toString());
        return str.toString();
    }







    public static ByteArrayOutputStream fetch(String urlString,ServerStatus serverStatus)
   //  public InputStream fetch(String urlString)
            throws  IOException,ConnectTimeoutException {

       //HttpParams httpParameters = new BasicHttpParams();
//       HttpConnectionParams.setSoTimeout(httpParameters, 1000);
//       HttpConnectionParams.setConnectionTimeout(httpParameters, 1000);


        //DefaultHttpClient client = new DefaultHttpClient(httpParameters);
        DefaultHttpClient client = new DefaultHttpClient();
        Log.v("url", urlString);

        //HttpGet request = new HttpGet(urlString+"?test="+System.currentTimeMillis());
                HttpGet request = new HttpGet(urlString);
                request.addHeader("Cache-Control","no-cache");
        //setProxy(client,"10.10.0.102",8080);

        HttpResponse response = client.execute(request);
        Log.v("response", response.getStatusLine().toString());
          int count;

      //  InputStream input = response.getEntity().getContent();
        InputStream input=null;
        ByteArrayOutputStream baos=null;
        try{
          input = new BufferedInputStream(response.getEntity().getContent());
            long lenghtOfFile = response.getEntity().getContentLength();


            byte data[] = new byte[1024];

            int total = 0;
            baos = new ByteArrayOutputStream();


            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                if(serverStatus!=null){
                    serverStatus.processDownload("Downloaded "+Integer.toString(total)+" bytes");
                }

                baos.write(data,0,count);               //

            }
        }   catch(Exception e){e.printStackTrace();}



        return baos;
    }




  public static HashSet<Server> loadMyServerList(Context activity ,
                                                 HashSet<Server> bigList  )
  throws XmlPullParserException, IOException
  {
   HashSet<Server> serverList=new HashSet<Server>();


//   Resources res = activity.getResources();
//   XmlResourceParser xpp = res.getXml(R.xml.myservers);
      InputStream fs =null;
      FileOutputStream fos =null;
      File serversXml =  new File(activity.getFilesDir().getAbsolutePath()+"/servers.xml");
      if(!serversXml.exists())
      {
          serversXml.createNewFile();
      }

        fs = activity.openFileInput("servers.xml");

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new InputStreamReader(fs));


   xpp.next();
   int eventType = xpp.getEventType();
   while (eventType != XmlPullParser.END_DOCUMENT)
   {
       Server server = new Server();
       if(xpp.getAttributeCount()>1){
     for (int i = 0; i < xpp.getAttributeCount(); i++) {
         if (xpp.getAttributeName(i).equals("data-name")){
                        server.setServerName(xpp.getAttributeValue(i).toUpperCase());
        }
     }

    serverList.add(server);
       }
    eventType = xpp.next();
   }

    ///comparing to get the freshest records


      HashMap<String,Server> map = makeServerMap(bigList);



      //HashMap<String,Server> map = makeServerMap(bigList);


    HashSet<Server> freshList = new HashSet<Server>();
    for(Server myserver:serverList)
    {
        if(map.containsValue(myserver))
        {
            freshList.add(map.get(myserver.getServerName()));
        }
    }

    ///returning list of freshest records with our servers


   return freshList;
  }

    public static HashMap<String,Server> makeServerMap(HashSet<Server> serverList)
    {
        HashMap<String,Server> map = new HashMap<String, Server>();
        for(Server server : serverList)
        {
            map.put(server.getServerName(),server);
        }

        return map;
    }


    public static String makeXml(HashSet<Server>  serverList){

        //eliminating dups
        HashSet<Server> servers = new HashSet<Server>();
        for(Server s: serverList)
        {
            servers.add(s);
        }


    XmlSerializer serializer = Xml.newSerializer();
    StringWriter writer = new StringWriter();

    try {
        serializer.setOutput(writer);
        serializer.startDocument("UTF-8", true);
        serializer.startTag("", "servers");
        //serializer.attribute("", "number", String.valueOf(messages.size()));


     for(Server server: servers){
        serializer.startTag("", "server");
        serializer.attribute("", "data-name", server.getServerName());
        serializer.attribute("", "data-type", server.getServerType());
        serializer.attribute("", "data-timezone", server.getServerTimezone()==null?"":server.getServerTimezone());
        serializer.attribute("", "date-language", server.getServerLanguage()==null?"":server.getServerLanguage());
        serializer.endTag("", "server");
     }

        serializer.endTag("", "servers");
        serializer.endDocument();


        return writer.toString();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

    public static void showToast(Activity activity,String msg){

                   Toast toast = Toast.makeText(activity, msg, 10);
                   toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
                   toast.show();
    }



    public static void writeMyServerListToXml(Activity activity
                                       ,String content)
    {
        try{
        FileOutputStream fOut = activity.openFileOutput("servers.xml", Context.MODE_WORLD_WRITEABLE);
    OutputStreamWriter osw = new OutputStreamWriter(fOut);
    String fileContent = content; //build file content
    osw.write(fileContent );
    osw.flush();
    osw.close();
        }catch (Exception e){e.printStackTrace();}
    }


}

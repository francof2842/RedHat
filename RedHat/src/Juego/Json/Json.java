/*
 * Implementacion de Json para ahcer la conexion con el Servidor
 */

package Juego.Json;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;


public class Json {
    
    private final String url = "http://nodejs2048-universidades.rhcloud.com/hi/start/MTG/json";
    
    private static String grid;
    private static String score;
    private static String moves;
    private static String won;
    private static String over;
    private static String session_id;

    public Json() throws IOException, JSONException {
        JSONObject json = readJsonFromUrl(url);
        setValues(json);
    }
    
  
    public String getGrid(){
      return grid;
    }
  
    public String getScore(){
      return score;
    }
    
    public String getWon(){
      return won;
    }
    
    public String getOver(){
      return over;
    }
    
    public String getMoves(){
      return moves;
    }
    
    public String getSessionId(){
      return session_id;
    }
    
    public void setGrid(String s){
      grid = s;
    }
  
    public void setScore(String s){
      score = s;
    }
    
    public void setWon(String s){
      won = s;
    }
    
    public void setOver(String s){
      over = s;
    }
    
    public void setMoves(String s){
      moves = s;
    }
    
    public void setSessionId(String s){
      session_id = s;
    }
    
    
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        // Desde el Trabajo CON PROXY   
        SocketAddress addr = new InetSocketAddress("proxy.corp.globant.com", 3128);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);

        URL url2 = new URL(url);
        URLConnection conn = url2.openConnection(proxy);
        InputStream is = conn.getInputStream();

        // Desde la Casa SIN PROXY   
        // InputStream is = new URL(url).openStream();

        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          JSONObject json = new JSONObject(jsonText);
          return json;
        } finally {
          is.close();
        }
    }
    
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
    }
    
    
    public void movementJson(int move) throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("http://nodejs2048-universidades.rhcloud.com/hi/state/" + session_id  + "/move/" + move + "/json");
        setValues(json);
    }
    
    public static void setValues(JSONObject json) throws JSONException{
        grid = json.get("grid").toString();
        score = json.get("score").toString();
        moves = json.get("moves").toString();
        won = json.get("won").toString();
        over = json.get("over").toString();
        session_id = json.get("session_id").toString();
    }
    
    
}

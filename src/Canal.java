import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

//API para ver los usuarios y rangos: http://tmi.twitch.tv/group/user/CANAL/chatters
//API para sacar mucha informacion acerca de los streams https://api.twitch.tv/kraken/streams/CANAL

public class Canal {
    private String channel;
    
    private URL url;
    private BufferedReader reader;
 
    private boolean online = false;
    private String juego;
    private String viewers;
    private String titulo;
    private String seguidores;
 
    public Canal(String channel){
        this.channel = channel;
 
        refresh();
    }
 
    public void refresh(){ //Para saber si esta online o no
        try {
            this.url = new URL("https://api.twitch.tv/kraken/streams/" + channel); //Bukkit automatically adds the URL tags, remove them when you copy the class
            this.reader = new BufferedReader(new InputStreamReader(url.openStream()));
 
            String cad=reader.readLine();
            if(cad.toLowerCase().contains("viewers")){
                online = true;
            } else {
                online = false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
 
    public URL getUrl(){
        return this.url;
    }
 
    public boolean isOnline(){
    	refresh();
        return this.online;
    }
    
    public String getChannel(){
    	return channel;
    }
    
    public void setJuego(String game){
    	juego=game;
    }
    
    public void setViews(String views){
    	viewers=views;
    }
    
    public void setTitulo(String title){
    	titulo=title;
    }
    
    public void setSubs(String subs){
    	seguidores=subs;
    }

	public String getJuego() {
		return juego;
	}

	public String getViewers() {
		return viewers;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getSeguidores() {
		return seguidores;
	}
}

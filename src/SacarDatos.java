import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


public class SacarDatos {
	 private static URL url;
	 private static BufferedReader reader;

	 public static void extraerDatos(ArrayList<Canal> listaCanales,String canal){
			String todo;
			int tamanio;
			int empieza;
			int termina;
			String juego = null;
			String viewers = null;
			String titulo = null;
			String seguidores = null;
			boolean online=false;
			
			for(int i=0;i<listaCanales.size();i++){
				if(listaCanales.get(i).getChannel().equalsIgnoreCase(canal)){
					if(listaCanales.get(i).isOnline()){
						online=true;
					}else{
						online=false;
					}
				}
			}
			
			try {
				url = new URL("https://api.twitch.tv/kraken/streams/" + canal); //Bukkit automatically adds the URL tags, remove them when you copy the class
	            reader = new BufferedReader(new InputStreamReader(url.openStream()));
	            
				if(online){
					reader = new BufferedReader(new InputStreamReader(url.openStream()));
					todo=reader.readLine();
					tamanio=todo.length();
					
					//Para sacar el juego
					empieza=(todo.indexOf("game"))+7; //Buscar Juego +7 caracteres
					termina=(todo.indexOf("viewers"))-3; //Donde termina el juego -3 caracteres
					juego=todo.substring(empieza, termina);
					
					//Para sacar los Viewers
					empieza=(todo.indexOf("viewers"))+9; //+7 caracteres
					termina=(todo.indexOf("created_at"))-2;
					viewers=todo.substring(empieza, termina);
					
					//Para sacar el titulo
					empieza=(todo.indexOf("status"))+9; //+7 caracteres
					termina=(todo.indexOf("partner"))-3;
					titulo=todo.substring(empieza, termina);
					
					//Para sacar los seguidores
					empieza=(todo.indexOf("followers"))+11; //+7 caracteres
					termina=(todo.indexOf("profile_banner"))-2;
					seguidores=todo.substring(empieza, termina);
					
					for(int i = 0;i<listaCanales.size();i++){
						if(listaCanales.get(i).getChannel().equalsIgnoreCase(canal)){
							listaCanales.get(i).setJuego(juego);
							listaCanales.get(i).setViews(viewers);
							listaCanales.get(i).setTitulo(titulo);
							listaCanales.get(i).setSubs(seguidores);
						}
					}
				}
				
				
			} catch (IOException e) {
			};
		}
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jibble.pircbot.Colors;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

/**
 * @since 14/09/2015
 * @version v2.4
 * @author Dm94
*/

/*
 * Esta clase es la que inicia el bot y lee los archivos de configuracion
 */

public class principal {
	private static String fConfig="config.txt";
	private static TwitchBot bot;
	
	public static void main(String[] args){
		
		mensajeInicio();
		
		//Leemos la config y creamos el bot
		try {
			leerConfig(fConfig);
		}catch(IrcException e1){
			System.out.println("IrcException");
		} catch (IOException e) {
			System.out.println("Error en la lectura de ficheros.");
		}
	}
	
	public static void leerConfig(String fConfig) throws IOException, NickAlreadyInUseException, IrcException{
		String cad = null;
		String canalHoster = null;
		String oauth = null;
		String admin = null;
		File f=new File(fConfig);
		
		if(!(f.canRead())){
			f.createNewFile();
			System.out.println("El archivo de configuración no existia se ha creado uno");
		}
		
		FileReader pr=new FileReader(fConfig);
		BufferedReader br=new BufferedReader(pr);
		
		while((cad=br.readLine())!=null){
			canalHoster=cad;
			oauth=br.readLine();
			admin=br.readLine();
			if((cad=br.readLine())!=null){
				System.out.println("Demasiados parametros de configuracion.");
			}
		}
		br.close();
		
		if(admin==null){//Si no hay nada en el admin el hoster se pone de admin
			admin=canalHoster;
		}
		if(canalHoster!=null && oauth!=null && admin!=null){
			System.out.println("--------------------Config--------------------");
			System.out.println("Canal Hoster: "+canalHoster);
			System.out.println("oauth: "+oauth);
			System.out.println("Administrador: "+admin);
			System.out.println("---------------------Bot----------------------");
			
			crearBot(canalHoster,oauth,admin);
		}else{
			System.out.println("Los datos de configuracion son incorrectos");
		}
		
	}
	
	public static void crearBot(String canalHoster, String oauth,String admin) throws NickAlreadyInUseException, IOException, IrcException{
		
		bot= new TwitchBot(canalHoster,admin);
		bot.setVerbose(true);
		bot.connect("irc.twitch.tv" ,6667,oauth);
		bot.joinChannel("#"+canalHoster);
		
	}
	
	public static void mandarMensaje(String canalHoster,String mensaje){
		bot.sendMessage("#"+canalHoster,mensaje);
	}
	
	public static void mensajeInicio(){
		System.out.println("----------------------------------------------");
		System.out.println("----------------                --------------");
		System.out.println("----------------   dmBot v2.3   --------------");
		System.out.println("----------------                --------------");
		System.out.println("----------------------------------------------");
	}
}



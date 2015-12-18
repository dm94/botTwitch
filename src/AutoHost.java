import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/*
 * Esta clase es una de las caracteristicas en ella se lee el fichero de canales a hostear,
 * se inicia un timertask que cada cierto tiempo comprueba de los canales cual esta online o offline
 * y segun cual sea lo hostea.
 */

public class AutoHost extends Thread {
	private static ArrayList<Canal> listaCanales; //Lista con todos los canales
	private static ArrayList<Canal> listaOnline; //Lista con solo los canales online
	private static String ficherocanales="canales.txt"; //Archivo donde estan guardados los canales
	private static Timer timer = new Timer(); //Creamos el timer para que repita la misma tarea
	private static String canalAntiHost="gzonecomunidad"; //Canal por defecto
	private static String canalNuevoHost="gzonecomunidad"; //Canal por defecto
	private static String canal; //Donde se almacena el canal hoster
	private static boolean modo=true; //Controla si esta activada o no esta funcion
	private static boolean iniciado=false;
	
	public static void iniciarAutoHost(){	
		listaCanales=new ArrayList<Canal>();
		listaOnline=new ArrayList<Canal>();
		canal=TwitchBot.getCanal();
			
		//Leer Canales
		try {
			leerCanales(ficherocanales);
		} catch (IOException e) {
			System.out.println("Fallo en la lectura del fichero");
		}
		
		if(!(iniciado)){
			timer.scheduleAtFixedRate(timerTask, 0, 600000); //1000 es un segundo, por defecto 600000
			System.out.println("Se a encendido el timertask");
			iniciado=true;
		}
		
		modo=true;
	}
	
	public static void pararAutoHost(){
		Config.desactivarAutoHost();	
		modo=false;
	}
	
	public static void leerCanales(String fichero) throws IOException{
		String cad = null;
		
		//Añadimos esto para controlar que si falta el archivo cree uno
		File f=new File(fichero);
		
		if(!(f.canRead())){
			f.createNewFile();
			System.out.println("El de canales no existia se ha creado uno");
		}
		
		FileReader pr=new FileReader(fichero);
		BufferedReader br=new BufferedReader(pr);
		
		while((cad=br.readLine())!=null){
			listaCanales.add(new Canal(cad));
		}
		
		br.close();
	}
	
	public static String crearHost() throws IndexOutOfBoundsException{
		//vaciarListaOnline();
		int conline=0;
		int j=0;
		
		String canalHosteado = "";
		
		if(listaOnline.isEmpty()){
			for(int i=0;i<listaCanales.size();i++){
				conline++;
				if(listaCanales.get(i).isOnline()){
					j++;
					listaOnline.add(new Canal(listaCanales.get(i).getChannel())); //Añadimos una segunda lista con solo los que estan online
				}
			}
		}
		for(int i=0;i<listaOnline.size();i++){
			if(listaOnline.get(i).isOnline()){
				canalHosteado=listaOnline.get(i).getChannel();
			}
			else{
				listaOnline.remove(listaOnline.get(i));
			}
		}
		System.out.println("Canales cargados: "+conline);
		System.out.println("Canales online: "+j);
		
		
		return canalHosteado;
	}

	public static boolean estaOnline(String canal){
		boolean online=false;
		
		for(int i=0;i<listaCanales.size();i++){
			if(listaCanales.get(i).getChannel().equalsIgnoreCase(canal)){
				if(listaCanales.get(i).isOnline()){
					online=true;
				}else{
					online=false;
					for(int j=0;j<listaOnline.size();j++){
						if(listaOnline.get(j).getChannel().equalsIgnoreCase(canal)){
							listaOnline.remove(listaOnline.get(j));
						}
					}
				}
			} 
		}
		
		return online;
	}
	
	public static void reloadCanales() throws IndexOutOfBoundsException{
		vaciarListaCanales();
		try {
			leerCanales(ficherocanales);
		} catch (IOException e) {
			System.out.println("Fallo en la lectura del fichero");
		}
	}
	
	private static void vaciarListaCanales(){
		for(int i=listaCanales.size();i>0;i--){
			listaCanales.remove(listaCanales.get(i));
		}
	}
	
	private static void vaciarListaOnline(){
		for(int i=listaOnline.size();i>0;i--){
			listaOnline.remove(listaOnline.get(i));
		}
	}
	
	//Apartado del TimerTask
	
	public static TimerTask timerTask = new TimerTask(){ 
		public void run(){ 
			if(listaOnline.isEmpty()){
   				canalAntiHost="";
   				canalNuevoHost="";
   			}
	        if(modo){ 
	        	if(!(estaOnline(canalAntiHost))){ //Comprobamos si el canal esta online, si no lo esta seguira con el programa
	        		canalNuevoHost=crearHost();
	       			System.out.println("Canal a hostear: "+canalNuevoHost);
	       			if(AutoHost.estaOnline(canalNuevoHost) && !(canalNuevoHost.equalsIgnoreCase(""))){ //Comprobamos que el canal este online y que no sea null
                   		if(!(canalNuevoHost.equalsIgnoreCase(canalAntiHost))){
    	       				canalAntiHost=canalNuevoHost;
    	       				principal.mandarMensaje(canal, "/host "+canalNuevoHost);
    	       			}else{
    	       				System.out.println("El canal Antiguo es el mismo que el nuevo.");
    	       				canalAntiHost="";
                       		canalNuevoHost="";
    	       			}
                   	}else{
                   		canalAntiHost="";
                   		canalNuevoHost="";
                   		try {
							Thread.sleep(300000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                   	}
	           		modo=Config.isAutoHost();
	       		}else{
	       			System.out.println("El canal "+canalAntiHost+" sigue online.");
	       			try {
						Thread.sleep(300000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	       		}
	        }else{
	       	 	System.out.println("El autohost esta desactivado");
	       	 	canalAntiHost="";
	        	modo=Config.isAutoHost();
	        	try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       	}
	    }
	};
}

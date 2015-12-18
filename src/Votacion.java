import java.util.ArrayList;


public class Votacion {

	private static String todo="";
	private static String soloMensaje="";
	private static int aFavor=0;
	private static int enContra=0;
	private static boolean encendido=false;
	private static ArrayList<String> apuntados;
	
	public static void pasarMensaje(String todo){
		int longString=0;
		
		longString=todo.length();
		
		soloMensaje=todo.substring(10,longString);
		
		System.out.println(soloMensaje);
		apuntados=new ArrayList<String>();
	}
	
	public static String getMensaje(){
		return soloMensaje;
	}
	
	public static void votoAFavor(String usuario){
		if(!(estaUsuario(usuario))){
			apuntados.add(usuario);
			aFavor++;
		}
	}
	
	public static void votoEnContra(String usuario){
		if(!(estaUsuario(usuario))){
			apuntados.add(usuario);
			enContra++;
		}
	}
	
	public static void resulVotacion(String canal){
		int totalVotos=aFavor+enContra;
		principal.mandarMensaje(canal, "Votos en Total: "+totalVotos);
		principal.mandarMensaje(canal, "Votos a favor: "+((aFavor*100)/totalVotos)+"%.");
	}
	
	public static boolean estaUsuario(String usuario){
		boolean esta=false;
		
			for(int i=0;i<apuntados.size();i++){
				if(apuntados.get(i).equalsIgnoreCase(usuario)){
					esta=true;
				}else{
					esta=false;
				}
			}	
		
		return esta;
	}
}

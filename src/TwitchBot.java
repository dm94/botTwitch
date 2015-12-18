import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.jibble.pircbot.*;

/*
 * Esta Clase es el bot una vez iniciada siempre esta activada, se encarga de leer el chat y decidir que hacer
 * en funcion del comando que envien por el chat.
 */

public class TwitchBot extends PircBot{
	private static String canal;
	private static String admin;
	private static boolean autohost=false;
	private static boolean sorteo=false;
	private static boolean genKey=false;
	private static boolean votacion=false;
	private static int i=0;
	
	//Para que cambien los !comandos solo hace falta que esos comandos no llegen a estar definidos.
	
	public TwitchBot(){
		this.setName("noname");
		canal="noname";
		admin="";
	}
	

	public TwitchBot(String nombre, String adminis){
		this.setName(nombre);
		this.canal=nombre;
		this.admin=adminis;
	}
	
	public static String getCanal(){
		return canal;
	}
	
	public void onMessage(String channel,String sender, String login, String hostname, String message) {
		//Caracteristica AutoHost
		if(message.equalsIgnoreCase("!ah-on")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				principal.mandarMensaje(canal, "AutoHost Encendido");
				Config.activarAutoHost();
				AutoHost.iniciarAutoHost();
			}else{
				principal.mandarMensaje(canal, "No tienes permiso para realizar ese comando.");
			}
		}else if(message.equalsIgnoreCase("!ah-off")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				principal.mandarMensaje(canal, "/unhost");
				principal.mandarMensaje(canal, "AutoHost Apagado");
				Config.desactivarAutoHost();
				AutoHost.pararAutoHost(); //Para parar el AutoHost
			}else{
				principal.mandarMensaje(canal, "No tienes permiso para realizar ese comando.");
			}
		}else if(message.equalsIgnoreCase("!ah-reload")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				AutoHost.reloadCanales();
			}else{
				principal.mandarMensaje(canal, "No tienes permiso para realizar ese comando.");
			}
		}
		//Caracteristia GenKey
		else if(message.equalsIgnoreCase("!gk-on")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				principal.mandarMensaje(canal, "GenKey Encendido");
				Config.activarGenKey();
				genKey=true;
			}else{
				principal.mandarMensaje(canal, "No tienes permiso para realizar ese comando.");
			}
		}else if(message.equalsIgnoreCase("!gk-off")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				principal.mandarMensaje(canal, "GenKey Apagado");
				Config.desactivarGenKey();
				genKey=false;
			}else{
				principal.mandarMensaje(canal, "No tienes permiso para realizar ese comando.");
			}
		}else if(message.equalsIgnoreCase("!genkey")){
			if(genKey){
				principal.mandarMensaje(canal, "Clave Generada de forma aleatoria: "+GenKey.devolverKey().toUpperCase());
			}
		}
		//Apartado Sorteo
		else if(message.equalsIgnoreCase("!sorteo-on")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				principal.mandarMensaje(canal, "Sorteo en marcha para participar poned: !gzone");
				sorteo=true;
				Sorteo.inciarSorteo();
				Config.activarSorteo();
			}else{
				principal.mandarMensaje(canal, "No tienes permiso para realizar ese comando.");
			}
		}else if(message.equalsIgnoreCase("!sorteo-off")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				sorteo=false;
				Sorteo.pararSorteo();
				principal.mandarMensaje(canal, "Ya no es posible apuntarse, pon !ganador para elegir a un ganador.");
				Config.desactivarSorteo();
			}else{
				principal.mandarMensaje(canal, "No tienes permiso para realizar ese comando.");
			}
		}else if(message.equalsIgnoreCase("!ganador")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				sorteo=false;
				String ganador;
				ganador=Sorteo.sacarGanador();
				principal.mandarMensaje(canal, "El ganador del Sorteo es @"+ganador);
				Config.desactivarSorteo();
			}
		}else if(message.equalsIgnoreCase("!gzone")){
			if(sorteo){
				Sorteo.aniadirUsuario(sender);
			}
		}
		//Caracteristica Votacion
		else if(message.indexOf("!votacion")!=-1){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				votacion=true;
				Votacion.pasarMensaje(message);
				principal.mandarMensaje(canal, "La votacion por \""+Votacion.getMensaje()+"\" ha empezado");
				principal.mandarMensaje(canal, "Poned +1 para Votos a favor");
				principal.mandarMensaje(canal, "Poned -1 para Votos en contra");
				Config.activarVotacion();
			}
		}else if(message.equalsIgnoreCase("!resultado")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				Votacion.resulVotacion(canal);
				votacion=false;
				Config.desactivarVotacion();
			}
		}else if(message.equalsIgnoreCase("+1")){
			if(votacion){
				Votacion.votoAFavor(sender);
			}
		}else if(message.equalsIgnoreCase("-1")){
			if(votacion){
				Votacion.votoEnContra(sender);
			}
		}
		//Apartado Global
		else if(message.equalsIgnoreCase("!exit")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				System.exit(1); 
			}else{
				principal.mandarMensaje(canal, "No tienes permiso para realizar ese comando.");
			}
		}else if(message.equalsIgnoreCase("!status")){
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				if(Config.isAutoHost()){
					principal.mandarMensaje(canal, "AutoHost: ON");
				}else{
					principal.mandarMensaje(canal, "AutoHost: OFF");
				}
				if(Config.isGenKey()){
					principal.mandarMensaje(canal, "GenKey: ON");
				}else{
					principal.mandarMensaje(canal, "GenKey: OFF");
				}
				if(Config.isSorteo()){
					principal.mandarMensaje(canal, "Sorteo: ON");
				}else{
					principal.mandarMensaje(canal, "Sorteo: OFF");
				}
				if(Config.isVotacion()){
					principal.mandarMensaje(canal, "Votacion: ON");
				}else{
					principal.mandarMensaje(canal, "Votacion: OFF");
				}
			}else{
				principal.mandarMensaje(canal, "No tienes permiso para realizar ese comando.");
			}
		}else if(message.equalsIgnoreCase("!help")){
			principal.mandarMensaje(canal, "Los comandos disponibles son !online,!help,!autor,!genkey.");
			if(sender.equalsIgnoreCase(admin) || sender.equalsIgnoreCase(canal)){
				principal.mandarMensaje(canal, "Comandos Globales: !status,!exit");
				principal.mandarMensaje(canal, "Comandos de AutoHost: !ah-(on|off|reload).");
				principal.mandarMensaje(canal, "Comandos de GeneradorKeys: !gk-(on|off).");
				principal.mandarMensaje(canal, "Comandos de Sorteo: !sorteo-(on|off),!ganador.");
				principal.mandarMensaje(canal, "Comandos de Votacion: !votacion,!resultado.");
			}
		//Apartado para usuarios normales
		}else if(message.equalsIgnoreCase("!online")){
			principal.mandarMensaje(canal, "Estoy conectado, DmBot v2.3");
		}else if(message.equalsIgnoreCase("!autor")){
			principal.mandarMensaje(canal, "El creador de este programa es Dm94 y es para uso exclusivo de la Comunidad GZone: http://gzone.es/");
		}else if(message.equalsIgnoreCase("hola")){
			principal.mandarMensaje(canal, "Hola @"+sender);
		}
	}
	
}


public class Config {
	
	private static boolean autoHost=false;
	private static boolean votacion=false;
	private static boolean genKey=false;
	private static boolean sorteo=false;
	
	public Config(){
		autoHost=false;
		votacion=false;
		genKey=false;
		sorteo=false;
	}
	
	public Config(boolean autoHost,boolean votacion,boolean genKey, boolean sorteo){
		this.autoHost=autoHost;
		this.votacion=votacion;
		this.genKey=genKey;
		this.sorteo=sorteo;
	}

	public static boolean isAutoHost() {
		return autoHost;
	}

	public static boolean isVotacion() {
		return votacion;
	}
	
	public static boolean isGenKey() {
		return genKey;
	}
	
	public static boolean isSorteo() {
		return sorteo;
	}
	
	public static void activarAutoHost(){
		autoHost=true;
	}
	
	public static void activarGenKey(){
		genKey=true;
	}
	
	public static void activarSorteo(){
		sorteo=true;
	}
	
	public static void activarVotacion(){
		votacion=true;
	}
	
	public static void desactivarAutoHost(){
		autoHost=false;
	}
	
	public static void desactivarVotacion(){
		votacion=false;
	}
	
	public static void desactivarGenKey(){
		genKey=false;
	}
	
	public static void desactivarSorteo(){
		sorteo=false;
	}
}

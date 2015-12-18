import java.util.ArrayList;

public class Sorteo {
	
	private static boolean sorteoEncendido;
	private static ArrayList<String> apuntados;
	
	public static void inciarSorteo(){
		sorteoEncendido=true;
		apuntados=new ArrayList<String>();
	}
	
	public static void pararSorteo(){
		sorteoEncendido=false;
	}

	public static void aniadirUsuario(String usuario){
		if(sorteoEncendido){
			if(!(estaUsuario(usuario))){
				apuntados.add(usuario);
				System.out.println("El usuario "+usuario+" ha sido apuntado al sorteo");
			}
		}else{
			System.out.println("El sorteo no esta en marcha");
		}
	}
	
	public static String sacarGanador(){
		String ganador="";
		int totalApuntados;
		int numGanador=0;
		
		totalApuntados=apuntados.size();
		
		System.out.println("Hay un total de "+totalApuntados);
		if(totalApuntados>1){
			numGanador=(int)(Math.random()*totalApuntados);
		}else{
			numGanador=0;
		}
		
		System.out.println("El ganador es "+numGanador);
		
		ganador=apuntados.get(numGanador).intern();
		pararSorteo(); //Apagamos el Sorteo
		
		return ganador;
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

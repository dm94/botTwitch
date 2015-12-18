
public class GenKey {
	private static boolean modo=true;
	
	private static String generarParte(){
		char[] elementos={'0','1','2','3','4','5','6','7','8','9','a',
				'b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
				'u','v','w','x','y','z'}; //Un char con todos los numeros y letras del abecedario

		char[] conjunto = new char[5]; //Creamos un char de maximo 5 elementos
		
		for(int i=0;i<5;i++){
			int el = (int)(Math.random()*36); //Genera una letra/numero de todos los que salgan
			conjunto[i] = (char)elementos[el]; //Implementa un elemento de la parte
		}
		
		return new String(conjunto); 
	}
	
	public static String devolverKey(){
		String key="";
		
		modo=Config.isGenKey();
		
		if(modo){
			key=(generarParte()+"-"+generarParte()+"-"+generarParte());
		}else{
			key="No Esta activada esta funcion";
		}
		
		return key;
	}
}

package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import regras_negocio.Fachada;

public class Alterar {

	public Alterar(){
		Fachada.inicializar();
		//----alteraçao 1
		try {
			Fachada.alterarData("joao","01/02/1990");
			System.out.println("alterado mes de nascimento de joao para mes 02");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//----alteraçao 2
		try {
			Fachada.alterarNumero("988880000", "999999999");
			System.out.println("alterado numero de telefone 988880000 para 999999999 ");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Alterar();
	}
}


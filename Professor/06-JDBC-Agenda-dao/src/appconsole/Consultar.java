package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import modelo.Pessoa;
import regras_negocio.Fachada;


public class Consultar {

	public Consultar(){

		try {
			Fachada.inicializar();
			System.out.println("\n1.procurando nome jo ");
			for(Pessoa p : Fachada.consultarPessoas("jo")) 
				System.out.println(p);

			System.out.println("\n2.listar pessoas que nasceram no mes 02");
			for(Pessoa p : Fachada.consultarMesNascimento("02")) 
				System.out.println(p);
			
			System.out.println("\n3.procurando quem tem dois telefones " );
			for(Pessoa p : Fachada.consultarPessoasNTelefones(2) ) 
				System.out.println(p);

//			System.out.println("\n4.maria tem telefone celular?\n"+
//					Fachada.temTelefoneCelular("maria") );
//
//			System.out.println("\n5.maria tem telefone fixo\n"+
//					Fachada.temTelefoneFixo("maria") );

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
		System.out.println("\nfim do programa");
	}


	//=================================================
	public static void main(String[] args) {
		new Consultar();
	}
}


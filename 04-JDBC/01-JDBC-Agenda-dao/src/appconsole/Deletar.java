package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import regras_negocio.Fachada;


public class Deletar {

	public Deletar(){
		Fachada.inicializar();
		try {
			Fachada.excluirPessoa("maria");
			System.out.println("apagou maria e seus 3 telefones");
			
			Fachada.excluirTelefone("988881111");
			System.out.println("apagou telefone... 988881111");
			
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Fachada.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Deletar();
	}
}


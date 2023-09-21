/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;


import regras_negocio.Fachada;

public class Apagar {

	public Apagar() {
		try {
			Fachada.inicializar();
			Fachada.excluirCarro("BBB2000");		 
			System.out.println("carro excluido");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Apagar();
	}
}

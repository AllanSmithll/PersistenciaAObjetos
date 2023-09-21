/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;

import regras_negocio.Fachada;

public class CadastrarUsuario {

	public CadastrarUsuario() {
		try {
			Fachada.inicializar();

			System.out.println("cadastrando usuario...");
			Fachada.cadastrarUsuario("fausto", "1234");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}



	public static void main(String[] args) {
		new CadastrarUsuario();
	}
}

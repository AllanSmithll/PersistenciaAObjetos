/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar() {
		try {
			Fachada.inicializar();
			System.out.println("cadastrando carro...");
			Fachada.cadastrarCarro("AAA1000", "palio");
			Fachada.cadastrarCarro("BBB2000", "onix");
			Fachada.cadastrarCarro("CCC3000", "civic");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("cadastrando cliente...");
			Fachada.cadastrarCliente("joao", "1111");
			Fachada.cadastrarCliente("maria", "2222");
			Fachada.cadastrarCliente("jose", "3333");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			System.out.println("alugando carro...");
			Fachada.alugarCarro("1111","AAA1000",100.0 , "01/05/2022", "10/05/2022");
			Fachada.alugarCarro("2222","BBB2000",200.0 , "01/05/2022", "10/05/2022");
			Fachada.alugarCarro("2222","CCC3000",300.0 , "01/05/2022", "10/05/2022");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}


	public static void main(String[] args) {
		new Cadastrar();
	}
}

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;

import modelo.Aluguel;
import modelo.Carro;
import regras_negocio.Fachada;

public class Consultar {

	public Consultar() {
		try {
			Fachada.inicializar();

			System.out.println("consultas... \n");
			System.out.println("\nalugueis de carro palio");
			for(Aluguel a : Fachada.alugueisModelo("palio"))
				System.out.println(a);


			System.out.println("\nalugueis finalizados");
			for(Aluguel a : Fachada.alugueisFinalizados())
				System.out.println(a);


			System.out.println("\ncarros que possuem 1 alugueis");
			for(Carro c : Fachada.carrosNAlugueis(1))
				System.out.println(c);


			//System.out.println("clientes que possuem 2 alugueis");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Consultar();
	}
}


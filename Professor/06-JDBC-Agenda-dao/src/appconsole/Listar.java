package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/


import modelo.Aluno;
import modelo.Pessoa;
import modelo.Telefone;
import regras_negocio.Fachada;

public class Listar {

	public Listar(){
		try {
			Fachada.inicializar();

			System.out.println("*** Listagem de pessoas:");
			for(Pessoa p : Fachada.listarPessoas())		
				System.out.println(p);

			System.out.println("\n*** Listagem de alunos:");
			for(Aluno a : Fachada.listarAlunos())		
				System.out.println(a);

			System.out.println("\n*** Listagem de telefones:");
			for(Telefone t : Fachada.listarTelefones())	
				System.out.println(t);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
	}


	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}


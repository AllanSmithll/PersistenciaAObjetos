package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar(){
		try {
			System.out.println("cadastrando pessoas...");
			Fachada.inicializar();
			
			Fachada.criarPessoa("joao", "01/01/1990");
			Fachada.criarPessoa("maria","01/01/1990");
			Fachada.criarPessoa("jose", "01/01/1990");
			Fachada.criarPessoa("paulo","01/01/1990");
			
			System.out.println("cadastrando aluno...");
			Fachada.criarAluno("ana","01/01/1990",	9.0);
			
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("cadastrando telefones...");
			Fachada.incluirTelefone("joao","988880000");
			Fachada.incluirTelefone("joao","988881111");	
			Fachada.incluirTelefone("maria","987882222");
			Fachada.incluirTelefone("maria","988883333");
			Fachada.incluirTelefone("maria","32471234");
			Fachada.incluirTelefone("jose","987884444");
			Fachada.incluirTelefone("paulo","988885555");
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("fim do programa");
	}

	
	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}



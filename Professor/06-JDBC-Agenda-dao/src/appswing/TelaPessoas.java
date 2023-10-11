/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Pessoa;
import modelo.Telefone;
import regras_negocio.Fachada;

public class TelaPessoas {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton button_4;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_3;
	private JTextField textField_1;
	private JLabel label_4;
	private JTextField textField_2;
	private JButton button_1;
	private JButton button_7;
	private JButton button_3;
	private JLabel label_8;
	private JTextField textField_4;


	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TelaReuniao window = new TelaReuniao();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaPessoas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Listar pessoas");
		frame.setBounds(100, 100, 813, 428);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 63, 751, 155);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		//****************************************
		//evento de click numa linha da tabela
		//****************************************
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					//pegar o nome, data nascimento e apelidos da pessoa selecionada
					String nome = (String) table.getValueAt( table.getSelectedRow(), 0);
					Pessoa p = Fachada.localizarPessoa(nome);
					String data = p.getDtNascimento();
					textField_1.setText(nome);
					textField_2.setText(data);
					label.setText("");
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}

			}
		});

		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"", "", "", ""}
				));
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		button_4 = new JButton("Apagar");
		button_4.setToolTipText("apagar pessoa e seus dados");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_1.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					//pegar o nome na linha selecionada
					String nome = textField_1.getText();
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null, "Esta operação apagará os telefones e removerá as reunioes de "+nome, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if(escolha == 0) {
						Fachada.excluirPessoa(nome);
						label.setText("pessoa excluida"); 
						listagem(); 	//listagem
					}
					else
						label.setText("exclusão cancelada"); 

				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_4.setBounds(318, 325, 74, 23);
		frame.getContentPane().add(button_4);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 359, 735, 14);
		frame.getContentPane().add(label);

		button = new JButton("Listar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(309, 28, 106, 23);
		frame.getContentPane().add(button);

		label_2 = new JLabel("selecione uma pessoa para editar");
		label_2.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(label_2);

		label_3 = new JLabel("nome:");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(31, 239, 62, 14);
		frame.getContentPane().add(label_3);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBackground(Color.WHITE);
		textField_1.setBounds(101, 235, 165, 20);
		frame.getContentPane().add(textField_1);

		label_4 = new JLabel("nascimento");
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_4.setBounds(31, 268, 62, 14);
		frame.getContentPane().add(label_4);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(101, 264, 86, 20);
		frame.getContentPane().add(textField_2);

		button_1 = new JButton("Criar");
		button_1.setToolTipText("cadastrar nova pessoa");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_1.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String nome = textField_1.getText().trim();
					String nascimento = textField_2.getText().trim();

					Fachada.criarPessoa(nome, nascimento);
					String numero = textField_4.getText();
					if(!numero.isEmpty())
						Fachada.incluirTelefone(nome, numero);
					label.setText("pessoa criada");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_1.setBounds(144, 325, 62, 23);
		frame.getContentPane().add(button_1);

		button_7 = new JButton("Atualizar");
		button_7.setToolTipText("atualizar pessoa ");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_1.getText().trim().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String nome = textField_1.getText();
					String nascimento = textField_2.getText();
					
					Fachada.alterarData(nome, nascimento);
					String numero = textField_4.getText();
					if(!numero.isEmpty())
						Fachada.incluirTelefone(nome, numero);
					label.setText("pessoa atualizada");
					listagem();
				}
				catch(Exception ex2) {
					label.setText(ex2.getMessage());
				}
			}
		});
		button_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_7.setBounds(221, 325, 87, 23);
		frame.getContentPane().add(button_7);

		button_3 = new JButton("Limpar");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
				textField_2.setText("");
			}
		});
		button_3.setBounds(428, 325, 89, 23);
		frame.getContentPane().add(button_3);

		label_8 = new JLabel("novo numero");
		label_8.setHorizontalAlignment(SwingConstants.LEFT);
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_8.setBounds(21, 298, 74, 14);
		frame.getContentPane().add(label_8);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_4.setColumns(10);
		textField_4.setBounds(101, 294, 86, 20);
		frame.getContentPane().add(textField_4);

		frame.setVisible(true);

	}

	public void listagem() {
		try{
			//objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//criar as colunas (0,1,2) da tabela
			model.addColumn("Nome");
			model.addColumn("Nascimento");
			model.addColumn("Lista de telefones");
			//criar as linhas da tabela
			String apelidos, telefones;
			
			List<Pessoa> lista = Fachada.listarPessoas();
			for(Pessoa p : lista) {
				if(p.getTelefones().size()==0)
					telefones="sem telefone";
				else{
					telefones="";
					for(Telefone t : p.getTelefones() )	
						telefones = telefones + " " + t.getNumero() ;
				}
				model.addRow(new Object[]{p.getNome(),p.getDtNascimento(),telefones});
			}
			table.setModel(model);
			label_2.setText("resultados: "+lista.size()+ " pessoas   - selecione uma linha para editar");

			//redimensionar a coluna 2
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 		//desabilita
			table.getColumnModel().getColumn(2).setMinWidth(300);	//coluna dos telefones
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //habilita

		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}

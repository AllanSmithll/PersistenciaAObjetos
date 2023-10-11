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

import modelo.Telefone;
import regras_negocio.Fachada;

public class TelaTelefones {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton button_1;
	private JLabel label;
	private JTextField textField;
	private JLabel label_1;
	private JLabel label_2;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton button_2;

	private JLabel label_3;
	private JLabel label_4;
	private JButton button_3;
	private JLabel label_5;
	private JTextField textField_3;

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
	public TelaTelefones() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Listar Telefones");
		frame.setBounds(100, 100, 378, 373);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 44, 315, 152);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		//evento de selecao de uma linha da tabela
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					//pegar o nome selecionado
					String numero = (String) table.getValueAt( table.getSelectedRow(), 0);
					String nome = (String) table.getValueAt( table.getSelectedRow(), 1);
					textField_1.setText(nome);
					textField_2.setText(numero);
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
		table.setBackground(Color.YELLOW);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"", ""}
				));
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		button_1 = new JButton("Apagar");
		button_1.setToolTipText("remover o numero da pessoa");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_2.getText().isEmpty()) {
						label.setText("numero vazio");
						return;
					}
					String numero = textField_2.getText();
					//confirmação
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null, "Confirma exclusão do telefone "+numero, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if(escolha == 0) {
						Fachada.excluirTelefone(numero);
						label.setText("exclusão realizada");
						listagem();
					}
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_1.setBounds(218, 270, 95, 23);
		frame.getContentPane().add(button_1);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(26, 309, 326, 14);
		frame.getContentPane().add(label);

		button = new JButton("Listar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(204, 10, 112, 23);
		frame.getContentPane().add(button);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(78, 11, 86, 20);
		frame.getContentPane().add(textField);

		label_1 = new JLabel("numero:");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(26, 217, 71, 14);
		frame.getContentPane().add(label_1);

		label_2 = new JLabel("nome:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(26, 242, 71, 14);
		frame.getContentPane().add(label_2);

		label_3 = new JLabel("Numero:");
		label_3.setBounds(26, 15, 57, 14);
		frame.getContentPane().add(label_3);

		label_4 = new JLabel("selecione um telefone");
		label_4.setBounds(26, 194, 336, 14);
		frame.getContentPane().add(label_4);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(78, 240, 105, 20);
		frame.getContentPane().add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(78, 215, 86, 20);
		frame.getContentPane().add(textField_2);

		button_2 = new JButton("Criar");
		button_2.setToolTipText("adicionar novo numero para pessoa");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_1.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String nome = textField_1.getText();
					String telefone = textField_2.getText();
					Fachada.incluirTelefone(nome, telefone);
					label.setText("telefone criado");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_2.setBounds(26, 270, 77, 23);
		frame.getContentPane().add(button_2);

		button_3 = new JButton("Atualizar");
		button_3.setToolTipText("alterar o numero da pessoa");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_1.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String numero1 = textField_2.getText();
					String numero2 = textField_3.getText();
					Fachada.alterarNumero(numero1, numero2);
					label.setText("telefone atualizado");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_3.setBounds(113, 270, 95, 23);
		frame.getContentPane().add(button_3);
		
		label_5 = new JLabel("novo numero:");
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_5.setBounds(172, 217, 90, 14);
		frame.getContentPane().add(label_5);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(255, 215, 86, 20);
		frame.getContentPane().add(textField_3);
		
		frame.setVisible(true);
	}

	public void listagem () {
		try{
			List<Telefone> lista = Fachada.consultarTelefones(textField.getText());
			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("Numero");
			model.addColumn("Nome");
			
			for(Telefone t : lista)
				if(t.getPessoa() != null)
					model.addRow(new String[]{  t.getNumero(), t.getPessoa().getNome() });
				else
					model.addRow(new String[]{ t.getNumero(),  "desconhecido"});

			table.setModel(model);
			label_4.setText("resultados: "+lista.size()+ " numeros  - selecione uma linha para editar");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}

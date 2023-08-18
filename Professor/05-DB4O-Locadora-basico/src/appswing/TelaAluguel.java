/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import appconsole.Util;
import modelo.Aluguel;
import modelo.Carro;
import modelo.Cliente;

public class TelaAluguel {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JButton button_3;
	
	private ObjectContainer manager;
	Random gerador = new Random();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TelaAluguel tela = new TelaAluguel();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public TelaAluguel() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Aluguel");
		frame.setBounds(100, 100, 729, 419);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				manager = Util.conectarBanco();
				listagem();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				Util.desconectar();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label_6.setText("selecionado="+ (int) table.getValueAt( table.getSelectedRow(), 0));
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(new Color(144, 238, 144));
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");		//label de mensagem
		label.setForeground(Color.BLUE);
		label.setBounds(12, 355, 688, 14);
		frame.getContentPane().add(label);

		label_6 = new JLabel("resultados:");
		label_6.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_6);

		label_1 = new JLabel("Data de In\u00EDcio:");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(12, 269, 89, 14);
		frame.getContentPane().add(label_1);

		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(103, 266, 195, 20);
		frame.getContentPane().add(textField);

		button_1 = new JButton("Criar novo aluguel");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField.getText().isEmpty() || textField_1.getText().isEmpty() || textField_2.getText().isEmpty() || textField_3.getText().isEmpty()) {
						label.setText("campo vazio");
						return;
					}
					String dataInicio = textField.getText();
					String dataFim = textField_1.getText();
					String placa = textField_2.getText();
					String cpf = textField_3.getText();
					double diaria = Double.parseDouble(textField_4.getText());

					//localizar cliente
					Query q = manager.query();
					q.constrain(Cliente.class);
					q.descend("cpf").descend(cpf);
					List<Cliente> resultados = q.execute();
					if (resultados.size() > 0) {
						Cliente cliente = resultados.get(0);

						//localizar carro
						Query q2 = manager.query();
						q2.constrain(Carro.class);
						q2.descend("placa").descend(placa);
						List<Carro> carros = q2.execute();
						if (carros.size() > 0) {
							Carro carro = carros.get(0);

							//criar aluguel
							Aluguel aluguel = new Aluguel(dataInicio, dataFim, diaria);
							int id = Util.gerarIdAluguel();
							aluguel.setId(id);
							aluguel.setCarro(carro);
							aluguel.setCliente(cliente);
							
							carro.adicionar(aluguel);
							carro.setAlugado(true);
							cliente.adicionar(aluguel);

							//atualizar objetos no banco
							manager.store(aluguel);
							manager.commit();
							label.setText("Aluguel criado: "+ aluguel.getId());
							listagem();
						}
						else 
							label.setText("Carro não existe");
					}
					else 
						label.setText("Cliente não existe");

				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.setBounds(281, 324, 153, 23);
		frame.getContentPane().add(button_1);

		button = new JButton("Listar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setBounds(308, 11, 89, 23);
		frame.getContentPane().add(button);

		label_2 = new JLabel("Data fim aluguel");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(310, 269, 101, 14);
		frame.getContentPane().add(label_2);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(414, 266, 168, 20);
		frame.getContentPane().add(textField_1);

		button_2 = new JButton("Apagar selecionado");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0) {	
						int idAluguel = (int) table.getValueAt( table.getSelectedRow(), 0);

						//consultar aluguel banco
						Query q = manager.query();
						q.constrain(Aluguel.class);  				
						q.descend("id").constrain(idAluguel);		 
						List<Aluguel> resultados = q.execute(); 

						if(resultados.size()>0) {
							Aluguel aluguel = resultados.get(0);
							Carro carro = aluguel.getCarro();
							carro.remover(aluguel);
							aluguel.setCarro(null);

							Cliente cliente = aluguel.getCliente();
							cliente.remover(aluguel);
							aluguel.setCliente(null);

							manager.store(carro);
							manager.store(cliente);
							manager.delete(aluguel);
							manager.commit();
							label.setText("aluguel apagado" );
							listagem();
						}

					}
					else
						label.setText("nao selecionado");
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_2.setBounds(229, 215, 171, 23);
		frame.getContentPane().add(button_2);


		textField_2 = new JTextField();
		textField_2.setBounds(113, 297, 130, 19);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(47, 308, 1, 16);
		frame.getContentPane().add(textPane);

		label_3 = new JLabel("Placa do carro");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_3.setBounds(12, 295, 89, 16);
		frame.getContentPane().add(label_3);

		label_4 = new JLabel("CPF do cliente");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_4.setBounds(253, 297, 116, 16);
		frame.getContentPane().add(label_4);

		textField_3 = new JTextField();
		textField_3.setBounds(345, 294, 130, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_4.setColumns(10);
		textField_4.setBounds(555, 298, 101, 20);
		frame.getContentPane().add(textField_4);

		label_5 = new JLabel("diaria");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_5.setBounds(498, 298, 52, 16);
		frame.getContentPane().add(label_5);

		button_3 = new JButton("devolver carro");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0) {	
						int idAluguel = (int) table.getValueAt( table.getSelectedRow(), 0);

						Query q = manager.query();
						q.constrain(Aluguel.class);  				
						q.descend("id").constrain(idAluguel);		 
						List<Aluguel> resultados = q.execute(); 

						if(resultados.size()>0) {
							Aluguel aluguel = resultados.get(0);
							aluguel.setFinalizado(true);
							Carro carro = aluguel.getCarro();
							carro.setAlugado(false);

							manager.store(aluguel);
							manager.commit();
							label.setText("aluguel atualizado" );
							listagem();
						}
					}
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_3.setBounds(411, 215, 171, 23);
		frame.getContentPane().add(button_3);
	}

	public void listagem() {
		try{
			//ler os alugueis do banco
			Query q = manager.query();
			q.constrain(Aluguel.class);  				
			List<Aluguel> lista = q.execute();

			// o model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("id");
			model.addColumn("nome");
			model.addColumn("placa");
			model.addColumn("data inicial");
			model.addColumn("data final");
			model.addColumn("total a pagar");
			model.addColumn("finalizado");

			//adicionar linhas no model
			for(Aluguel aluguel : lista) {
				model.addRow(new Object[]{aluguel.getId(), aluguel.getCliente().getNome(), aluguel.getCarro().getPlaca(), aluguel.getDatainicio(), aluguel.getDatafim(), aluguel.getValor(), aluguel.isFinalizado()});
			}


			//atualizar model no table (visualizacao)
			table.setModel(model);

			label_6.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	
}

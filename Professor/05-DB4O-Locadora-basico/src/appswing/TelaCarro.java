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

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import appconsole.Util;
import modelo.Aluguel;
import modelo.Carro;
import modelo.Cliente;

public class TelaCarro {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JTextField textField_1;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;

	private ObjectContainer manager;
	private JButton button_3;

	/**
	 * Launch the application.
	 */
//		public static void main(String[] args) {
//			EventQueue.invokeLater(new Runnable() {
//				public void run() {
//					try {
//						TelaCarro tela = new TelaCarro();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		}

	/**
	 * Create the application.
	 */
	public TelaCarro() {
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
		frame.setTitle("Carro");
		frame.setBounds(100, 100, 729, 385);
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
				label_4.setText("selecionado="+ (String) table.getValueAt( table.getSelectedRow(), 0));
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.YELLOW);
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
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		label_4 = new JLabel("resultados:");
		label_4.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_4);

		label_2 = new JLabel("placa:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(21, 269, 71, 14);
		frame.getContentPane().add(label_2);

		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(68, 264, 195, 20);
		frame.getContentPane().add(textField);

		button_1 = new JButton("Criar novo carro");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField.getText().isEmpty() || textField_1.getText().isEmpty()) {
						label.setText("campo vazio");
						return;
					}
					String placa = textField.getText();
					String modelo = textField_1.getText();
					Carro car = new Carro(placa,modelo);
					
					manager.store(car);
					manager.commit();
					
					label.setText("carro criado: "+ placa);
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.setBounds(525, 265, 153, 23);
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

		label_3 = new JLabel("modelo:");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_3.setBounds(281, 269, 63, 14);
		frame.getContentPane().add(label_3);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(336, 264, 168, 20);
		frame.getContentPane().add(textField_1);

		button_2 = new JButton("Apagar selecionado");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){	
						label.setText("nao implementado " );
						String placa = (String) table.getValueAt( table.getSelectedRow(), 0);
						
						//consultar placa no banco
						Query q = manager.query();
						q.constrain(Carro.class);  				
						q.descend("placa").constrain(placa);		 
						List<Carro> resultados = q.execute(); 

						if(resultados.size()>0) {
							Carro car = resultados.get(0);
							
							for (Aluguel a : car.getAlugueis()) {
								Cliente cli = a.getCliente();
								cli.remover(a);
								//atualizar o cliente no banco
								manager.store(cli);
								manager.delete(a);  //apagar aluguel para nao ficar orfao
							}
							
							// apagar o carro
							manager.delete(car);
							manager.commit();
						}
						label.setText("carro apagado" );
						listagem();
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
		button_2.setBounds(281, 213, 171, 23);
		frame.getContentPane().add(button_2);
		
		button_3 = new JButton("exibir alugueis");
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){	
						String placa = (String) table.getValueAt( table.getSelectedRow(), 0);
						//consultar cpf no banco
						Query q = manager.query();
						q.constrain(Carro.class);  				
						q.descend("placa").constrain(placa);		 
						List<Carro> resultados = q.execute(); // select p from Pessoa p where p.nome="joao"

						if(resultados.size()>0) {
							Carro car = resultados.get(0);
							String texto="";
							for (Aluguel a : car.getAlugueis()) {
								texto = texto + a.getDatainicio()+ "-" + a.getDatafim() + "-" +a.getCliente().getNome()+ "\n";
							}
							if(texto.isEmpty())
								texto = "nao possui alugueis";
							
							JOptionPane.showMessageDialog(frame, texto, "alugueis", 1);
						}
					}
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
				
			}
		});
		button_3.setBounds(47, 215, 134, 23);
		frame.getContentPane().add(button_3);
	}

	public void listagem() {
		try{
			//ler os carros do banco
			Query q = manager.query();
			q.constrain(Carro.class);  				
			List<Carro> lista = q.execute();

			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("placa");
			model.addColumn("modelo");
			model.addColumn("alugado");

			//adicionar linhas no model
			for(Carro car : lista) {
				model.addRow(new Object[]{car.getPlaca(), car.getModelo(), car.isAlugado()} );
			}
			
			//atualizar model no table (visualizacao)
			table.setModel(model);
			
			label_4.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	

}

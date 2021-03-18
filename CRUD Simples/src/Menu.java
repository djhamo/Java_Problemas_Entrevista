import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class Menu extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
			
	JFrame frmCadastrarAlterar, frmManutencao;
	JButton btnCadastro, btnOp, btnVoltarCadAlt, btnVoltaManutencao, btnSair;
	JButton btnSalvar, btnSalvarAlteracao, btnAlterar, btnBuscar, btnExcluir, btnExibirTudo;
	JLabel lblId, lblNome, lblTelefone, lblSexo, lblRenda;
	JTextField txtNome;
	JFormattedTextField txtTelefone, txtId,txtIdConsulta, txtRenda;
	MaskFormatter mskTelefone, mskId;
	ButtonGroup gruSexo;
	JRadioButton rdoMasculino, rdoFeminino;
	JPanel pnlCampos, pnlTabela;
	TitledBorder tituloPnlCampos, tituloPnlTabela;
	DefaultTableModel tblConsultaModel;
	JTable tblConsulta;
	JScrollPane srcRolagem;
	Connection conn;
	PreparedStatement st;
	ResultSet rs;
	
	public Menu() {
		
		//Principal
		setTitle("Menu Principal");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(500, 300, 300, 210);
		
		btnCadastro = new JButton("Cadastro");
		btnCadastro.setBounds(50, 20, 150, 30);
		btnCadastro.addActionListener(this);
		
		btnOp = new JButton("Outras Operações");
		btnOp.setToolTipText("Consultas, exclusão e alteração de dados");
		btnOp.setBounds(50, 70, 150, 30);
		btnOp.addActionListener(this);
		
		btnSair = new JButton("Sair");
		btnSair.setBounds(50, 120, 150, 30);
		btnSair.addActionListener(this);
		
		add(btnCadastro);
		add(btnOp);
		add(btnSair);
		
		//Cadastro
		frmCadastrarAlterar = new JFrame("Cadastro");
		frmCadastrarAlterar.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frmCadastrarAlterar.setLayout(null);
		frmCadastrarAlterar.setBounds(400, 200, 600, 280);
		frmCadastrarAlterar.getContentPane().setBackground(Color.LIGHT_GRAY);
		
		lblId = new JLabel("ID:");
		lblId.setBounds(10, 10, 50, 30);
		frmCadastrarAlterar.add(lblId);
		
		txtId = new JFormattedTextField();
		txtId.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#"))));
		txtId.setBounds(10, 40, 150, 30);
		txtId.requestFocus();
		frmCadastrarAlterar.add(txtId);
		
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(170, 10, 50, 30);
		frmCadastrarAlterar.add(lblNome);
		
		txtNome = new JTextField(40);
		txtNome.setBounds(170, 40, 400, 30);
		frmCadastrarAlterar.add(txtNome);
		
		lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(10, 80, 150, 30);
		frmCadastrarAlterar.add(lblTelefone);
		
		try {
			mskTelefone = new MaskFormatter("(##)#####-####");
			mskTelefone.setPlaceholderCharacter('_');
		} catch (ParseException ex) {}
		txtTelefone = new JFormattedTextField(mskTelefone);
		txtTelefone.setBounds(10, 110, 100, 30);
		frmCadastrarAlterar.add(txtTelefone);
		
		lblSexo = new JLabel("Sexo");
		lblSexo.setBounds(170, 80, 150, 30);
		frmCadastrarAlterar.add(lblSexo);
		
		rdoMasculino = new JRadioButton("Masculino");
		rdoMasculino.setBounds(170, 110, 100, 30);		
		rdoFeminino = new JRadioButton("Feminino");
		rdoFeminino.setBounds(300, 110, 100, 30);
		gruSexo = new ButtonGroup();
		gruSexo.add(rdoMasculino);
		gruSexo.add(rdoFeminino);
		frmCadastrarAlterar.add(rdoMasculino);
		frmCadastrarAlterar.add(rdoFeminino);
		
		lblRenda = new JLabel("Renda:");
		lblRenda.setBounds(450, 80, 150, 30);
		frmCadastrarAlterar.add(lblRenda);
		
		txtRenda = new JFormattedTextField();
		txtRenda.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#0.00"))));
		txtRenda.setBounds(450, 110, 100, 30);
		txtRenda.setToolTipText("Utilize vírgula para separar as casas decimais");
		frmCadastrarAlterar.add(txtRenda);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(10, 170, 150, 30);
		btnSalvar.addActionListener(this);
		btnSalvar.setVisible(false);
		frmCadastrarAlterar.add(btnSalvar);
		
		btnSalvarAlteracao = new JButton("Salvar Alteração");
		btnSalvarAlteracao.setBounds(10, 170, 150, 30);
		btnSalvarAlteracao.addActionListener(this);
		btnSalvarAlteracao.setVisible(false);
		frmCadastrarAlterar.add(btnSalvarAlteracao);
		
		btnVoltarCadAlt = new JButton("Voltar");
		btnVoltarCadAlt.setBounds(170, 170, 150, 30);
		btnVoltarCadAlt.addActionListener(this);
		frmCadastrarAlterar.add(btnVoltarCadAlt);
		
		//Outras Operações
		
		frmManutencao = new JFrame("Manutenção");
		frmManutencao.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frmManutencao.setLayout(null);
		frmManutencao.setBounds(400, 200, 600, 500);
		frmManutencao.getContentPane().setBackground(Color.LIGHT_GRAY);
		
		pnlCampos = new JPanel(null);
		tituloPnlCampos = BorderFactory.createTitledBorder("Área de operações");
		pnlCampos.setBorder(tituloPnlCampos);
		pnlCampos.setBounds(10, 10, 565, 100);
		
		lblId = new JLabel("ID:");
		lblId.setBounds(10, 35, 50, 30);
		pnlCampos.add(lblId);
		
		txtIdConsulta = new JFormattedTextField();
		txtIdConsulta.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#"))));
		txtIdConsulta.setBounds(40, 35, 100, 30);
		pnlCampos.add(txtIdConsulta);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(170, 35, 80, 30);
		btnBuscar.addActionListener(this);
		pnlCampos.add(btnBuscar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(255, 35, 80, 30);
		btnExcluir.addActionListener(this);
		pnlCampos.add(btnExcluir);
		
		btnExibirTudo = new JButton("Exibir Tudo");
		btnExibirTudo.setBounds(340, 35, 100, 30);
		btnExibirTudo.addActionListener(this);
		pnlCampos.add(btnExibirTudo);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setBounds(445, 35, 80, 30);
		btnAlterar.addActionListener(this);
		pnlCampos.add(btnAlterar);
		
		frmManutencao.add(pnlCampos);
		
		pnlTabela = new JPanel(null);
		pnlTabela.setLayout(new GridLayout(1, 1));
		tituloPnlTabela = BorderFactory.createTitledBorder("Área de exibição");
		pnlTabela.setBorder(tituloPnlTabela);
		pnlTabela.setBounds(10, 120, 565, 300);
		frmManutencao.add(pnlTabela);
		
		String[] cols = {"ID", "Nome", "Telefone", "Sexo", "Renda"};
		tblConsultaModel = new DefaultTableModel(cols, 5);
		tblConsulta = new JTable(tblConsultaModel);
		tblConsultaModel.setNumRows(0);
		srcRolagem = new JScrollPane(tblConsulta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		pnlTabela.add(srcRolagem);
		
		btnVoltaManutencao = new JButton("Voltar");
		btnVoltaManutencao.setBounds(200, 425, 150, 30);
		frmManutencao.add(btnVoltaManutencao);
		btnVoltaManutencao.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object btn =arg0.getSource();
		
		if (btn == btnSair) {
			System.exit(0);
		}
		
		if (btn == btnCadastro) {
			setVisible(false);
			btnSalvar.setVisible(true);
			frmCadastrarAlterar.setVisible(true);
		}
		
		if (btn == btnVoltarCadAlt) {
			setVisible(true);
			limparCampos();
			frmCadastrarAlterar.setVisible(false);
		}
		
		if (btn == btnOp) {
			setVisible(false);
			tblConsultaModel.setRowCount(0);
			txtIdConsulta.setText("");
			frmManutencao.setVisible(true);
		}
		
		if (btn == btnVoltaManutencao) {
			setVisible(true);
			frmManutencao.setVisible(false);
		}
		
		if (btn == btnSalvar) {
			int id;
			double renda;
			String nome, telefone, sexo;
			
			id = Integer.parseInt(txtId.getText());
			nome = txtNome.getText();
			telefone = txtTelefone.getText();
			
			if(rdoMasculino.isSelected()) {
				sexo = rdoMasculino.getText();
			} else if (rdoFeminino.isSelected()) {
				sexo = rdoFeminino.getText();
			} else {
				sexo = "";
			}
			
			renda = Double.parseDouble(txtRenda.getText().replace(",", "."));
			
			if (conectar() == null) {
				JOptionPane.showMessageDialog(btnSalvar, "Erro ao tentar se conectar ao banco de dados");
			} else {
				try {
					st = conn.prepareStatement("insert into cliente (id, nome, telefone, sexo, renda) value (?,?,?,?,?)");
					st.setInt(1, id);
					
					st.setString(2, nome);
					st.setString(3, telefone);
					st.setString(4, sexo);
					st.setDouble(5, renda);
					System.out.println(st);
					st.executeUpdate();
					
					JOptionPane.showMessageDialog(btnSalvar, "Os Dados do cliente foram salvos");
					
					limparCampos();
				} catch (SQLException ex) {
					if (ex.getErrorCode() == 1062) {
						JOptionPane.showMessageDialog(btnSalvar, "Já existe um cliente com esse ID");
						txtId.requestFocus();
					} else {
						JOptionPane.showMessageDialog(btnSalvar, "Erro ao tentar salvar os dados");						
					}
				}
				desconectar();
			}
		}
		
		if (btn == btnBuscar) {
			try {
				if (conectar() == null) {
					JOptionPane.showMessageDialog(btnBuscar, "Erro ao tentar conectar ao banco de dados");
				} else {
					if (txtIdConsulta.getText().isEmpty()) {
						JOptionPane.showMessageDialog(btnBuscar, "Por favor, digite um valor no campo ID");
						txtIdConsulta.requestFocus();
					} else {
						
						int id = Integer.parseInt(txtIdConsulta.getText());
						
						st = conn.prepareStatement("select * from cliente where id = ?");
						st.setInt(1, id);
						tblConsultaModel.setRowCount(0);
						rs = st.executeQuery();
						
						if(rs.next()) {
							String[] linha = {rs.getString("id"), rs.getString("nome"), rs.getString("telefone"), rs.getString("sexo"), rs.getString("renda").replace(".", ",")};
							tblConsultaModel.addRow(linha);
						} else {
							JOptionPane.showMessageDialog(btnBuscar, "Este cliente não foi encontrado");
						}
						
						desconectar();
					}
				}
			} catch(SQLException ex) {
				JOptionPane.showMessageDialog(btnBuscar, "Erro na consulta");
			}
		}
		
		if (btn == btnExibirTudo) {
			try {
				if (conectar() == null) {
					JOptionPane.showMessageDialog(btnExibirTudo, "Erro ao tentar conectar ao banco de dados");
				} else {
					st = conn.prepareStatement("select * from cliente");
					rs = st.executeQuery();
					tblConsultaModel.setRowCount(0);
					while(rs.next()) {
						String[] linha = {rs.getString("id"), rs.getString("nome"), rs.getString("telefone"), rs.getString("sexo"), rs.getString("renda").replace(".", ",")};
						tblConsultaModel.addRow(linha);
					}
					desconectar();
				}
			} catch(SQLException ex) {
				JOptionPane.showMessageDialog(btnExibirTudo, "Erro na consulta");
			}
		}
		
		if (btn == btnExcluir) {
			try {
				if (conectar() == null) {
					JOptionPane.showMessageDialog(btnExcluir, "Erro ao tentar conectar ao banco de dados");
				} else {
					if (txtIdConsulta.getText().isEmpty()) {
						JOptionPane.showMessageDialog(btnExcluir, "Por favor, digite um valor no campo ID");
						txtIdConsulta.requestFocus();
					} else {
						int op = JOptionPane.showConfirmDialog(btnExcluir, "Tem certea que deseja excluir este cliente ?", JOptionPane.OPTIONS_PROPERTY, JOptionPane.YES_NO_OPTION);
						if (op == 0) {
							int id = Integer.parseInt(txtIdConsulta.getText());
							
							st = conn.prepareStatement("delete from cliente where id = ?");
							st.setInt(1, id);
							
							int r = st.executeUpdate();
							if (r == 1) {
								JOptionPane.showMessageDialog(btnExcluir, "Cliente excluído com sucesso");
								tblConsultaModel.setRowCount(0);
								txtIdConsulta.setText("");
								txtIdConsulta.requestFocus();
							} else {
								JOptionPane.showMessageDialog(btnExcluir, "Este cliente não está cadastrado");								
							}
							desconectar();
						}
						
					}
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(btnExcluir, "Erro na exclusão");
			}
		}
		
		if (btn == btnAlterar) {
			try {
				if (conectar() == null) {
					JOptionPane.showMessageDialog(btnAlterar, "Erro ao tentar conectar ao banco de dados");
				} else {
					if (txtIdConsulta.getText().isEmpty()) {
						JOptionPane.showMessageDialog(btnAlterar, "Por favor, digite um valor no campo ID");
						txtIdConsulta.requestFocus();
					} else {
						int id = Integer.parseInt(txtIdConsulta.getText());
						
						st = conn.prepareStatement("select * from cliente where id = ?");
						st.setInt(1, id);
				
						rs = st.executeQuery();
						
						if (rs.next()) {
							
							txtId.setText(rs.getString("id"));
							txtId.setEditable(false);
							
							txtNome.setText(rs.getString("nome"));
							txtTelefone.setText(rs.getString("telefone"));
							
							if (rs.getString("sexo").equalsIgnoreCase("masculino")) {
								rdoMasculino.setSelected(true);
							}
							if (rs.getString("sexo").equalsIgnoreCase("feminino")) {
								rdoFeminino.setSelected(true);
							}
							
							txtRenda.setText(rs.getString("renda").replace(".", ","));
							btnSalvar.setVisible(false);
							btnSalvarAlteracao.setVisible(true);
							
							frmManutencao.setVisible(false);
							frmCadastrarAlterar.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(btnAlterar, "Este cliente não foi encontrado");
								
						}
					}
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(btnAlterar, "Erro na consulta");
			}
		}
		
		if (btn == btnSalvarAlteracao) {
			int id;
			double renda;
			String nome, telefone, sexo;
			
			id = Integer.parseInt(txtId.getText());
			nome = txtNome.getText();
			telefone = txtTelefone.getText();
			
			if(rdoMasculino.isSelected()) {
				sexo = rdoMasculino.getText();
			} else if (rdoFeminino.isSelected()) {
				sexo = rdoFeminino.getText();
			} else {
				sexo = "";
			}
			
			renda = Double.parseDouble(txtRenda.getText().replace(",", "."));
			
			if (conectar() == null) {
				JOptionPane.showMessageDialog(btnSalvarAlteracao, "Erro ao tentar se conectar ao banco de dados");
			} else {
				try {
					st = conn.prepareStatement("update cliente set nome = ?, telefone = ?, sexo = ?, renda = ? where id = ?");
					st.setInt(5, id);
					st.setString(1, nome);
					st.setString(2, telefone);
					st.setString(3, sexo);
					st.setDouble(4, renda);
					st.executeUpdate();
					
					JOptionPane.showMessageDialog(btnSalvarAlteracao, "Os Dados do cliente foram alterados");
					
					limparCampos();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(btnSalvarAlteracao, "Erro ao tentar salvar os dados");
				}
				desconectar();
				limparCampos();
				frmCadastrarAlterar.setVisible(false);
				tblConsultaModel.setRowCount(0);
				txtIdConsulta.setText("");
				txtIdConsulta.requestFocus();
				frmManutencao.setVisible(true);
			}
		}
	}
	
	
	private Connection conectar() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://10.1.1.11:3306/test", "root", "");
			return conn;
		} catch (ClassNotFoundException | SQLException ex) {
			return null;
		}
	}
	
	private void desconectar() {
		try {
			conn.close();			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(btnSalvar,  "Erro ao tentar se desconectar do banco de dados");
		}
	}
	
	private void limparCampos() {
		txtId.setEditable(true);
		txtId.setText("");
		txtNome.setText("");
		txtTelefone.setText("");
		gruSexo.clearSelection();
		txtRenda.setText("");
		txtId.requestFocus();
	}
	
}

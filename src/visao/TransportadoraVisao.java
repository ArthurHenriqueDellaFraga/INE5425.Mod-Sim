package visao;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import controle.TransportadoraControle;
import padrao_de_projeto.Captador;

public class TransportadoraVisao extends JPanel implements Captador<TransportadoraDTO>{
	private final InterfaceDaAplicacao INTERFACE_DA_APLICACAO = InterfaceDaAplicacao.invocarInstancia();
	private final TransportadoraControle CONTROLE;
	
	private JLabel jLabel = new JLabel("");
	private JPanel[] jPanel = new JPanel[]{ new JPanel(), new JPanel() };
	private JButton[] jButton = new JButton[]{
			new JButton("Prosseguir 1 vez"){{
				addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){					
						CONTROLE.prosseguir(1);
					}
				});
			}},
			new JButton("Prosseguir * vezes"){{
				addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){					
						CONTROLE.prosseguir(INTERFACE_DA_APLICACAO.coletarNumero("Insira o periodo a se prosseguir", "Coletar Numero"));
					}
				});
			}},
			new JButton("Prosseguir até o próximo evento"){{
				addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						CONTROLE.prosseguirProximoEvento();
					}
				});
			}},
	};
	
	
	public TransportadoraVisao(TransportadoraControle controle){
		super();
		this.CONTROLE = controle;
		
		TransportadoraDTO dto = new TransportadoraDTO();
		JTabbedPane jTabbedPane = new JTabbedPane();
		
		jPanel[0].add(new JScrollPane(new JTable(dto.conteudoLinhaDoTempo, dto.cabecalhoLinhaDoTempo)));
		jPanel[1].setPreferredSize(new Dimension(400, 300));
		
		jTabbedPane.addTab("Linha do tempo", jPanel[0]);
		jTabbedPane.addTab("Estatisticas", jPanel[1]);
		
		this.add(jLabel);
		this.add(jTabbedPane);
		for(JButton button : jButton){
			this.add(button);
		}
		
		INTERFACE_DA_APLICACAO.FRAME_FAMILIAR.add(this);
		INTERFACE_DA_APLICACAO.FRAME_FAMILIAR.setVisible(true);
	}
	
	//FUNCOES

	public void captar(TransportadoraDTO dto) {
		atualizarReferenciaTemporal(dto.referenciaTemporal);
		atualizarLinhaDoTempo(dto.conteudoLinhaDoTempo, dto.cabecalhoLinhaDoTempo);
		atualizarEstatisticas(dto);
	
		INTERFACE_DA_APLICACAO.FRAME_FAMILIAR.revalidate();
		INTERFACE_DA_APLICACAO.FRAME_FAMILIAR.repaint();
	}
	
	private void atualizarReferenciaTemporal(int referenciaTemporal){
		jLabel.setText("Momento " + referenciaTemporal);
	}
	
	private void atualizarLinhaDoTempo(String[][] conteudo, String[] cabecalho){
		jPanel[0].removeAll();
		jPanel[0].add(new JScrollPane(new JTable(conteudo, cabecalho)));
	}
	
	private void atualizarEstatisticas(TransportadoraDTO dto){
		jPanel[1].removeAll();
		JTabbedPane jTabbedPane = new JTabbedPane();
		JPanel[] jPanelAuxiliar = new JPanel[]{
			new JPanel(new GridLayout(2, 0)),
			new JPanel(new GridLayout(2, 0)),
		};
		
		//
		JPanel jPanel1 = new JPanel(new GridLayout(3, 0));
		jPanel1.add(new JLabel("Carregador",JLabel.CENTER));
		jPanel1.add(new JLabel("Tamanho da fila", JLabel.CENTER));
		String[] cabecalho = new String[]{"Tamanho atual", "Minimo", "Medio", "Maximo"};
		Object[][] conteudo = new Object[][]{{ 
			dto.QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO,
			dto.TAMANHO_MINIMO_FILA_CARREGAMENTO,
			dto.TAMANHO_MEDIO_FILA_CARREGAMENTO,
			dto.TAMANHO_MAXIMO_FILA_CARREGAMENTO
		}};
		JScrollPane jScrollPane = new JScrollPane(new JTable(conteudo, cabecalho));
		jScrollPane.setPreferredSize(new Dimension(400, 50));
		jPanel1.add(jScrollPane);
		jPanelAuxiliar[0].add(jPanel1);
			
		//
		jPanel1 = new JPanel(new GridLayout(3, 0));
		jPanel1.add(new JLabel("Tempo da entidade na fila", JLabel.CENTER));
		cabecalho = new String[]{"Minimo", "Medio", "Maximo"};
		conteudo = new Object[][]{{ 
			dto.TEMPO_MINIMO_ENTIDADE_FILA_CARREGADOR,
			dto.TEMPO_MEDIO_ENTIDADE_FILA_CARREGADOR,
			dto.TEMPO_MAXIMO_ENTIDADE_FILA_CARREGADOR
		}};
		jScrollPane = new JScrollPane(new JTable(conteudo, cabecalho));
		jScrollPane.setPreferredSize(new Dimension(400, 50));
		jPanel1.add(jScrollPane);
		jPanel1.add(new JLabel("Taxa media de ocupacao: " + dto.TAXA_MEDIA_OCUPACAO_CARREGADOR,JLabel.CENTER));
		jPanelAuxiliar[0].add(jPanel1);
		
		//
		jPanel1 = new JPanel(new GridLayout(3, 0));
		jPanel1.add(new JLabel("Balanca", JLabel.CENTER));
		jPanel1.add(new JLabel("Tamanho da fila", JLabel.CENTER));
		cabecalho = new String[]{"Tamanho atual", "Minimo", "Medio", "Maximo"};
		conteudo = new Object[][]{{ 
			dto.QUANTIDADE_CAMINHOES_FILA_PESAGEM,
			dto.TAMANHO_MINIMO_FILA_PESAGEM,
			dto.TAMANHO_MEDIO_FILA_PESAGEM,
			dto.TAMANHO_MAXIMO_FILA_PESAGEM
		}};
		jScrollPane = new JScrollPane(new JTable(conteudo, cabecalho));
		jScrollPane.setPreferredSize(new Dimension(400, 50));
		jPanel1.add(jScrollPane);
		jPanelAuxiliar[1].add(jPanel1);
		
		//
		jPanel1 = new JPanel(new GridLayout(3, 0));
		jPanel1.add(new JLabel("Tempo da entidade na fila", JLabel.CENTER));
		cabecalho = new String[]{"Minimo", "Medio", "Maximo"};
		conteudo = new Object[][]{{ 
			dto.TEMPO_MINIMO_ENTIDADE_FILA_PESAGEM,
			dto.TEMPO_MEDIO_ENTIDADE_FILA_PESAGEM,
			dto.TEMPO_MAXIMO_ENTIDADE_FILA_PESAGEM
		}};
		jScrollPane = new JScrollPane(new JTable(conteudo, cabecalho));
		jScrollPane.setPreferredSize(new Dimension(400, 50));
		jPanel1.add(jScrollPane);
		jPanel1.add(new JLabel("Taxa media de ocupacao: " + dto.TAXA_MEDIA_OCUPACAO_CARREGADOR, JLabel.CENTER));
		jPanelAuxiliar[1].add(jPanel1);
		
		jTabbedPane.addTab("Carregador", jPanelAuxiliar[0]);
		jTabbedPane.addTab("Balanca", jPanelAuxiliar[1]);
		jPanel[1].add(jTabbedPane);
		
		jPanel1 = new JPanel(new GridLayout(2, 0));
		jPanel1.add(new JLabel(dto.CONTADOR_VIAGENS + " entregas realzadas", JLabel.CENTER));
		jPanel1.add(new JLabel(dto.QUANTIDADE_CAMINHOS_ENTREGANDO + " caminhoes em percurso", JLabel.CENTER));
		jPanel[1].add(jPanel1);

	}
}

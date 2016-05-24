package visao;

import java.awt.Dimension;
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
	};
	
	
	public TransportadoraVisao(TransportadoraControle controle){
		super();
		this.CONTROLE = controle;
		
		TransportadoraDTO dto = new TransportadoraDTO();
		JTabbedPane jTabbedPane = new JTabbedPane();
		
		jPanel[0].add(new JScrollPane(new JTable(dto.conteudoLinhaDoTempo, dto.cabecalhoLinhaDoTempo)));
		
		jTabbedPane.addTab("Linha do tempo", jPanel[0]);
		jTabbedPane.addTab("Estatisticas", jPanel[1]);
		
		this.add(jLabel);
		this.add(jTabbedPane);
		this.add(jButton[0]);
		this.add(jButton[1]);
		
		INTERFACE_DA_APLICACAO.FRAME_FAMILIAR.add(this);
		INTERFACE_DA_APLICACAO.FRAME_FAMILIAR.setVisible(true);
	}
	
	//FUNCOES

	public void captar(TransportadoraDTO dto) {
		atualizarReferenciaTemporal(dto.referenciaTemporal);
		atualizarLinhaDoTempo(dto.conteudoLinhaDoTempo, dto.cabecalhoLinhaDoTempo);
	
		INTERFACE_DA_APLICACAO.FRAME_FAMILIAR.revalidate();
		INTERFACE_DA_APLICACAO.FRAME_FAMILIAR.repaint();
	}
	
	private void atualizarReferenciaTemporal(int referenciaTemporal){
		jLabel.setText("Momento " + referenciaTemporal);
	}
	
	private void atualizarLinhaDoTempo(String[][] conteudo, String[] cabecalho){
		jPanel[0].remove(0);
		jPanel[0].add(new JScrollPane(new JTable(conteudo, cabecalho)));
	}
}

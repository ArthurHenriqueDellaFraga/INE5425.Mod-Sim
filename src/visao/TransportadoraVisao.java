package visao;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import padrao_de_projeto.Captador;

public class TransportadoraVisao extends JPanel implements Captador<TransportadoraDTO>{
	private JLabel jLabel = new JLabel("Momento 0");
	private JPanel[] jPanel = new JPanel[]{ new JPanel(), new JPanel() };
	private JButton[] jButton = new JButton[]{};
	
	
	public TransportadoraVisao(){
		super();
		
		TransportadoraDTO dto = new TransportadoraDTO();
		JTabbedPane jTabbedPane = new JTabbedPane();
		
		jPanel[0].add(new JScrollPane(new JTable(dto.conteudoLinhaDoTempo, dto.cabecalhoLinhaDoTempo)));
		
		jTabbedPane.addTab("Linha do tempo", jPanel[0]);
		jTabbedPane.addTab("Estatisticas", jPanel[1]);
		
		this.add(jLabel);
		this.add(jTabbedPane);
		
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.add(this);
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.setVisible(true);
	}
	
	//FUNCOES

	public void captar(TransportadoraDTO dto) {
		atualizarReferenciaTemporal(dto.referenciaTemporal);
		atualizarLinhaDoTempo(dto.conteudoLinhaDoTempo, dto.cabecalhoLinhaDoTempo);
	
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.revalidate();
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.repaint();
	}
	
	private void atualizarReferenciaTemporal(int referenciaTemporal){
		jLabel.setText("Momento " + referenciaTemporal);
	}
	
	private void atualizarLinhaDoTempo(String[][] conteudo, String[] cabecalho){
		jPanel[0].remove(0);
		jPanel[0].add(new JScrollPane(new JTable(conteudo, cabecalho)));
	}
}

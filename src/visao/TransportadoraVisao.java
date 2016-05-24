package visao;

import javax.swing.JPanel;
import javax.swing.JTable;

import padrao_de_projeto.Captador;

public class TransportadoraVisao extends JPanel implements Captador<TransportadoraDTO>{
	private JTable linhaDoTempoJTable;
	
	
	public TransportadoraVisao(){
		super();
		
		TransportadoraDTO dto = new TransportadoraDTO();
		linhaDoTempoJTable = new JTable(dto.conteudoLinhaDoTempo, dto.cabecalhoLinhaDoTempo);
		
		this.add(linhaDoTempoJTable);
		
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.add(this);
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.setVisible(true);
	}
	
	//FUNCOES

	public void captar(TransportadoraDTO dto) {
		linhaDoTempoJTable = new JTable(dto.conteudoLinhaDoTempo, dto.cabecalhoLinhaDoTempo);
		
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.repaint();
	}
}

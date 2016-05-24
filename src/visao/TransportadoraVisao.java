package visao;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import padrao_de_projeto.Captador;

public class TransportadoraVisao extends JPanel implements Captador<TransportadoraDTO>{
	private JScrollPane linhaDoTempoJTable;
	
	
	public TransportadoraVisao(){
		super();
		
		TransportadoraDTO dto = new TransportadoraDTO();
		linhaDoTempoJTable = new JScrollPane(new JTable(dto.conteudoLinhaDoTempo, dto.cabecalhoLinhaDoTempo));
		
		this.add(new JScrollPane(linhaDoTempoJTable));
		
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.add(this);
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.setVisible(true);
	}
	
	//FUNCOES

	public void captar(TransportadoraDTO dto) {
		atualizarLinhaDoTempoJTable(dto.conteudoLinhaDoTempo, dto.cabecalhoLinhaDoTempo);
	
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.revalidate();
		InterfaceDaAplicacao.invocarInstancia().FRAME_FAMILIAR.repaint();
	}
	
	private void atualizarLinhaDoTempoJTable(String[][] conteudo, String[] cabecalho){
		this.remove(0);
		linhaDoTempoJTable = new JScrollPane(new JTable(conteudo, cabecalho));
		this.add(linhaDoTempoJTable);
	}
}

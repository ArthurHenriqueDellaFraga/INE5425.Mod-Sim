package visao;

import javax.swing.JFrame;

public class InterfaceDaAplicacao extends Comunicador{
	private static InterfaceDaAplicacao INSTANCIA = new InterfaceDaAplicacao();

	protected final JFrame FRAME_FAMILIAR;

	private InterfaceDaAplicacao() {
		FRAME_FAMILIAR = new JFrame("INE5425 - Modelagem e Simulacao");
		FRAME_FAMILIAR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME_FAMILIAR.setSize(500, 800);
	}

	public static InterfaceDaAplicacao invocarInstancia() {
		return INSTANCIA;
	}

	// FUNCOES
	
	public int iniciar(){
		String mensagemInicializacao = "UNIVERSIDADE FEDERAL DE SANTA CATARINA"
				+ "\n" + "MODELAGEM & SIMULACAO"
				+ "\n\n" + "Arthur Henrique Della Fraga \t 11200623"
				+ "\n" + "Felipe Calistro Chaiben \t 11200632";
		
		return apresentarDialogoOpitativo(mensagemInicializacao, "TRABALHO 1 - TRANSPORTADORA", new String[]{"Iniciar"});
	}
}

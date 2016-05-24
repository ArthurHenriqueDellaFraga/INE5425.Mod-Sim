package visao;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import modelo.Transportadora;

public class InterfaceDaAplicacao extends Comunicador{
	private static InterfaceDaAplicacao INSTANCIA = new InterfaceDaAplicacao();

	protected final JFrame FRAME_FAMILIAR;

	private InterfaceDaAplicacao() {
		FRAME_FAMILIAR = new JFrame("INE5425 - Modelagem e Simulacao");
		
		FRAME_FAMILIAR.setSize(500, 600);
		FRAME_FAMILIAR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		    int x = (int) ((dimension.getWidth() - FRAME_FAMILIAR.getWidth()) / 2);
		    int y = (int) ((dimension.getHeight() - FRAME_FAMILIAR.getHeight()) / 2);
		FRAME_FAMILIAR.setLocation(x, y);
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
		
		return apresentarDialogoOpitativo(mensagemInicializacao, "TRABALHO 1 - TRANSPORTADORA", new String[]{"Inicializacao padrao", "Inicializacao customizada"});
	}
	
	public void inicializacaoCustomizada(){
		String titulo = "Definicao de valores";
		
		Transportadora.NUMERO_DE_CAMINHOES = coletarNumero("Numero de Caminhoes", titulo);
		Transportadora.QUANTIDADE_CARREGADOR = coletarNumero("Quantidade de Carregadores", titulo);
		Transportadora.QUANTIDADE_BALANCA = coletarNumero("Quantidade de Balancas", titulo);
		
		Transportadora.TEMPO_CARGA = coletarNumero("Tempo de Carga", titulo);
		Transportadora.TEMPO_PESAGEM = coletarNumero("Tempo de Pesagem", titulo);
		Transportadora.TEMPO_TRANSPORTE = coletarNumero("Tempo de Transporte", titulo);
	}
}

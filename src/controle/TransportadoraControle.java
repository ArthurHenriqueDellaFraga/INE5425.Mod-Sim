package controle;

import modelo.Transportadora;
import primitivo.LinhaDoTempo;
import visao.TransportadoraVisao;

public class TransportadoraControle {
	private final Transportadora transportadora;
	private final LinhaDoTempo linhaDoTempo;
	
	public TransportadoraControle(Transportadora transportadora){
		this.transportadora = transportadora;
		linhaDoTempo = transportadora.linhaDoTempo;
		
		TransportadoraVisao visao = new TransportadoraVisao(this);
		transportadora.propagador.inscrever(visao);
		transportadora.propagador.propagar();
	}

	public void prosseguir(int periodo) {
		for(int i=0; i < periodo; i++){
			linhaDoTempo.prosseguir();
		}
	}
	
	//FUNCOES
	
}

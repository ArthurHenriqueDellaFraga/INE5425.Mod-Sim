package controle;

import modelo.Transportadora;
import visao.TransportadoraVisao;

public class TransportadoraControle {
	private final Transportadora transportadora;
	
	public TransportadoraControle(Transportadora transportadora){
		this.transportadora = transportadora;
		TransportadoraVisao visao = new TransportadoraVisao();
		
		transportadora.propagador.inscrever(visao);
	}
	
	//FUNCOES
	
}

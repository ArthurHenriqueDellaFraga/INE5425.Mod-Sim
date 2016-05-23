package modelo;

import padrao_de_projeto.Captador;
import primitivo.LinhaDoTempo;
import primitivo.Momento;
import primitivo.Ocorrencia;

public abstract class Temporal implements Captador<Momento>{
	protected Momento momento = LinhaDoTempo.inicio;
	
	public void registrar(Ocorrencia o){
		this.momento.add(o);
	};
	
	public void captar(Momento momento) {
		this.momento = momento;
		prosseguir();
	}
	
	public abstract void prosseguir();
}

package Modelo;

import PadraoDeProjeto.Captador;
import Primitivo.LinhaDoTempo;
import Primitivo.Momento;
import Primitivo.Ocorrencia;

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

package Modelo;

import PadraoDeProjeto.Captador;
import Primitivo.Momento;
import Primitivo.Ocorrencia;

public abstract class Temporal implements Captador<Momento>{
	protected Momento momento;
	
	public void registrar(Ocorrencia o){
		if(this.momento != null){
			this.momento.add(o);
		}
	};
	
	public void captar(Momento momento) {
		this.momento = momento;
		prosseguir();
	}
	
	public abstract void prosseguir();
}

package modelo;

import primitivo.LinhaDoTempo;

public abstract class Simulacao extends Temporal {
	public final LinhaDoTempo linhaDoTempo = new LinhaDoTempo(this);
	
	//FUNCOES 
	
	public abstract void simular();
	
}

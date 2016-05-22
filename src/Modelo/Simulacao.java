package Modelo;

import Primitivo.LinhaDoTempo;

public abstract class Simulacao extends Temporal {
	protected LinhaDoTempo linhaDoTempo = new LinhaDoTempo(this);
	
	public abstract void simular();
	
}

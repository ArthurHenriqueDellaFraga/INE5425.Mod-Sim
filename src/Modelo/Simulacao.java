package Modelo;

import Primitivo.LinhaDoTempo;

public abstract class Simulacao {
	protected LinhaDoTempo linhaDoTempo = LinhaDoTempo.invocarInstancia();
	
	public abstract void simular();
	
}

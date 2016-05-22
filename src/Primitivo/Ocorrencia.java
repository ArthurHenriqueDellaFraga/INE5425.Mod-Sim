package Primitivo;

public class Ocorrencia {
	public final int clienteId;
	public final Evento evento;
	public final int recursoId;
	
	public Ocorrencia(int clienteId, Evento evento, int recursoId){
		this.clienteId = clienteId;
		this.evento = evento;
		this.recursoId = recursoId;
	}
	
	//FUNCOES
	
	public String toString(){
		return this.clienteId + " " + this.evento + " " + this.recursoId;
	}
	
	//SUBCLASSES
	
	public enum Evento {
		Chegada, InicioDoAtendimento, FimDoAtendimento
	}
}

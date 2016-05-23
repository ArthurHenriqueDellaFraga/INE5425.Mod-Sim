package primitivo;

import modelo.Cliente;
import modelo.Recurso;

public class Ocorrencia {
	public final Cliente cliente;
	public final Evento evento;
	public final Recurso recurso;
	
	public Ocorrencia(Cliente cliente, Evento evento, Recurso recurso){
		this.cliente = cliente;
		this.evento = evento;
		this.recurso = recurso;
	}
	
	//FUNCOES
	
	public String toString(){
		String string = "Caminhao" + this.cliente.id
					  + " - " + this.evento + " - " 
					  + this.recurso.nome;
		
		return string;
	}
	
	//SUBCLASSES
	
	public enum Evento {
		Chegada, InicioDoAtendimento, FimDoAtendimento
	}
}

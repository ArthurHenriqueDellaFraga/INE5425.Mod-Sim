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
		Chegada("Chegada"),
		InicioDoAtendimento("Inicio do atendimento"),
		FimDoAtendimento("Fim do atendimento");
		
		private String string;
		
		Evento(String string){
			this.string = string;
		}
		
		public String toString(){
			return string;
		}
	}
}

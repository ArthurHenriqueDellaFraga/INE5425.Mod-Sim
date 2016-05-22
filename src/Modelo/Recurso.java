package Modelo;

import java.util.ArrayList;

import Primitivo.Ocorrencia;
import Primitivo.Ocorrencia.Evento;

public abstract class Recurso extends Temporal{
	public static int quantidade = 0;
	public final int id = quantidade++;
	public final String nome;
	
	private int capacidade = 0;
	private ArrayList<Cliente> filaDeEspera = new ArrayList<Cliente>();
	private ArrayList<Servico> listaDeServicosEmAndamento = new ArrayList<Servico>();
	
	public Recurso(String nome){
		this.nome = nome;
	}
	
	public Recurso(String nome, int capacidade){
		this.nome = nome;
		this.capacidade = capacidade;
	}
	
	//FUNCOES
	
	public void receber(Cliente cliente){
		if(capacidade == 0 || listaDeServicosEmAndamento.size() < capacidade){
			listaDeServicosEmAndamento.add(
				new Servico(tempoDeServico(), cliente)
			);
		}
		else{
			filaDeEspera.add(cliente);
		}
	}
	
	//ABSTRACT
	
	public void prosseguir(){		
		for(int i = 0; i < listaDeServicosEmAndamento.size(); i++){
			Servico servico = listaDeServicosEmAndamento.get(i);
			
			try{
				servico.prosseguir();
			}
			catch(Servico.ServicoConcluidoException e){
				if(!filaDeEspera.isEmpty()){
					listaDeServicosEmAndamento.add(
							i, 
							new Servico(tempoDeServico(), filaDeEspera.remove(0))				
					);
				}
			}
			
		}
	}
	
	public abstract int tempoDeServico();
	
	//SUBCLASSES
	
	public class Servico{
		private int periodoRestante;
		private Cliente cliente;
		
		public Servico(int tempoDeServico, Cliente cliente){
			this.periodoRestante = tempoDeServico;
			this.cliente = cliente;
			
			registrar(new Ocorrencia(cliente, Evento.InicioDoAtendimento, Recurso.this));
		}
		
		public void prosseguir(){
			periodoRestante--;
			
			if(periodoRestante == 0){
				cliente.prosseguir();
				listaDeServicosEmAndamento.remove(cliente);
				
				registrar(new Ocorrencia(cliente, Evento.FimDoAtendimento, Recurso.this));
				throw new ServicoConcluidoException();
			}
		}
		
		public class ServicoConcluidoException extends RuntimeException{ }
	}
}

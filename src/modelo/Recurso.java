package modelo;

import java.util.ArrayList;

import padrao_de_projeto.Propagador;
import primitivo.Ocorrencia;
import primitivo.Ocorrencia.Evento;

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
	
	public class Servico extends Propagador<Ocorrencia>{
		private int periodoRestante;
		private Cliente cliente;
		
		public Servico(int tempoDeServico, Cliente cliente){
			this.periodoRestante = tempoDeServico;
			this.cliente = cliente;
			
			inscrever(cliente.getAtencao());
			Ocorrencia o = new Ocorrencia(cliente, Evento.InicioDoAtendimento, Recurso.this);
			propagar(o);
			registrar(o);
		}
		
		public void prosseguir(){
			periodoRestante--;
			
			if(periodoRestante == 0){
				listaDeServicosEmAndamento.remove(cliente);
				
				Ocorrencia o = new Ocorrencia(cliente, Evento.FimDoAtendimento, Recurso.this);
				propagar(o);
				registrar(o);
				
				throw new ServicoConcluidoException();
			}
		}
		
		public class ServicoConcluidoException extends RuntimeException{ }
	}
}

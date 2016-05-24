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
	
	private ArrayList<Cliente> filaDeChegada = new ArrayList<Cliente>();
	
	public Recurso(String nome){
		this.nome = nome;
	}
	
	public Recurso(String nome, int capacidade){
		this.nome = nome;
		this.capacidade = capacidade;
	}
	
	public boolean equals(String recurso){
		
		return false;
	}
	
	//FUNCOES
	
	public void receber(Cliente cliente, int referenciaTemporal){
		if(referenciaTemporal < momento.referenciaTemporal){
			
			receber(cliente);
		}
		else{
			filaDeChegada.add(cliente);
		}
	}
	
	private void receber(Cliente cliente){
		Ocorrencia o = new Ocorrencia(cliente, Evento.Chegada, Recurso.this);
		registrar(o);
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
				listaDeServicosEmAndamento.remove(i);
				if(!filaDeEspera.isEmpty()){
					listaDeServicosEmAndamento.add(
							i, 
							new Servico(tempoDeServico(), filaDeEspera.remove(0))				
					);
				}
				else{
					i--;
				}
			}
		}
		
		for(Cliente cliente : filaDeChegada){
			receber(cliente);
		}
		filaDeChegada.clear();
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
			registrar(o);
			propagar(o);
		}
		
		public void prosseguir(){
			periodoRestante--;
			
			if(periodoRestante == 0){
				listaDeServicosEmAndamento.remove(cliente);
				
				Ocorrencia o = new Ocorrencia(cliente, Evento.FimDoAtendimento, Recurso.this);
				registrar(o);
				propagar(o);
				
				throw new ServicoConcluidoException();
			}
		}
		
		public class ServicoConcluidoException extends RuntimeException{ }
	}
}

package Modelo;

import java.util.ArrayList;

import PadraoDeProjeto.Captador;
import Primitivo.LinhaDoTempo;
import Primitivo.Momento;
import Primitivo.Ocorrencia;
import Primitivo.Ocorrencia.Evento;

public abstract class Recurso extends Captador<Momento>{
	public static int quantidade = 0;
	public final int id = quantidade++;
	
	private int capacidade = 0;
	private ArrayList<Cliente> filaDeEspera = new ArrayList<Cliente>();
	private ArrayList<Servico> listaDeServicosEmAndamento = new ArrayList<Servico>();
	
	private ExtratoDeFuncionamento extratoDeFuncionamento = new ExtratoDeFuncionamento();
	
	public Recurso() { }
	
	public Recurso(int capacidade){
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
	
	public void captar(Momento momento){		
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
	
	public String toString(){
		return extratoDeFuncionamento.toString();
	}
	
	//SUBCLASSES
	
	public class Servico{
		private int periodoRestante;
		private Cliente cliente;
		
		public Servico(int tempoDeServico, Cliente cliente){
			this.periodoRestante = tempoDeServico;
			this.cliente = cliente;
			
			extratoDeFuncionamento.registrar(new Ocorrencia(cliente.id, Evento.InicioDoAtendimento, id));
		}
		
		public void prosseguir(){
			periodoRestante--;
			
			if(periodoRestante == 0){
				cliente.prosseguir();
				listaDeServicosEmAndamento.remove(cliente);
				
				extratoDeFuncionamento.registrar(new Ocorrencia(cliente.id, Evento.FimDoAtendimento, id));
				throw new ServicoConcluidoException();
			}
		}
		
		public class ServicoConcluidoException extends RuntimeException{ }
	}
	
	public class ExtratoDeFuncionamento {
		
		public ExtratoDeFuncionamento(){ }
		
		//FUNCOES
		
		public void registrar(Ocorrencia registro){
			LinhaDoTempo.invocarInstancia().registrar(registro);
		}
		
		//ABSTRACT
		
		public String toString(){
			return LinhaDoTempo.invocarInstancia().toString(id);
		}
		
	}
}

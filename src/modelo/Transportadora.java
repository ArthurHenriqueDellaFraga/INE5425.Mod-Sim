package modelo;

import java.util.ArrayList;

import controle.TransportadoraControle;
import padrao_de_projeto.Propagador;
import primitivo.Momento;
import primitivo.Ocorrencia;
import primitivo.Ocorrencia.Evento;
import visao.TransportadoraDTO;

public class Transportadora extends Simulacao{
	
	//DADOS A SEREM APRESENTADOS
	
	public static int QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO = 0;
	public static int TAMANHO_MINIMO_FILA_CARREGAMENTO = Integer.MAX_VALUE;
	public static int TAMANHO_MAXIMO_FILA_CARREGAMENTO = Integer.MIN_VALUE;
	public static int TAMANHO_MEDIO_FILA_CARREGAMENTO = 0;
	
	public static int QUANTIDADE_CAMINHOES_FILA_PESAGEM = 0;
	public static int TAMANHO_MINIMO_FILA_PESAGEM = Integer.MIN_VALUE;
	public static int TAMANHO_MAXIMO_FILA_PESAGEM = Integer.MIN_VALUE;
	public static int TAMANHO_MEDIO_FILA_PESAGEM = 0;
	
	public static int QUANTIDADE_CAMINHOS_ENTREGANDO = 0;
	
	
	//TAMANHO DA FROTA
	private final int TAMANHO_FROTA = 2;
	
	//QUANTIDADE DE RECURSOS
	private final int QUANTIDADE_CARREGADOR = 2;
	private final int QUANTIDADE_BALANCA = 1;
	
	//TEMPO DE ESPERA
	private final int TEMPO_CARREGAMENTO = 5;
	private final int TEMPO_PESAGEM = 2;
	private final int TEMPO_ENTREGA = 10;
	
	//ATRIBUTOS
	public final TransportadoraPropagador propagador = new TransportadoraPropagador();
	
	private ArrayList<Recurso> percurso;
	private ArrayList<Caminhao> frota;

	public Transportadora(){
		super();
		new TransportadoraControle(this);
		
		this.percurso = new ArrayList<Recurso>();
			Recurso carregamento = new Recurso("Carregador", QUANTIDADE_CARREGADOR){
				public int tempoDeServico() {
					return TEMPO_CARREGAMENTO;
				}
			};
			Recurso pesagem = new Recurso("Balança", QUANTIDADE_BALANCA) {
				public int tempoDeServico() {
					return TEMPO_PESAGEM;
				}

			};
			Recurso entrega = new Recurso("Estrada") {
				public int tempoDeServico() {
					return TEMPO_ENTREGA;
				}
			};
		
			this.percurso.add(carregamento);
			this.percurso.add(pesagem);
			this.percurso.add(entrega);
		
		
		this.frota = new ArrayList<Caminhao>();
		for(int i = 0; i < TAMANHO_FROTA; i++){
			this.frota.add(new Caminhao(percurso));
		}
	}
	
	//FUNCOES
	
	public void simular() {
		for(int i = 0; i < 10; i++){
			linhaDoTempo.prosseguir();
		}
		
		//calcularNumeroEntidadesNaFila();
		
		propagador.propagar();
	}
	
	public void calcularNumeroEntidadesNaFila(){
		for(Momento momento : linhaDoTempo.getLinhaDoTempo()){
			for(Ocorrencia ocorrencia : momento.listaDeOcorrencia){
				if(ocorrencia.recurso.nome.equals("Balança")){
					if(ocorrencia.evento.name().equals(Evento.InicioDoAtendimento)){
						QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO++;
					}
					else if(ocorrencia.evento.name().equals(Evento.FimDoAtendimento)){
						QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO--;
					}
				}
				else if (ocorrencia.recurso.nome.equals("Carregador")){
					if(ocorrencia.evento.name().equals(Evento.InicioDoAtendimento)){
						QUANTIDADE_CAMINHOES_FILA_PESAGEM++;
					}
					else if(ocorrencia.evento.name().equals(Evento.FimDoAtendimento)){
						QUANTIDADE_CAMINHOES_FILA_PESAGEM--;
					}
				}
				else{
					if(ocorrencia.evento.name().equals(Evento.InicioDoAtendimento)){
						QUANTIDADE_CAMINHOS_ENTREGANDO++;
					}
					else if(ocorrencia.evento.name().equals(Evento.FimDoAtendimento)){
						QUANTIDADE_CAMINHOS_ENTREGANDO--;
					}
				}
			}
		}
		QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO = 0;
		TAMANHO_MINIMO_FILA_CARREGAMENTO = Integer.MAX_VALUE;
		TAMANHO_MAXIMO_FILA_CARREGAMENTO = Integer.MIN_VALUE;
		TAMANHO_MEDIO_FILA_CARREGAMENTO = 0;
		
		QUANTIDADE_CAMINHOES_FILA_PESAGEM = 0;
		TAMANHO_MINIMO_FILA_PESAGEM = Integer.MIN_VALUE;
		TAMANHO_MAXIMO_FILA_PESAGEM = Integer.MIN_VALUE;
		TAMANHO_MEDIO_FILA_PESAGEM = 0;
	}

	//ABSTRACT
	
	public void prosseguir(){
		for(Recurso recurso : percurso){
			recurso.captar(momento);
		}
		for(Caminhao caminhao : frota){
			caminhao.captar(momento);
		}
	}
	
	//SUBCLASSES
	
	public class TransportadoraPropagador extends Propagador<TransportadoraDTO>{
		
		public void propagar(){
			propagar(
				new TransportadoraDTO(
						linhaDoTempo.getPresente().referenciaTemporal,
						linhaDoTempo.toTable()
				)
			);
		}
	}
	
}

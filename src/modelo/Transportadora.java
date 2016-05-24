package modelo;

import java.util.ArrayList;
import java.util.HashMap;

import controle.TransportadoraControle;
import padrao_de_projeto.Propagador;
import primitivo.LinhaDoTempo;
import primitivo.Momento;
import primitivo.Ocorrencia;
import primitivo.Ocorrencia.Evento;
import visao.TransportadoraDTO;

public class Transportadora extends Temporal{
	
	//DADOS A SEREM APRESENTADOS
	public static int QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO = 0;
	public static int TAMANHO_MINIMO_FILA_CARREGAMENTO = Integer.MAX_VALUE;
	public static int TAMANHO_MAXIMO_FILA_CARREGAMENTO = Integer.MIN_VALUE;
	public static double TAMANHO_MEDIO_FILA_CARREGAMENTO = 0;
	
	public static int QUANTIDADE_CAMINHOES_FILA_PESAGEM = 0;
	public static int TAMANHO_MINIMO_FILA_PESAGEM = Integer.MAX_VALUE;
	public static int TAMANHO_MAXIMO_FILA_PESAGEM = Integer.MIN_VALUE;
	public static double TAMANHO_MEDIO_FILA_PESAGEM = 0;
	
	public static int QUANTIDADE_CAMINHOS_ENTREGANDO = 0;
	
	public static double TAXA_MEDIA_OCUPACAO_CARREGADOR = 0;
	public static double TAXA_MEDIA_OCUPACAO_BALANCA = 0;
	
	public static int CONTADOR_VIAGENS = 0;
	
	public static int TEMPO_MINIMO_ENTIDADE_FILA_CARREGADOR = Integer.MAX_VALUE;
	public static int TEMPO_MAXIMO_ENTIDADE_FILA_CARREGADOR = Integer.MIN_VALUE;
	public static int TEMPO_MEDIO_ENTIDADE_FILA_CARREGADOR = 0;
	
	public static int TEMPO_MINIMO_ENTIDADE_FILA_PESAGEM = Integer.MAX_VALUE;
	public static int TEMPO_MAXIMO_ENTIDADE_FILA_PESAGEM = Integer.MIN_VALUE;
	public static int TEMPO_MEDIO_ENTIDADE_FILA_PESAGEM = 0;
	
	
	//AUXILIARES
	private static HashMap<Integer, Integer> inicioFilaCarregador = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> inicioFilaPesagem = new HashMap<Integer, Integer>();
	private static int tempoFilaCarregador = 0;
	private static int tempoFilaPesagem = 0;
	
	//TAMANHO DA FROTA
	private final int TAMANHO_FROTA = 7;
	
	//QUANTIDADE DE RECURSOS
	private final int QUANTIDADE_CARREGADOR = 2;
	private final int QUANTIDADE_BALANCA = 1;
	
	//TEMPO DE ESPERA
	private final int TEMPO_CARREGAMENTO = 5;
	private final int TEMPO_PESAGEM = 2;
	private final int TEMPO_ENTREGA = 10;
	
	//ATRIBUTOS
	public final TransportadoraPropagador propagador = new TransportadoraPropagador();
	public final LinhaDoTempo linhaDoTempo = new LinhaDoTempo(this);
	
	private ArrayList<Recurso> percurso;
	private ArrayList<Caminhao> frota;

	public Transportadora(){
		super();
		new TransportadoraControle(this);
		
		this.percurso = new ArrayList<Recurso>();
			Recurso carregador = new Recurso("Carregador", QUANTIDADE_CARREGADOR){
				public int tempoDeServico() {
					return TEMPO_CARREGAMENTO;
				}
			};
			Recurso balanca = new Recurso("Balanca", QUANTIDADE_BALANCA) {
				public int tempoDeServico() {
					return TEMPO_PESAGEM;
				}

			};
			Recurso entrega = new Recurso("Estrada") {
				public int tempoDeServico() {
					return TEMPO_ENTREGA;
				}
			};
		
			this.percurso.add(carregador);
			this.percurso.add(balanca);
			this.percurso.add(entrega);
			
		
		
		this.frota = new ArrayList<Caminhao>();
		for(int i = 0; i < TAMANHO_FROTA; i++){
			this.frota.add(new Caminhao(percurso));
		}
	}
	
	//FUNCOES
	
	public void calcularNumeroEntidadesNaFila(){
		for(int i = 0; i < momento.listaDeOcorrencia.size(); i++){
			Ocorrencia ocorrencia = momento.listaDeOcorrencia.get(i);
			if(ocorrencia.recurso.nome.equals("Carregador")){
				if(ocorrencia.evento.equals(Evento.Chegada)){
					for(int j = 0; j < momento.listaDeOcorrencia.size(); j++){
						if(i != j){
							Ocorrencia ocorrencia2 = momento.listaDeOcorrencia.get(j);
								if(ocorrencia.cliente.equals(ocorrencia2.cliente) &&  
										!ocorrencia2.evento.equals(Evento.InicioDoAtendimento)){
									QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO++;
								}
						}	
					}
				}
				else if(ocorrencia.evento.equals(Evento.InicioDoAtendimento)){
					for(int j = 0; j < momento.listaDeOcorrencia.size(); j++){
						if(ocorrencia.cliente.equals(momento.listaDeOcorrencia.get(j).cliente) &&  
								!momento.listaDeOcorrencia.get(j).evento.equals(Evento.Chegada)){
							QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO--;
						}
					}
				}
			}
			else if (ocorrencia.recurso.nome.equals("Balanca")){
				if(ocorrencia.evento.equals(Evento.Chegada)){
					for(int j = 0; j < momento.listaDeOcorrencia.size(); j++){
						if(i != j){
							Ocorrencia ocorrencia2 = momento.listaDeOcorrencia.get(j);
								if(ocorrencia.cliente.equals(ocorrencia2.cliente) &&  
										!ocorrencia2.evento.equals(Evento.InicioDoAtendimento)){
									QUANTIDADE_CAMINHOES_FILA_PESAGEM++;
								}
						}
					}
				}
				else if(ocorrencia.evento.equals(Evento.InicioDoAtendimento)){
					for(int j = 0; j < momento.listaDeOcorrencia.size(); j++){
						if(ocorrencia.cliente.equals(momento.listaDeOcorrencia.get(j).cliente) &&  
								!momento.listaDeOcorrencia.get(j).evento.equals(Evento.Chegada)){
							QUANTIDADE_CAMINHOES_FILA_PESAGEM--;
						}
					}
				}
			}
			else{
				if(ocorrencia.evento.equals(Evento.InicioDoAtendimento)){
					QUANTIDADE_CAMINHOS_ENTREGANDO++;
				}
				else if(ocorrencia.evento.equals(Evento.FimDoAtendimento)){
					QUANTIDADE_CAMINHOS_ENTREGANDO--;
				}
			}
		}
		if(QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO < TAMANHO_MINIMO_FILA_CARREGAMENTO){
			TAMANHO_MINIMO_FILA_CARREGAMENTO = QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO;
		}
		if(QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO > TAMANHO_MAXIMO_FILA_CARREGAMENTO){
			TAMANHO_MAXIMO_FILA_CARREGAMENTO = QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO;
		}
				
		if(QUANTIDADE_CAMINHOES_FILA_PESAGEM < TAMANHO_MINIMO_FILA_PESAGEM){
			TAMANHO_MINIMO_FILA_PESAGEM = QUANTIDADE_CAMINHOES_FILA_PESAGEM;
		}
		if(QUANTIDADE_CAMINHOES_FILA_PESAGEM > TAMANHO_MAXIMO_FILA_PESAGEM){
			TAMANHO_MAXIMO_FILA_PESAGEM = QUANTIDADE_CAMINHOES_FILA_PESAGEM;
		}
		
		int i = momento.referenciaTemporal;
		if(i == 1){
			TAMANHO_MEDIO_FILA_CARREGAMENTO = QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO;
			TAMANHO_MEDIO_FILA_PESAGEM = QUANTIDADE_CAMINHOES_FILA_PESAGEM;
		}
		else{
			TAMANHO_MEDIO_FILA_CARREGAMENTO = ((TAMANHO_MEDIO_FILA_CARREGAMENTO * (i-1)) + QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO) / i;
			TAMANHO_MEDIO_FILA_PESAGEM = ((TAMANHO_MEDIO_FILA_PESAGEM * (i-1)) + QUANTIDADE_CAMINHOES_FILA_PESAGEM) / i;
		}
	}
	
	public void calcularTaxaMediaOcupacaoRecursos(){
		int carregadorOcupado = 0;
		int balancaOcupado = 0;
		for(Recurso recurso : percurso){
			if(!(recurso.capacidade == 0 || recurso.listaDeServicosEmAndamento.size() < recurso.capacidade)){
				if(recurso.nome.equals("Carregador")){
					carregadorOcupado = 1;
				}
				else if(recurso.nome.equals("Balanca")){
					balancaOcupado = 1;
				}
			}
		}
		
		TAXA_MEDIA_OCUPACAO_CARREGADOR = (TAXA_MEDIA_OCUPACAO_CARREGADOR + carregadorOcupado) / momento.referenciaTemporal;
		TAXA_MEDIA_OCUPACAO_BALANCA = (TAXA_MEDIA_OCUPACAO_BALANCA + balancaOcupado) / momento.referenciaTemporal;
	}
	
	public void calcularTempoEntidadeFila(){
		for(int i = 0; i < momento.listaDeOcorrencia.size(); i++){
			Ocorrencia ocorrencia = momento.listaDeOcorrencia.get(i);
			if(ocorrencia.recurso.nome.equals("Carregador")){
				if(ocorrencia.evento.equals(Evento.Chegada)){
					for(int j = 0; j < momento.listaDeOcorrencia.size(); j++){
						if(i != j){
							Ocorrencia ocorrencia2 = momento.listaDeOcorrencia.get(j);
								if(ocorrencia.cliente.equals(ocorrencia2.cliente) &&  
										!ocorrencia2.evento.equals(Evento.InicioDoAtendimento)){
									inicioFilaCarregador.put(ocorrencia.cliente.id, momento.referenciaTemporal);
									System.out.println("ADICIONOU O ELEMENTO: " + ocorrencia.cliente.id);
								}	
						}
					}
				}
				else if(ocorrencia.evento.equals(Evento.InicioDoAtendimento)){
					if(inicioFilaCarregador.get(ocorrencia.cliente.id) != null){
						int tempoInicioFila = inicioFilaCarregador.get(ocorrencia.cliente.id);
						int tempoTotalFila = momento.referenciaTemporal - tempoInicioFila;
						if(tempoTotalFila > TEMPO_MAXIMO_ENTIDADE_FILA_CARREGADOR){
							TEMPO_MAXIMO_ENTIDADE_FILA_CARREGADOR = tempoTotalFila;
						}
						if(tempoTotalFila < TEMPO_MINIMO_ENTIDADE_FILA_CARREGADOR){
							TEMPO_MINIMO_ENTIDADE_FILA_CARREGADOR = tempoTotalFila;
						}
						tempoFilaCarregador += tempoTotalFila;
						inicioFilaCarregador.remove(ocorrencia.cliente.id);
						System.out.println("REMOVEU O ELEMENTO: " + ocorrencia.cliente.id);
					}
				}
			}
			else if (ocorrencia.recurso.nome.equals("Balanca")){
				if(ocorrencia.evento.equals(Evento.Chegada)){
					for(int j = 0; j < momento.listaDeOcorrencia.size(); j++){
						if(i != j){
							Ocorrencia ocorrencia2 = momento.listaDeOcorrencia.get(j);
								if(ocorrencia.cliente.equals(ocorrencia2.cliente) &&  
										!ocorrencia2.evento.equals(Evento.InicioDoAtendimento)){
									inicioFilaPesagem.put(ocorrencia.cliente.id, momento.referenciaTemporal);
								}
							}
					}
				}
				else if(ocorrencia.evento.equals(Evento.InicioDoAtendimento)){
					if(inicioFilaPesagem.get(ocorrencia.cliente.id) != null){
						int tempoInicioFila = inicioFilaPesagem.get(ocorrencia.cliente.id);
						int tempoTotalFila = momento.referenciaTemporal - tempoInicioFila;
						if(tempoTotalFila > TEMPO_MAXIMO_ENTIDADE_FILA_PESAGEM){
							TEMPO_MAXIMO_ENTIDADE_FILA_PESAGEM = tempoTotalFila;
						}
						if(tempoTotalFila < TEMPO_MINIMO_ENTIDADE_FILA_PESAGEM){
							TEMPO_MINIMO_ENTIDADE_FILA_PESAGEM = tempoTotalFila;
						}
						tempoFilaPesagem += tempoTotalFila;
						inicioFilaPesagem.remove(ocorrencia.cliente.id);
					}
				}
			}
		}
		
		int i = momento.referenciaTemporal;
		if(i == 0 || i == 1){
			TEMPO_MEDIO_ENTIDADE_FILA_CARREGADOR += tempoFilaCarregador;
			TEMPO_MEDIO_ENTIDADE_FILA_PESAGEM += tempoFilaPesagem;
		}
		else{
			TEMPO_MEDIO_ENTIDADE_FILA_CARREGADOR = ((TEMPO_MEDIO_ENTIDADE_FILA_CARREGADOR * (i-1)) + tempoFilaCarregador) / i;
			TEMPO_MEDIO_ENTIDADE_FILA_PESAGEM = ((TEMPO_MEDIO_ENTIDADE_FILA_PESAGEM * (i-1)) + tempoFilaPesagem) / i;
		}
	}

	//ABSTRACT
	
	public void prosseguir(){
		for(Recurso recurso : percurso){
			recurso.captar(momento);
		}
		for(Caminhao caminhao : frota){
			caminhao.captar(momento);
		}
		
		calcularNumeroEntidadesNaFila();
		calcularTaxaMediaOcupacaoRecursos();
		calcularTempoEntidadeFila();
		
		System.out.println("Momento: " + momento.referenciaTemporal);
		System.out.println("Tempo máximo na fila carregador: " + TEMPO_MAXIMO_ENTIDADE_FILA_CARREGADOR);
		System.out.println("Tempo minimo na fila carregador: " + TEMPO_MINIMO_ENTIDADE_FILA_CARREGADOR);
		System.out.println("Tempo medio na fila carregador: " + TEMPO_MEDIO_ENTIDADE_FILA_CARREGADOR);
		
		System.out.println("Tempo máximo na fila pesagem: " + TEMPO_MAXIMO_ENTIDADE_FILA_PESAGEM);
		System.out.println("Tempo minimo na fila pesagem: " + TEMPO_MINIMO_ENTIDADE_FILA_PESAGEM);
		System.out.println("Tempo medio na fila pesagem: " + TEMPO_MEDIO_ENTIDADE_FILA_PESAGEM);
		
		propagador.propagar();
	}
	
	//SUBCLASSES
	
	public class TransportadoraPropagador extends Propagador<TransportadoraDTO>{
		
		public void propagar(){
			propagar(
				new TransportadoraDTO(
						momento.referenciaTemporal,
						linhaDoTempo.toTable()
				)
			);
		}
	}
	
}

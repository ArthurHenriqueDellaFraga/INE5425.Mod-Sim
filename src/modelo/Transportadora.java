package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import controle.TransportadoraControle;
import padrao_de_projeto.Propagador;
import primitivo.LinhaDoTempo;
import primitivo.Ocorrencia;
import primitivo.Ocorrencia.Evento;
import utils.Tuple;
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
	
	public static int TEMPO_MINIMO_CICLO = Integer.MAX_VALUE;
	public static int TEMPO_MAXIMO_CICLO = Integer.MIN_VALUE;
	public static int TEMPO_MEDIO_CICLO = 0;
	
	//AUXILIARES
	private static HashMap<Integer, Integer> inicioFilaCarregador = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> inicioFilaPesagem = new HashMap<Integer, Integer>();
	private static int tempoFilaCarregador = 0;
	private static int tempoFilaPesagem = 0;
	
	private static HashMap<Integer, Integer> inicioFilaCarregadorCiclo = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> inicioFilaPesagemCiclo = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> tempoCiclo = new HashMap<Integer, Integer>();
	private static HashMap<Integer, ArrayList<Integer>> listaDeCiclos = new HashMap<Integer, ArrayList<Integer>>();
 	
	//TAMANHO DA FROTA
	public static int NUMERO_DE_CAMINHOES = 3;
	
	//QUANTIDADE DE RECURSOS
	public static int QUANTIDADE_CARREGADOR = 2;
	public static int QUANTIDADE_BALANCA = 1;
	
	//TEMPO DE ESPERA
	public static int TEMPO_CARGA = 5;
	public static int TEMPO_PESAGEM = 2;
	public static int TEMPO_TRANSPORTE = 10;
	
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
					return TEMPO_CARGA;
				}
			};
			Recurso balanca = new Recurso("Balanca", QUANTIDADE_BALANCA) {
				public int tempoDeServico() {
					return TEMPO_PESAGEM;
				}

			};
			Recurso entrega = new Recurso("Estrada") {
				public int tempoDeServico() {
					return TEMPO_TRANSPORTE;
				}
			};
		
			this.percurso.add(carregador);
			this.percurso.add(balanca);
			this.percurso.add(entrega);
			
		
		
		this.frota = new ArrayList<Caminhao>();
		for(int i = 0; i < NUMERO_DE_CAMINHOES; i++){
			this.frota.add(new Caminhao(percurso));
		}
	}
	
	//FUNCOES
	
	public void calcularNumeroEntidadesNaFila(){
		for(int i = 0; i < momento.listaDeOcorrencia.size(); i++){
			Ocorrencia ocorrencia = momento.listaDeOcorrencia.get(i);
			if(ocorrencia.recurso.nome.equals("Carregador")){
				if(insereFilaCarregamento(ocorrencia, i)){
					QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO++;
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
				if(insereFilaPesagem(ocorrencia)){
									QUANTIDADE_CAMINHOES_FILA_PESAGEM++;
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
				if(insereFilaCarregamento(ocorrencia, i)){
					inicioFilaCarregador.put(ocorrencia.cliente.id, momento.referenciaTemporal);
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
					}
				}
			}
			else if (ocorrencia.recurso.nome.equals("Balanca")){
				if(insereFilaPesagem(ocorrencia)){
					inicioFilaPesagem.put(ocorrencia.cliente.id, momento.referenciaTemporal);
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
	
	public void calcularTempoCiclo(){
		for(int i = 0; i < momento.listaDeOcorrencia.size(); i++){
			Ocorrencia ocorrencia = momento.listaDeOcorrencia.get(i);
			if(ocorrencia.recurso.nome.equals("Carregador")){
				if(insereFilaCarregamento(ocorrencia, i)){
					inicioFilaCarregadorCiclo.put(ocorrencia.cliente.id, momento.referenciaTemporal);
				}
				else if(ocorrencia.evento.equals(Evento.FimDoAtendimento)){
					int tempoTotalFila = 0;
					if(inicioFilaCarregadorCiclo.get(ocorrencia.cliente.id) != null){
						int tempoInicioFila = inicioFilaCarregadorCiclo.get(ocorrencia.cliente.id);
						tempoTotalFila += momento.referenciaTemporal - tempoInicioFila;
						inicioFilaCarregadorCiclo.remove(ocorrencia.cliente.id);
					}
					else{
						tempoTotalFila = TEMPO_CARGA;
					}
					tempoCiclo.put(ocorrencia.cliente.id, tempoTotalFila);
				}
			}
			else if (ocorrencia.recurso.nome.equals("Balanca")){
				if(insereFilaPesagem(ocorrencia)){
					inicioFilaPesagemCiclo.put(ocorrencia.cliente.id, momento.referenciaTemporal);
				}
				else if(ocorrencia.evento.equals(Evento.FimDoAtendimento)){
					int tempoTotalFila = tempoCiclo.get(ocorrencia.cliente.id);
					if(inicioFilaPesagemCiclo.get(ocorrencia.cliente.id) != null){
						int tempoInicioFila = inicioFilaPesagemCiclo.get(ocorrencia.cliente.id);
						tempoTotalFila += momento.referenciaTemporal - tempoInicioFila;
						inicioFilaPesagemCiclo.remove(ocorrencia.cliente.id);
					}
					tempoCiclo.put(ocorrencia.cliente.id, tempoTotalFila);
				}
			}
			else if(ocorrencia.recurso.nome.equals("Estrada")){
				if(ocorrencia.evento.equals(Evento.FimDoAtendimento)){
					int tempoTotalFila = tempoCiclo.get(ocorrencia.cliente.id);
					tempoCiclo.put(ocorrencia.cliente.id, tempoTotalFila + TEMPO_TRANSPORTE);
					ArrayList<Integer> ciclos = listaDeCiclos.get(ocorrencia.cliente.id);
					if(ciclos == null){
						ciclos = new ArrayList<Integer>();
					}
					ciclos.add(tempoTotalFila + TEMPO_TRANSPORTE);
					listaDeCiclos.put(ocorrencia.cliente.id, ciclos);
				}
			}
		}
		
		calcularTemposCiclo();
	}
	
	private void calcularTemposCiclo(){
		Set<Integer> keySet = listaDeCiclos.keySet();
		int somaTempo = 0;
		int i = 0;
		for(Integer key : keySet){
			for(Integer ciclo : listaDeCiclos.get(key)){
				if(ciclo > TEMPO_MAXIMO_CICLO){
					TEMPO_MAXIMO_CICLO = ciclo;
				}
				if(ciclo < TEMPO_MINIMO_CICLO){
					TEMPO_MINIMO_CICLO = ciclo;
				}
				somaTempo += ciclo;
				i++;
			}
		}
		if(i != 0){
			TEMPO_MEDIO_CICLO = somaTempo / i;
		}
	}
	
	private boolean insereFilaCarregamento(Ocorrencia ocorrencia, int i){
		if(ocorrencia.evento.equals(Evento.Chegada)){
			int numOcorrencias = 0;
			for(int j = 0; j < momento.listaDeOcorrencia.size(); j++){
					Ocorrencia ocorrencia2 = momento.listaDeOcorrencia.get(j);
					if(ocorrencia.cliente.equals(ocorrencia2.cliente)){
						numOcorrencias++;
					}
					if(i != j &&
						ocorrencia.cliente.equals(ocorrencia2.cliente) &&  
							ocorrencia2.recurso.nome.equals("Carregador") &&
								!ocorrencia2.evento.equals(Evento.InicioDoAtendimento)){
						return true;
					}
				}
			if(numOcorrencias == 1){
				return true;
			}
		}
		return false;
	}
	
	private boolean insereFilaPesagem(Ocorrencia ocorrencia){
		if(ocorrencia.evento.equals(Evento.Chegada)){
			for(int j = 0; j < momento.listaDeOcorrencia.size(); j++){
				Ocorrencia ocorrencia2 = momento.listaDeOcorrencia.get(j);
					if(ocorrencia.cliente.equals(ocorrencia2.cliente) &&  
						ocorrencia2.recurso.nome.equals("Balanca") &&
						!ocorrencia2.evento.equals(Evento.InicioDoAtendimento)){
							return true;
				}
			}
		}
		return false;
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
		calcularTempoCiclo();
		
		System.out.println("Momento: " + momento.referenciaTemporal);
		System.out.println();
		
		propagador.propagar();
	}
	
	//SUBCLASSES
	
	public class TransportadoraPropagador extends Propagador<TransportadoraDTO>{
		
		public void propagar(){
			TransportadoraDTO dto = new TransportadoraDTO();
			
			dto.referenciaTemporal = momento.referenciaTemporal;
			dto.conteudoLinhaDoTempo = linhaDoTempo.toTable();
			
			dto.QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO = QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO;
			dto.TAMANHO_MINIMO_FILA_CARREGAMENTO = TAMANHO_MINIMO_FILA_CARREGAMENTO;
			dto.TAMANHO_MAXIMO_FILA_CARREGAMENTO = TAMANHO_MAXIMO_FILA_CARREGAMENTO;
			dto.TAMANHO_MEDIO_FILA_CARREGAMENTO = TAMANHO_MEDIO_FILA_CARREGAMENTO;
			
			dto.QUANTIDADE_CAMINHOES_FILA_PESAGEM = QUANTIDADE_CAMINHOES_FILA_PESAGEM;
			dto.TAMANHO_MINIMO_FILA_PESAGEM = TAMANHO_MINIMO_FILA_PESAGEM;
			dto.TAMANHO_MAXIMO_FILA_PESAGEM = TAMANHO_MAXIMO_FILA_PESAGEM;
			dto.TAMANHO_MEDIO_FILA_PESAGEM = TAMANHO_MEDIO_FILA_PESAGEM;
			
			dto.QUANTIDADE_CAMINHOS_ENTREGANDO = QUANTIDADE_CAMINHOS_ENTREGANDO;
			
			dto.TAXA_MEDIA_OCUPACAO_CARREGADOR = TAXA_MEDIA_OCUPACAO_CARREGADOR;
			dto.TAXA_MEDIA_OCUPACAO_BALANCA = TAXA_MEDIA_OCUPACAO_BALANCA;
			
			dto.CONTADOR_VIAGENS = CONTADOR_VIAGENS;
			
			dto.TEMPO_MINIMO_ENTIDADE_FILA_CARREGADOR = TEMPO_MINIMO_ENTIDADE_FILA_CARREGADOR;
			dto.TEMPO_MAXIMO_ENTIDADE_FILA_CARREGADOR = TEMPO_MAXIMO_ENTIDADE_FILA_CARREGADOR;
			dto.TEMPO_MEDIO_ENTIDADE_FILA_CARREGADOR = TEMPO_MEDIO_ENTIDADE_FILA_CARREGADOR;
			
			dto.TEMPO_MINIMO_ENTIDADE_FILA_PESAGEM = TEMPO_MINIMO_ENTIDADE_FILA_PESAGEM;
			dto.TEMPO_MAXIMO_ENTIDADE_FILA_PESAGEM = TEMPO_MAXIMO_ENTIDADE_FILA_PESAGEM;
			dto.TEMPO_MEDIO_ENTIDADE_FILA_PESAGEM = TEMPO_MEDIO_ENTIDADE_FILA_PESAGEM;
			
			propagar(dto);
		}
	}
	
}

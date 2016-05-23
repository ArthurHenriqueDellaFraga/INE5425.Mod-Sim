package modelo;

import java.util.ArrayList;

public class Transportadora extends Simulacao{
	
	//TAMANHO DA FROTA
	private final int TAMANHO_FROTA = 2;
	
	//QUANTIDADE DE RECURSOS
	private final int QUANTIDADE_CARREGADOR = 2;
	private final int QUANTIDADE_BALANCA = 1;
	
	//TEMPO DE ESPERA
	private final int TEMPO_CARREGAMENTO = 5;
	private final int TEMPO_PESAGEM = 2;
	private final int TEMPO_ENTREGA = 10;
	
	private ArrayList<Recurso> percurso;
	private ArrayList<Caminhao> frota;

	public Transportadora(){
		super();
		
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
		
		for(Caminhao caminhao : frota){
			System.out.println(linhaDoTempo.toString(caminhao) + "\n\n");
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
	}
	
	
}

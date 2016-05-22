package Modelo;

import java.util.ArrayList;

public class Transportadora extends Simulacao{
	private ArrayList<Recurso> percurso;
	private ArrayList<Caminhao> frota;

	public Transportadora(){
		super();
		
		this.percurso = new ArrayList<Recurso>();
			Recurso carregamento = new Recurso("Carregador", 2){
				public int tempoDeServico() {
					return 5;
				}
			};
			Recurso pesagem = new Recurso("Balança", 1) {
				public int tempoDeServico() {
					return 3;
				}

			};
			Recurso entrega = new Recurso("Estrada") {
				public int tempoDeServico() {
					return 10;
				}
			};
		
			this.percurso.add(carregamento);
			this.percurso.add(pesagem);
			this.percurso.add(entrega);
		
		
		this.frota = new ArrayList<Caminhao>();
			Caminhao caminhao1 = new Caminhao(percurso);
			Caminhao caminhao2 = new Caminhao(percurso);
			
			this.frota.add(caminhao1);
			this.frota.add(caminhao2);


	}
	
	//FUNCOES
	
	public void simular() {
		for(int i = 0; i < 10; i++){
			linhaDoTempo.prosseguir();
		}
		
		for(Recurso recurso : percurso){
			System.out.println(linhaDoTempo.toString(recurso) + "\n\n");
		}
	}

	//ABSTRACT
	
	public void prosseguir(){
		for(Caminhao caminhao : frota){
			caminhao.captar(momento);
		}
		
		for(Recurso recurso : percurso){
			recurso.captar(momento);
		}		
	}
	
	
}

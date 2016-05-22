package Modelo;

import java.util.ArrayList;

public class Transportadora extends Simulacao{
	private ArrayList<Recurso> percurso;
	private ArrayList<Caminhao> frota;

	public Transportadora(){
		super();
		
		this.percurso = new ArrayList<Recurso>(){{
			Recurso carregamento = new Recurso(2){
				public int tempoDeServico() {
					return 5;
				}
			};
			
			Recurso pesagem = new Recurso(1) {
				public int tempoDeServico() {
					return 3;
				}

			};
			
			Recurso entrega = new Recurso() {
				public int tempoDeServico() {
					return 10;
				}
			};
			
			linhaDoTempo.adicionarCaptador(carregamento);
			linhaDoTempo.adicionarCaptador(pesagem);
			linhaDoTempo.adicionarCaptador(entrega);
			
			add(carregamento);
			add(pesagem);
			add(entrega);
		}};
		
		this.frota = new ArrayList<Caminhao>(){{
			Caminhao caminhao1 = new Caminhao(percurso);
			Caminhao caminhao2 = new Caminhao(percurso);
			
			linhaDoTempo.adicionarCaptador(caminhao1);
			linhaDoTempo.adicionarCaptador(caminhao2);
			
			add(caminhao1);
			add(caminhao2);
		}};
	}
	
	//FUNCOES
	
	public void simular() {
		for(int i = 0; i < 10; i++){
			linhaDoTempo.prosseguir();
		}
		
		for(Recurso recurso : percurso){
			System.out.println(recurso.toString() + "\n\n");
		}
	}
	
	
}

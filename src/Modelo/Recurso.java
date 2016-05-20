package Modelo;

import java.util.ArrayList;

import Primitivo.RegistroDeUtilizacao;

public abstract class Recurso {
	private int capacidade;
	
	public Recurso(int capacidade){
		this.capacidade = capacidade;
		
	}
	
	//FUNCOES
	
	public void servir(Caminhao caminhao){
	}
	
	public abstract int tempoDeServico();
	
	//SUBCLASSES
	
	public class ExtratoDeFuncionamento {
		private ArrayList<RegistroDeUtilizacao> listaDeRegistro;
		
		public ExtratoDeFuncionamento(){
			listaDeRegistro = new ArrayList<RegistroDeUtilizacao>();
		}
		
		public void registrar(RegistroDeUtilizacao registro){
			
			for(int i = 0; i < listaDeRegistro.size(); i++){
				if(listaDeRegistro.get(i).momento > registro.momento){
					listaDeRegistro.add(i, registro);
				}
			}
		}
		
	}
}

package Primitivo;

import java.util.ArrayList;

import PadraoDeProjeto.Propagador;

public class LinhaDoTempo extends Propagador<Momento>{
	private static LinhaDoTempo INSTANCIA;
	private ArrayList<Momento> linhaDoTempo = new ArrayList<Momento>();
		
	private LinhaDoTempo(){
		super();
	}
	
	public static LinhaDoTempo invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new LinhaDoTempo();
		}
		
		return INSTANCIA;
	}
	
	
	//FUNCOES
	
	public void prosseguir(){
		Momento presente = new Momento();
		
		linhaDoTempo.add(presente);
		try {
			(new Thread()).wait(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		propagar(presente);
	}
	
	public void registrar(Ocorrencia registro) {
		linhaDoTempo.get(linhaDoTempo.size()-1).add(registro);
	}
	
	//ABSTRACT
	
	public String toString(int recursoId){
		String string= "";
		
		for(Momento momento : linhaDoTempo){
			string += momento.toString(recursoId);
		}
		
		return string;
	}
}

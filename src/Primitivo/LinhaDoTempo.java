package Primitivo;

import java.util.ArrayList;

import Modelo.Recurso;
import Modelo.Simulacao;
import PadraoDeProjeto.Captador;
import PadraoDeProjeto.Propagador;

public class LinhaDoTempo extends Propagador<Momento>{
	private ArrayList<Momento> linhaDoTempo = new ArrayList<Momento>();
		
	public LinhaDoTempo(Simulacao simulacao){
		super();
		inscrever((Captador<Momento>) simulacao);
	}

	//FUNCOES
	
	public void prosseguir(){
		Momento presente = new Momento();
		
		linhaDoTempo.add(presente);
		propagar(presente);
	}
	
	public void registrar(Ocorrencia registro) {
		if(linhaDoTempo.size() > 0){
			linhaDoTempo.get(linhaDoTempo.size()-1).add(registro);
		}
	}
	
	//ABSTRACT
	
	public String toString(Recurso recurso){
		String string= "";
		
		for(Momento momento : linhaDoTempo){
			string += momento.toString(recurso);
		}
		
		return string;
	}
}

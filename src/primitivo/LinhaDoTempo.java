package primitivo;

import java.util.ArrayList;

import modelo.Cliente;
import modelo.Recurso;
import modelo.Simulacao;
import padrao_de_projeto.Captador;
import padrao_de_projeto.Propagador;

public class LinhaDoTempo extends Propagador<Momento>{
	public static final Momento inicio = new Momento();
	private ArrayList<Momento> linhaDoTempo = new ArrayList<Momento>();
		
	public LinhaDoTempo(Simulacao simulacao){
		super();
		inscrever((Captador<Momento>) simulacao);
		linhaDoTempo.add(inicio);
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
	
	public ArrayList<Momento> getLinhaDoTempo(){
		return this.linhaDoTempo;
	}
	
	//ABSTRACT
	
	public String toString(Recurso recurso){
		String string= "";
		
		for(Momento momento : linhaDoTempo){
			string += momento.toString(recurso);
		}
		
		return string;
	}
	
	public String toString(Cliente cliente){
		String string= "";
		
		for(Momento momento : linhaDoTempo){
			string += momento.toString(cliente);
		}
		
		return string;
	}
}

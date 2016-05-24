package primitivo;

import java.util.ArrayList;

import modelo.Cliente;
import modelo.Recurso;

public class Momento{
	public static int numInstancias = -1;
	public int referenciaTemporal = numInstancias++;
	
	public final ArrayList<Ocorrencia> listaDeOcorrencia = new ArrayList<Ocorrencia>();
	
	public Momento(){ }
	
	//FUNCOES
	
	public void add(Ocorrencia o){
		listaDeOcorrencia.add(o);
	}
	
	//ABSTRACT
	
	public String toString(Recurso recurso){
		String string = referenciaTemporal + "\n";
		
		for(Ocorrencia ocorrencia : listaDeOcorrencia){
			if(ocorrencia.recurso.nome == recurso.nome){
				string += "    : " + ocorrencia.toString() + "\n";
			}
		}
		
		return string;
	}
	
	public String toString(Cliente cliente){
		String string = referenciaTemporal + "\n";
		
		for(Ocorrencia ocorrencia : listaDeOcorrencia){
			if(ocorrencia.cliente == cliente){
				string += "    : " + ocorrencia.toString() + "\n";
			}
		}
		
		return string;
	}
	
	public String toString(){
		String string = referenciaTemporal + "\n";
		
		for(Ocorrencia ocorrencia : listaDeOcorrencia){
			string += "    : " + ocorrencia.toString() + "\n";
		}
		
		return string;
	}
}

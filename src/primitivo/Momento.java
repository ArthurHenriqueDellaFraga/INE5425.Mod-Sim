package primitivo;

import java.util.ArrayList;

import modelo.Cliente;
import modelo.Recurso;

public class Momento{
	public static int numInstancias = 0;
	public int referenciaTemporal = numInstancias++;
	
	private ArrayList<Ocorrencia> ListaDeOcorrencia = new ArrayList<Ocorrencia>();
	
	public Momento(){ }
	
	//FUNCOES
	
	public void add(Ocorrencia o){
		ListaDeOcorrencia.add(o);
	}
	
	public String toString(Recurso recurso){
		String string = referenciaTemporal + "\n";
		
		for(Ocorrencia ocorrencia : ListaDeOcorrencia){
			if(ocorrencia.recurso.nome == recurso.nome){
				string += "    : " + ocorrencia.toString() + "\n";
			}
		}
		
		return string;
	}
	
	public String toString(Cliente cliente){
		String string = referenciaTemporal + "\n";
		
		for(Ocorrencia ocorrencia : ListaDeOcorrencia){
			if(ocorrencia.cliente == cliente){
				string += "    : " + ocorrencia.toString() + "\n";
			}
		}
		
		return string;
	}
	
	public String toString(){
		String string = referenciaTemporal + "\n";
		
		for(Ocorrencia ocorrencia : ListaDeOcorrencia){
			string += "    : " + ocorrencia.toString() + "\n";
		}
		
		return string;
	}
}

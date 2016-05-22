package Primitivo;

import java.util.ArrayList;

import Modelo.Recurso;

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
}

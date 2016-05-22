package Primitivo;

import java.util.ArrayList;

public class Momento{
	public static int numInstancias = -1;
	public int referenciaTemporal = numInstancias++;
	
	private ArrayList<Ocorrencia> ListaDeOcorrencia = new ArrayList<Ocorrencia>();
	
	public Momento(){ }
	
	//FUNCOES
	
	public void add(Ocorrencia o){
		ListaDeOcorrencia.add(o);
	}
	
	public String toString(int recursoId){
		String string = "";
		
		for(Ocorrencia ocorrencia : ListaDeOcorrencia){
			if(ocorrencia.recursoId == recursoId){
				string += referenciaTemporal + " : " + ocorrencia.toString() + "\n";
			}
		}
		
		return string;
	}
}

package Controle;

import Excecao.OperacaoCanceladaException;
import Modelo.Transportadora;
import Visao.InterfaceDaAplicacao;

public class GerenteDaAplicacao{
	private static GerenteDaAplicacao INSTANCIA;
	private final InterfaceDaAplicacao INTERFACE_DA_APLICACAO = InterfaceDaAplicacao.invocarInstancia();
	
	private GerenteDaAplicacao(){
		
	}
	
	public static GerenteDaAplicacao invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new GerenteDaAplicacao();
		}
		
		return INSTANCIA;
	}
	
	//MAIN
	
	public static void main(String[] args){
		GerenteDaAplicacao.invocarInstancia().iniciar();
	}
	
	//FUNCOES

	public void iniciar(){
		Transportadora transportadora =  new Transportadora();;
		
		try{
			INTERFACE_DA_APLICACAO.iniciar();
		}
		catch(OperacaoCanceladaException e){
			System.exit(0);
		}
		
		transportadora.simular();
	}
}

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
	
	//FUNCOES

	public void iniciar(){
		Transportadora transportadora;
		
		try{
			INTERFACE_DA_APLICACAO.iniciar();
			switch(1){							
				case 0:		transportadora = new Transportadora(); break;
				
				default:	transportadora = new Transportadora(
							);
			}
		}
		catch(OperacaoCanceladaException e){
			System.exit(0);
		}
	}
}

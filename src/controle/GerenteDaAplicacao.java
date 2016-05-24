package controle;

import excecao.OperacaoCanceladaException;
import modelo.Transportadora;
import primitivo.Momento;
import visao.InterfaceDaAplicacao;

public class GerenteDaAplicacao{
	private static GerenteDaAplicacao INSTANCIA = new GerenteDaAplicacao();
	private final InterfaceDaAplicacao INTERFACE_DA_APLICACAO = InterfaceDaAplicacao.invocarInstancia();
	
	private GerenteDaAplicacao(){
		
	}
	
	public static GerenteDaAplicacao invocarInstancia(){
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

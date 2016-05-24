package controle;

import excecao.OperacaoCanceladaException;
import modelo.Transportadora;
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
		try{
			switch (INTERFACE_DA_APLICACAO.iniciar()){
				case 1:		INTERFACE_DA_APLICACAO.inicializacaoCustomizada(); break;
				
				default:
			}
		}
		catch(OperacaoCanceladaException e){
			System.exit(0);
		}
		
		Transportadora transportadora =  new Transportadora();
	}
}

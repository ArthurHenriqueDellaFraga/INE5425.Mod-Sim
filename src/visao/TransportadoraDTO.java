package visao;

public class TransportadoraDTO{
	public final String[] cabecalhoLinhaDoTempo = new String[]{"Momento", "Caminhao", "Evento", "Recurso"};
	public final String[][] conteudoLinhaDoTempo;
	
	public TransportadoraDTO(){
		this.conteudoLinhaDoTempo = new String[][]{{"-", "-", "-", "-"}};
	}
	
	public TransportadoraDTO(String[][] conteudoLinhaDoTempo){
		this.conteudoLinhaDoTempo = conteudoLinhaDoTempo;
	}
}

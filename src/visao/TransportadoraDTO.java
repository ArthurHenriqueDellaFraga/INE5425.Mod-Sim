package visao;

public class TransportadoraDTO{
	public final int referenciaTemporal;
	
	public final String[] cabecalhoLinhaDoTempo = new String[]{"Momento", "Caminhao", "Evento", "Recurso"};
	public final String[][] conteudoLinhaDoTempo;
	
	public TransportadoraDTO(){
		referenciaTemporal = 0;
		this.conteudoLinhaDoTempo = new String[][]{{"-", "-", "-", "-"}};
	}
	
	public TransportadoraDTO(int referenciaTemporal, String[][] conteudoLinhaDoTempo){
		this.referenciaTemporal = referenciaTemporal;
		this.conteudoLinhaDoTempo = conteudoLinhaDoTempo;
	}
}

package visao;

public class TransportadoraDTO{
	public int referenciaTemporal = 0;
	
	public final String[] cabecalhoLinhaDoTempo = new String[]{"Momento", "Caminhao", "Evento", "Recurso"};
	public String[][] conteudoLinhaDoTempo = new String[][]{{"-", "-", "-", "-"}};
	
	public int QUANTIDADE_CAMINHOES_FILA_CARREGAMENTO = 0;
	public int TAMANHO_MINIMO_FILA_CARREGAMENTO = 0;
	public int TAMANHO_MAXIMO_FILA_CARREGAMENTO = 0;
	public double TAMANHO_MEDIO_FILA_CARREGAMENTO = 0;
	
	public int QUANTIDADE_CAMINHOES_FILA_PESAGEM = 0;
	public int TAMANHO_MINIMO_FILA_PESAGEM = 0;
	public int TAMANHO_MAXIMO_FILA_PESAGEM = 0;
	public double TAMANHO_MEDIO_FILA_PESAGEM = 0;
	
	public int QUANTIDADE_CAMINHOS_ENTREGANDO = 0;
	
	public double TAXA_MEDIA_OCUPACAO_CARREGADOR = 0;
	public double TAXA_MEDIA_OCUPACAO_BALANCA = 0;
	
	public int CONTADOR_VIAGENS = 0;
	
	public int TEMPO_MINIMO_ENTIDADE_FILA_CARREGADOR = 0;
	public int TEMPO_MAXIMO_ENTIDADE_FILA_CARREGADOR = 0;
	public int TEMPO_MEDIO_ENTIDADE_FILA_CARREGADOR = 0;
	
	public int TEMPO_MINIMO_ENTIDADE_FILA_PESAGEM = 0;
	public int TEMPO_MAXIMO_ENTIDADE_FILA_PESAGEM = 0;
	public int TEMPO_MEDIO_ENTIDADE_FILA_PESAGEM = 0;
	
	
	public TransportadoraDTO(){ }

}

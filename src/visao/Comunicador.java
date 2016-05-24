package visao;

import javax.swing.JOptionPane;

import excecao.OperacaoCanceladaException;

public abstract class Comunicador {
	
	//FUNCOES
	
	private void apresentarMensagem(String mensagem, String titulo, int tipo){
		JOptionPane.showMessageDialog(null, mensagem, titulo, tipo);
	}
	
	public void apresentarMensagemDeErro(String mensagem, String titulo){
		apresentarMensagem(mensagem, titulo, JOptionPane.ERROR_MESSAGE);
	}
		
	public void apresentarMensagemDeInformacao(String mensagem, String titulo){
		apresentarMensagem(mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
	}
		
	public void apresentarMensagemDeAlerta(String mensagem, String titulo){
		apresentarMensagem(mensagem, titulo, JOptionPane.WARNING_MESSAGE);
	}
		
	public int apresentarDialogoOpitativo(String mensagem, String titulo, Object[] opcoes){
		int retorno = JOptionPane.showOptionDialog(null, mensagem, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
		
		if(retorno == JOptionPane.CLOSED_OPTION || retorno == JOptionPane.CANCEL_OPTION){
			throw new OperacaoCanceladaException();
		}
		
		return retorno;
	}
	
	public String coletarTextoSimples(String mensagem, String titulo){
		return JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.QUESTION_MESSAGE);
	}
	
	public int coletarNumero(String mensagem, String titulo){
		int retorno = Integer.MIN_VALUE;
		String string = "";

		do{
			try{
				string = JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.QUESTION_MESSAGE);
				retorno = Integer.parseInt(string);
			}
			catch(NumberFormatException e){
				apresentarMensagemDeErro("\"" + string + "\" não é um número", titulo);
			}
		}while(retorno == Integer.MIN_VALUE);
		
		return retorno;
	}

	/*
	public int apresentarDialogoPersonalizado(Object[] listaDeComponentes, String titulo){
		int retorno = JOptionPane.showConfirmDialog(null, listaDeComponentes, titulo, JOptionPane.OK_CANCEL_OPTION , JOptionPane.QUESTION_MESSAGE);
		
		if(retorno == JOptionPane.CLOSED_OPTION || retorno == JOptionPane.CANCEL_OPTION){
			throw new OperacaoCanceladaException();
		}
		
		return retorno;
	}	
	
	public Object selecionarDaLista(String mensagem, String titulo, Object[] lista){
		return JOptionPane.showInputDialog(FRAME_FAMILIAR, mensagem, titulo, JOptionPane.QUESTION_MESSAGE, null, lista, null);
	}
	*/
}
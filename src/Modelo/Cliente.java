package Modelo;

import java.util.ArrayList;
import java.util.Iterator;

import PadraoDeProjeto.Captador;
import Primitivo.Momento;

public abstract class Cliente extends Captador<Momento>{
	public static int quantidade = 0;
	public final int id = quantidade++;
	
	protected ArrayList<Recurso> listaDeAtividades;
	protected Iterator<Recurso> atividadeCorrente;
	
	public Cliente(ArrayList<Recurso> listaDeAtividades){
		assert(listaDeAtividades.size() > 0);
		
		this.listaDeAtividades = listaDeAtividades;
		this.atividadeCorrente = listaDeAtividades.iterator();
		prosseguir();
	}

	public void prosseguir() {
		if(!atividadeCorrente.hasNext()){
			this.atividadeCorrente = listaDeAtividades.iterator();
		}
		atividadeCorrente.next().receber(this);
	}
}

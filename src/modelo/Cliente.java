package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import padrao_de_projeto.Captador;
import primitivo.Ocorrencia;

public abstract class Cliente extends Temporal{
	public static int quantidade = 0;
	public final int id = quantidade++;
	
	protected ArrayList<Recurso> listaDeAtividades;
	protected Iterator<Recurso> atividadeCorrente;
	
	public Cliente(ArrayList<Recurso> listaDeAtividades){
		assert(listaDeAtividades.size() > 0);
		
		this.listaDeAtividades = listaDeAtividades;
		this.atividadeCorrente = listaDeAtividades.iterator();
		atividadeCorrente.next().receber(Cliente.this, momento.referenciaTemporal);
	}

	public void prosseguir(){}
	
	public Captador<Ocorrencia> getAtencao(){
		
		return new Captador<Ocorrencia>() {
			
			public void captar(Ocorrencia ocorrencia) {
				switch (ocorrencia.evento) {
					case FimDoAtendimento:		
						if(!atividadeCorrente.hasNext()){
							Cliente.this.atividadeCorrente = listaDeAtividades.iterator();
						}	
						atividadeCorrente.next().receber(Cliente.this, momento.referenciaTemporal);	
						break;
		
					default: break;
				}
			}
		};
	}
}

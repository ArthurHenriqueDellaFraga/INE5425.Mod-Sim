package PadraoDeProjeto;

import java.util.HashSet;

public class Propagador<T> {
    private final HashSet<Captador<T>> ConjuntoDeCaopadores =  new HashSet<Captador<T>>();
 
    public Propagador(){
    	
    }
    
    //FUNCOES
    
    public void adicionarCaptador(Captador<T> captador) {
    	ConjuntoDeCaopadores.add(captador);
    }
 
    public void removerCaptador(Captador<T> captador) {
    	ConjuntoDeCaopadores.remove(captador);
    }
 
    public void propagar(T informacao) {
    	for(Captador<T> captador : ConjuntoDeCaopadores){
    		captador.captar(informacao);
        }
    }

}
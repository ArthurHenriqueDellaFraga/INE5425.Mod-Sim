package padrao_de_projeto;

import java.util.HashSet;

public class Propagador<T> {
    private final HashSet<Captador<T>> ConjuntoDeCaopadores =  new HashSet<Captador<T>>();
 
    public Propagador(){ }
    
    //FUNCOES
    
    public void inscrever(Captador<T> captador){
    	ConjuntoDeCaopadores.add(captador);
    }
 
    public void desinscrever(Captador<T> captador){
    	ConjuntoDeCaopadores.remove(captador);
    }
 
    public void propagar(T informacao) {
    	for(Captador<T> captador : ConjuntoDeCaopadores){
    		captador.captar(informacao);
        }
    }

}
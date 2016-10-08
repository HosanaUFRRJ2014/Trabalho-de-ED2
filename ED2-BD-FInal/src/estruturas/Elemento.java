package estruturas;
import java.io.Serializable;

/**
 * Implementa��o da estrutura do Elemento de uma Pagina da �rvoreB.
 * 
 * 
 * @author L�via de Azevedo da Silva
 * @see ArvoreB
 * @see ChaveArvoreB
 */

public class Elemento implements Serializable, Comparable<Elemento>
{
	private ChaveArvoreB chave;
		
	public Elemento(ChaveArvoreB chave)
	{
		this.chave = chave;
	}

	public ChaveArvoreB getChave() {
		return chave;
	}

	public void setChave(ChaveArvoreB chave) {
		this.chave = chave;
	}

	@Override
	public int compareTo(Elemento e) 
	{
		if(this.chave.comparar(e.getChave()) < 0)
			return -1;
			 		 
		if(this.chave.comparar(e.getChave()) > 0)
			return 1;
			  
		return 0;
		
	}
	
}


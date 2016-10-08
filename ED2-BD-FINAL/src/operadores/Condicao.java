package operadores;

public interface Condicao 
{	
	/**
	 * Interface que constitui um interpletador de logica booleana.
	 * Utilizada pela Classe Selecao
	 * 
	 * @author William Anderson de B. Gomes
	 * @see CondicaoComparadora
	 * @see CondicaoLogica
	 * @see Selecao
	 */
	public abstract boolean avaliar(Tupla t);
}

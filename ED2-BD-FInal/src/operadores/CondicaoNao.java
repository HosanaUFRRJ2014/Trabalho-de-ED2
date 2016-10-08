package operadores;

public class CondicaoNao extends CondicaoLogica
{
	/**
	 * Classe responsavel por implementar o comportamento do operador logico NAO.
	 * Utilizada pela Classe Selecao
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Condicao
	 * @see CondicaoLogica
	 * @see Selecao
	 */
	
	public CondicaoNao(Condicao cond) {
		super(cond, null);
	}

	@Override
	public boolean avaliar(Tupla t) 
	{
		return (!getCondEsq().avaliar(t));
	}

}

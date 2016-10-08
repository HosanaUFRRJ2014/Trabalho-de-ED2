package operadores;

public class CondicaoE extends CondicaoLogica
{
	/**
	 * Classe responsavel por implementar o comportamento do operador logico E.
	 * Utilizada pela Classe Selecao
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Condicao
	 * @see CondicaoLogica
	 * @see Selecao
	 */
	public CondicaoE(Condicao condEsq, Condicao condDir) {
		super(condEsq, condDir);
	}

	@Override
	public boolean avaliar(Tupla t) 
	{
		return (getCondEsq().avaliar(t) && getCondDir().avaliar(t));
	}

}

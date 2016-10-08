package operadores;

public class CondicaoOu extends CondicaoLogica {

	/**
	 * Classe responsavel por implementar o comportamento do operador logico OU.
	 * Utilizada pela Classe Selecao
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Condicao
	 * @see CondicaoLogica
	 * @see Selecao
	 */
	
	public CondicaoOu(Condicao condEsq, Condicao condDir) {
		super(condEsq, condDir);
	}

	@Override
	public boolean avaliar(Tupla t) 
	{
		return (getCondEsq().avaliar(t) || getCondDir().avaliar(t));
	}

}

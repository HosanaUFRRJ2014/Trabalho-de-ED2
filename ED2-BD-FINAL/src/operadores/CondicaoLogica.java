package operadores;

public abstract class CondicaoLogica implements Condicao 
{
	
	/**
	 * Afilia o conjunto de classes de logica booleana que incubem a função de 
	 * agregar outras condicoes.
	 * Utilizada pela Classe Selecao
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Condicao
	 * @see CondicaoE
	 * @see CondicaoOu
	 * @see CondicaoNao
	 * @see Selecao
	 */
	
	private Condicao condEsq;
	private Condicao condDir;
	
	public CondicaoLogica(Condicao condEsq, Condicao condDir) {
		super();
		this.condEsq = condEsq;
		this.condDir = condDir;
	}
	
	@Override
	public abstract boolean avaliar(Tupla t);


	public Condicao getCondEsq() {
		return condEsq;
	}

	public Condicao getCondDir() {
		return condDir;
	}

}

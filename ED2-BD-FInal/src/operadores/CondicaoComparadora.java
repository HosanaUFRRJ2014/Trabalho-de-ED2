package operadores;

public abstract class CondicaoComparadora implements Condicao {

	/**
	 * Afilia o conjunto de classes de logica booleana que incubem a função de compara valores.
	 * Utilizada pela Classe Selecao
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Condicao
	 * @see CondicaoIgual
	 * @see CondicaoDiferente
	 * @see CondicaoMaior
	 * @see CondicaoMenor
	 * @see CondicaoMaiorIgual
	 * @see CondicaoMenorIgual
	 * @see Selecao
	 */
	
	private String nomeCampo;
	private String valorCampo;
	
	public CondicaoComparadora(String nomeCampo, String valorCampo) {
		super();
		this.nomeCampo = nomeCampo;
		this.valorCampo = valorCampo;
	}

	@Override
	public abstract boolean avaliar(Tupla t);

	public String getNomeCampo() {
		return nomeCampo;
	}

	public String getValorCampo() {
		return valorCampo;
	}
	
	

}

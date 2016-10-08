package operadores;

public class CondicaoMenor extends CondicaoComparadora {

	/**
	 * Classe responsavel por implementar o comportamento do operador de comparacoa "<".
	 * Utilizada pela Classe Selecao
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Condicao
	 * @see CondicaoComparadora
	 * @see Selecao
	 */
	
	public CondicaoMenor(String nomeCampo, String valorCampo) {
		super(nomeCampo, valorCampo);
	}

	@Override
	public boolean avaliar(Tupla t) 
	{
		Integer valCampo = null;
		try {
			valCampo = Integer.parseInt(getValorCampo());
			int valCampoTupla = Integer.parseInt(t.getColunaTupla(t.retornaIndiceCampo(getNomeCampo())).getValor());
			if(valCampoTupla < valCampo)
				return true;
			else
				return false;
		} catch (Exception e) {
			if(valCampo != null)
				return false;
			
			try {
				int valCampoTupla = Integer.parseInt(t.getColunaTupla(t.retornaIndiceCampo(getNomeCampo())).getValor());
				return false;
			} catch (Exception e2) {
				if(t.getColunaTupla(t.retornaIndiceCampo(getNomeCampo())).getValor().compareToIgnoreCase(getValorCampo()) < 0)
					return true;
				return false;	
			}
		}
	}
}

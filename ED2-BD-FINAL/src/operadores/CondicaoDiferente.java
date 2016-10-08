package operadores;

public class CondicaoDiferente extends CondicaoComparadora {
	
	/**
	 * Classe responsavel por verificar se uma Tupla tem o campo determinado 
	 * com valor diferente o valor desejado.
	 * Utilizada pela Classe Selecao
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Condicao
	 * @see CondicaoComparadora
	 * @see Selecao
	 */
	
	public CondicaoDiferente(String nomeCampo, String valorCampo) {
		super(nomeCampo, valorCampo);
	}

	@Override
	public boolean avaliar(Tupla t) 
	{
		Integer valCampo = null;
		try {
			valCampo = Integer.parseInt(getValorCampo());
			int valCampoTupla = Integer.parseInt(t.getColunaTupla(t.retornaIndiceCampo(getNomeCampo())).getValor());
			if(valCampoTupla != valCampo)
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
				return !t.getColunaTupla(t.retornaIndiceCampo(getNomeCampo())).getValor().equals(getValorCampo());	
			}
			
		}
		
	}

}

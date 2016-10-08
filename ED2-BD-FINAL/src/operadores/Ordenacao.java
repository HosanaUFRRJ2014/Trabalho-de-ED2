package operadores;

public class Ordenacao implements Interpreter
{
	/**
	 * Classe responsavel por implementar o operador Ordenacao da algebra relacional.
	 * Dado o nome de um campo existente na Tupla de uma relacao e uma ordem(crescente ou decrescente) 
	 * ordenar as tuplas de uma relacao. Utiliza do metodo de ordenacao da classe Conteiner
	 * 
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Conteiner
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private Conteiner c;
	private Interpreter relacao;
	private boolean ordemCrescente;
	private String campoOrdenacao;
	private int indiceCampoOrdenacao = -1;
	private boolean ordenada;
	
	public Ordenacao(String campo, boolean ordemCrescente, Interpreter relacao)
	{
		c = new Conteiner();
		this.relacao = relacao;
		this.ordemCrescente = ordemCrescente;
		this.campoOrdenacao = campo;
		ordenada = false;
	}
	@Override
	public Interpreter open() 
	{
		if(!ordenada)
		{
			c.open();
			relacao.open();
			Tupla t = (Tupla) relacao.next();
			if(t != null)
			{
				indiceCampoOrdenacao = t.retornaIndiceCampo(campoOrdenacao);
				if(indiceCampoOrdenacao == -1)
					return null;
				
				while(t!=null)
				{
					c.associarTupla(t);
					t = (Tupla) relacao.next();
				}
			}
			c.close().open();
		}
		else
		{
			c.open();
		}
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Interpreter next() 
	{
		if(!ordenada)
		{
			if(ordemCrescente)
			{
				c.ordenar(1, campoOrdenacao, indiceCampoOrdenacao);
			}
			else
			{
				c.ordenar(-1, campoOrdenacao, indiceCampoOrdenacao);
			}
			ordenada = true;
			c.open();		
		}
		
		return c.next();
			
		// TODO Auto-generated method stub
	}

	@Override
	public Interpreter close() 
	{
		relacao.close();
		c.close();
		// TODO Auto-generated method stub
		return this;
	}

}

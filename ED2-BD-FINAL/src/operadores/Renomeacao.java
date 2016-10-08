package operadores;

public class Renomeacao implements Interpreter
{
	/**
	 * Classe responsavel por implementar o operador Renomeacao da algebra relacional.
	 * Dado o nome de um campo existente na Tupla de uma relacao permite altera-lo para
	 * um nome determinado
	 * 
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private String camposNovos[];
	private String camposVelhos[];
	private Interpreter relacao;
	private Conteiner c;
	private boolean jahRodouTodas = false;

	public Renomeacao(String[] camposVelhos, String[] camposNovos, Interpreter relacao)
	{
		super();
		this.camposNovos = camposNovos;
		this.camposVelhos = camposVelhos;
		this.relacao = relacao;
		c = new Conteiner();
	}

	@Override
	public Interpreter open()
	{
		if(jahRodouTodas)
		{
			c.open();
		}
		else
		{
			relacao.open();
			c.removerTuplas();
		}
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Interpreter next() 
	{
		// TODO Auto-generated method stub
		Tupla t;
		if(jahRodouTodas)
		{
			return c.next();
		}
		else
		{
			t = (Tupla) relacao.next();
			if(t == null)
			{
				jahRodouTodas = true;
				relacao.open();
				c.close();
				return null;
			}
			Tupla tr = t.copiaTupla();
			for(int i = 0; i < camposNovos.length;i++)
			{
				tr.getColunaTupla(t.retornaIndiceCampo(camposVelhos[i])).setNome(camposNovos[i]);
			}
			
			c.associarTupla(tr);
			return tr;
		}
	}

	@Override
	public Interpreter close() 
	{
		// TODO Auto-generated method stub
		c.close();
		relacao.close();
		return null;
	}

}

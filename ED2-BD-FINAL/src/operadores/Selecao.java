package operadores;

public class Selecao implements Interpreter 
{
	/**
	 * Classe responsavel por implementar o operador Selecao da algebra relacional.
	 * Dado uma condicao sobre as Tuplas de uma relacao, retorna todas as Tuplas que 
	 * satisfacam essa condicao.
	 * 
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Tupla
	 * @see Condicao
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private Condicao cond;
	private Interpreter relacao;//operador ou arquivo
	private Tupla t;
	
	public Selecao(Condicao cond, Interpreter relacao)
	{
		super();
		this.cond = cond;
		this.relacao = relacao;
		t = null;
	}

	@Override
	public Interpreter open() 
	{
		relacao.open();
		return this;
		// TODO Auto-generated method stub
	}

	@Override
	public Interpreter next()
	{
		t = (Tupla) relacao.next();
		while(t != null)
		{
			if(cond.avaliar(t))
				return t;
			
			t = (Tupla) relacao.next();
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Interpreter close() 
	{
		relacao.close();
		t = null;
		return this;
		// TODO Auto-generated method stub
	}

	public Condicao getCond() {
		return cond;
	}

	public Interpreter getRelacao() {
		return relacao;
	}

}

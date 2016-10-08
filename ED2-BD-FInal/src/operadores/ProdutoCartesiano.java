package operadores;

public class ProdutoCartesiano implements Interpreter {

	/**
	 * Classe responsavel por implementar o operador Produto Cartesiano da algebra relacional.
	 * soma uma Tupla da esquerda e uma Tupla da direita e retorna. Para toda 
	 * Tupla da esquerda e Toda Tupla da direita.
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see JuncaoNatural
	 * @see HashJoin
	 * @see ProdutoCartesiano
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private Interpreter relacaoEsq;
	private Interpreter relacaoDir;
	private Tupla tuplaAtualEsq;
	private Tupla tuplaAtualDir;
	private Conteiner tuplasResultantes;
	private boolean jahRodouTudo = false;
	
	public ProdutoCartesiano(Interpreter relacaoEsq, Interpreter relacaoDir) {
		super();
		this.relacaoDir = relacaoDir;
		if(relacaoEsq == relacaoDir)
		{
			this.relacaoEsq = new Conteiner();
			Tupla t = (Tupla) this.relacaoDir.open().next();
			while(t != null)
			{
				((Conteiner)this.relacaoEsq).associarTupla(t.copiaTupla());
				t = (Tupla) this.relacaoDir.next();
			}
			this.relacaoEsq.close();
			this.relacaoDir.close();
		}
		else
		{
			this.relacaoEsq = relacaoEsq;
		}
		this.tuplasResultantes = new Conteiner();
	}

	@Override
	public Interpreter open() 
	{
		if(jahRodouTudo)
		{
			tuplasResultantes.open();
		}
		else
		{
			tuplasResultantes.removerTuplas();
			tuplasResultantes.close();
			tuplaAtualEsq = (Tupla) relacaoEsq.open().next();
			tuplaAtualDir = (Tupla) relacaoDir.open().next();
		}
		return this;
	}

	@Override
	public Interpreter next() 
	{
		if(jahRodouTudo)
		{
			return tuplasResultantes.next();
		}
		else
		{
			while(tuplaAtualEsq != null)
			{
				while(tuplaAtualDir != null)
				{
					Tupla t = tuplaAtualEsq.somarTupla(tuplaAtualDir);
					tuplasResultantes.associarTupla(t);
					tuplaAtualDir = (Tupla) relacaoDir.next();
					return t;
				}
				tuplaAtualEsq = (Tupla) relacaoEsq.next();
				if(tuplaAtualEsq != null)
					tuplaAtualDir = (Tupla) relacaoDir.open().next();
			}
		}
		jahRodouTudo = true;
		tuplasResultantes.close();
		return null;
	}

	@Override
	public Interpreter close() 
	{
		relacaoEsq.close();
		relacaoDir.close();
		tuplaAtualEsq = null;
		tuplaAtualDir = null;
		tuplasResultantes.removerTuplas();
		tuplasResultantes.close();
		jahRodouTudo = false;
		return this;
	}

}

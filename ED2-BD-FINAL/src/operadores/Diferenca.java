package operadores;

public class Diferenca implements Interpreter 
{
	/**
	 * Classe responsavel por implementar um operador que faz a diferenca de duas relacoes.
	 * Se assemelha a operacao de conjuntos (relacaoEsq - relacaoDir)
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private Interpreter relacaoEsq;
	private Interpreter relacaoDir;
	private Tupla T;
	private Conteiner c;
	private boolean jahRodouTudo = false;

	public Diferenca(Interpreter relacaoEsq, Interpreter relacaoDir) {
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
		c = new Conteiner();
	}
	
	@Override
	public Interpreter open()
	{
		if(!jahRodouTudo)
			c.removerTuplas();
		
		relacaoEsq.open();
		relacaoDir.open();
		c.open();
		return this;
	}
	
	@Override
	public Interpreter next() 
	{
		// TODO Auto-generated method stub
		if(jahRodouTudo)
		{
			return c.next();
		}
		else
		{
			T = (Tupla) relacaoEsq.next();
			while(T != null)
			{
				boolean naoTemNaDir = true;
				while(naoTemNaDir)
				{
					Tupla Tdir = (Tupla) relacaoDir.next();
					if(Tdir == null)
					{
						c.associarTupla(T);
						relacaoDir.open();
						return T;
					}
					if(T.equals(Tdir))
					{
						naoTemNaDir = false;
						relacaoDir.open();
					}
				}
				
				T = (Tupla) relacaoEsq.next();
			}
		}
		jahRodouTudo = true;
		return null;
	}

	@Override
	public Interpreter close() 
	{
		// TODO Auto-generated method stub
		relacaoEsq.close();
		relacaoDir.close();
		jahRodouTudo = false;
		c.removerTuplas();
		c.close();
		T = null;
		return this;
	}

}

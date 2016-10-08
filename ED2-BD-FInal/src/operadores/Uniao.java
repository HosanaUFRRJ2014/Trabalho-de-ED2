package operadores;

public class Uniao implements Interpreter 
{
	/**
	 * Classe responsavel por implementar um operador que faz a uniao de duas relacoes
	 * Se assemelha a operacao de conjuntos (relacaoEsq U relacaoDir)
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private Interpreter relacaoEsq;
	private Interpreter relacaoDir;
	private Conteiner tuplasEsqInterseccaoDir;
	private Conteiner tuplasEsqSomaDir;
	private Interseccao i;
	private boolean jahRodouTudo = false;
	
	public Uniao(Interpreter relacaoEsq, Interpreter relacaoDir) 
	{
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

		this.tuplasEsqInterseccaoDir = new Conteiner();
		this.tuplasEsqSomaDir = new Conteiner();
		this.i = new Interseccao(this.relacaoEsq, this.relacaoDir);
	}

	@Override
	public Interpreter open()
	{
		// TODO Auto-generated method stub
		if(jahRodouTudo)
		{
			tuplasEsqSomaDir.open();
		}
		else
		{
			tuplasEsqSomaDir.removerTuplas();
			tuplasEsqInterseccaoDir.removerTuplas();
			
			Tupla t1 = (Tupla) i.open().next();
			
			while(t1 != null)
			{
				tuplasEsqInterseccaoDir.associarTupla(t1);
				t1 = (Tupla) i.next();
			}
			
			relacaoEsq.open();
			relacaoDir.open();
			
			t1 = (Tupla) relacaoEsq.next();
			Tupla t2 = (Tupla) relacaoDir.next();
			while(t1 != null || t2 != null)
			{
				if(t1 != null)
				{
					tuplasEsqSomaDir.associarTupla(t1);
					t1 = (Tupla) relacaoEsq.next();
				}
				if(t2 != null)
				{
					tuplasEsqSomaDir.associarTupla(t2);
					t2 = (Tupla) relacaoDir.next();
				}
			}
			
			t1 = (Tupla) tuplasEsqInterseccaoDir.open().next();
			while(t1 != null)
			{
				tuplasEsqSomaDir.removerTupla(t1);
				t1 = (Tupla) tuplasEsqInterseccaoDir.next();
			}
			tuplasEsqSomaDir.open();
			relacaoEsq.open();
			relacaoDir.open();
			jahRodouTudo = true;
		}
		return this;
	}

	@Override
	public Interpreter next() 
	{
		// TODO Auto-generated method stub
		Tupla t = (Tupla) tuplasEsqSomaDir.next();
		if(t == null)
			tuplasEsqSomaDir.open();
		return t;
	}

	@Override
	public Interpreter close() {
		// TODO Auto-generated method stub
		relacaoEsq.close();
		relacaoDir.close();
		tuplasEsqSomaDir.removerTuplas();
		tuplasEsqInterseccaoDir.removerTuplas();
		i.close();
		jahRodouTudo = false;
		return this;
	}

}

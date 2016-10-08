package operadores;

public class Interseccao implements Interpreter 
{
	/**
	 * Classe responsavel por implementar um operador que faz a interseccao de duas relacoes
	 * Se assemelha a operacao de conjuntos (relacaoEsq n relacaoDir)
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private Interpreter relacaoEsq;
	private Interpreter relacaoDir;
	private Conteiner TuplasEsqDiferencaDir;
	private Diferenca d;
	
	public Interseccao(Interpreter relacaoEsq, Interpreter relacaoDir) {
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
		d = new Diferenca(this.relacaoEsq, this.relacaoDir);
		TuplasEsqDiferencaDir = new Conteiner();
	}

	@Override
	public Interpreter open() 
	{
		// TODO Auto-generated method stub
		relacaoEsq.open();
		relacaoDir.open();
		d.open();
		Tupla T = (Tupla) d.next();
		while(T!=null)
		{
			TuplasEsqDiferencaDir.associarTupla(T);
			T = (Tupla) d.next();
		}
		d.close();
		d = new Diferenca(relacaoEsq, TuplasEsqDiferencaDir);
		
		d.open();
		return this;
	}

	@Override
	public Interpreter next() 
	{
		return d.next();
		// TODO Auto-generated method stub
	}

	@Override
	public Interpreter close() {
		// TODO Auto-generated method stub
		relacaoEsq.close();
		relacaoDir.close();
		d.close();
		return this;
	}
	
	

}

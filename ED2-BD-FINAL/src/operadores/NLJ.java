package operadores;

public class NLJ implements Interpreter 
{
	/**
	 * Classe responsavel por implementar o operador Nested Loop Join da algebra relacional.
	 * Dado um campo de juncao, tuplas que possuem o mesmo valor neste campo sao somadas e o
	 * resultado e retornado.
	 * 
	 * As Tuplas resultantes possuem o nome dos campos alterados para "[nomeArquivoOrigem].[nomeCampo]"
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
	private String campoJuncao;
	private int indiceJuncaoEsq = -1;
	private int indiceJuncaoDir = -1;
	private Conteiner c;
	private boolean jahRodouTudo = false;
	//private boolean terminouDir;

	
	public NLJ(String campoJuncao, Interpreter relacaoEsq, Interpreter relacaoDir)
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
		
		this.campoJuncao = campoJuncao;
		c = new Conteiner();
		//this.terminouDir = true;
		
		this.tuplaAtualDir = null;
		this.tuplaAtualEsq = null;
	}

	@Override
	public Interpreter open() 
	{
		if(jahRodouTudo)
		{
			c.open();
			relacaoEsq.open();
			relacaoDir.open();
		}
		else
		{
			tuplaAtualEsq = (Tupla) relacaoEsq.open().next();
			tuplaAtualDir = (Tupla) relacaoDir.open().next();
			indiceJuncaoEsq = tuplaAtualEsq.retornaIndiceCampo(campoJuncao);
			indiceJuncaoDir = tuplaAtualDir.retornaIndiceCampo(campoJuncao);
			c.removerTuplas();
			c.open();
		}
		return this;
	}

	@Override
	public Interpreter next() 
	{
		/*System.out.println();
		tuplaAtualEsq.imprimirValoresColunasTupla();
		tuplaAtualDir.imprimirValoresColunasTupla();
		System.out.println();*/
		if(jahRodouTudo)
		{
			return c.next();
		}
		else
		{
			while(true)
			{
				while(tuplaAtualDir != null)
				{
					//System.out.print("TUPLA DIR : ");
					//tuplaAtualDir.imprimirValoresColunasTupla();
					
					if(tuplaAtualEsq.getColunaTupla(indiceJuncaoEsq).getValor().equals(
							tuplaAtualDir.getColunaTupla(indiceJuncaoDir).getValor()))
					{
						Tupla retorno = new Tupla();
						tuplaAtualEsq.open(); //volta o indice pro inicio, só por precaucao (olhar o metodo na classe) 
						tuplaAtualDir.open();
						retorno = tuplaAtualEsq.somarTupla(tuplaAtualDir);
						for(int i = 0; i< retorno.getTamanhoTupla(); i++)
						{
							String aux = retorno.getColunaTupla(i).getNome();
							retorno.getColunaTupla(i).setNome(retorno.getColunaTupla(i).getArquivoOrigem() + "." + aux);
						}
						tuplaAtualDir = (Tupla) relacaoDir.next();
						//System.out.print("TUPLA RETORNADA : ");
						//retorno.imprimirValoresColunasTupla();
						c.associarTupla(retorno);
						return retorno;
					}
					else // se n for uma tupla correspondente
						tuplaAtualDir = (Tupla) relacaoDir.next(); // atualiza a tupla
				}
				
				
				tuplaAtualEsq = (Tupla) relacaoEsq.next(); //quer dizer que passou toda a relacao da direita e agora vai para proxima linha da esq
				if(tuplaAtualEsq == null) //se isso acontece chegou no fim da relacao esq entao volta ao inicio e continua
				{
					jahRodouTudo = true;
					c.close();
					return null;
				}
				//System.out.print("TUPLA ESQ :");
				//tuplaAtualEsq.imprimirValoresColunasTupla();
				tuplaAtualDir = (Tupla) relacaoDir.open().next();
	
			}
		}
	}
/*	//método auxiliar pra dizer se uma tupla tem o campo que to buscando
	// facilita a leitura do código do next
	private boolean existeCampo(Tupla t) 
	{
		t.open();
		if(  t.getColunaTupla( t.retornaIndiceCampo(campoJuncao.getNome()) ).getValor().contains(campoJuncao.getValor())  )
			return true;

		return false;
	}*/

	@Override
	public Interpreter close() {
		relacaoEsq.close();
		relacaoDir.close();
		c.removerTuplas();
		c.open();
		jahRodouTudo = false;
		// TODO Auto-generated method stub
		return this;
	}

}

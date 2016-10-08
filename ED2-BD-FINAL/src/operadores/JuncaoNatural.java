package operadores;

import java.util.LinkedList;

public class JuncaoNatural implements Interpreter 
{

	/**
	 * Classe responsavel por implementar o operador Juncao Natural da algebra relacional.
	 * Busca por campos de mesmo tipo e retorna uma Tupla que é a soma de duas tuplas 
	 * sem repeticao de campos ((tuplaAtualEsq + tuplaAtualDir) - (tuplaAtualEsq n tuplaAtualDir))
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see ProdutoCartesiano
	 * @see HashJoin
	 * @see NLJ
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private Interpreter relacaoEsq;
	private Interpreter relacaoDir;
	private Conteiner resultadoIntermediario;
	private Tupla tuplaAtualEsq = null;
	private Tupla tuplaAtualDir = null;
	private LinkedList<Integer> indicesJuncaoEsq;
	private LinkedList<Integer> indicesJuncaoDir;
	private Conteiner c;
	private boolean jahRodouTudo = false;
	
	
	public JuncaoNatural(Interpreter relacaoEsq, Interpreter relacaoDir) {
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
		
		this.indicesJuncaoEsq = new LinkedList<Integer>();
		this.indicesJuncaoDir = new LinkedList<Integer>();
		c = new Conteiner();
	}

	@Override
	public Interpreter open() 
	{
		if(jahRodouTudo)
		{
			c.open();
			relacaoDir.open();
			relacaoEsq.open();
		}
		else
		{
			c.open();
			c.removerTuplas();
			tuplaAtualEsq = (Tupla) relacaoEsq.open().next();
			tuplaAtualDir = (Tupla) relacaoDir.open().next();
			
			tuplaAtualEsq.open();
			tuplaAtualDir.open();
			
			for(int i = 0; i < tuplaAtualEsq.getTamanhoTupla();i++)
			{
				int indice = tuplaAtualDir.retornaIndiceCampo(tuplaAtualEsq.getColunaTupla(i).getNome());
				if(indice != -1)
				{
					indicesJuncaoDir.add(indice);
					indicesJuncaoEsq.add(i);
				}
			}
			tuplaAtualEsq.open();
			tuplaAtualDir.open();
		}
		return this;
	}

	@Override
	public Interpreter next() 
	{
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
					if(tuplaAtualEsq == null)
						break;
					boolean retorna = true;
					for(int i = 0; i < indicesJuncaoEsq.size();i++)
					{
						if(!tuplaAtualEsq.getColunaTupla(indicesJuncaoEsq.get(i)).getValor().equals(
								tuplaAtualDir.getColunaTupla(indicesJuncaoDir.get(i)).getValor()))
						{
							retorna = false;
							break;
						}
					}
					
					if(retorna)
					{
						Tupla retorno = tuplaAtualEsq.uniaoTupla(tuplaAtualDir);
						c.associarTupla(retorno);
						tuplaAtualDir = (Tupla) relacaoDir.next();
						return retorno;		
					}
					else
						tuplaAtualDir = (Tupla) relacaoDir.next();
				}
				
				tuplaAtualEsq = (Tupla) relacaoEsq.next();
				if(tuplaAtualEsq == null)
				{
					c.close();
					jahRodouTudo = true;
					return null;
				}
				tuplaAtualDir = (Tupla) relacaoDir.open().next();
			}
		}
			
			
		/*	
			Tupla retorno = null;			
			boolean retorna = false;
			while(!retorna)
			{
				if(tuplaAtualEsq == null)
				{
					jahRodouTudo = true;
					c.open();
					return null;
				}
				
				retorna = true;
				for(int i = 0; i < indicesJuncaoEsq.size();i++)
				{
					if(!tuplaAtualEsq.getColunaTupla(indicesJuncaoEsq.get(i)).getValor().equals(
							tuplaAtualDir.getColunaTupla(indicesJuncaoDir.get(i))))
					{
						retorna = false;
						break;
					}
				}
				
				if(retorna)
				{
					retorno = tuplaAtualEsq.uniaoTupla(tuplaAtualDir);
					c.associarTupla(retorno);
					
				}
			
				
				tuplaAtualDir = (Tupla) relacaoDir.next();
				if(tuplaAtualDir == null)
				{
					tuplaAtualDir = (Tupla) relacaoDir.open().next();
					tuplaAtualEsq = (Tupla) relacaoEsq.next();
				}
			}
			return retorno;
		}*/
		
	}

	@Override
	public Interpreter close() 
	{
		relacaoEsq.close();
		relacaoDir.close();
		jahRodouTudo = false;
		c.close();
		return this;
	}

}

package operadores;

import estruturas.Tabela;

import java.io.IOException;
import java.util.LinkedList;


public class HashJoin implements Interpreter 
{
	/**
	 * Classe responsavel por implementar o operador HashJoin da algebra relacional.
	 * Constroi uma hash da menor relacao e faz a juncao de relacaoEsq com relacaoDir
	 * por um determinado campo de juncao.
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see NLJ
	 * @see JuncaoNatural
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
	private int indiceCampoJuncaoEsq = -1;
	private int indiceCampoJuncaoDir = -1;
	private Tabela t;
	private Conteiner ce;
	private Conteiner cd;
	private Conteiner cRetorno;
	private boolean jahRodouTudo = false;
	private int relacaoEmHash = 0;
	private int indiceAtualLista = -1;
	private LinkedList<Long> e;
	
	public HashJoin(String campoJuncao, Interpreter relacaoEsq, Interpreter relacaoDir)
	{
		super();
		this.relacaoEsq = relacaoEsq;
		this.relacaoDir = relacaoDir;
		this.tuplaAtualEsq = null;
		this.tuplaAtualDir = null;
		this.campoJuncao = campoJuncao;
		ce = new Conteiner();
		cd = new Conteiner();
		cRetorno = new Conteiner();
	}
	
	@Override
	public Interpreter open() 
	{
		if(!jahRodouTudo)
		{
			cRetorno.removerTuplas();
			cRetorno.open();
			ce.removerTuplas();
			ce.open();
			cd.removerTuplas();
			cd.open();
			tuplaAtualEsq = (Tupla) relacaoEsq.open().next();
			indiceCampoJuncaoEsq = tuplaAtualEsq.retornaIndiceCampo(campoJuncao);
			
			int contEsq = 0;
			while(tuplaAtualEsq != null)
			{
				contEsq++;
				ce.associarTupla(tuplaAtualEsq.copiaTupla());
				tuplaAtualEsq = (Tupla) relacaoEsq.next();
			}
			relacaoEsq.close().open();
			
			tuplaAtualDir = (Tupla) relacaoDir.open().next();
			indiceCampoJuncaoDir = tuplaAtualDir.retornaIndiceCampo(campoJuncao);
			int contDir = 0;
			
			while(tuplaAtualDir != null)
			{
				contDir++;
				cd.associarTupla(tuplaAtualDir.copiaTupla());
				tuplaAtualDir = (Tupla) relacaoDir.next();
			}
			
			int i = 0;
			if(contEsq > contDir)
			{
				relacaoEmHash = 1;
				if(contDir>5)
					t = new Tabela(contDir/5, "tempHashJoin", campoJuncao);
				else
					t = new Tabela(contDir, "tempHashJoin", campoJuncao);
				Tupla tt = (Tupla) cd.open().next();
				while(tt != null)
				{
					try {
						t.adicionarElemento(tt.getColunaTupla(indiceCampoJuncaoDir).getValor(), i);
						i++;
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					tt = (Tupla) cd.next();
				}
			}
			else
			{
				relacaoEmHash = -1;
				if(contEsq>5)
					t = new Tabela(contEsq/5, "tempHashJoin", campoJuncao);
				else
					t = new Tabela(contEsq, "tempHashJoin", campoJuncao);
				
				Tupla tt = (Tupla) ce.open().next();
				while(tt != null)
				{
					try {
						t.adicionarElemento(tt.getColunaTupla(indiceCampoJuncaoDir).getValor(), Long.parseLong(String.valueOf(i)));
						i++;
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					tt = (Tupla) ce.next();
				}
			}
			e = null;
			indiceAtualLista = -1;
			tuplaAtualEsq = (Tupla) ce.open().next();
			relacaoEsq.open();
			tuplaAtualDir = (Tupla) cd.open().next();
			relacaoDir.open();
		}
		else
		{
			cRetorno.open();
			relacaoEsq.open();
			relacaoDir.open();
			cd.open();
			ce.open();
		}
		return this;
	}

	@Override
	public Interpreter next() 
	{
		if(jahRodouTudo)
		{
			return cRetorno.next();
		}
		else
		{
			Tupla t;
			if(relacaoEmHash > 0)
			{
				while(tuplaAtualEsq != null)
				{
					if(indiceAtualLista == -1)
					{
						e = this.t.buscar(tuplaAtualEsq.getColunaTupla(indiceCampoJuncaoEsq).getValor());
						indiceAtualLista = 0;
					}
					
					if(e!=null)
					{
						while(indiceAtualLista < e.size())
						{
							tuplaAtualDir = cd.getTupla(Integer.parseInt(String.valueOf(e.get(indiceAtualLista))));
							indiceAtualLista++;
							t = tuplaAtualEsq.somarTupla(tuplaAtualDir);
							
							/*for(int i = 0; i< t.getTamanhoTupla(); i++)
							{
								String aux = t.getColunaTupla(i).getNome();
								t.getColunaTupla(i).setNome(t.getColunaTupla(i).getArquivoOrigem() + "." + aux);
							}*/
							cRetorno.associarTupla(t);
							return t;
						}
	
					}
					
					indiceAtualLista = -1;
					e = null;
					tuplaAtualEsq = (Tupla) ce.next();
				}
			}
			else if(relacaoEmHash < 0)
			{
				while(tuplaAtualDir != null)
				{
					if(indiceAtualLista == -1)
					{
						e = this.t.buscar(tuplaAtualDir.getColunaTupla(indiceCampoJuncaoDir).getValor());
						indiceAtualLista = 0;
					}
					
					if(e!=null)
					{
						while(indiceAtualLista < e.size())
						{
							tuplaAtualEsq = ce.getTupla(Integer.parseInt(String.valueOf(e.get(indiceAtualLista))));
							indiceAtualLista++;
							t = tuplaAtualEsq.somarTupla(tuplaAtualDir);
							cRetorno.associarTupla(t);
							return t;
						}
	
					}
					
					indiceAtualLista = -1;
					e = null;
					tuplaAtualDir = (Tupla) cd.next();
				}
			}
		}
		jahRodouTudo = true;
		cRetorno.open();
		return null;	
		
		// TODO Auto-generated method stub
	}

	@Override
	public Interpreter close() {
		// TODO Auto-generated method stub
		relacaoEsq.close();
		relacaoDir.close();
		jahRodouTudo = false;
		
		return this;
	}
}

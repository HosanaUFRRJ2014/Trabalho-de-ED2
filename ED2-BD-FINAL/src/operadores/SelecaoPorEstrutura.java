package operadores;
import estruturas.Estrutura;

import arquivos.ArquivoDado;
import arquivos.ArquivoEstrutura;

import java.util.LinkedList;


public class SelecaoPorEstrutura implements Interpreter
{
	/**
	 * Classe responsavel por implementar o operador Selecao Por Arvoreb ou Hash da algebra relacional.
	 * Busca em um indice gerado em cima de uma relacao e um campo chave, um valor especifico deste campo
	 * 
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Selecao
	 * @see ArvoreB
	 * @see Tabela
	 * @see Estrutura
	 * @see GerenciadorDeRelacoes
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private Estrutura estrutura;
	private String valorBuscado;
	private LinkedList<Tupla> tuplasRetorno;
	private int indiceAtual;
	private ArquivoDado arquivoOrigemEstrutura;
	
	public SelecaoPorEstrutura(Estrutura est, String valorBuscado, ArquivoDado arq)
	{
		this.estrutura = est;
		this.valorBuscado = valorBuscado;
		this.tuplasRetorno = new LinkedList<Tupla>();
		this.indiceAtual = -1;
		this.arquivoOrigemEstrutura = arq;
	}

	@Override
	public Interpreter open() 
	{
		LinkedList end =  estrutura.buscar(valorBuscado);
		tuplasRetorno = new LinkedList<Tupla>();
		if(end!=null)
		{
			for(int i = 0; i<end.size();i++)
			{
				tuplasRetorno.add(arquivoOrigemEstrutura.criarTupla(arquivoOrigemEstrutura.lerLinhaArquivo((long)end.get(i))));
			}
		}
		indiceAtual = 0;
		return this;
	}

	@Override
	public Interpreter next() 
	{
		if(indiceAtual < tuplasRetorno.size() && indiceAtual >= 0)
		{
			indiceAtual++;
			return this.tuplasRetorno.get(indiceAtual-1);
		}
		
		return null;
	}

	@Override
	public Interpreter close() 
	{
		this.indiceAtual = -1;
		this.tuplasRetorno = null;
		return this;
	}
	
	
}

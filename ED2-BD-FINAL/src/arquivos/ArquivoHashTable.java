package arquivos;
import estruturas.Tabela;

import java.io.IOException;
import java.util.Hashtable;


/**
 * Manipula a estrutura de HashTable.
 * Escrita e leitura  dela em arquivos.
 * 
 * @author Hosana Gomes Pinto
 * @see Arquivo
 * @see ArquivoEstrutura
 */
public class ArquivoHashTable extends ArquivoEstrutura 
{
	private Tabela tabela;


	public ArquivoHashTable(String nomeArquivo, int opcao)
	{
		super(nomeArquivo, opcao);

	}


	/**
	 *Serializa o objeto. Rebece como paramentro um objeto da classe Tabela. 
	 * @param tabela só que será gravada em forma de Object.
	 */

	@Override
	public void escreverNoArquivo(Object tabela)
	{
		this.tabela = (Tabela) tabela;
		

		try
		{
			//salva a hashtable, não um objeto da classe tabela
			getObjetoSaidaStream().writeObject(this.tabela.getHashTable()); 	
			this.getArquivoSaidaStream().close();

		}
		
		catch(IOException e)
		{
			e.printStackTrace();
			//System.out.println("Não gravou a hash");
		}
		

	}

	/**
	 * Deserializa a tabela do arquivo.
	 */
    @Override  
    public Hashtable lerArquivo() throws ClassNotFoundException
	{
		
    	Hashtable hashTable;

		try
		{
			/***************************************/
			opcao = 1;  //um atributo protected de arquivoEstrutura. Setado para reabrir o arquivo no modo leitura.
			this.open();
			/***************************************/
			//pega a hashtable, não um objeto da classe Tabela
			hashTable = (Hashtable) getObjetoEntradaStream().readObject(); 	
		//	this.getArquivoSaidaStream().close();
			
			return hashTable;
		}
		
		catch(IOException e)
		{
			System.out.println("Não leu a hash");
		}
		
		return null;
	}

	/**
	 * Método de criar tabela a partir do método lerArquivo().
	 * <p>A hashtable h será o retorno do método mencionado.
	 * @param h HashTable;
	 * @param tamanho  da tabela.
	 * @return Tabela
	 * @see Tabela
	 */
    
    public Tabela criarTabela(Hashtable h, int tamanho, String pseudonimoArquivoDadoOrigem, String campoChave)
    {
    	Tabela tabela = new Tabela(tamanho, pseudonimoArquivoDadoOrigem, campoChave);
    	
    	tabela.getHashTable().putAll(h);
    	
    	return tabela;
    }


	public Tabela getTabela() 
	{
		return tabela;
	}



	public void setTabela(Tabela tabela) 
	{
		this.tabela = tabela;
	}



}
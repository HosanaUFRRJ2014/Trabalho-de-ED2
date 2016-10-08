package estruturas;
import java.io.IOException;
import java.util.LinkedList;
//import java.util.HashSet;
import java.util.Hashtable;

/**
 * 
 * @author Hosana Gomes Pinto.
 *
 */


public class Tabela implements Estrutura
{

	private Hashtable <Integer, LinkedList> hashTable; 
	private int tamanho;
	private String pseudonimoArquivoDadoOrigem;
	private String nomeCampoChave;

	/**	
	 * Contrutor da classe Tabela.
	 * @param  tamanho inicial da tabela. 
	 */
	public Tabela(int tamanho, String pseudonimoArquivoDadoOrigem, String nomeCampoChave)
	{
		hashTable = new Hashtable(tamanho,1);
        this.tamanho = tamanho;
        this.pseudonimoArquivoDadoOrigem = pseudonimoArquivoDadoOrigem;
        this.nomeCampoChave = nomeCampoChave;
	}

	//só para testes
	/**
	 * Função de dispersão para calcular a posição na {@code Tabela}. 
	 * @param chave Objeto chave a ser adionado na HashTable.
	 * @return int
	 * 
	 */
	private int funcaoDispercao(Chave chave)
	{
	
		return (chave.getString().hashCode()) % tamanho;

	}

	/**
	 * Ao se adicionar um elemento, retorna {@code true} se o elemento foi adicionado
	 * ou {@code false} caso contrário. è a implementação da interface Estrutura
	 * @param string String a ser adicionada.
	 * @param enderecoArquivo Endereco no arquivo .data lido.
	 * @return boolean
	 * @throws IOException 
	 * @see Estrutura
	 * */
	@Override
	public boolean adicionarElemento(String string, long enderecoArquivo) throws IOException 
	{
		/***************Para procurar as chaves, independente do case****************/
		string = string.toUpperCase();
		/******************************************************************************/

		Chave chave = new Chave(string);
		chave.setPosicao(enderecoArquivo);
	
		
		//mapear posicao na tabela dado uma chave
		int posicao = funcaoDispercao(chave);
		
		//se a posição mapeada está vazia
		if(hashTable.get(posicao) == null)
		{
			//cria a "lista"
			LinkedList <Chave> lista = new LinkedList<Chave>();

			//adiona a chave a "lista". 
			boolean b = lista.add(chave);
			
			
			//põe a "lista" na tabela
			hashTable.put(posicao, lista);

			return b;
		}

		//se já tem algum elemento criado na "lista"
		else
		{

			boolean b = hashTable.get(posicao).add(chave);

			return b;

		}


	}
	
	/**
	 * Ao se procurar por um elemento, uma LinkedList de chaves, caso haja valor(es) correspodente(s)
	 * ou null caso contrário.
	 * @param stringBuscada String a ser procurada.
	 * @return LinkedList de objetos chave, ou null, caso não ache valor igual ao procurado.
	 * */
	public LinkedList <Chave> buscarChaveRetornaChave(String stringBuscada)
	{
	
		/***************Para procurar as chaves, independente do case****************/
		stringBuscada = stringBuscada.toUpperCase();
		/******************************************************************************/
		
		LinkedList <Chave> listaChave = new LinkedList<Chave>();
		
		//buscar só pelo primeiro nome
	//	String primeiroNome = stringBuscada.split(" ")[0];
		Chave temp = new Chave(stringBuscada);
		
		//calcular a posição na tabela a partir da chave buscada usando a função de dispersão
		int posicao = funcaoDispercao(temp);
	
		//se a posição não é nula, percorre a lista associada a ela.
		if(hashTable.get(posicao) != null)
		{
			LinkedList aux = hashTable.get(posicao);
	
			
			for(Object chave : aux)
			{
				Chave c = (Chave)chave;

				
				if(c.getString().equalsIgnoreCase(temp.getString()))
				{
		
					listaChave.add(c);
				}

			}
			
			
		}
		

		return listaChave;
	}

	
	/*Esse método retorna a posicao do registro arquivo Binário , dada uma chave*/
	
	/**
	 * Ao se buscar por uma string, retorna uma linkedList de endereços de ocorrência da mesma no
	 * arquivo .data. É a implementação da interface Estrutura
	 * @param stringBuscada
	 * @return LinkedList  de endereços {@code long}
	 * @see Estrutura
	 */
	@Override
	public LinkedList buscar(String stringBuscada)
	{
		//armazenará todos os endereços de todas as chaves que tiverem o mesmo nome. Lembre: mesmo nome != mesmo registro, por isso foi incluso
		LinkedList enderecos = new LinkedList();
		
		/***************Para procurar as chaves, independente do case****************/
		stringBuscada = stringBuscada.toUpperCase();
		/******************************************************************************/

		Chave temp = new Chave(stringBuscada);
		
		//calcular a posição na tabela a partir da chave buscada usando a função de dispersão
		int posicao = funcaoDispercao(temp);
	
		//se a posição não é nula, percorre a lista associada a ela.
		if(hashTable.get(posicao) != null)
		{
			LinkedList aux = hashTable.get(posicao);
			
			
			for(Object chave : aux)
			{
				Chave c = (Chave)chave;
				if(/*temp.equals(c)*/ /*c.getString().regionMatches(true, 0, temp.getString(), 0, temp.getString().length())*/c.getString().equalsIgnoreCase(temp.getString()))
				{
	
					enderecos.add(c.getPosicao());
				}

			}
			
			
		}
		
		return enderecos;
	}
	
	
	public Hashtable<Integer,LinkedList> getHashTable() 
	{
		return hashTable;
	}

	public void setHashTable(Hashtable<Integer, LinkedList> hashTable) 
	{
		this.hashTable = hashTable;
	}

	public int getTamanho() 
	{
		return tamanho;
	}

	public void setTamanho(int tamanho) 
	{
		this.tamanho = tamanho;
	}


	@Override
	public String getPeseudonimoArquivoDadoOrigem() {
		return pseudonimoArquivoDadoOrigem;
	}
		
	

	/**
	 * Não imprime todos os valores contidos na hashTable... Favor, não usar esse método **/

	@Deprecated
	public void imprimirTabela() throws IOException
	{
	//	BufferedWriter buffWrite = new BufferedWriter(new FileWriter("resultado.txt"));
	    

		for(int i = 0; i < tamanho; i++)
		{
			//System.out.println("posicao "+ i + ":");
			//buffWrite.write("poisição:  " + i + ":" + "\n");
			if(hashTable.get(i) != null)
			{
				System.out.println("posicao "+ i + " ocupada");
				LinkedList lista = hashTable.get(i);
				//System.out.println(hashTable.get(i).size());
				for(Object chave : lista)
				{
					Chave c = (Chave)chave;
					System.out.println(c.getString());
					
		//			buffWrite.write(c.getString() + '\n');
			//		buffWrite.flush();
					

				}
				
				
			}
			
			else
				System.out.println("posicao "+ i + " vazia \n");

		//	System.out.println("-----vazio------");
		}
		
	//	buffWrite.close();
	}

	@Override
	public String getNomeCampoChave() 
	{
		return this.nomeCampoChave;
	}



}

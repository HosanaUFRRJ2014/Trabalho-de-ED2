package entity;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import arquivos.ArquivoHashTable;

 
public class Principal 
{
	/*	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		Tabela t =  new Tabela(13);
		byte opcao = 0;

		//abrir para escrita
		//ArquivoHashTable hashArquivo = new ArquivoHashTable("arquivoHash",opcao);

		t.adicionarElemento(new Chave("Hosana"));
		t.adicionarElemento(new Chave("Oficina G3"));
		t.adicionarElemento(new Chave("Breaking Bad"));
		t.adicionarElemento(new Chave("The 100"));
		t.adicionarElemento(new Chave("The Last Ship"));
		t.adicionarElemento(new Chave("Chris Evans"));
		t.adicionarElemento(new Chave("Captain"));


	/*	t.buscarChave("Hosana");

		t.buscarChave("The 100");

		t.buscarChave("Captain");*/

	/*Abrir arquivo para escrita da hashTable*
		ArquivoHashTable hashArquivo = new ArquivoHashTable("arquivoHash.data",opcao);

		hashArquivo.escreverNoArquivo(t);

		hashArquivo.fecharArquivo();

		/*Abrir arquivo para leitura da hashTable*

		opcao = 1;

		hashArquivo = new ArquivoHashTable("arquivoHash.data",opcao);


		Tabela t2 = new Tabela(13); //do mesmo tamanho da outra. Caso não saibamos, colocar um valor indicando o tamanho no começo do arquivo

		/*Aqui, está pegando a HashTable que está no arquivo e colocando no objeto Tabela criado por mim
	 *Reparem que a HashTable é um atributo da classe Tabela*
		t2.getTabela().putAll((Hashtable)hashArquivo.lerArquivo()); 

		/*Imprimir tabela lida do arquivo*

		t2.imprimirTabela();*


	}*/

	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		
		/*ArquivoAutor aa = new ArquivoAutor("autorBinario.data",(byte)1);
		
		Tabela t = new Tabela(1000);
		
		LinkedList<ColunaTupla> listaColunaTupla = new LinkedList();
		
		ColunaTupla colunaTuplaIDautor;
		
		Tupla tuplaAutor = new Tupla();
		long posicaoDoRegistroNoArquivo = 0;
		
	//	BufferedWriter buffWrite = new BufferedWriter(new FileWriter("resultado.txt"));
		
	
		int co = 0;
		while(posicaoDoRegistroNoArquivo < aa.getArquivoEntradaStream().getChannel().size())
		{
			//System.out.print(posicaoDoRegistroNoArquivo + "	");
			listaColunaTupla = aa.lerLinhaArquivo(posicaoDoRegistroNoArquivo);
			
			//criando uma chave de id para a tabela hash. adicionar o id de autor e a posicao da linha do registro
			  t.adicionarElemento(listaColunaTupla.get(0).getValor(),posicaoDoRegistroNoArquivo);//pegar o id de autor a cada iteração
			
		//	  buffWrite.write(listaColunaTupla.get(1).getValor() + "\n");
		//	  buffWrite.flush();  
			  
			 //criação de tupla
			tuplaAutor.associarMuitasColunaTupla(listaColunaTupla);
			// outra abordagem: tuplaAutor = aa.criarTupla(aa.lerLinhaArquivo(posicaoDoRegistroNoArquivo)); ///cria a tupla
			
			posicaoDoRegistroNoArquivo = aa.getArquivoEntradaStream().getChannel().position();
			
		
		}
		
	//	t.imprimirTabela();
		
		//buscar um nome que aparece em 3 registros diferentes. Retornará o endereço dos 3 registros.
		
		LinkedList ll = t.buscarChaveRetornaPosicaoArquivo("LUIZ MIGUEL GOMES");
		
		int f = 0;
		
		while(f < ll.size())
		{
			System.out.println(ll.get(f));
			f++;
		}*/
		
		/*Para buscar todas as chaves do arquivo na tabela de uma única vez*/
		/*resultado2.txt contém todos os nomes do registro*/
/*		posicaoDoRegistroNoArquivo = 0;
		
		BufferedReader buffread = new BufferedReader(new FileReader("resultado2.txt"));
		String linha = "";
	//	while(posicaoDoRegistroNoArquivo < buffread.//aa.getArquivoEntradaStream().getChannel().size())
		{
			//System.out.print(posicaoDoRegistroNoArquivo + "	");
			//listaColunaTupla = aa.lerLinhaArquivo(posicaoDoRegistroNoArquivo);
			
			while(true)
			{
				if(linha != null)
				{
			        t.buscarChave(linha);
				}
				else
					break;
				
				linha = buffread.readLine();
			} 
			
			posicaoDoRegistroNoArquivo = aa.getArquivoEntradaStream().getChannel().position();
		}*/
		
		
		
		
		//t.buscarChave("1");
		
        /*ArquivoLivro al = new ArquivoLivro("livroBinario.data",1);
		
		Tabela t = new Tabela(1000);
		
		LinkedList<ColunaTupla> listaColunaTupla = new LinkedList();
		
		ColunaTupla colunaTuplaIDautor;
		
		Tupla tuplaAutor = new Tupla();
		long posicaoDoRegistroNoArquivo = 0;
		
		int j = 0;
	
		while(posicaoDoRegistroNoArquivo < al.getArquivoEntradaStream().getChannel().size())
		{
			System.out.println(j);
			j++;
			listaColunaTupla = al.lerLinhaArquivo(posicaoDoRegistroNoArquivo);
			
			//criando uma chave de id para a tabela hash. adicionar o id de autor e a posicao da linha do registro
			  t.adicionarElemento(listaColunaTupla.get(2).getValor(),posicaoDoRegistroNoArquivo);//pegar o id de autor a cada iteração
			
			//criação de tupla
			tuplaAutor.associarMuitasColunaTupla(listaColunaTupla);
			// outra abordagem: tuplaAutor = al.criarTupla(aa.lerLinhaArquivo(posicaoDoRegistroNoArquivo)); ///cria a tupla
			
			posicaoDoRegistroNoArquivo = al.getArquivoEntradaStream().getChannel().position();
		
		}
		
		t.imprimirTabela();
		
		Chave teste = t.buscarChaveRetornaChave("1000");
		
		for(int i = 0;i < teste.getPosicao().size(); i++)
		{
			System.out.println(teste.getPosicao(i));
		}*/
		
		//System.out.println(t.buscarChaveRetornaPosicaoArquivo("5000"));
		//t.imprimirTabela();
		
	
		/*Abrir arquivo para  a escrita da HashTable. Só fiz uma vez, já que os dados não mudam*/
		
	//	 ArquivoHashTable hashArquivo = new ArquivoHashTable("arquivoHashIdArquivoAutor.data",0);
	  
	//      ArquivoHashTable hashArquivo = new ArquivoHashTable("arquivoHashNomeArquivoAutor.data",0);
	    
	   // ArquivoHashTable hashArquivo = new ArquivoHashTable("arquivoHashIdAutorArquivoLivro.data",0);
		
	/*	hashArquivo.escreverNoArquivo(t);

		hashArquivo.fecharArquivo();*/
		
		
		/*Abrir arquivo para leitura da hashTable*/
		
		//ou abre a hash por id ou abre a por nome;

	//	ArquivoHashTable hashArquivo = new ArquivoHashTable("arquivoHashIdArquivoAutor.data",1);
		
	/*	ArquivoHashTable hashArquivo = new ArquivoHashTable("arquivoHashNomeArquivoAutor.data",1);


		Tabela t2; //do mesmo tamanho da outra. Caso não saibamos, colocar um valor indicando o tamanho no começo do arquivo

		
		//Método pedido pelo William
		
		Hashtable h = (Hashtable)hashArquivo.lerArquivo();
		
		t2 = hashArquivo.criarTabela(h, 1000);
		
		/*Aqui, está pegando a HashTable que está no arquivo e colocando no objeto Tabela criado por mim
		 *Reparem que a HashTable é um atributo da classe Tabela*/
		//outra abordagem:	t2.getHashTable().putAll((Hashtable)hashArquivo.lerArquivo());
		 
			
		//	t2.imprimirTabela();
		
			//Isso aqui pega todas as informações de um autor, dado seu id.
			//Exemplo: quero todas as infomrações do autor de id = 1000
			//Faz se a busca na HashTable e decobre-se a posição do registro no arquivo AutorBinario.data, caso exista essa registro
			//essa posição é onde as informações do autor estão no arquivo AutorBinario.data
			//System.out.println(aa.lerLinhaArquivo(t2.buscarChaveRetornaPosicaoArquivo("1000")));
			
    

	}
	
	
	
	

}

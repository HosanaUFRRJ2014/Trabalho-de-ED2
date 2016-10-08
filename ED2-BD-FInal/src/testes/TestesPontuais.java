package testes;

import operadores.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

import arquivos.*;
import controladores.Par;
import controladores.Shell;
public class TestesPontuais 
{
	/**
	 * Classe utilizada para todos os testes finais da implementacao.
	 * Pode ser transcrita futuramente para testes do JUnit.
	 * Garante a corretude dos operadores e do Shell 
	 * 
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Shell
	 * @see Interpreter
	 * @see Arquivo
	 * @see Condicao
	 */
	
	static LinkedList<Conteiner> listaPseudoArquivosTeste = new LinkedList<Conteiner>();
//========================OPERADORES===============================================
	/* LISTA DE OPERADORES TESTADOS E FUNCIONANDO
	  Tupla
	  ColunaTupla
	  Conteiner
	  ArquivoDado
	  Diferenca
	  Interseccao
	  Uniao
	  Selecao
	  Projecao
	  Ordenacao
	  Renomeacao
	  NLJ
	  juncaoNatural
	  produtoCartesiano
	  SelecaoPorEstrutura
	  HashJoin
	 */
	
	
//===========================SHELL=================================================
	
	//projecao("campo1 campo2 campo3", relacao(<parametros>))
	//uniao(projecao("campo1 campo2 campo3", relacao(<parametros>)), projecao("campo1 campo2 campo3", relacao(<parametros>)))
	//selecao("campo = valor", relacao(<parametros>))
	//nlj("campoJuncao = valorCampoJuncao", projecao1("campo1 campo2 campo3", relacao(<parametros>)), projecao2("campo1 campo2 campo3", relacao(<parametros>)))
	
	
	//teste para recursividade
	//
	
	public static void testesArquivo() throws IOException
	{
		//testar com diretorio
		ArquivoDado ad = new ArquivoDado("/home/will/editora.txt","pseudonimo",0); //abre o .data para escrita
		
		long pos = ad.lerCabecalhoDoArquivo();
	
		
		LinkedList<ColunaTupla>  c = ad.lerLinhaArquivo(pos);
		
		for(int i = 0; i < c.size(); i++)
			System.out.println(c.get(i).getNome() + ": " + c.get(i).getValor());
		
		
 	}
	
	public static void testeFuncaoBalanceado(String s)
	{
		LinkedList<Par> indice = Shell.verificarBalanceamento(s);

		if(indice != null)
		{
			System.out.println("INDICES :");
			
			for(int i = 0; i < indice.size();i++)
			{
				if(indice.get(i) != null)
				{
					System.out.println(i + " = (" + indice.get(i).getIndiceAberto() + "," + indice.get(i).getIndiceFechado() + ")");
					System.out.println();
				}
			}
		}
		else
		{
			System.out.println("não está balanceada");
		}
		
		return;
	}
	

	public static ArquivoDado testeCriarArquivoDado(String comando)
	{
		return (ArquivoDado) Shell.lerString(comando);
	}
	
	public static void testarOperadores(Interpreter operador, String nomeOperador)
	{
		System.out.println(nomeOperador + ": ");
		operador.open();
		Conteiner c = new Conteiner();
		c.open();
		Tupla t = (Tupla) operador.next();
		while(t != null)
		{
			c.associarTupla(t);
			t = (Tupla) operador.next();
		}
		
		imprimirTuplasRelacao(c);
		c.removerTuplas();
		operador.close();
	}
	public static void imprimirTuplasRelacao(Conteiner c)
	{
		c.open();
		Tupla t = (Tupla) c.next();
		if(t == null)
			return;
		t.imprimirNomeColunasTupla();
		while(t != null)
		{
			t.imprimirValoresColunasTupla();
			t = (Tupla) c.next();
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
//==============TESTE DE EXECUÇÃO GERAL COM INSERÇÃO MANUAL DE COMANDOS=======================		
		Shell.lerString("help()");
		System.out.println();
		Scanner leitor = new Scanner(System.in);
		String s = leitor.nextLine();
		while(!s.equals( "exit()"))
		{
			long tempo = System.currentTimeMillis();
			Interpreter relacao = Shell.lerString(s);
			
			if(relacao!= null)
				testarOperadores(relacao, s);
			tempo = System.currentTimeMillis() - tempo;
			System.out.println("comando executado em : " + tempo + "ms");
			s = leitor.nextLine();
			
		}
		
		//testeFuncaoBalanceado(s);
		
		//testesArquivo();
//====================CRIACAO DE ARQUIVOS PELO SHELL==================================		
		ArquivoDado editora4 = (ArquivoDado) Shell.lerString("criarArquivo(editora4.txt, editora4)");
		ArquivoDado editora4jn = (ArquivoDado) Shell.lerString("criarArquivo(editora4jn.txt, editora4jn)");
		ArquivoDado editora = (ArquivoDado) Shell.lerString("criarArquivo(editora.txt, editora)");
		ArquivoDado editora1 = (ArquivoDado) Shell.lerString("criarArquivo(editora1.txt, editora1)");
		ArquivoDado editora2 = (ArquivoDado) Shell.lerString("criarArquivo(editora2.txt, editora2)");
		ArquivoDado editora3 = (ArquivoDado) Shell.lerString("criarArquivo(editora3.txt, editora3)");
		
//		testarOperadores(editora4, "editora4");
//		System.out.println();
//		
//		testarOperadores(editora3, "editora3");
//		System.out.println();
//		
//		testarOperadores(editora2, "editora2");
//		System.out.println();
//		
//		testarOperadores(editora1, "editora1");
//		System.out.println();
//		
//		testarOperadores(editora, "editora");
//		System.out.println();
		
		
//==========================TESTES DE CONDIÇÕES========================================
		Condicao cond;
//		Condicao cond = new CondicaoIgual("id_editora", "7");
//		Condicao cond = new CondicaoIgual("nome", "Editora das Aventuras Maravilhosas");
//		Condicao cond = new CondicaoIgual("telefone", "99981-1436");
//		Condicao cond = new CondicaoIgual("endereco", "ALAMEDA ENZO MONTORIL REINALDO, 6035,  SAO PAULO");
		
//		Condicao cond = new CondicaoMaior("id_editora", "6");
//		Condicao cond = new CondicaoMaior("nome", "Editora das Aventuras Maravilhosas");
//		Condicao cond = new CondicaoMaior("telefone", "99981-1436");
//		Condicao cond = new CondicaoMaior("endereco", "ALAMEDA ENZO MONTORIL REINALDO, 6035,  SAO PAULO");
		
//		Condicao cond = new CondicaoMenor("id_editora", "6");
//		Condicao cond = new CondicaoMenor("nome", "Editora das Aventuras Maravilhosas");
//		Condicao cond = new CondicaoMenor("telefone", "99981-1436");
//		Condicao cond = new CondicaoMenor("endereco", "ALAMEDA ENZO MONTORIL REINALDO, 6035,  SAO PAULO");
		
//		Condicao cond = new CondicaoNao(new CondicaoE(new CondicaoMaior("id_editora", "5"), new CondicaoMenorIgual("endereco", "ALAMEDA ENZO MONTORIL REINALDO, 6035,  SAO PAULO")));
//		Condicao cond = new CondicaoNao(new CondicaoMenorIgual("endereco", "ALAMEDA ENZO MONTORIL REINALDO, 6035,  SAO PAULO"));
//		Condicao cond = new CondicaoOu(new CondicaoMaior("id_editora", "5"), new CondicaoNao(new CondicaoMenorIgual("endereco", "ALAMEDA ENZO MONTORIL REINALDO, 6035,  SAO PAULO")));
		
		
		//teste com entrada de teclado (trocar visibilidade do método lerCondicao)
/*		Scanner leitor = new Scanner(System.in);
		String s = leitor.nextLine();
		while(s != "exit()")
		{
			Tupla t = (Tupla) editora.open().next();
			cond = Shell.lerCondicao(s, t);
			
			while(t != null)
			{
				System.out.println(cond.avaliar(t));
				t = (Tupla) editora4.next();
			}
			s = leitor.nextLine();
			
		}*/
		
		
/*		Tupla t = (Tupla) editora4.open().next();
		while(t != null)
		{
			System.out.println(cond.avaliar(t));
			t = (Tupla) editora4.next();
		}*/
//============TESTE DAS MINHAS FUNÇÕES GERENCIADORAS DA HASH==============================

//		ArquivoHashTable arqHash = (ArquivoHashTable) Shell.lerString("criarIndice(hashtable, editoraNome, nome, criarArquivo(\"editora.txt\", editora))");
//	
//		ArquivoDado arqDado = (ArquivoDado) Shell.lerString("arquivo(editora)");
//		//System.out.println(arqDado.getNumLinhas());
//		Tabela t = new Tabela(arqDado.getNumLinhas()/5);
//		t.getHashTable().putAll((Hashtable) arqHash.lerArquivo());
//		t.imprimirTabela();
//			
//		LinkedList result = t.buscarChaveRetornaPosicaoArquivo("Editora Universitária");
//		
//		for(int i = 0;i<result.size();i++)
//		{
//			Tupla linha = arqDado.criarTupla(arqDado.lerLinhaArquivo((long)result.get(i)));
//			linha.imprimirValoresColunasTupla();
//		}

		
//=============TESTE FINAL DE OPERADORES (TODOS OS JÁ CRIADOS (exceto os de estrutura)===========================
		Interpreter operador1;
		//ULTIMO TESTE 04/10
		
		//RENOMEACAO
//		operador1 = Shell.lerString("renomeacao(\"id_editora telefone\", \"id TTlegone\", Arquivo(editora))");
//		if(operador1 != null)
//			testarOperadores(operador1, "RENOMEACAO -- id_editora e telefone - Editora");
//		else
//			System.out.println("operador nulo");

		
		
		
		//ORDENACAO
//		operador1 = Shell.lerString("ordenacao(\"nome\", \"crescente\", Arquivo(editora))");
//		if(operador1 != null)
//			testarOperadores(operador1, "ORDENAR -- nome - Editora");
//		else
//			System.out.println("operador nulo");
		
		
		
			
		//JUNCAO NATURAL
//		operador1 = Shell.lerString("juncaoNatural(Arquivo(editora4jn), Arquivo(editora))");
////		operacao acima com projecao que remove os campos de endereco para melhor visualizacao (projecao testada e funcionando)
////		operador1 = Shell.lerString("projecao(\"id_editora nome telefone ideditora noome\", juncaoNatural(Arquivo(editora4jn), Arquivo(editora)))");
//		if(operador1 != null)
//			testarOperadores(operador1, "JUNCAO NATURAL -- Editora4jn x Editora");
//		else
//			System.out.println("operador nulo");
		
		
		
		
		//NLJ
//		operador1 = Shell.lerString("NLJ(\"nome\", Arquivo(editora), renomeacao(noome, nome, Arquivo(editora4jn)))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "NLJ -- id_editora - Editora x editora4");
//		}
//		else
//			System.out.println("operador nulo");
		
		
		
		
		//PROJECAO
//		operador1 = Shell.lerString("projecao(\"id_editora nome\", Arquivo(editora))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "PROJECAO -- id_editora nome- editora");
//		}
//		else
//			System.out.println("operador nulo");
		
		
		
		
		//SELECAO -- 2 CASOS DE TESTE -- 
		//CASO 1 = nome (String) valor (int)
//		operador1 = Shell.lerString("selecao(MAIORIGUAL(nome, \"9\"), Arquivo(editora))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "SELECAO -- cond(nome, num) - editora");
//		}
//		else
//			System.out.println("operador nulo");
	
		//CASO 2 = id_editora (int) valor (String) 
//		operador1 = Shell.lerString("selecao(E(MAIORIGUAL(id_editora, \"1\"), DIFERENTE(nome, \"Editora Orleans\")), Arquivo(editora))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "PROJECAO -- id_editora nome- editora");
//		}
//		else
//			System.out.println("operador nulo");
		
		
		//DIFERENCA
//		operador1 = Shell.lerString("diferenca(Arquivo(editora), Arquivo(editora4))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "DIFERENCA -- editora x editora4");
//		}
//		else
//			System.out.println("operador nulo");
		
		
		
		
		//INTESECCAO -- 4 CASOS DE TESTE
		//CASO1 = SEM INTERSECAO
//		operador1 = Shell.lerString("interseccao(Arquivo(editora3), Arquivo(editora4))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "INTERSECCAO -- editora3 x editora4");
//		}
//		else
//			System.out.println("operador nulo");
		
		//CASO2 = COMPLETA INTESECAO
//		operador1 = Shell.lerString("uniao(Arquivo(editora4), Arquivo(editora4))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "INTERSECCAO -- editora4 x editora4");
//		}
//		else
//			System.out.println("operador nulo");
			
		//CASO3
//		operador1 = Shell.lerString("interseccao(Arquivo(editora3), Arquivo(editora2))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "INTERSECCAO -- editora3 x editora2");
//		}
//		else
//			System.out.println("operador nulo");
		
		//CASO4 = editora3 C editora
//		operador1 = Shell.lerString("interseccao(Arquivo(editora) , Arquivo(editora3))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "INTERSECCAO -- editora x editora3");
//		}
//		else
//			System.out.println("operador nulo");
	
		
		
	
		//UNIAO
//		operador1 = Shell.lerString("uniao(Arquivo(editora3), Arquivo(editora4))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "UNIAO -- editora3 x editora4");
//		}
//		else
//			System.out.println("operador nulo");
		
		
		
		//PRODUTO CARTESIANO
//		operador1 = Shell.lerString("PRODUTOCARTESIANO(Arquivo(editora3), Arquivo(editora4))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "UPRODUTOCARTESIANONIAO -- editora3 x editora4");
//		}
//		else
//			System.out.println("operador nulo");

		
		//SELECAO POR INDICE
//		operador1 = Shell.lerString("SELECAOPORINDICE(nome, editora orleans, CriarIndice(hash, -6, editoraNome, nome, Arquivo(editora)))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "SELECAO POR INDICE -- nome = editora universitária, editora");
//		}
//		else
//			System.out.println("operador nulo");
		
		
		//RENOMEACAO -- alterando somente os campos informados
//		operador1 = Shell.lerString("RENOMEACAO(\"nome id_editora\", \"noome ideditora\", Arquivo(editora4))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "renomeacao -- editora4");
//		}
//		else
//			System.out.println("operador nulo");
		
		
		//HASHJOIN
//		operador1 = Shell.lerString("HASHJOIN(\"id_editora\", Arquivo(editora4), Arquivo(editora4))");
//		if(operador1 != null)
//		{
//			testarOperadores(operador1, "HASHJOIN -- id_editora - Editora4 x editora4");
//		}
//		else
//			System.out.println("operador nulo");
		
		
		
		//TESTES DE COMANDOS DO SHELL DE AUXILIO
		
// 		Shell.lerString("help()");
// 		Shell.lerString("listar()");
//		Shell.lerString("listar(\"editora\")");
//		Shell.lerString("listar(editora)");
//		Shell.lerString("listar(editora\")");
//		Shell.lerString("listar(\"editora4jn\")");
		
//===============TODOS OS COMANDOS FUNCIONANDO 04/10==============================
	
		
		
//		Interpreter operador1 = new NLJ("id_editora", editora, editora4);
//		testarOperadores(operador1, "NLJ -- id_editora - Editora x Editora4");
		System.out.println();
/*		String a[] = {"nome", "endereco"};
		String b[] = {"AAnome", "BBendereco"};
		Interpreter operador2 = new NLJ(editora, editora4, "nome", "Editora");
		testarOperadores(operador2, "NLJ -- editora x editora4");*/
		
	/*	ArquivoHashTable arqHash = (ArquivoHashTable) Shell.lerString("criarIndice(hashtable, a, editoraNome, nome, criarArquivo(\"editora.txt\", editorah))");
		
		ArquivoDado arqDado = (ArquivoDado) Shell.lerString("arquivo(editorah)");
		//System.out.println(arqDado.getNumLinhas());
		Tabela t = arqHash.criarTabela(arqHash.lerArquivo(), arqDado.getNumLinhas()/5, "editorah", "nome");
	
		arqDado.open();
		
		SelecaoPorEstrutura s = new SelecaoPorEstrutura(t,"editora freijó", arqDado);
		testarOperadores(s, "selecao por estrutura hash");*/
		//LinkedList <Chave> chave = t.buscarChaveRetornaChave("Editora Universitária");
		
	/*	for(Chave c: chave)
		{
			Tupla tt = arqDado.criarTupla(arqDado.lerLinhaArquivo(c.getPosicao()));
			//System.out.println(c.getString()+ " : " + c.getPosicao());
			tt.imprimirValoresColunasTupla();
		}
		
		
		arqDado.open();
		LinkedList <Chave> chave2 = t.buscarChaveRetornaChave("Editora ");
		for(Chave c: chave2)
		{
			Tupla tt = arqDado.criarTupla(arqDado.lerLinhaArquivo(c.getPosicao()));
			//System.out.println(c.getString()+ " : " + c.getPosicao());
			tt.imprimirValoresColunasTupla();
		}*/


//     EM SEGUIDA PROCURAR PELAS CLASSES SEM MODIFICADOR DE VISIBILIDADE
//     E POR ULTIMO CONFERIR AS STRINGS DE ERRO DA SHELL PARA VER SE ESTÃO COERENTES COM AS ULTIMAS ALTERAÇOES
	}
}

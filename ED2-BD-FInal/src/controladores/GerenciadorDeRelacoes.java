package controladores;

import arquivos.*;

import estruturas.Estrutura;
import estruturas.ArvoreB;
import estruturas.Tabela;

import operadores.Interpreter;
import operadores.Tupla;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

public class GerenciadorDeRelacoes 
{
	/**
	 * Classe responsavel por gerenciar e prover toda a criacao de indices e arquivos do sistema.
	 * Armazena as relacoes e indices criados e os retorna. 
	 * Impede repeticao e nomes
	 * 
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Estrutura
	 * @see Arquivo
	 * @see Shell
	 */
	
	private static LinkedList<ArquivoDado> arquivosDado = new LinkedList<ArquivoDado>();
	private static LinkedList<String> nomeArquivosDado = new LinkedList<String>();
	private static LinkedList<ArquivoEstrutura> arquivosIndice = new LinkedList<ArquivoEstrutura>();
	private static LinkedList<String> nomeArquivosIndice = new LinkedList<String>();
	
	
	public static Arquivo criarArquivoDado(String diretorio, String pseudonimo)
	{
		if(nomeArquivosDado.contains(pseudonimo))
		{
			return arquivosDado.get(nomeArquivosDado.indexOf(pseudonimo));
		}
		ArquivoDado arq = new ArquivoDado(diretorio, pseudonimo, OpcaoAbertura.ESCRITA.getValor());
		if(arq != null)
		{
			arquivosDado.add(arq);
			nomeArquivosDado.add(pseudonimo);
		}
		
		return arq;
	}
	
	public static Interpreter criarIndice(ArquivoDado arquivoBase, String pseudonimo, String campoChave, boolean Hash, int tamanho) throws IOException
	{
		ArquivoEstrutura arq = null;
		
		arquivoBase.open();//abre o arquivo
		Tupla tupla = (Tupla) arquivoBase.next();//recupera a primeira tupla
		tupla.open();//inicia o indice da tupla
		int indiceChave = tupla.retornaIndiceCampo(campoChave); 
		if(indiceChave == -1)
			return null;
		if(nomeArquivosIndice.contains(pseudonimo))
		{
			Shell.mensagemDeErro("Nao foi possivel criar o indice : o pseudonimo \"" + pseudonimo + "\" ja existe, digite um novo pseudonimo ou use o comando Indice("+pseudonimo+")");
			return null;
		}
		
		if(Hash)
		{
			
			
			/*ColunaTupla ct = (ColunaTupla) tupla.next();//pega a primeira coluna tupla
			int indiceChave = 0;//vai marcar o indice
			while(ct!=null)//busca o indice da coluna tupla de chave
			{
				if(ct.getNome().equals(campoChave))
					break;

				ct = (ColunaTupla) tupla.next();
				indiceChave++;
			}
			
			if(ct == null)//se nao achou o campo fecha o arquivo e retorna nulo
			{
				//arquivoBase.close();
				return null;
			}*/

			Tabela t = new Tabela(tamanho, nomeArquivosDado.get(arquivosDado.indexOf(arquivoBase)), campoChave);//valor 5 é empirico
			//System.out.println(arquivoBase.getNumLinhas());
			tupla.open();//volta a tupla para o indice de inicio
			while(tupla!=null)
			{
				//tupla.imprimirValoresColunasTupla();
				t.adicionarElemento(tupla.getColunaTupla(indiceChave).getValor(), tupla.getColunaTupla(indiceChave).getEnderecoTuplaOrigem());
				tupla = (Tupla) arquivoBase.next();
			}
			
			arq = new ArquivoHashTable(pseudonimo+".data", OpcaoAbertura.ESCRITA.getValor());
			//t.imprimirTabela();
			arq.escreverNoArquivo((Object) t);
			arq.fecharArquivo();
			arq.setOpcao(OpcaoAbertura.LEITURA.getValor());
			arq.open();
			
		}
		else
		{
			ArvoreB a = new ArvoreB(tamanho, arquivoBase.getTipoDado()[indiceChave], nomeArquivosDado.get(arquivosDado.indexOf(arquivoBase)), campoChave);
			
			tupla.open();
			while(tupla != null)
			{
				a.adicionarElemento(tupla.getColunaTupla(indiceChave).getValor() , tupla.getColunaTupla(indiceChave).getEnderecoTuplaOrigem());
				tupla = (Tupla) arquivoBase.next();
			}
			
			arq = new ArquivoArvoreB(pseudonimo+".data", OpcaoAbertura.ESCRITA.getValor());
			arq.escreverNoArquivo(a);
			arq.fecharArquivo();
			arq.setOpcao(OpcaoAbertura.LEITURA.getValor());
			arq.open();
		}
		
		if(arq != null)
		{
			arquivosIndice.add(arq);
			nomeArquivosIndice.add(pseudonimo);
		}
		
		return arq;
	}
	
	public static Interpreter retornaArquivoDado(String pseudonimo)
	{
		if(nomeArquivosDado.contains(pseudonimo))
			return (Interpreter) arquivosDado.get(nomeArquivosDado.indexOf(pseudonimo));
		
		return null;
	}
	
	public static Interpreter retornaIndice(String pseudonimo)
	{
		if(nomeArquivosIndice.contains(pseudonimo))
			return arquivosIndice.get(nomeArquivosIndice.indexOf(pseudonimo));
		
		return null;
	}
	
	public static void imprimirRelacoes()
	{
		System.out.println();
		System.out.println("ARQUIVOS: ");
		for(int i = 0; i<nomeArquivosDado.size(); i++)
			System.out.println("	" + i + " - \"" + nomeArquivosDado.get(i) + "\" numero de registros : " + arquivosDado.get(i).getNumLinhas());
		
		System.out.println();
		System.out.println("INDICES : ");
		for(int i = 0; i<nomeArquivosIndice.size(); i++)
			System.out.println("	" + i + " - \"" + nomeArquivosIndice.get(i) + "\"");
		System.out.println();
	}
	//como tratar para as estruturas ?
	public static void imprimirCamposRelacao(String nomeRelacao)
	{
		if(nomeArquivosDado.contains(nomeRelacao))
		{
			System.out.println();
			String nomeCampos [] = arquivosDado.get(nomeArquivosDado.indexOf(nomeRelacao)).getNomeCampo();
			String tipoCampos [] = arquivosDado.get(nomeArquivosDado.indexOf(nomeRelacao)).getTipoDado();
			System.out.println("Relacao \"" + nomeRelacao + "\" : ");
			for(int i = 0; i < nomeCampos.length; i++)
				System.out.print("\t" + nomeCampos[i] + tipoCampos[i] + "\t");
			System.out.println();
		}
		else
			System.out.println("nome da relacao nao encontrado");
		
		
		
	}
}

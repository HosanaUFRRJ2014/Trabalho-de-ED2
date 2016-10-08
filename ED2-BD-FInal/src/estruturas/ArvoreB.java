package estruturas;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Implementa��o da �rvoreB.
 * M�todos de busca, inser��o e cis�o implementados.
 * 
 * @author L�via de Azevedo da Silva
 * 
 * 
 */

public class ArvoreB implements Serializable, Estrutura
{
		private int d;
		private Pagina raiz;
		
		private Pagina ultimaVisitada;
		private String tipoDominante;
		private String pseudonimoArquivoDadoOrigem;
		private String nomeCampoChave;
		
		public ArvoreB(int d, String tipoDominante, String pseudonimoArquivoDadoOrigem, String nomeCampoChave)
		{
			this.d = d;
			this.raiz = new Pagina(this);
			
			this.ultimaVisitada = null;
			this.tipoDominante = tipoDominante;
			this.pseudonimoArquivoDadoOrigem = pseudonimoArquivoDadoOrigem;
			this.nomeCampoChave = nomeCampoChave;

		}
		 
		@Override
		public String getPeseudonimoArquivoDadoOrigem() {
			return pseudonimoArquivoDadoOrigem;
		}
			
		

		public String getNomeCampoChave() {
			return nomeCampoChave;
		}

		public Pagina getUltimaVisitada() {
			return ultimaVisitada;
		}

		public void setUltimaVisitada(Pagina ultimaVisitada) {
			this.ultimaVisitada = ultimaVisitada;
		}

		//Se tiver o elemento, retorna-o, sen�o retorna null.
		 public Elemento busca(ChaveArvoreB chave)
		 {
			 Pagina auxP = this.getRaiz();
			 Pagina auxP2 = null;
						 
			 int inicio = 0;
			 int fim = auxP.getElementos().size() - 1;
			 int meio;
			 
			 while(auxP != null)
			 {
				 this.setUltimaVisitada(auxP);
				 
				 /*-----------------------Busca bin�ria---------------------------*/
				 
				 inicio = 0;
				 fim = auxP.getElementos().size() - 1;
				
				 while(inicio <= fim)
				 {
					 meio = (inicio+fim)/2;
					 					 
					 if(chave.comparar(auxP.getElementos().get(meio).getChave()) == 0)
					 {	
						 return auxP.getElementos().get(meio);
					 }
					 
					 if(chave.comparar(auxP.getElementos().get(meio).getChave()) > 0)
					 {
						 inicio = meio + 1;
						 
						 if(!auxP.ehFolha())
							 auxP2 = auxP.getFilhos().get(meio + 1);
						 else
							 auxP2 = null;
					 }
					 else
					 {
						 fim = meio - 1;
						 
						 if(!auxP.ehFolha())
							 auxP2 = auxP.getFilhos().get(meio);
						 else
							 auxP2 = null;
					 }
				 }
				 /*-------------------------------------------------------------------------------*/

				 auxP = auxP2;
			 }
			 
			 return null;
		 }
		 
		 public void verificarExistenciaElemento(ChaveArvoreB chave)
		 {
			 Elemento existe = this.busca(chave);
			 
			 if(existe != null)
				 System.out.println("Elemento existe!");
			 else
				 System.out.println("Elemento n�o existe!");
		 }
		 
		 //True para inser��o de um elemento na �rvore e False para a exist�ncia desse elemento, mas com a inser��o de seu endere�o na LinkedList
		 public boolean insercao(ChaveArvoreB chave)
		 {
			 Elemento auxE = this.busca(chave);
			 Elemento novo;
			 
			 //H� o elemento na �rvoreB. Inserir o seu endere�o na lista de endere�os do elemento!
			 if(auxE != null)
			 {
				 auxE.getChave().adicionarEnderecoNaLista(chave.getEnderecosLinhaArquivo().get(0));
				 return false;
			 }
			 else //Inserir de fato o elemento na �rvoreB...
			 {
				 novo = new Elemento(chave);
				 auxE = this.getUltimaVisitada().inserirElemento(novo);
				 
				//Teve sobra. Chamar cis�o
				 if(auxE != null)
				 {
					 cisao(this.getUltimaVisitada(),auxE, null);
					 return true;
				 }
				 else
				 {
					 return true;
				 }
			 }
		 }
		 
		 public void cisao(Pagina pag,Elemento sobra,Pagina[] filhasPagIrma)
		 {
			 Pagina pagIrma = new Pagina(this);
			 Elemento sobraCisao;
			 
			 //A lista de filhos sempre estar� ordenada!
			 if(filhasPagIrma != null) //Se for null, significa que � a primeira vez que a cis�o � chamada.
			 {				
				 //O tamanho desta lista SEMPRE ser� igual a (d + 1).				 			
				 for(int i = 0;i < filhasPagIrma.length;i++)
				 {
					 filhasPagIrma[i].setIndiceFilho(i);
					 pagIrma.inserirFilho(filhasPagIrma[i]);
				 }
			 }
			 
			 pag.getElementos().remove(2 * this.getD());
			 			 
			 //Ir� para a p�gina pai.(elemento central)
			 Elemento iraSubir = pag.getElementos().get(this.getD());
			 
			 //Elementos que passar�o para a p�gina irm�.
			 List<Elemento> elemIrma = pag.getElementos().subList(this.getD() + 1, 2 * this.getD());
			 pagIrma.getElementos().addAll(elemIrma); //A tend�ncia � esta subLista j� esta ordenada...
			 pagIrma.inserirElemento(sobra);
			 //-------------------------------------------------------------------------------------
			 
			 //Remove todos os elementos passados para a pagIrma e o que ir� subir para o pai.
			 pag.getElementos().removeAll(elemIrma);
			 pag.getElementos().remove(this.getD());
			 //-------------------------------------------------------------------------------
			 
			 //Criar nova raiz!
			 if(pag == this.getRaiz())
			 {
				 Pagina novaRaiz = new Pagina(this);
				 novaRaiz.inserirElemento(iraSubir);//Adicionar o elemento central na nova raiz.
				 
				 //Ser� o filho a direita do elemento que subir� para a p�gina pai(ficar� a direita de pag).
				 pagIrma.setIndiceFilho(1);
				 pag.setIndiceFilho(0);
				 
				 novaRaiz.inserirFilho(pag);
				 novaRaiz.inserirFilho(pagIrma);
				 
				 this.setRaiz(novaRaiz);
			 }
			 else
			 {
				 //Ser� o filho a direita do elemento que subir� para a p�gina pai(ficar� a direita de pag).
				 pagIrma.setIndiceFilho(pag.getIndiceFilho() + 1);
				 pag.getPai().inserirFilho(pagIrma);
				 
				 sobraCisao = pag.getPai().inserirElemento(iraSubir);
				 
				 if(sobraCisao != null)
				 {
					 //Passar o conjunto de filhos no intervalo de [d;2d + 1] para esta lista(Preparar os ponteiros dos filhos)
					 Pagina[] novosFilhosIrma = new Pagina[this.getD() + 1];
					 
					 for(int i = 2 * this.getD() + 1,j = this.getD();i >= this.getD() + 1;i--,j--)
					 {
						 novosFilhosIrma[j] = pag.getPai().getFilhos().get(i);
						 pag.getPai().getFilhos().remove(i);
					 }
					 					 										 
					 this.cisao(pag.getPai(),sobraCisao,novosFilhosIrma);
				 }
			 }
	 
			//---------------------------------------------------------------------------------
		 }
		  	
		 /*-------------------Fun��o de impress�o para testes-----------------------------------------*/

		public void imprimirArvore(Pagina pag)
		{				
			if(pag == this.raiz)
				pag.imprimirElementos();
			
			if(pag.getFilhos().size() != 0)
			{				
				if(pag.getFilhos().get(0) != null)
				{
					System.out.println();
									
					pag.getFilhos().get(0).imprimirElementos();
					
					//Imprimir todos os filhos
					for(int i = 0; i < pag.getElementos().size();i++)
					{
						pag.getFilhos().get(i + 1).imprimirElementos();
					}
					
					imprimirArvore(pag.getFilhos().get(0));
					
					for(int j = 0;j < pag.getElementos().size();j++)
					{			
						imprimirArvore(pag.getFilhos().get(j + 1));
					}
				}
			}	
		}

		/*---------------------------------------------------------------------------------*/
		
		public int getD() {
			return d;
		}

		public void setD(int d) {
			this.d = d;
		}

		public Pagina getRaiz() {
			return raiz;
		}

		public void setRaiz(Pagina raiz) {
			this.raiz = raiz;
		}

		@Override
		public boolean adicionarElemento(String chave, long enderecoArquivo) throws IOException {
						
			ChaveArvoreB c = null;
			
			if(this.tipoDominante.equalsIgnoreCase("(int)"))
			{
				int chaveInt = Integer.parseInt(chave);
				c = new ChaveArvoreB(chaveInt, enderecoArquivo);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(string)"))
			{
				c = new ChaveArvoreB(chave, enderecoArquivo);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(short)"))
			{
				short chaveShort = (short)Integer.parseInt(chave);
				c = new ChaveArvoreB(chaveShort, enderecoArquivo);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(long)"))
			{
				long chaveLong = Long.parseLong(chave);
				c = new ChaveArvoreB(chaveLong, enderecoArquivo);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(float)"))
			{
				float chaveFloat = Float.parseFloat(chave);
				c = new ChaveArvoreB(chaveFloat, enderecoArquivo);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(double)"))
			{
				double chaveDouble = Double.parseDouble(chave);
				c = new ChaveArvoreB(chaveDouble, enderecoArquivo);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(char)"))
			{
				char[] chaveChar = chave.toCharArray();
				c = new ChaveArvoreB(chaveChar[0], enderecoArquivo);
			}
			
			boolean sucesso = this.insercao(c);
			
			if(sucesso)
				return true;
			else
				return false;
		}

		@Override
		public LinkedList buscar(String chave) {
			
			ChaveArvoreB c = null;
			
			if(this.tipoDominante.equalsIgnoreCase("(int)"))
			{
				int chaveInt = Integer.parseInt(chave);
				c = new ChaveArvoreB(chaveInt);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(string)"))
			{
				c = new ChaveArvoreB(chave);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(short)"))
			{
				short chaveShort = (short)Integer.parseInt(chave);
				c = new ChaveArvoreB(chaveShort);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(long)"))
			{
				long chaveLong = Long.parseLong(chave);
				c = new ChaveArvoreB(chaveLong);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(float)"))
			{
				float chaveFloat = Float.parseFloat(chave);
				c = new ChaveArvoreB(chaveFloat);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(double)"))
			{
				double chaveDouble = Double.parseDouble(chave);
				c = new ChaveArvoreB(chaveDouble);
			}
			else if(this.tipoDominante.equalsIgnoreCase("(char)"))
			{
				char[] chaveChar = chave.toCharArray();
				c = new ChaveArvoreB(chaveChar[0]);
			}

			Elemento retorno = this.busca(c);
			
			if(retorno != null)
				return retorno.getChave().getEnderecosLinhaArquivo();
			else
				return null;
		}

		
}


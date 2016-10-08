package estruturas;
import java.io.Serializable;
import java.util.*;

/**
 * Implementa��o da estrutura P�gina da �rvoreB.
 * A p�gina � composta de elementos que cont�m a chave.
 * 
 * @author L�via de Azevedo da Silva
 * @see ArvoreB
 * @see Elemento
 * @see ChaveArvoreB
 */

public class Pagina implements Serializable, Comparable<Pagina>
{
	//Max de elementos: 2d e Max de filhos: 2d + 1
	private List<Elemento> elementos;
	private List<Pagina> filhos; //Elemento de �ndice i: filhos: filhos[i] e filhos[i+1](com exe��o do elemento na posi��o 2d)
	private ArvoreB arvore;
	private int indiceFilho; //posi��o na lista de filhos da p�gina.
	private Pagina pai;
	
	public Pagina(ArvoreB arvore)
	{
		this.elementos = new ArrayList<Elemento>(2 * arvore.getD() + 1); //"+1" � para guardar o elemento de sobra para a cis�o!
		this.filhos = new ArrayList<Pagina>(2 * arvore.getD() + 2); //"+1" � para guardar o filho excedente da cis�o!
		this.arvore = arvore;
		this.indiceFilho = -1;//Posi��o do filho na lista de filhos da p�gina.
		this.pai = null;
	}

	public boolean ehFolha(){
		return (this.filhos.size() == 0);
	}
	
	public boolean maxFilhos(){
		return (this.filhos.size() == 2 * this.arvore.getD() + 1);
	}
	
	public Pagina getPai() {
		return pai;
	}

	public void setPai(Pagina pai) {
		this.pai = pai;
	}

	public int getIndiceFilho() {
		return indiceFilho;
	}

	public void setIndiceFilho(int indiceFilho) {
		this.indiceFilho = indiceFilho;
	}

	public ArvoreB getArvore() {
		return arvore;
	}

	public void setArvore(ArvoreB arvore) {
		this.arvore = arvore;
	}
	
	public List<Elemento> getElementos() {
		return elementos;
	}

	public void setElementos(List<Elemento> elementos) {
		this.elementos = elementos;
	}
	
	public List<Pagina> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Pagina> filhos) {
		this.filhos = filhos;
	}
	
	//Insere sempre no �ltimo elemento da p�gina e depois ordena.
	public Elemento inserirElemento(Elemento e)
	{
		this.elementos.add(e);
		Collections.sort(this.elementos);
				
		//Tem elemento de sobra? Se sim, dever� sofrer cis�o(elemento retornado � a sobra).
		if(this.elementos.size() == 2 * this.arvore.getD() + 1)
			return this.elementos.get(2 * this.arvore.getD());
		else
			return null;
	}
		
	public void inserirFilho(Pagina p)
	{
		int novoIndice;
		
		//Serve para ordenar o novo filho na posi��o correta no vetor de filhos da p�gina.
		for(int i = p.getIndiceFilho();i < this.filhos.size();i++)
		{
			novoIndice = this.filhos.get(i).getIndiceFilho() + 1;
			this.filhos.get(i).setIndiceFilho(novoIndice);
		}
		
		p.setPai(this);
		this.filhos.add(p);
		Collections.sort(this.filhos);		
	}
			
	/*Fun��o de impress�o para testes*/
	public void imprimirElementos()
	{
		for(int i = 0;i < this.getElementos().size();i++)
		{	
			this.getElementos().get(i).getChave().imprimirChave();
		}
		System.out.print("		");
	}

	@Override
	public int compareTo(Pagina p) {
		
		if(this.getIndiceFilho() < p.getIndiceFilho())
			return -1;
			 		 
		if(this.getIndiceFilho() > p.getIndiceFilho())
			return 1;
			  
		//Isto NUNCA deve acontecer...pois n�o existir� �ndices iguais entre os filhos.
		return 0;
	}
	
}



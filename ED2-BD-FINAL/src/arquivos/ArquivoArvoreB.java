package arquivos;
import estruturas.ArvoreB;
import estruturas.ChaveArvoreB;
import estruturas.Elemento;
import java.io.IOException;


/**
 * Manipula a estrutura de �rvoreB.
 * Escrita e leitura  dela em arquivos.
 * 
 * @author L�via de Azevedo da Silva
 * @see Arquivo
 * @see ArquivoEstrutura
 */
public class ArquivoArvoreB extends ArquivoEstrutura 
{
	private ArvoreB arvoreB;


	public ArquivoArvoreB(String nomeArquivo, int opcao)
	{
		super(nomeArquivo, opcao);

	}


	/**
	 *Serializa o objeto. Rebece como paramentro um objeto da classe �rvoreB. 
	 * @param A �rvoreB ser� gravada em forma de Object.
	 */

	@Override
	public void escreverNoArquivo(Object arvoreB)
	{
		this.arvoreB = (ArvoreB) arvoreB;
		

		try
		{
			//salva a hashtable, n�o um objeto da classe tabela
			getObjetoSaidaStream().writeObject(this.arvoreB); 	
			this.getArquivoSaidaStream().close();

		}
		
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("N�o gravou a �rvore");
		}
		

	}

	/**
	 * Deserializa a �rvoreB do arquivo.
	 */
    @Override  
    public ArvoreB lerArquivo() throws ClassNotFoundException
	{
		
    	ArvoreB arvoreB;

		try
		{
			/***************************************/
			opcao = 1;  //um atributo protected de arquivoEstrutura. Setado para reabrir o arquivo no modo leitura.
			this.open();
			/***************************************/
			//pega a �rvoreB
			arvoreB = (ArvoreB) getObjetoEntradaStream().readObject(); 				
			return arvoreB;
		}
		
		catch(IOException e)
		{
			System.out.println("N�o leu a �rvoreB");
		}
		
		return null;
	}
    
	public ArvoreB getArvoreB() 
	{
		return arvoreB;
	}

	public void setArvoreB(ArvoreB arvoreB) 
	{
		this.arvoreB = arvoreB;
	}

}

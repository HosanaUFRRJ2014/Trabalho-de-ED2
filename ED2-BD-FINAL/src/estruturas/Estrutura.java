package estruturas;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Interface para ser implementada pelas estruturas �rvoreB e Tabela.
 *
 */

public interface Estrutura 
{
	
	boolean adicionarElemento(String string, long enderecoArquivo) throws IOException;

	/**
	 * Retornar uma linkedList de endere�os no arquivo 
	 */ 
	public abstract LinkedList buscar(String chave);
	
	public abstract String getPeseudonimoArquivoDadoOrigem();
	
	public abstract String getNomeCampoChave();

	

}

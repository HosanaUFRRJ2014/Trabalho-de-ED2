package arquivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe responsável pela manipulação de arquivos da aplicação.
 * @author Hosana Gomes Pinto.
 *
 */

public abstract class Arquivo
{

	private String diretorioPastaLocal;
	private File arquivo;
	
	private String nomeArquivo;
	private int opcao;
	
	private FileInputStream arquivoEntradaStream;
	private FileOutputStream arquivoSaidaStream;
	
//	OpcaoAbertura opcaoAbertura;

	/**
	 * 
	 * @param nomeArquivo
	 * @param opcao de abertura  {@code 0} para escrita e {code 1} para leitura.
	 */

	public Arquivo(String nomeArquivo, int opcao) 
	{
		Properties propriedades = getPropriedades();
		diretorioPastaLocal = propriedades.getProperty("home.dir");
		this.nomeArquivo = nomeArquivo;
		this.opcao = opcao;
	}


	public String getNomeArquivo() 
	{
		return nomeArquivo;
	}

	public int getOpcao() 
	{
		return opcao;
	}
	
	public File getArquivo() 
	{
		return arquivo;
	}

	public void setArquivo(File arquivo) 
	{
		this.arquivo = arquivo;
	}
	
	public FileInputStream getArquivoEntradaStream() 
	{
		return arquivoEntradaStream;
	}

	public void setArquivoEntradaStream(FileInputStream arquivo) 
	{
		this.arquivoEntradaStream = arquivo;
	}


	public FileOutputStream getArquivoSaidaStream() 
	{
		return arquivoSaidaStream;
	}


	public void setArquivoSaidaStream(FileOutputStream arquivoSaidaStream) 
	{
		this.arquivoSaidaStream = arquivoSaidaStream;
	}


	public String getDiretorioPastaLocal() 
	{
		return diretorioPastaLocal;
	}


	public void setDiretorioPastaLocal(String diretorioPastaLocal)
	{
		this.diretorioPastaLocal = diretorioPastaLocal;
	}

	/**
	 * Para poder ler de diretório home de qualquer máquina Linux. 
	 * @return Properties
	 */
	public static Properties getPropriedades()
	{
		Properties propriedades = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream("./properties/path.properties");
			propriedades.load(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return propriedades;
	}
	
	
	

}
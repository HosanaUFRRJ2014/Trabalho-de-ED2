package arquivos;
import operadores.Interpreter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe responsável pela escrita(opcao == 0) e
 * <p>leitura(opcao == 1) das estruturas TabelaHash
 * <p>e ÁrvoreB
 * @author Hosana Gomes Pinto.
 *@see Arquivo
 */
public abstract class ArquivoEstrutura extends Arquivo implements Interpreter
{

	private ObjectOutputStream objetoSaidaStream; 
	private ObjectInputStream objetoEntradaStream;
	private boolean estahAberto = false;
	protected int opcao;

	public ArquivoEstrutura(String nomeArquivo, int opcao) //opcao indica se vai abrir escrita(0) ou leitura(1)
	{
		super(nomeArquivo, opcao);
		this.opcao = opcao;
		this.open();


	}

	public abstract void escreverNoArquivo(Object tabelaOuArvore);


	public abstract Object lerArquivo() throws ClassNotFoundException;

	public void fecharArquivo() throws IOException
	{
		if (getOpcao() == 0 /*opcaoAbertura.ESCRITA.getValor()*/)
		{
			getObjetoSaidaStream().flush();
			getObjetoSaidaStream().close();
			getArquivoSaidaStream().close();
		}

		else
		{
			getObjetoEntradaStream().close();
			getArquivoEntradaStream().close();
		}
	}


	public ObjectOutputStream getObjetoSaidaStream() 
	{
		return objetoSaidaStream;
	}

	public void setObjetoSaidaStream(ObjectOutputStream objetoSaidaStream) 
	{
		this.objetoSaidaStream = objetoSaidaStream;
	}

	public ObjectInputStream getObjetoEntradaStream() 
	{
		return objetoEntradaStream;
	}

	public void setObjetoEntradaStream(ObjectInputStream objetoEntradaStream) 
	{
		this.objetoEntradaStream = objetoEntradaStream;
	}

	public void setOpcao(int opcao)
	{
		this.opcao = opcao;
	}


	/**
	 * Esse foi o método que alterei. Estava faltando um estahAberto no else
	 * @author hosana
	 */
	@Override
	public Interpreter open() {

		//	if(!estahAberto)
		//	{
		setArquivo(new File(getDiretorioPastaLocal()+getNomeArquivo()));
		if(opcao == 0 )
		{

			try
			{

				setArquivoSaidaStream(new FileOutputStream(getArquivo()));
				objetoSaidaStream = new ObjectOutputStream(getArquivoSaidaStream()); 

			}

			catch(IOException e)
			{
				System.out.println("Arquivo não encontrado");
			}

		}

		else //abrir arquivo para leitura
		{
			estahAberto = true;



			try
			{
				setArquivoEntradaStream(new FileInputStream(getArquivo()));
				objetoEntradaStream = new ObjectInputStream(getArquivoEntradaStream());
			}

			catch(IOException e)
			{
				System.out.println("Arquivo não encontrado");
			}

		}
		//	}


		return this;
	}
	/**@author William Anderson de Brito Gomes
	 * 
	 *  
	 */
	/*	 @Override
	public Interpreter open() 
	{
		// TODO Auto-generated method stub
		if(getOpcao() == 0)
		{
			//System.out.println("aaaaaaaaa");
			try
			{

				arquivoSaidaStream = new FileOutputStream(getArquivo());
				objetoSaidaStream = new ObjectOutputStream(arquivoSaidaStream); 

				setArquivoSaidaStream(new FileOutputStream(getArquivo()));
					objetoSaidaStream = new ObjectOutputStream(getArquivoSaidaStream()); 

			}

			catch(IOException e)
			{
				System.out.println("Arquivo não encontrado");
			}

		}

		else //abrir arquivo para leitura
		{

			try
			{

				setArquivoEntradaStream(new FileInputStream(getArquivo()));
				objetoEntradaStream = new ObjectInputStream(getArquivoEntradaStream());
			}

			catch(IOException e)
			{
				System.out.println("Arquivo não encontrado");
			}

		}

		return this;
	}
	 */

	/**
	 * @author William Anderson de Brito Gomes.
	 */
	@Override
	public Interpreter next() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author William Anderson de Brito Gomes.
	 */
	@Override
	public Interpreter close() {
		// TODO Auto-generated method stub
		return null;
	}




}
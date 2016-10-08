package arquivos;
import operadores.Interpreter;
import operadores.Tupla;
import operadores.ColunaTupla;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * Classe responsável pela manipulação dos arquivos
 * <p> enviados pelo professor.<p>
 * Responsável pela criação de tuplas
 * @author Hosana Gomes Pinto.
 * @see Arquivo
 */

/**/

public class ArquivoDado extends Arquivo implements Interpreter
{
	
	private ArquivoTxt arquivoTxt;

	private DataInputStream filtroLeitura;
	private DataOutputStream filtroEscrita;

	private boolean estahAberto = false;

	private int numLinhas; 
	private int numColunas; 
	private String [] tipoDado;
	private String [] nomeCampo;

	private LinkedList<ColunaTupla> colunas;
	protected long posAtual = 0;

	private long posicaoComecoColunaTupla;
	private long posicaoInicial = 0; //início dos registros sem o cabeçalho


	private String pseudonimo; //nome que o usuario identificara o arquivo dentro do shell



	BufferedWriter buffWrite;// = new BufferedWriter(new FileWriter("resultado.txt"));
	private int opcao = 0;

	public ArquivoDado(String nomeArquivoTxt, String pseudonimo, int opcao) 
	{
		super(nomeArquivoTxt, opcao);
		arquivoTxt = new ArquivoTxt(nomeArquivoTxt, opcao);
		
	//	arquivoTxt.setArquivoDado(this);
		this.pseudonimo = pseudonimo;
		this.opcao = opcao;
		this.open();

		colunas = new LinkedList<ColunaTupla>();
		
		
		try 
		{
			gerarArquivoData();
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	private void gerarArquivoData() throws IOException
	{

		long posicao = arquivoTxt.lerCabecalhoTxt();
	
		arquivoTxt.lerLinhaArquivoRetornaStrings(posicao);
	
		arquivoTxt.escreverCabecalhoNoArquivo(this);
		arquivoTxt.setPosicaoComecoColunaTupla(posicao);

		while(arquivoTxt.getPosicaoComecoColunaTupla() < arquivoTxt.getArquivoEntradaStreamTxt().getChannel().size())
		{
			String [] coluna = arquivoTxt.lerLinhaArquivoRetornaStrings(arquivoTxt.getPosicaoComecoColunaTupla());	

			arquivoTxt.escreverConteudoNoArquivo(this,coluna);

			arquivoTxt.setPosicaoComecoColunaTupla(arquivoTxt.arquivoEntradaStreamTxt.getChannel().position());
			//	System.out.println("h");

		}
	
		this.getArquivoSaidaStream().close();
		
		setArquivo(new File(getNomeArquivo()));
		//abrir o arquivo como leitura
		opcao = 1;
		this.open();
		
		

		//return (new ArquivoDado(arquivoTxt.getNomeArquivo(),pseudonimo,1));

	}



	public long lerCabecalhoDoArquivo() throws IOException
	{
		posicaoComecoColunaTupla = 0;
		setNumLinhas(filtroLeitura.readInt());
		setNumColunas(filtroLeitura.readInt());

		tipoDado = new String[getNumColunas()];
		nomeCampo = new String[getNumColunas()];

		/*System.out.println("nl " + getNumLinhas());
		System.out.println("nc "  + getNumColunas());*/

		for(int i = 0; i < getNumColunas(); i++)
		{
			int tam = filtroLeitura.readInt();
			byte [] b = new byte[tam];
			filtroLeitura.read(b);
			tipoDado[i] = new String(b);



			tam = filtroLeitura.readInt();	
			b = new byte[tam];
			filtroLeitura.read(b);
			nomeCampo[i] = new String(b);

			//	System.out.print(tipoDado[i] + "");
		}


		//System.out.println(tipoDado[0] + tipoDado[1]);
		posicaoComecoColunaTupla = getArquivoEntradaStream().getChannel().position();
		posicaoInicial = getArquivoEntradaStream().getChannel().position();

		return posicaoComecoColunaTupla;

	}

	private String lerCampoDoArquivo(long posicao, int i) throws IOException
	{
		//System.out.println();
		posicaoComecoColunaTupla = posicao;//arquivo.getChannel().position();
		getArquivoEntradaStream().getChannel().position(posicaoComecoColunaTupla);

		String registro = new String();


		ByteBuffer b = null;
		switch(tipoDado[i])
		{
		case "(char)":
			registro = String.valueOf(filtroLeitura.readChar());
			break;

		case "(String)":
			int tam = filtroLeitura.readInt();
			b = ByteBuffer.allocate(tam);

			filtroLeitura.read(b.array());
			String stringLido = new String(b.array());

			registro = stringLido;

			break;

		case "(int)":
			registro = String.valueOf(filtroLeitura.readInt());

			break;

		case "(float)":
			registro = String.valueOf(filtroLeitura.readFloat());

			break;

		case "(double)":
			registro = String.valueOf(filtroLeitura.readDouble());
			break;

		case "(short)":
			registro = String.valueOf(filtroLeitura.readShort());
			break;

		case "(long)":
			registro = String.valueOf(filtroLeitura.readLong());
			break;

		case "(boolean)":
			registro = String.valueOf(filtroLeitura.readBoolean());
			break;

		case "(byte)":
			registro = String.valueOf(filtroLeitura.readByte());
			break;


		default:
			System.out.println("Erro no reconhecimento de tipo");
			break;


		}

		posicaoComecoColunaTupla = getArquivoEntradaStream().getChannel().position();

		return registro;


	}

	/**
	 * Lê todos os campos de uma linha e retorna-os em forma de ColunaTupla.
	 * @param posicao no arquivo
	 * @return LinkedList de ColunaTupla
	 */
	public  LinkedList<ColunaTupla> lerLinhaArquivo(long posicao)
	{
		this.setPosicaoComecoColunaTupla(posicao);
		String [] campo = null;

		ColunaTupla colunaTupla;

		LinkedList<ColunaTupla> colunaTuplaLinkedList = new LinkedList<ColunaTupla>();

		campo = new String[getNumColunas()];

		for(int j = 0; j < getNumColunas(); j++)
		{
			long le = getPosicaoComecoColunaTupla();
			try 
			{
				campo[j] = lerCampoDoArquivo(le, j);
				colunaTupla = new ColunaTupla(nomeCampo[j], campo[j], pseudonimo, posicao);

				colunaTuplaLinkedList.add(colunaTupla);

				posicaoComecoColunaTupla = getArquivoEntradaStream().getChannel().position();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			

		}

		return colunaTuplaLinkedList;
	}

	/**
	 *  Cria uma tupla e retorna a tupla criada.
	 * @param c que é LinkedList de ColunaTupla
	 * @return Tupla
	 * @see Tupla
	 * @see ColunaTupla
	 */
	public Tupla criarTupla(LinkedList<ColunaTupla> c) 
	{
		Tupla t = new Tupla();

		t.associarMuitasColunaTupla(c);


		return t;

	}



	public int getNumLinhas() 
	{
		return numLinhas;
	}



	public void setNumLinhas(int numLinhas) 
	{
		this.numLinhas = numLinhas;
	}



	public int getNumColunas() 
	{
		return numColunas;
	}



	public void setNumColunas(int numColunas) 
	{
		this.numColunas = numColunas;
	}



	public long getPosicaoComecoColunaTupla() 
	{
		return posicaoComecoColunaTupla;
	}

	public void setPosicaoComecoColunaTupla(long posicaoComecoColunaTupla) 
	{
		this.posicaoComecoColunaTupla = posicaoComecoColunaTupla;
	}



	public DataInputStream getFiltroLeitura() 
	{
		return filtroLeitura;
	}

	public void setFiltroEscrita(DataInputStream filtro) 
	{
		this.filtroLeitura = filtro;

	}

	public LinkedList<ColunaTupla> getColunas() 
	{
		return colunas;
	}

	public void setColunas(LinkedList<ColunaTupla> colunas) 
	{
		this.colunas = colunas;
	}


	public String[] getTipoDado() 
	{
		return tipoDado;
	}

	public String[] getNomeCampo() 
	{
		return nomeCampo;
	}

	@Override
	public Interpreter open() 
	{
		if(!estahAberto)
		{
			if(opcao == 1) //leitura
			{
				try
				{
					estahAberto = true;
					setArquivoEntradaStream(new FileInputStream(getDiretorioPastaLocal() + pseudonimo +".data")); //mudar isso!! Só pus para não dar erro
					filtroLeitura =  new DataInputStream(getArquivoEntradaStream());
					this.posAtual = this.lerCabecalhoDoArquivo();
					
					//			buffWrite = new BufferedWriter(new FileWriter("resultado2.txt"));
					
				}
	
				catch(IOException e)
				{
					System.out.println("Arquivo não encontrado");
				}
			}
	
			else //escrita
			{
				try
				{
					setArquivoSaidaStream(new FileOutputStream(getDiretorioPastaLocal() + pseudonimo +".data"));//mudar isso!! Só pus para não dar erro
					filtroEscrita =  new DataOutputStream(getArquivoSaidaStream());
	
					//	buffWrite = new BufferedWriter(new FileWriter("resultado2.txt"));
	
				}
	
				catch(IOException e)
				{
					System.out.println("Arquivo não encontrado");
				}
			}
		}
		else
		{
			
			try {
				this.getArquivoEntradaStream().getChannel().position(posicaoInicial);
				posAtual = posicaoInicial;
				posicaoComecoColunaTupla = posAtual;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return this;
	}

/*	@Override
	public Interpreter next()
	{
		// TODO Auto-generated method stub
		//Auxi++;
		try
		{
			if(posAtual >= this.getArquivoEntradaStream().getChannel().size())
			{
				this.posAtual = posicaoInicial;
				this.posicaoComecoColunaTupla = posicaoInicial;
				this.getArquivoEntradaStream().getChannel().position(posicaoInicial);
		//		System.out.println("terminou do next autor após  execuções");
				return null;
			}
			Tupla t = new Tupla();
			t.associarMuitasColunaTupla(this.lerLinhaArquivo(posAtual));
			this.posAtual = this.getArquivoEntradaStream().getChannel().position();
			return t;

		}
		catch (IOException e) 
		{
			System.out.println("Problema ao obter o tamanho do arquivo");
			return null;
		}
	}*/
	
	@Override
	public Interpreter next()
	{
		// TODO Auto-generated method stub
		//Auxi++;
		try
		{
			if(posAtual >= this.getArquivoEntradaStream().getChannel().size())
			{
				//System.out.println("terminou do next autor após " + Auxi + " execuções");
				return null;
			}
			Tupla t = new Tupla();
			t.associarMuitasColunaTupla(this.lerLinhaArquivo(posAtual));
			this.posAtual = this.getArquivoEntradaStream().getChannel().position();
			return t;

		}
		catch (IOException e) 
		{
			System.out.println("Problema ao obter o tanaho do arquivo");
			return null;
		}
	}

	@Override
	public Interpreter close() 
	{
		// TODO Auto-generated method stub
		try {
			this.getArquivoEntradaStream().close();
			posAtual = 0;
			posicaoComecoColunaTupla = 0;
			estahAberto = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}

	public DataOutputStream getFiltroEscrita() {
		return filtroEscrita;
	}

	public void setFiltroEscrita(DataOutputStream filtroEscrita) {
		this.filtroEscrita = filtroEscrita;
	}



}
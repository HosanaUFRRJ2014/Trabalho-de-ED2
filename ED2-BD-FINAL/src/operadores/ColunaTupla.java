package operadores;

/**
 * Armazena os dados de um campo de uma linha de uma relacao.
 * Utilizada pela Classe Tupla
 * 
 * @author William Anderson de B. Gomes
 * @see Tupla
 * @see Interpreter
 */

public class ColunaTupla implements Interpreter
{

	private String nome;
	private String valor;
	
	private String arquivoOrigem;
	private long enderecoTuplaOrigem;
	
	public ColunaTupla(String nome, String valor, String nomeArquivo, long endereco) 
	{
		super();
		this.nome = nome;
		this.valor = valor;
		this.arquivoOrigem = nomeArquivo;
		this.enderecoTuplaOrigem = endereco;
	}
	
	public ColunaTupla copiarColunaTupla()
	{
		return new ColunaTupla(this.nome, this.valor, this.arquivoOrigem, this.enderecoTuplaOrigem);
	}
	public String getValor()
	{
		return this.valor;
	}
	
	public void setValor(String valor)
	{
		this.valor = valor;
	}
	
	public String getArquivoOrigem() 
	{
		return arquivoOrigem;
	}
	
	public long getEnderecoTuplaOrigem() 
	{
		return enderecoTuplaOrigem;
	}
	
/*	public void renomearColunaJuncao()
	{
		nomeComposto = nome + arquivoOrigem;
	}
	
	public String getNomeComposto() 
	{
		return nomeComposto;
	}*/
	
	public String getNome()
	{
		return this.nome;
	}
	
	public void setNome(String nome)
	{
	    this.nome = nome;
	}

	@Override
	public Interpreter open() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Interpreter next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Interpreter close() {
		// TODO Auto-generated method stub
		return null;
	}

}

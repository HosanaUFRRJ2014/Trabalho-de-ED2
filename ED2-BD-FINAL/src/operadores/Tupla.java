package operadores;

import java.util.LinkedList;

public class Tupla implements Interpreter, Comparable<Tupla>
{
	/**
	 * Classe responsavel por representar uma linha de uma relacao, contendo seus campos.
	 * E utilizada por operadores da logica relacional e possui funcoes uteis para suas operacoes
	 * 
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Conteiner
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private LinkedList<ColunaTupla> colunas;
	private int colunaAtual;
	private String campoOrdenacao = "";

	public Tupla()
	{
		super();
		colunaAtual = 0;
		colunas = new LinkedList<ColunaTupla>();
	}
	
	//cria uma nova colunaTupla, e adiciona essa colunaTupla criada à tupla
	
	public void imprimirNomeColunasTupla()
	{
		int auxColunaAtual = colunaAtual;
		colunaAtual = 0;
		ColunaTupla c;
		do
		{
			c = (ColunaTupla) this.next();
			if(c==null)
				break;
			System.out.print(c.getNome() + "	");
		}
		while(colunaAtual != 0);
		System.out.print('\n');
		colunaAtual = auxColunaAtual;
	}
	
	public void imprimirValoresColunasTupla()
	{
		int auxColunaAtual = colunaAtual;
		colunaAtual = 0;
		ColunaTupla c;
		do
		{
			c = (ColunaTupla) this.next();
			if(c==null)
				break;
			System.out.flush();
			System.out.print(c.getValor() + " 	 ");
		}
		while(colunaAtual != 0);
		System.out.print('\n');
		colunaAtual = auxColunaAtual;
	}
	
	//***Opinião Hosana: Acho que tem que passar o endereço do arquivo por parâmetro. Como acessar esssa informações depois?
	public void addColunaTupla(String c, String valor) 
	{
		ColunaTupla colunaTupla = new ColunaTupla(c, valor, "", -1);
		colunas.add(colunaTupla);
	}
	
	//adiciona uma colunaTupla já existente a Tupla
	public void associarColunaTupla(ColunaTupla c)   
	{
		colunas.add(c);
	}
	
	//criado por Hosana. Vi a necessidade de adicionar tudo de vez
	public Tupla associarMuitasColunaTupla(LinkedList<ColunaTupla> c)
	{
		colunas.addAll(0, c);
		return this;
	}
	
	public ColunaTupla getColunaTupla(int index)
	{
		return colunas.get(index);
	}
	
	public Tupla copiaTupla()
	{
		Tupla t = new Tupla();
		for(int i = 0; i < colunas.size(); i++)
			t.associarColunaTupla(this.getColunaTupla(i).copiarColunaTupla());
		
		return t;
	}
	
	public Tupla somarTupla(Tupla t)
	{
		Tupla tr = this.copiaTupla();
		ColunaTupla ct = (ColunaTupla) t.open().next();
		while(ct != null)
		{
			tr.associarColunaTupla(ct.copiarColunaTupla());
			ct = (ColunaTupla) t.next();
		}
		t.close();
		return tr;
	}
	
	//testar
	public Tupla uniaoTupla(Tupla t)
	{
		Tupla tr = this.copiaTupla();
		ColunaTupla ct = (ColunaTupla) t.open().next();
		while(ct != null)
		{
			if(tr.retornaIndiceCampo(ct.getNome()) == -1)
				tr.associarColunaTupla(ct.copiarColunaTupla());
			
			ct = (ColunaTupla) t.next();
		}
		t.close();
		return tr;
	}
	
	public int getTamanhoTupla()
	{
		return colunas.size();
	}
	
	public int retornaIndiceCampo(String nomeCampo)
	{
		for(int i = 0; i < colunas.size(); i++)
		{
			if(nomeCampo.equals(colunas.get(i).getNome()))
				return i;
		}
		
		return -1;
	}
	
	public String getCampoOrdenacao() {
		return campoOrdenacao;
	}

	public void setCampoOrdenacao(String campoOrdenacao) {
		this.campoOrdenacao = campoOrdenacao;
	}
	//métodos referentes ao interpreter
	
	@Override
	public Interpreter open() 
	{
		colunaAtual = 0;
		return this;
	}

	@Override
	public Interpreter next() 
	{
		colunaAtual++;
		if(colunaAtual > colunas.size())
		{
			return null;
		}
		return (Interpreter) colunas.get(colunaAtual - 1);
		//return (Interpreter) colunas.get(colunaAtual - 1);
	}

	@Override
	public Interpreter close() 
	{
		
		colunaAtual = 0;
		return this;
	}
	
/*	public boolean temTodosOsTiposCamposIguais(Tupla t)
	{	
		if(t == null)
			return false;
		t.open();
		if(this.getTamanhoTupla() != t.getTamanhoTupla())
			return false;
		
		for(int i = 0;i<this.colunas.size();i++)
		{
			if(!this.colunas.get(i).getTipoDado().equals(t.getColunaTupla(i).getTipoDado()))
			{
				t.open();
				return false;
			}
		}
		//System.out.println();
		return true;
	}*/
	
	public boolean temValoresIguais(Tupla t)
	{
		if(this.getTamanhoTupla() != t.getTamanhoTupla())
			return false;
		
		for(int i = 0; i < colunas.size(); i++)
		{
			if(!this.getColunaTupla(i).getValor().equals(t.getColunaTupla(i).getValor()))
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tupla other = (Tupla) obj;
		if (colunas == null)
		{ 
			if (other.colunas != null)
				return false;
		}else if (!this.temValoresIguais(other))
			return false;

		return true;
	}

	@Override
	public int compareTo(Tupla arg0) 
	{
		if(campoOrdenacao.equals(""))
			campoOrdenacao = this.getColunaTupla(0).getNome();
		
		CondicaoMaiorIgual c = new CondicaoMaiorIgual(this.getColunaTupla(this.retornaIndiceCampo(campoOrdenacao)).getNome(), this.getColunaTupla(this.retornaIndiceCampo(campoOrdenacao)).getValor());
		
		if(!c.avaliar(arg0))
		{
			if(this.getColunaTupla(this.retornaIndiceCampo(campoOrdenacao)).getValor().equals(
					arg0.getColunaTupla(arg0.retornaIndiceCampo(campoOrdenacao)).getValor()))
				return 0;
			else
				return 1;
		}
		else
		{
			return -1;
		}

	}
}

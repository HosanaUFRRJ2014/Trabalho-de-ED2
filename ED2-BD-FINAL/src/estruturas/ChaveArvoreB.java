package estruturas;
import java.io.Serializable;
import java.util.*;

/**
 * Implementa��o da ChaveArvoreB para �rvoreB.
 * 
 * 
 * @author L�via de Azevedo da Silva
 * @see ArvoreB
 * 
 */

public class ChaveArvoreB implements Serializable
{
	private int chaveInt;
	private short chaveShort;
	private long chaveLong;
	private char chaveChar;
	private String chaveString;
	private float chaveFloat;
	private double chaveDouble;
	
	private boolean ehInt;
	private boolean ehShort;
	private boolean ehLong;
	private boolean ehChar;
	private boolean ehString;
	private boolean ehFloat;
	private boolean ehDouble;
	
	//Endere�os que aponta para a posi��o exata do arquivo em que est�o as chaves(caso que possuem nomes iguais, o que � poss�vel e pedido).
	private LinkedList<Long> enderecosLinhaArquivo;
	
	public ChaveArvoreB(int chave)
	{
		this.chaveInt = chave;
		this.ehInt = true;
		this.ehShort = false;
		this.ehLong = false;
		this.ehChar = false;
		this.ehString = false;
		this.ehFloat = false;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
	}
	
	public ChaveArvoreB(short chave)
	{
		this.chaveShort = chave;
		this.ehInt = false;
		this.ehShort = true;
		this.ehLong = false;
		this.ehChar = false;
		this.ehString = false;
		this.ehFloat = false;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
	}
	
	public ChaveArvoreB(long chave)
	{
		this.chaveLong = chave;
		this.ehInt = false;
		this.ehShort = false;
		this.ehLong = true;
		this.ehChar = false;
		this.ehString = false;
		this.ehFloat = false;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
	}
	
	public ChaveArvoreB(char chave)
	{
		this.chaveChar = chave;
		this.ehInt = false;
		this.ehShort = false;
		this.ehLong = false;
		this.ehChar = true;
		this.ehString = false;
		this.ehFloat = false;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
	}
	
	public ChaveArvoreB(String chave)
	{
		this.chaveString = chave;
		this.ehInt = false;
		this.ehShort = false;
		this.ehLong = false;
		this.ehChar = false;
		this.ehString = true;
		this.ehFloat = false;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
	}
	
	public ChaveArvoreB(float chave)
	{
		this.chaveFloat = chave;
		this.ehInt = false;
		this.ehShort = false;
		this.ehLong = false;
		this.ehChar = false;
		this.ehString = false;
		this.ehFloat = true;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
	}
	
	public ChaveArvoreB(double chave)
	{
		this.chaveDouble = chave;
		this.ehInt = false;
		this.ehShort = false;
		this.ehLong = false;
		this.ehChar = false;
		this.ehString = false;
		this.ehFloat = false;
		this.ehDouble = true;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
	}
	
	
	public ChaveArvoreB(int chave, long endereco)
	{
		this.chaveInt = chave;
		this.ehInt = true;
		this.ehShort = false;
		this.ehLong = false;
		this.ehChar = false;
		this.ehString = false;
		this.ehFloat = false;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
		
		Long end = new Long(endereco);
		this.enderecosLinhaArquivo.add(end);
	}
	
	public ChaveArvoreB(short chave, long endereco)
	{
		this.chaveShort = chave;
		this.ehInt = false;
		this.ehShort = true;
		this.ehLong = false;
		this.ehChar = false;
		this.ehString = false;
		this.ehFloat = false;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
		
		Long end = new Long(endereco);
		this.enderecosLinhaArquivo.add(end);
	}
	
	public ChaveArvoreB(long chave, long endereco)
	{
		this.chaveLong = chave;
		this.ehInt = false;
		this.ehShort = false;
		this.ehLong = true;
		this.ehChar = false;
		this.ehString = false;
		this.ehFloat = false;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
		
		Long end = new Long(endereco);
		this.enderecosLinhaArquivo.add(end);
	}
	
	public ChaveArvoreB(char chave, long endereco)
	{
		this.chaveChar = chave;
		this.ehInt = false;
		this.ehShort = false;
		this.ehLong = false;
		this.ehChar = true;
		this.ehString = false;
		this.ehFloat = false;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
		
		Long end = new Long(endereco);
		this.enderecosLinhaArquivo.add(end);
	}
	
	public ChaveArvoreB(String chave, long endereco)
	{
		this.chaveString = chave;
		this.ehInt = false;
		this.ehShort = false;
		this.ehLong = false;
		this.ehChar = false;
		this.ehString = true;
		this.ehFloat = false;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
		
		Long end = new Long(endereco);
		this.enderecosLinhaArquivo.add(end);
	}
	
	public ChaveArvoreB(float chave, long endereco)
	{
		this.chaveFloat = chave;
		this.ehInt = false;
		this.ehShort = false;
		this.ehLong = false;
		this.ehChar = false;
		this.ehString = false;
		this.ehFloat = true;
		this.ehDouble = false;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
		
		Long end = new Long(endereco);
		this.enderecosLinhaArquivo.add(end);
	}
	
	public ChaveArvoreB(double chave, long endereco)
	{
		this.chaveDouble = chave;
		this.ehInt = false;
		this.ehShort = false;
		this.ehLong = false;
		this.ehChar = false;
		this.ehString = false;
		this.ehFloat = false;
		this.ehDouble = true;
		this.enderecosLinhaArquivo = new LinkedList<Long>();
		
		Long end = new Long(endereco);
		this.enderecosLinhaArquivo.add(end);
	}

	public int getChaveInt() {
		return chaveInt;
	}

	public void setChaveInt(int chaveInt) {
		this.chaveInt = chaveInt;
	}
	
	public short getChaveShort() {
		return chaveShort;
	}

	public void setChaveShort(short chaveShort) {
		this.chaveShort = chaveShort;
	}

	public long getChaveLong() {
		return chaveLong;
	}

	public void setChaveLong(long chaveLong) {
		this.chaveLong = chaveLong;
	}

	public char getChaveChar() {
		return chaveChar;
	}

	public void setChaveChar(char chaveChar) {
		this.chaveChar = chaveChar;
	}

	public String getChaveString() {
		return chaveString;
	}

	public void setChaveString(String chaveString) {
		this.chaveString = chaveString;
	}

	public float getChaveFloat() {
		return chaveFloat;
	}

	public void setChaveFloat(float chaveFloat) {
		this.chaveFloat = chaveFloat;
	}

	public double getChaveDouble() {
		return chaveDouble;
	}

	public void setChaveDouble(double chaveDouble) {
		this.chaveDouble = chaveDouble;
	}
		
	public LinkedList<Long> getEnderecosLinhaArquivo() {
		return enderecosLinhaArquivo;
	}	
	
	public void adicionarEnderecoNaLista(long endereco)
	{
		Long newEnd = new Long(endereco);
		this.getEnderecosLinhaArquivo().add(newEnd);
	}

	public boolean isEhInt() {
		return ehInt;
	}

	public void setEhInt(boolean ehInt) {
		this.ehInt = ehInt;
	}

	public boolean isEhShort() {
		return ehShort;
	}

	public void setEhShort(boolean ehShort) {
		this.ehShort = ehShort;
	}

	public boolean isEhLong() {
		return ehLong;
	}

	public void setEhLong(boolean ehLong) {
		this.ehLong = ehLong;
	}

	public boolean isEhChar() {
		return ehChar;
	}

	public void setEhChar(boolean ehChar) {
		this.ehChar = ehChar;
	}

	public boolean isEhString() {
		return ehString;
	}

	public void setEhString(boolean ehString) {
		this.ehString = ehString;
	}

	public boolean isEhFloat() {
		return ehFloat;
	}

	public void setEhFloat(boolean ehFloat) {
		this.ehFloat = ehFloat;
	}

	public boolean isEhDouble() {
		return ehDouble;
	}

	public void setEhDouble(boolean ehDouble) {
		this.ehDouble = ehDouble;
	}
	
	public int comparar(ChaveArvoreB e)
	{
		if(this.isEhInt())
		{
			if(this.getChaveInt() > e.getChaveInt())
				return 1;
			else if(this.getChaveInt() == e.getChaveInt())
				return 0;
			else
				return -1;
		}
		
		if(this.isEhShort())
		{
			if(this.getChaveShort() > e.getChaveShort())
				return 1;
			else if(this.getChaveShort() == e.getChaveShort())
				return 0;
			else
				return -1;
		}
		
		if(this.isEhLong())
		{
			if(this.getChaveLong() > e.getChaveLong())
				return 1;
			else if(this.getChaveLong() == e.getChaveLong())
				return 0;
			else
				return -1;
		}
		
		if(this.isEhChar())
		{
			if(this.getChaveChar() > e.getChaveChar())
				return 1;
			else if(this.getChaveChar() == e.getChaveChar())
				return 0;
			else
				return -1;
		}
		
		if(this.isEhString())
		{
			/*Casos, considerando a ordem lexicogr�fica(ordem alfab�tica):
			 *
			 * -Retorno < 0 : e1 vem antes de e;
			 * -Retorno == 0: e1 � igual a e;
			 * -Retorno > 0: e1 vem depois de e;
			 * 
			 * */
			
			Integer c1 = null;
			Integer c2 = null;
			
			/*Se os dois argumentos forem n�meros, considerar a ordem de como fossem vari�veis inteiras.*/
			try
			{
				c1 = Integer.parseInt(this.getChaveString());
				c2 = Integer.parseInt(e.getChaveString());
				
				if(c1.longValue() > c2.longValue())
					return 1;
				else if(c1.longValue() == c2.longValue())
					return 0;
				else
					return -1;
			}
			catch(Exception e1) /*Um deles ou os dois n�o s�o n�meros. Faz a compara��o normal de strings*/
			{
				return this.getChaveString().compareToIgnoreCase(e.getChaveString());
			}
		}
		
		if(this.isEhFloat())
		{
			return Float.compare(this.getChaveFloat(),e.getChaveFloat());
		}
		
		if(this.isEhDouble())
		{
			return Double.compare(this.getChaveDouble(),e.getChaveDouble());
		}
		
		//Se der algo errado, ir� retornar o que estiver aqui.
		System.out.println("N�o deveria retornar este valor!");
		return -1;
		
	}
	
	public void imprimirChave()
	{
		if(this.isEhInt())
		{
			System.out.print(this.getChaveInt() + "  ");
		}
		
		if(this.isEhShort())
		{
			System.out.print(this.getChaveShort() + "  ");
		}
		
		if(this.isEhLong())
		{
			System.out.print(this.getChaveLong() + "  ");
		}
		
		if(this.isEhChar())
		{
			System.out.print(this.getChaveChar() + "  ");
		}
		
		if(this.isEhString())
		{
			System.out.print(this.getChaveString() + " | ");
		}
		
		if(this.isEhFloat())
		{
			System.out.print(this.getChaveFloat() + "  ");
		}
		
		if(this.isEhDouble())
		{
			System.out.print(this.getChaveDouble() + "  ");
		}
	}
	
	public void imprimirEnderecos()
	{
		System.out.println("Endere�os:");
		
		for(Long l: this.enderecosLinhaArquivo)
		{
			System.out.print(l.longValue() + "  ");
		}
		
		System.out.println();
	}
}



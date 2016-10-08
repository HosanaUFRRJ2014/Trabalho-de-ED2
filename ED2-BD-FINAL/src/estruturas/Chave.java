package estruturas;

import java.io.Serializable;

/**
 * 
 * @author Hosana Gomes Pinto
 *
 */

public class Chave implements Serializable
{
	private String string;
	private long posicao; 
	
	/**
	 * 
	 * @param string sobre a qual será contruída uma chave.
	 */
	
	public Chave(String string)
	{
		this.string = string;
		posicao = -1;
	}
	
	
	
	/*Métodos gerados automaticamente pelo Eclipse. Se estiver ruim, não fui eu!!*/

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((string == null) ? 0 : string.hashCode());
		
		//retornar só número positivo
		return Math.abs(result);
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Chave other = (Chave) obj;
		
		if (string == null) 
		{
			if (other.string != null)
				return false;
		} 
		
		else if (!string.equals(other.string))
			return false;
		
		return true;
	}

	public String getString() 
	{
		return string;
	}

	public void setString(String string) 
	{
		this.string = string;
	}

	/**
	 * 
	 * @return posicao da string contida na Chave no arquivo .data lido.
	 */
	public long getPosicao() 
	{
		return  posicao;
	}
	
	/**
	 * 
	 * @param posicao da string contida na Chave no arquivo .data lido.
	 */

	public void setPosicao(long posicao) 
	{
		this.posicao = posicao;
	}
	

	
	
	

	
	
	

}

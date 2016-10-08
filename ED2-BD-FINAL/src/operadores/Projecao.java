package operadores;

import java.util.Collections;
import java.util.LinkedList;

public class Projecao implements Interpreter 
{
	/**
	 * Classe responsavel por implementar o operador Projecao da algebra relacional.
	 * Dado os nomes de campo existentes na Tupla de uma relacao permite criar uma 
	 * uma nova Tupla que somente possua os campos especificados.
	 * 
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private LinkedList<String> campos;
	private Interpreter relacao;
	private int qtdCampos;
	private LinkedList<Integer> indiceCamposBuscado;
	private Tupla t = null;
	private Conteiner c;
	private boolean jahRodouTudo = false;
	
	public Projecao(String[] campos, Interpreter relacao) {
		super();
		
		this.campos = new LinkedList<String>();
		qtdCampos = campos.length;
		for(int i = 0;i < qtdCampos;i++)
			this.campos.add(campos[i]);

		indiceCamposBuscado = new LinkedList<Integer>();
		for(int i = 0; i< qtdCampos;i++)
			indiceCamposBuscado.add(-1);
		
		c = new Conteiner();
		
		this.relacao = relacao;
	}
	@Override
	public Interpreter open() 
	{
		if(jahRodouTudo)
		{
			c.open();
		}
		else
		{
			c.removerTuplas();
			c.open();
			t = (Tupla) relacao.open().next();
			for(int i = 0; i < this.qtdCampos;i++) // para cada campo da projecao procura seu equivalente na tupla
			{
				if(indiceCamposBuscado.get(i) == -1)
					indiceCamposBuscado.set(i, t.retornaIndiceCampo(campos.get(i)));
			}
			relacao.open();
		}
		
		return this;
		// TODO Auto-generated method stub
	}

	@Override
	public Interpreter next() 
	{	
		Tupla t;
		if(jahRodouTudo)
		{
			return c.next();
		}
		else
		{
			t = (Tupla) relacao.next();
			if(t != null)
			{
				Tupla tFinal = new Tupla();
				t.open(); // volta o indicador para a primeira colunaTupla
				
				for(int i = 0; i < this.qtdCampos;i++) // para cada campo da projecao procura seu equivalente na tupla
				{
					if(indiceCamposBuscado.get(i) != -1)
					{
						tFinal.associarColunaTupla(t.getColunaTupla(indiceCamposBuscado.get(i)).copiarColunaTupla());
					}
					/*if((relacao instanceof HashJoin) || (relacao instanceof NLJ))
					{
						if(c.getNomeComposto().startsWith(campos[i]))
						{
							temCampoBuscado[i] = true;
							tFinal.associarColunaTupla(c);
							break;
						}
					}	
					else
					{
					
						if(c.getNome().equals(campos[i]))
						{
							temCampoBuscado[i] = true;
							tFinal.associarColunaTupla(c);
							break;
						} // qnd retorna null zera o indicador de colunaTupla
					}
						c = (ColunaTupla) tOriginal.next();*/
					
				}
				
				t.close(); // volta o indicador para a primeira colunaTupla
				c.associarTupla(tFinal);
				return tFinal;
			}
			relacao.open();
			jahRodouTudo = true;
			c.close();
		}
				
		return null;
	

		
		// TODO Auto-generated method stub
	}

	@Override
	public Interpreter close()
	{
		relacao.close();
		c.close();
		return this;
		// TODO Auto-generated method stub
	}

}

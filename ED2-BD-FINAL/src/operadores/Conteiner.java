package operadores;

import java.util.Collections;
import java.util.LinkedList;

public class Conteiner implements Interpreter
{
	/**
	 * Classe responsavel por implementar um armazenador temporário de Tuplas.
	 * Simula o camportamento de uma relacao ou operador Interpreter
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	private LinkedList<Tupla> tuplas;
	private int indice;
	
	public Conteiner() 
	{
		super();
		tuplas = new LinkedList<Tupla>();
		indice = 0;
	}
	//bubble sort comentado para ajudar no desenvolvimento de um algoritmo mais rapido, porem usa Collections.sort
	public void ordenar(int ordem, String campoOrdenacao, int indiceCampo)
	{
		for(int i = 0; i<tuplas.size(); i++)
			tuplas.get(i).setCampoOrdenacao(campoOrdenacao);
		
		Collections.sort(tuplas);
		if(ordem < 0)
			Collections.reverse(tuplas);
		/*Tupla t = tuplas.getFirst();
		if(t != null)
		{
			int indCampoOrden = indiceCampo;
			if(indCampoOrden == -1)
				indCampoOrden = t.retornaIndiceCampo(campoOrdenacao);
			
			if(indCampoOrden != -1)
			{
				for(int i = 0; i < tuplas.size(); i++)
				{
					for(int j = i+1; j < tuplas.size(); j++)
					{
						if(ordem >= 0)
						{
							if(tuplas.get(i).getColunaTupla(indCampoOrden).getValor().compareToIgnoreCase(
								tuplas.get(j).getColunaTupla(indCampoOrden).getValor()) > 0)
							{
								t = tuplas.get(i);
								tuplas.set(i, tuplas.get(j));
								tuplas.set(j, t);
							}
						}
						else
						{
							if(tuplas.get(i).getColunaTupla(indCampoOrden).getValor().compareToIgnoreCase(
									tuplas.get(j).getColunaTupla(indCampoOrden).getValor()) < 0)
								{
									t = tuplas.get(i);
									tuplas.set(i, tuplas.get(j));
									tuplas.set(j, t);
								}
						}
					}
				}
			}
		}*/
	}

	@Override
	public Interpreter open() 
	{
		// TODO Auto-generated method stub
		indice = 0;
		return this;
	}

	@Override
	public Interpreter next() 
	{
		if(indice < tuplas.size())
		{
			indice++;
			return tuplas.get(indice-1);
		}
		else
		{
			return null;
		}
		// TODO Auto-generated method stub
	}

	@Override
	public Interpreter close() 
	{
		// TODO Auto-generated method stub
		indice = tuplas.size();
		return this;
	}
	
	public Tupla getTupla(int indice)
	{
		if(indice >= tuplas.size() || indice < 0)
			return null;
		else
			return tuplas.get(indice);
	}
	
	public void associarTupla(Tupla t)
	{
		tuplas.add(t);
	}
	
	//criado por Hosana. Vi a necessidade de adicionar tudo de vez
	public void associarTuplas(LinkedList<Tupla> t)
	{
		tuplas.addAll(tuplas.size(), t);
	}
	
	public void removerTupla(Tupla t)
	{
		if(indice == tuplas.size() -1)
			indice--;
		
		tuplas.remove(t);
	}
	
	public void removerTuplas()
	{
		tuplas.removeAll(tuplas);
		indice = 0;
	}

}

package ga.grafos;

import java.util.ArrayList;
import java.util.List;

public class Camino implements Cloneable {

	private ArrayList<Integer> nodos;

	private Integer distancia;

	private Boolean caminoTerminado;
	
	private Boolean caminoValido;

	public ArrayList<Integer> getNodos() {
		return nodos;
	}

	public void setNodos(ArrayList<Integer> nodos) {
		this.nodos = nodos;
	}

	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}

	public Boolean getCaminoTerminado() {
		return caminoTerminado;
	}

	public void setCaminoTerminado(Boolean caminoTerminado) {
		this.caminoTerminado = caminoTerminado;
	}

	public Boolean getCaminoValido() {
		return caminoValido;
	}

	public void setCaminoValido(Boolean caminoValido) {
		this.caminoValido = caminoValido;
	}

	public Camino() {
		this.caminoTerminado = false;
		this.caminoValido = false;
		this.distancia = 0;
		this.nodos = null;
	}

	@Override
	protected Object clone() {
		Camino obj = null;
		try {
			obj = (Camino) super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println(" no se puede duplicar");
		}
		obj.distancia = obj.distancia.intValue();
		obj.caminoTerminado = obj.caminoTerminado.booleanValue();
		List<Integer> nodos = new ArrayList<Integer>();
		for (Integer nodo : obj.nodos) {
			nodos.add(nodo.intValue());
		}
		obj.nodos = (ArrayList<Integer>) nodos;
		return obj;
	}

}

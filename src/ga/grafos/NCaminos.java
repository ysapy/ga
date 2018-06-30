package ga.grafos;

import java.util.ArrayList;
import java.util.List;

public class NCaminos {

	public static List<Camino> encontrarCaminos(List<List<Boolean>> matriz, Integer origen, Integer destino, Integer k) {
		List<Camino> caminos = null;
		List<Camino> caminosValidos = new ArrayList<Camino>();
		Boolean terminar = false;
		while (!terminar) {
			caminos = procesarCaminos(matriz, origen, destino, caminos);
			Boolean procesamientoTerminado = true;
			for (Camino camino : caminos) {
				procesamientoTerminado = procesamientoTerminado && camino.getCaminoTerminado();
			}
			if (procesamientoTerminado) {
				terminar = true;
			}
		}
		
		for (Camino camino : caminos){
			if(camino.getCaminoValido()){
				caminosValidos.add(camino);
				if (caminosValidos.size() == 3)
					return caminosValidos;
			}
		}
		
		return caminosValidos;
	}

	public static List<Camino> procesarCaminos(List<List<Boolean>> matriz, Integer origen, Integer destino,
			List<Camino> caminosExistentes) {
		List<Camino> caminos = new ArrayList<Camino>();
		if (caminosExistentes == null) {
			for (int i = 0; i < matriz.get(origen).size(); i++) {
				if (matriz.get(origen).get(i)) {
					Camino camino = setearCamino(i, null);
					camino.getNodos().add(0, origen.intValue());
					caminos.add((Camino) camino.clone());
				}
			}
		} else {
			for (Camino caminoE : caminosExistentes) {
				Integer ultimo = caminoE.getNodos().size();
				Integer nuevoOrigen = caminoE.getNodos().get(ultimo - 1);
				if (nuevoOrigen != destino && !caminoE.getCaminoTerminado()) {
					for (int i = 0; i < matriz.get(nuevoOrigen).size(); i++) {
						if (matriz.get(nuevoOrigen).get(i)) {
							Camino camino = setearCamino(i, caminoE);
							caminos.add((Camino) camino.clone());
						}
					}
				} else {
					if(nuevoOrigen == destino){
						caminoE.setCaminoValido(true);
					}
					caminoE.setCaminoTerminado(true);
					caminos.add((Camino) caminoE.clone());
				}
			}
		}
		return caminos;
	}

	public static Camino setearCamino(Integer destino, Camino ruta) {
		Camino camino;
		if (ruta == null) {
			camino = new Camino();
			camino.setNodos(new ArrayList<Integer>());
			camino.setDistancia(1);
		} else {
			camino = (Camino) ruta.clone();
			for(Integer nodo : ruta.getNodos()){
				if(nodo.equals(destino)){
					camino.setCaminoTerminado(true);
					
				}
			}
		}
		camino.getNodos().add(destino.intValue());
		camino.setDistancia(camino.getDistancia() + 1);
		return camino;
	}

}
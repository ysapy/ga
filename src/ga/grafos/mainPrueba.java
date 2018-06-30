package ga.grafos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mainPrueba {	
	
	private static String pathResources = "C:\\Users\\julio\\Desktop\\grafos\\";
	
	public static void main(String [] args) throws FileNotFoundException {
        List<List<Boolean>> topologia = leerTopologia();

        List<Camino> caminos = new ArrayList<Camino>();

        caminos.addAll(NCaminos.encontrarCaminos(topologia, 2, 5, 2));
        
        for (Camino camino : caminos){
        	for(Integer nodo : camino.getNodos()){
        		System.out.print(nodo + " ");
        	}
        	System.out.println();
        }
        
    }

	public static List<List<Boolean>> leerTopologia() throws FileNotFoundException {
        FileReader fr = new FileReader(pathResources + "topologia.txt");
        BufferedReader bf = new BufferedReader(fr);
        String fila;
        int i;

        List<List<Boolean>> topologia = new ArrayList<List<Boolean>>();
        List<Boolean> filaAux = new ArrayList<Boolean>();

        try {
            while ((fila = bf.readLine()) != null) {
                for (i = 0; i < fila.length(); i++) {
                    if (fila.charAt(i) == '1') {
                        filaAux.add(true);
                    } else {
                        filaAux.add(false);
                    }
                }
                topologia.add(filaAux);
                filaAux = new ArrayList<Boolean>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return topologia;
    }
}

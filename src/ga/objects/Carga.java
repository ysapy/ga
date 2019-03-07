package ga.objects;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ilp.Configuracion;
import ilp.Solicitud;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.*;


/**
 * Created by ysapymimbi on 20/12/2016.
 */
public class Carga {

    public static void cargarGA(String pathGeneral, Integer k, String pathActual) throws IOException {
        // Definimos e instanciamos el grafo con pesos
        SimpleDirectedGraph<String, DefaultWeightedEdge> directedGraph = new SimpleDirectedGraph(DefaultWeightedEdge.class);
        cargarGrafo(directedGraph, pathGeneral);
        leerSolicitudes(directedGraph, k, pathActual);
    }

    private static void cargarGrafo(SimpleDirectedGraph<String, DefaultWeightedEdge> directedGraph, String pathGeneral) {
        try {
            FileReader fr = new FileReader(pathGeneral + "/edges.txt");
            String[] edgeText;
            DefaultWeightedEdge ed;

            try (BufferedReader entrada = new BufferedReader(fr)) {
                String line;
                while ((line = entrada.readLine()) != null) {
                    edgeText = line.split("\t");
                    directedGraph.addVertex(edgeText[0]);
                    directedGraph.addVertex(edgeText[1]);
                    ed = directedGraph.addEdge(edgeText[0], edgeText[1]);
                    directedGraph.setEdgeWeight(ed, Double.parseDouble(edgeText[2]));
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado: " + ex);
        } catch (IOException ex) {
            System.out.println("Archivo no encontrado: " + ex);
        }
    }

    private static void calcularCaminosMasCortos(SimpleDirectedGraph<String, DefaultWeightedEdge> directedGraph, String origen, String destino, int k, double cantSolicitada, String pathActual) throws IOException {
        File f = new File(pathActual + "ga.txt");

        FileWriter fw3;
        if (origen.equals("0") && destino.equals("1")) {
            fw3 = new FileWriter(f);
        } else {
            fw3 = new FileWriter(f, true);
        }

        BufferedWriter bw3 = new BufferedWriter(fw3);
        try (PrintWriter salida4 = new PrintWriter(bw3)) {

            KShortestPaths caminosCandidatos = new KShortestPaths(directedGraph, origen, k);
            List<GraphPath<String, DefaultWeightedEdge>> paths = caminosCandidatos.getPaths(destino);

            for (GraphPath<String, DefaultWeightedEdge> path : paths) {
                int cantidadNodos = 0;
                double distanciaPath = 0;
                salida4.print(origen + destino + "\t");
                salida4.print(origen + "\t");
                salida4.print(destino + "\t");
                for (DefaultWeightedEdge edge : path.getEdgeList()) {
                    if (cantidadNodos == 0) {
                        salida4.print(directedGraph.getEdgeSource(edge) + "-" + directedGraph.getEdgeTarget(edge));
                    } else {
                        salida4.print("-" + directedGraph.getEdgeTarget(edge));
                    }
                    cantidadNodos++;
                    distanciaPath = distanciaPath + directedGraph.getEdgeWeight(edge);
                }

                salida4.print("\t" + (int)cantSolicitada);
                salida4.print("\t" + (int)distanciaPath);
                salida4.print("\t" + (int)(distanciaPath*cantSolicitada) + "\n");
            }
            salida4.close();
            bw3.close();
        } catch (IOException ex) {
            System.out.println("Archivo no encontrado: " + ex);
        }
    }

    private static void leerSolicitudes(SimpleDirectedGraph<String, DefaultWeightedEdge> directedGraph, int k, String pathActual) {
        ArrayList<Solicitud> listaSolicitudes = new ArrayList();
        try {
            FileReader fr = new FileReader(pathActual + "/solicitudes.txt");
            String[] edgeText;

            try (BufferedReader entrada = new BufferedReader(fr)) {
                String line;
                while ((line = entrada.readLine()) != null) {
                    edgeText = line.split("\t");
                    Solicitud sol = new Solicitud(edgeText[0], edgeText[1], Double.parseDouble(edgeText[2]));
                    listaSolicitudes.add(sol);
                }
            }

            Double ftotal = 0.0;
            int contadorEscritura = 0;
            for (Iterator<Solicitud> it = listaSolicitudes.iterator(); it.hasNext();) {
                Solicitud solicitud = it.next();

                calcularCaminosMasCortos(directedGraph, solicitud.getOrigen(), solicitud.getDestino(), k, solicitud.getVelocidad(), pathActual);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado: " + ex);
        } catch (IOException ex) {
            System.out.println("Archivo no encontrado: " + ex);
        }
    }
}

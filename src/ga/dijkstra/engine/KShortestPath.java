package ga.dijkstra.engine;

import ga.AGHelper;
import ga.dijkstra.model.Edge;
import ga.dijkstra.model.Vertex;
import ga.grafos.Camino;
import ga.grafos.NCaminos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysapy on 26/03/16.
 */
public class KShortestPath {

    private static String pathInicialArchivo = "C:\\Users\\ysapymimbi\\IdeaProjects\\tesis\\src\\ga\\archivos\\arpanet\\";

    public static void main(String [] args) throws IOException {
        List<List<Boolean>> topologia = AGHelper.leerTopologia(pathInicialArchivo);
        Integer i, j, m, n;
        int cantNodosTopologia = 14;
        String tipo = "alta";
        List<Integer> k = new ArrayList<>();
        k.add(1);
        k.add(2);
        k.add(3);
        k.add(4);
        k.add(5);
        k.add(6);
        List<Integer> demanda = new ArrayList<>();
        demanda.add(50);
        demanda.add(100);
        demanda.add(150);
        demanda.add(200);

        List<Camino> caminos = new ArrayList<Camino>();
        List<Camino> ordenados = new ArrayList<Camino>();

        try{
            for (int contK = 0; contK < k.size(); contK++) {
                String pathK = pathInicialArchivo + "k" + k.get(contK) + "\\";
                for (int contDem = 0; contDem < demanda.size(); contDem++) {
                    String pathDemand = pathK + "cantSolicitada" + demanda.get(contDem) + "\\entradas\\";
                    File f = new File(pathDemand + "demanda_info.txt");
                    caminos = new ArrayList<Camino>();
                    ordenados = new ArrayList<Camino>();
                    FileWriter fw = new FileWriter(f);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter wr = new PrintWriter(bw);

                    // para el ga

//                    for (i = 0; i < cantNodosTopologia; i++) {
//                        for (j = 0; j < cantNodosTopologia; j++) {
//                            if (i != j) {
//                                caminos.addAll(NCaminos.encontrarCaminos(topologia, i, j, k));
//                                ordenados.addAll(ordenarCaminos(caminos));
//
//                                for (m = 0; m < ordenados.size(); m++) {
//                                    wr.write(i.toString().concat(j.toString()) + " " + i + " " + j + " ");
//                                    for (n = 0; n < ordenados.get(m).getNodos().size(); n++) {
//                                        if (n == ordenados.get(m).getNodos().size() - 1) {
//                                            wr.write(ordenados.get(m).getNodos().get(n).toString());
//                                        } else {
//                                            wr.write(ordenados.get(m).getNodos().get(n) + "-");
//                                        }
//                                    }
//                                    wr.write(" " + demandaBaja + " " + (ordenados.get(m).getNodos().size() - 1) + " " + (demandaBaja * (ordenados.get(m).getNodos().size() - 1)));
//                                    wr.write("\n");
//                                }
//                                caminos = new ArrayList<Camino>();
//                                ordenados = new ArrayList<Camino>();
//                            }
//                        }
//                    }

                    // para el ilp

//            for (i = 0; i < 6; i++) {
//                for (j = 0; j < 6; j++) {
//                    if (i != j) {
//                        caminos.addAll(NCaminos.encontrarCaminos(topologia, i, j, k));
//                        ordenados.addAll(ordenarCaminos(caminos));
//
//                        for (m = 0; m < ordenados.size(); m++) {
//                            for (n = 0; n < ordenados.get(m).getNodos().size(); n++) {
//                                if (n  != ordenados.get(m).getNodos().size() - 1) {
//                                    wr.write("(" + ordenados.get(m).getNodos().get(n) + " : " + ordenados.get(m).getNodos().get(n + 1) + ") ");
//                                }
//                            }
//                            wr.write(":" + "\t" + (ordenados.get(m).getNodos().size()-1));
//                            wr.write("\n");
//                        }
//                        caminos = new ArrayList<Camino>();
//                        ordenados = new ArrayList<Camino>();
//                    }
//                }
//            }

                    wr.close();
                    bw.close();
                    fw.close();
                }
            }

        } catch (IOException e){

        }

//        File fs = new File(pathArchivo + "salto_" + tipo + ".txt");
//        FileWriter fws = new FileWriter(fs);
//
//        BufferedWriter bws = new BufferedWriter(fws);
//        PrintWriter wrs = new PrintWriter(bws);
//
//        for (i = 0; i < 6; i++) {
//            for (j = 0; j < 6; j++) {
//                if (i != j) {
//                    caminos.addAll(NCaminos.encontrarCaminos(topologia, i, j, k));
//                    ordenados.addAll(ordenarCaminos(caminos));
//
//                    for (m = 0; m < ordenados.size(); m++) {
//                        wrs.write(ordenados.get(m).getNodos().size() - 1);
//                    }
//                }
//            }
//        }
//        fws.close();
//        wrs.close();
//        bws.close();
    }


    public static void matrizAObjetos (List<List<Boolean>> topologia, List<Edge> aristas, List<Vertex> vertices) {
        Integer i, j;
        Edge arista;
        Vertex vertice;

        for (i = 0; i < topologia.get(0).size(); i++ ) {
            for (j = 0; j < topologia.get(0).size(); j++ ) {
                if (topologia.get(i).get(j)) {
                    arista = new Edge();
                    arista.setId(i.toString() + j.toString());

                    vertice = new Vertex();
                    vertice.setId(i.toString());
                    vertice.setName(i.toString());
                    arista.setSource(vertice);

                    vertice = new Vertex();
                    vertice.setId(j.toString());
                    vertice.setName(j.toString());
                    arista.setDestination(vertice);

                    arista.setWeight(1);

                    aristas.add(arista);
                }
            }
        }

        for (i = 0; i < topologia.get(0).size(); i++) {
            vertice = new Vertex();
            vertice.setId(i.toString());
            vertice.setName(i.toString());

            vertices.add(vertice);
        }

    }

    public static List<Camino> ordenarCaminos (List<Camino> caminos) {
        int i, j;
        Camino aux = new Camino();

        for (i = 0; i < caminos.size(); i++) {
            for (j = i + 1; j < caminos.size(); j++) {
                if (caminos.get(i).getDistancia() > caminos.get(j).getDistancia()){
                    aux = caminos.get(i);
                    caminos.set(i, caminos.get(j));
                    caminos.set(j, aux);
                }
            }
        }

        return caminos;
    }

}

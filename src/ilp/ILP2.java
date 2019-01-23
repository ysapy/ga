//package ilp;
//
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.jgrapht.GraphPath;
//import org.jgrapht.alg.KShortestPaths;
//import org.jgrapht.graph.*;
//
//
///**
// * Created by ysapymimbi on 20/12/2016.
// */
//public class ILP2 {
//
//    public static void main(String args[]) throws IOException {
//        Configuracion config = leerConfiguraciones();
//
//        // Definimos e instanciamos el grafo con pesos
//        SimpleDirectedGraph<String, DefaultWeightedEdge> directedGraph = new SimpleDirectedGraph(DefaultWeightedEdge.class);
//        cargarGrafo(directedGraph);
//        String alfa = leerSolicitudes(directedGraph, config.getK());
//    }
//
//    private static void cargarGrafo(SimpleDirectedGraph<String, DefaultWeightedEdge> directedGraph) {
//        try {
//            FileReader fr = new FileReader("edges.txt");
//            String[] edgeText;
//            DefaultWeightedEdge ed;
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                while ((line = entrada.readLine()) != null) {
//                    edgeText = line.split("\t");
//                    directedGraph.addVertex(edgeText[0]);
//                    directedGraph.addVertex(edgeText[1]);
//                    ed = directedGraph.addEdge(edgeText[0], edgeText[1]);
//                    directedGraph.setEdgeWeight(ed, Double.parseDouble(edgeText[2]));
//                }
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//        } catch (IOException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//        }
//    }
//
//    private static void calcularCaminosMasCortos(SimpleDirectedGraph<String, DefaultWeightedEdge> directedGraph, String origen, String destino, int contadorEscritura, int k, double cantSolicitada) {
//        try {
//            int cont = 0;
//            FileWriter fw3;
//            if (origen.equals("0") && destino.equals("1")) {
//                fw3 = new FileWriter("ga.txt");
//            } else {
//                fw3 = new FileWriter("ga.txt", true);
//            }
//            BufferedWriter bw3 = new BufferedWriter(fw3);
//            try (PrintWriter salida4 = new PrintWriter(bw3)) {
//
//                KShortestPaths caminosCandidatos = new KShortestPaths(directedGraph, origen, k);
//
//                System.out.println("K-shortest path de " + origen + " a " + destino + " es ");
//
//                List<GraphPath<String, DefaultEdge>> paths = caminosCandidatos.getPaths(destino);
//
//                for (GraphPath<String, DefaultEdge> path : paths) {
//                    System.out.print("Camino " + ++cont + ": ");
//                    int cantidadNodos = 0;
//                    salida4.print(origen + destino + "\t");
//                    salida4.print(origen + "\t");
//                    salida4.print(destino + "\t");
//                    for (DefaultEdge edge : path.getEdgeList()) {
//                        if (cantidadNodos == 0) {
//                            salida4.print(edge);
//                        } else {
//                            salida4.print("-" + edge);
//                        }
//                        cantidadNodos++;
//                    }
//
//                    salida4.print("\t" + (int)cantSolicitada);
//                    salida4.print("\t" + (int)cantidadNodos);
//                    salida4.print("\t" + (int)(cantidadNodos*cantSolicitada) + "\n");
//                }
//            }
//
//        } catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private static String leerSolicitudes(SimpleDirectedGraph<String, DefaultWeightedEdge> directedGraph, int k) {
//        ArrayList<Solicitud> listaSolicitudes = new ArrayList();
//        try {
//            FileReader fr = new FileReader("solicitudes.txt");
//            String[] edgeText;
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                while ((line = entrada.readLine()) != null) {
//                    edgeText = line.split("\t");
//                    Solicitud sol = new Solicitud(edgeText[0], edgeText[1], Double.parseDouble(edgeText[2]));
//                    listaSolicitudes.add(sol);
//                }
//            }
//
//            Double ftotal = 0.0;
//
//
//            int contadorEscritura = 0;
//            for (Iterator<Solicitud> it = listaSolicitudes.iterator(); it.hasNext();) {
//                Solicitud solicitud = it.next();
//
//                System.out.print("Origen: " + solicitud.getOrigen() + "\t"
//                        + "Destino:" + solicitud.getDestino() + "\t"
//                        + "Velocidad requerida:" + solicitud.getVelocidad() + "\n");
//                calcularCaminosMasCortos(directedGraph, solicitud.getOrigen(), solicitud.getDestino(), contadorEscritura, k, solicitud.getVelocidad());
//                ArrayList<Double> listaDistancias = leerDistancias();
//                Double tsd;
//                int cont = 0;
//                for (Iterator<Double> it2 = listaDistancias.iterator(); it2.hasNext();) {
//                    Double dist = it2.next();
//                    System.out.println("Distancia de camino " + ++cont + ": " + dist);
//                    System.out.println("alfa = " + solicitud.getVelocidad());
//                    Double d = Math.ceil(solicitud.getVelocidad());
//                    tsd = solicitud.getVelocidad();
//                    System.out.println("Tsd = " + tsd);
//                    ftotal += tsd;
//                }
//                contadorEscritura++;
//            }
//
//            //System.out.println("vectorAlfa = " + vectorAlfa);
//            System.out.println("ftotal = " + ftotal + ";");
//
//        } catch (FileNotFoundException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//        } catch (IOException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//        }
//    }
//
//    private static String leerSolicitudesSegundaFase(SimpleDirectedGraph<String, DefaultWeightedEdge> directedGraph, ArrayList<Integer> indiceCaminosElegidos) {
//
//        final double C = 154;
//
//        ArrayList<Parametro> listaParams = leerParametros();
//
//        String vectorAlfa = "[";
//
//        ArrayList<Solicitud> listaSolicitudes = new ArrayList();
//        try {
//
//            FileReader fr = new FileReader("solicitudes.txt");
//            String[] edgeText;
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                while ((line = entrada.readLine()) != null) {
//                    edgeText = line.split("\t");
//                    Solicitud sol = new Solicitud(edgeText[0], edgeText[1], Double.parseDouble(edgeText[2]));
//                    listaSolicitudes.add(sol);
//                }
//            }
//
//            Double ftotal = 0.0;
//
//
//            int contadorEscritura = 0;
//            int cont = 0;
//            for (Iterator<Solicitud> it = listaSolicitudes.iterator(); it.hasNext();) {
//                Solicitud solicitud = it.next();
//
//                System.out.print("Origen: " + solicitud.getOrigen() + "\t"
//                        + "Destino:" + solicitud.getDestino() + "\t"
//                        + "Velocidad requerida:" + solicitud.getVelocidad() + "\n");
//                //calcularCaminosMasCortos(directedGraph, solicitud.getOrigen(), solicitud.getDestino(), contadorEscritura);
//                ArrayList<Double> listaDistancias = leerDistanciasTodasSolicitudes();
//                Double tsd;
//
//                //for (int cont = 0; cont < indiceCaminosElegidos.size(); cont++) {
//                Double dist = listaDistancias.get(indiceCaminosElegidos.get(cont) - 1);
//                //if(indiceCaminosElegidos.get(cont) == cont + 1) {
//                cont = cont + 1;
//                System.out.println("Distancia de camino " + cont + ": " + dist);
//                int nivelModulacionCorrespondiente = leerNivelModulacionCorrespondiente(dist, listaParams);
//                System.out.println("Nivel de Modulación correspondiente es " + nivelModulacionCorrespondiente);
//                System.out.println("alfa = " + Math.ceil(solicitud.getVelocidad() * C / (nivelModulacionCorrespondiente * C)));
//                Double d = Math.ceil(solicitud.getVelocidad() * C / (nivelModulacionCorrespondiente * C));
//
//                if (it.hasNext()) {
//                    vectorAlfa = vectorAlfa + d.intValue() + ",";
//                } else {
//                    vectorAlfa = vectorAlfa + d.intValue();
//                }
//                tsd = Math.ceil(solicitud.getVelocidad() * C / C);
//                System.out.println("Tsd = " + tsd);
//                ftotal += tsd;
//                //}
//                //}
//                contadorEscritura++;
//            }
//
//            vectorAlfa = vectorAlfa + "];";
//
//            //System.out.println("vectorAlfa = " + vectorAlfa);
//            System.out.println("ftotal = " + ftotal / C + ";");
//
//        } catch (FileNotFoundException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//            Logger
//                    .getLogger(ILP2.class
//                            .getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//        return vectorAlfa;
//    }
//
//    private static ArrayList<Parametro> leerParametros() {
//
//        ArrayList<Parametro> listaParametros = new ArrayList();
//
//        try {
//
//            FileReader fr = new FileReader("parametros.txt");
//            String[] edgeText;
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                while ((line = entrada.readLine()) != null) {
//                    edgeText = line.split("\t");
//                    Parametro param = new Parametro(Double.parseDouble(edgeText[0]), Integer.parseInt(edgeText[1]));
//                    listaParametros.add(param);
//                }
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//            Logger
//                    .getLogger(ILP2.class
//                            .getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        return listaParametros;
//    }
//
//    private static Configuracion leerConfiguraciones() {
//
//        Configuracion config = null;
//
//        try {
//
//            FileReader fr = new FileReader("configuraciones.txt");
//            String texto = new String();
//            String configText[];
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                while ((line = entrada.readLine()) != null) {
//                    texto += line + "\t";
//                }
//                configText = texto.split("\t");
//                config = new Configuracion(Integer.parseInt(configText[0]), Integer.parseInt(configText[1]));
//
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//            Logger
//                    .getLogger(ILP2.class
//                            .getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        return config;
//    }
//
//
//    private static ArrayList<Double> leerDistancias() {
//
//        ArrayList<Double> listaDistancias = new ArrayList();
//        try {
//
//            FileReader fr = new FileReader("kshortestpath.txt");
//            String[] edgeText;
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                while ((line = entrada.readLine()) != null) {
//                    edgeText = line.split("\t");
//                    Double dist = Double.parseDouble(edgeText[edgeText.length - 1]);
//                    listaDistancias.add(dist);
//                }
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//            Logger
//                    .getLogger(ILP2.class
//                            .getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        return listaDistancias;
//    }
//
//    private static ArrayList<Double> leerDistanciasTodasSolicitudes() {
//
//        ArrayList<Double> listaDistancias = new ArrayList();
//        try {
//
//            FileReader fr = new FileReader("kshortestpathCompleto.txt");
//            String[] edgeText;
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                while ((line = entrada.readLine()) != null) {
//                    edgeText = line.split("\t");
//                    Double dist = Double.parseDouble(edgeText[edgeText.length - 1]);
//                    listaDistancias.add(dist);
//                }
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//            Logger
//                    .getLogger(ILP2.class
//                            .getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        return listaDistancias;
//    }
//
//    private static int leerNivelModulacionCorrespondiente(Double distancia, ArrayList<Parametro> listaParams) {
//        int nivMod = 0;
//
//        for (Parametro parametroMod : listaParams) {
//
//            if (distancia < parametroMod.getDistanciaMaxima()) {
//                nivMod = parametroMod.getNivelModulacion();
//                break;
//            }
//
//        }
//        return nivMod;
//    }
//
//    private static ArrayList<ArrayList<String>> leerTotalCaminos() {
//
//
//
//        ArrayList<ArrayList<String>> listaCaminos = new ArrayList();
//
//        try {
//            FileReader fr = new FileReader("kshortestpathCompleto.txt");
//            String[] edgeText;
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                while ((line = entrada.readLine()) != null) {
//                    ArrayList<String> listaEnlaces = new ArrayList();
//                    edgeText = line.split("\\)");
//                    String dist;
//                    for (int i = 0; i < edgeText.length - 1; i++) {
//                        dist = edgeText[i].trim().substring(1);
//                        listaEnlaces.add(dist);
//                    }
//                    listaCaminos.add(listaEnlaces);
//                }
//
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//            Logger
//                    .getLogger(ILP2.class
//                            .getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        return listaCaminos;
//    }
//
//    private static ArrayList<Integer> leerSalidaCplex(int k) {
//
//
//
//        ArrayList<Integer> listaCaminosElegidos = new ArrayList();
//
//        try {
//            FileReader fr = new FileReader("salidaCplexILP2_3.txt");
//            String[] edgeText;
//            int indicadorLinea = 0;
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                int lineaLectura = 0;
//
//                while ((line = entrada.readLine()) != null) {
//
//
//                    //line = entrada.readLine();
//                    edgeText = line.split(".\\[");
//                    lineaLectura = lineaLectura + 1;
//                    String indices = new String();
//
//
//                    if (lineaLectura >= 12) {
//                        indicadorLinea = indicadorLinea + 1;
//                        try{
//                            indices = edgeText[1].replace("[", "").replace("]", "").replace(";", "").replace(" ", "");
//                        } catch (Exception e) {
//                            System.out.print(e);
//                        }
//                    }
//
//                    for (int i = 0; i < indices.length() - 1; i++) {
//
//                        if (indices.charAt(i) == '1') {
//                            listaCaminosElegidos.add(indicadorLinea * k - k + i + 1);
//                            break;
//                        }
//
//                    }
//
//
//
//                }
//
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//            Logger
//                    .getLogger(ILP2.class
//                            .getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        return listaCaminosElegidos;
//    }
//
//    private static ArrayList<ArrayList<String>> leerSoloCaminosElegidos(ArrayList<Integer> indiceCaminosElegidos) {
//
//
//
//        ArrayList<ArrayList<String>> listaCaminosElegidos = new ArrayList();
//
//        try {
//            FileReader fr = new FileReader("kshortestpathCompleto.txt");
//            String[] edgeText;
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                int contador = 1;
//                while ((line = entrada.readLine()) != null) {
//                    if (indiceCaminosElegidos.contains(contador)) {
//                        ArrayList<String> listaEnlaces = new ArrayList();
//                        edgeText = line.split("\\)");
//                        String dist;
//                        for (int i = 0; i < edgeText.length - 1; i++) {
//                            dist = edgeText[i].trim().substring(1);
//                            listaEnlaces.add(dist);
//                        }
//                        listaCaminosElegidos.add(listaEnlaces);
//                    }
//                    contador = contador + 1;
//                }
//
//
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("Archivo no encontrado: " + ex);
//            Logger
//                    .getLogger(ILP2.class
//                            .getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        return listaCaminosElegidos;
//    }
//
//    private static String leerSaltos() {
//
//        String vectorAlfa = "[";
//        int cant = 0;
//        try {
//
//            FileReader fr = new FileReader("saltos.txt");
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                String line;
//                vectorAlfa = vectorAlfa + entrada.readLine();
//                while ((line = entrada.readLine()) != null) {
//                    vectorAlfa = vectorAlfa + ", " + line;
//                    cant++;
//                }
//                vectorAlfa = vectorAlfa + "];";
//            }catch (FileNotFoundException ex) {
//                System.out.println("Archivo no encontrado: " + ex);
//                Logger
//                        .getLogger(ILP2.class
//                                .getName()).log(Level.SEVERE, null, ex);
//            }
//        }catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return vectorAlfa;
//    }
//
//    /**
//     * Valores maximos de espectro disponible, ruta mas larga, costo mayor, posibles
//     * @return
//     */
//    private static List<String> leerMaximos() {
//
//        List<String> vectorAlfa = new ArrayList();
//        try {
//
//            FileReader fr = new FileReader("baja-maximos.txt");
//
//            try (BufferedReader entrada = new BufferedReader(fr)) {
//                vectorAlfa.add(entrada.readLine());
//                vectorAlfa.add(entrada.readLine());
//                vectorAlfa.add(entrada.readLine());
//
//            }catch (FileNotFoundException ex) {
//                System.out.println("Archivo no encontrado: " + ex);
//                Logger
//                        .getLogger(ILP2.class
//                                .getName()).log(Level.SEVERE, null, ex);
//            }
//        }catch (IOException ex) {
//            Logger.getLogger(ILP2.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return vectorAlfa;
//    }
//
//
//
//
//
//}

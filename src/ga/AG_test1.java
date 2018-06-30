//package jmetal.metaheuristics.ga;
//
//import jmetal.metaheuristics.ga.objects.Link;
//import jmetal.metaheuristics.ga.objects.Route;
////import jmetal.metaheuristics.ga.objects.Solution;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import static java.lang.Math.floor;
//
//
///**
// * Created by ysapy on 30/01/16.
// */
//public class AG_test1 {
//
//    public static void main(String [] args) throws IOException {
//        int i, j, g, n, k;
//        String path = "/home/ysapy/personalProjects/tesis/JMETALHOME/archives/";
//
//        Solution childSolution; // solucion generada luego de cruce y mutacion
//        List<Solution> population = new ArrayList<Solution>(); // donde se copia la poblacion inicial para ir eliminando los padres electos de aqui
//        List<Solution> population2 = new ArrayList<Solution>(); // donde se almacenan los hijos antes de ser electos para la siguiente generacion
//        List<Solution> population3 = new ArrayList<Solution>(); // donde se almacenan los padres, y posteriormente los hijos seleccionados, para la sgte generacion
//        List<Solution> reproductors = new ArrayList<Solution>(); // 2 padres electos randomicamente
//
//        List<List<Solution>> finalPopulation = new ArrayList<List<Solution>>(); // almacena una lista de los conjuntos soluciones hallados en las countRunners corridas
//
//        List<Solution> initialSolutions = new ArrayList<Solution>();
//        List<Solution> initialPopulation = new ArrayList<Solution>();
//        Solution randomSolution;
//
//        // leer parametros
//        try {
//            FileReader f = new FileReader(path + "params.txt");
//            BufferedReader b = new BufferedReader(f);
//
//            int countSlots = Integer.parseInt(b.readLine());
//            int countAlfa = Integer.parseInt(b.readLine());
//            int countBeta = Integer.parseInt(b.readLine());
//            double probabMutation = Double.parseDouble(b.readLine());
//            int countGenerations = Integer.parseInt(b.readLine())*60;
//            int countRunners = Integer.parseInt(b.readLine());
//            int countNodos = Integer.parseInt(b.readLine());
//
//            b.close();
//
//            boolean[][] topology = new boolean[countNodos][countNodos];
//            int[][] traf = new int[countNodos][countNodos];
//
//            String[][] aux = readMatrix(path + "topology.txt", countNodos);
//            for (i = 0; i < aux.length ; i++){
//                for (j = 0; j < aux.length; j++) {
//                    if (aux[i][j].equals("1")){
//                        topology[i][j] = true;
//                    } else {
//                        topology[i][j] = false;
//                    }
//                }
//            }
//
//            aux = readMatrix(path + "demand.txt", countNodos);
//            for (i = 0; i < aux.length ; i++){
//                for (j = 0; j < aux.length; j++) {
//                    traf[i][j] = Integer.parseInt(aux[i][j]);
//                }
//            }
//
//            List<Route> demands = getDemands(traf);
//
//            for (i = 0; i < countRunners; i++) {
//
//                generateInitialSolution(initialSolutions, demands, countAlfa, traf, topology, countSlots);
//
//                for (g = 0; g < countGenerations ; g++) {
//                    selectFirstFit(initialPopulation, initialSolutions, countBeta);
//
//                    for (i = 0; i < countBeta * 0.7; i++) {
//                        randomSolution = selectRandom(initialSolutions);
//                        initialPopulation.add(randomSolution);
//                        initialSolutions.remove(randomSolution);
//                    }
//
//                    population = initialPopulation;
//
//                    for (j = 0; j < countBeta / 2; j++) {
//                        randomSolution = selectRandom(population);
//                        reproductors.add(randomSolution);
//                        population.remove(randomSolution);
//                        randomSolution = selectRandom(population);
//                        reproductors.add(randomSolution);
//                        population.remove(randomSolution);
//
//                        childSolution = cruceOperator(reproductors);
//
//                        if (Math.random() >= probabMutation) {
//                            childSolution = mutationOperator(childSolution, topology);
//                        }
//
//                        if (verifyConstraints(childSolution, traf, countSlots)) {
//                            population2.add(childSolution);
//                        }
//
//                        population3.add(reproductors.get(0));
//                        population3.add(reproductors.get(1));
//
//                        reproductors = new ArrayList<Solution>();
//
//                    }
//
//                    selectFirstFit(initialSolutions, population2, countBeta);
//                    initialSolutions.addAll(population3);
//
//                }
//
//                finalPopulation.add(initialSolutions);
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String ficheroSalida = "solucion.txt";
//        File fichero = new File(ficheroSalida);
//
//        System.out.println("Escribiendo salida");
//        BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida));
//
//        for (i = 0; i < finalPopulation.size(); i++){
//            bw.write("Conjunto solucion Nro: " + i + "; \n");
//            for (j = 0; j < finalPopulation.get(i).size(); j++){
//                bw.write("Solucion Nro: " + j + "\n");
//                for (n = 0; n < finalPopulation.get(i).get(j).getRoutes().size(); n++){
//                    bw.write("Demanda: " + finalPopulation.get(i).get(j).getRoutes().get(n).getOrigin() + " - " + finalPopulation.get(i).get(j).getRoutes().get(n).getDestiny() + "\n");
//                    bw.write("Ruta: ");
//                    for (g = 0; g < finalPopulation.get(i).get(j).getRoutes().get(n).getLinks().size(); g++){
//                        bw.write("\n * " + finalPopulation.get(i).get(j).getRoutes().get(n).getLinks().get(g).getOrigin() + " - " + finalPopulation.get(i).get(j).getRoutes().get(n).getLinks().get(g).getDestiny());
//                        bw.write(" Ranuras: ");
//                        for (k = 0; k < finalPopulation.get(i).get(j).getRoutes().get(n).getLinks().get(g).getUsedSlots().size(); k++){
//                            if (finalPopulation.get(i).get(j).getRoutes().get(n).getLinks().get(g).getUsedSlots().get(k)){
//                                bw.write(finalPopulation.get(i).get(j).getRoutes().get(n).getLinks().get(g).getUsedSlots().get(k) + ", ");
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        bw.close();
//
//    }
//
//    public static void selectFirstFit (List<Solution> initialPopulation, List<Solution> initialSolutions, int countBeta){
//        System.out.println("select First fit");
//        int j, c, i;
//        c = initialSolutions.size();
//        double n;
//        Solution aux;
//
//        for (i = 0; i < c; i++){
//            for (j = i+1; j < c-1; j++){
//                if (initialSolutions.get(i).getFitness() > initialSolutions.get(j).getFitness()){
//                    aux = initialSolutions.get(i);
//                    initialSolutions.set(i, initialSolutions.get(j));
//                    initialSolutions.set(j, aux);
//                }
//            }
//        }
//
//        n = floor(countBeta * 0.3);
//
//        for (i = 0; i < n; i++){
//            initialPopulation.add(initialSolutions.get(i));
//        }
//
//        for (i = 0; i < n; i++){
//            initialSolutions.remove(i);
//        }
//
//    }
//
//    public static Solution selectRandom (List<Solution> solutions){
//        System.out.println("select Random");
//        int randm;
//
//        randm = (int)Math.random()*(solutions.size()-1);
//
//        return solutions.get(randm);
//    }
//
//    public static Solution cruceOperator (List<Solution> reproductors){
//        System.out.println("Crossover");
//        Solution solution = new Solution();
//        List<Route> routes = new ArrayList<Route>();
//
//        for (int i = 0; i < reproductors.get(0).getRoutes().size(); i++){
//            if (i % 2 == 0){
//                routes.add(reproductors.get(0).getRoutes().get(i));
//            } else {
//                routes.add(reproductors.get(1).getRoutes().get(i));
//            }
//        }
//
//        solution.setRoutes(routes);
//
//        return solution;
//
//    }
//
//    public static Solution mutationOperator (Solution solution, boolean[][] topology){
//        System.out.println("Mutacion");
//
//        int routePosition = (int)Math.random()*(solution.getRoutes().size()-1);
//        Route route = solution.getRoutes().get(routePosition);
//        Route newRoute = new Route();
//        Link link = new Link();
//        List<Link> links = new ArrayList<Link>();
//        List<Boolean> slots = route.getLinks().get(0).getUsedSlots();
//
//        int origin = route.getLinks().get(0).getOrigin();
//        int destiny = route.getLinks().get(route.getLinks().size()-1).getDestiny();
//        boolean isCompleted = false;
//        int i, j;
//        List<Integer> used = new ArrayList<Integer>();
//
//        while (!isCompleted){
//            used.add(origin);
//
//            for (i = 0; i < topology.length; i++){
//                if (i != origin && !used.contains(i) && topology[origin][i]){
//                    link.setOrigin(origin);
//                    link.setDestiny(i);
//                    link.setUsedSlots(slots);
//                    links.add(link);
//
//                    if (destiny != i){
//                        origin = i;
//                    } else {
//                        isCompleted = true;
//                        break;
//
//                    }
//                }
//            }
//        }
//
//        newRoute.setLinks(links);
//
//        solution.getRoutes().set(routePosition, newRoute);
//
//        return solution;
//
//    }
//
//    public static boolean verifyConstraints (Solution solution, int[][] traf, int cantRanuras){
////        System.out.println("Verificar Restricciones");
//
//        Boolean onlyFirstSlot = onlyFirstSlot(solution);
//        Boolean isContiguity = isContiguity(solution, traf);
//        Boolean isContinuity = isContinuity(solution);
//        Boolean notExceedMaxSlots = notExceedMaxSlots(solution, traf);
//        Boolean noSuperposicion = noSuperposicion(solution, cantRanuras);
//
//        return onlyFirstSlot && isContiguity && isContinuity && notExceedMaxSlots && noSuperposicion;
//
//
//    }
//
//    public static boolean onlyFirstSlot(Solution solution){
////        System.out.println("Verificar Restricciones: only first");
//
//        int sum = 0;
//        int j;
//
//        for (int i = 0; i < solution.getRoutes().size() ; i ++ ){
//            for (Link link : solution.getRoutes().get(i).getLinks()){
//                for (j = 0 ; j < link.getFirstSlot().size(); j++) {
//                    sum = sum + link.getFirstSlot().get(j);
//                }
//                if (sum != 1)
//                    return false;
//                sum = 0;
//            }
//        }
//        return true;
//    }
//
//    public static boolean isContiguity (Solution solution, int[][] traf){
////        System.out.println("Verificar Restricciones: contiguo");
//
//        int totalSlots = solution.getRoutes().get(0).getLinks().get(0).getUsedSlots().size();
//        int j;
//        int initialSlot = 0, finalSlot = 0, countSlots  = 0;
//        int demand = 0;
//
//        for (int i = 0; i < solution.getRoutes().size() ; i ++ ){
//            demand = traf[solution.getRoutes().get(i).getOrigin()][solution.getRoutes().get(i).getDestiny()];
//            for (Link link : solution.getRoutes().get(i).getLinks()){
//                for (j = 0 ; j < link.getFirstSlot().size(); j++) {
//                    if (link.getFirstSlot().get(j) == 1){
//                        initialSlot = j;
//                        countSlots++;
//                    } else if (link.getUsedSlots().get(j) == true){
//                        finalSlot = j;
//                        countSlots++;
//                    }
//                }
//
//                if (!(countSlots == demand)
//                    || !(totalSlots-demand > initialSlot)
//                    || !(finalSlot + 1 - demand == initialSlot)){
//                    return false;
//                }
//
//                initialSlot = 0;
//                finalSlot = 0;
//                countSlots = 0;
//            }
//        }
//        return true;
//
//    }
//
//    public static boolean isContinuity (Solution solution){
////        System.out.println("Verificar Restricciones: continuo");
//
//        int j, n;
//        int finalSlot = 0;
//        int firstLinkSlot = 0, lastLinkSlot = 0;
//
//        for (Route route : solution.getRoutes()){
//            for (n = 0 ; n < route.getLinks().size(); n++){
//                for (j = 0 ; j < route.getLinks().get(n).getFirstSlot().size(); j++) {
//                    if (route.getLinks().get(n).getFirstSlot().get(j) == 1){
//                        if (n == 0) {
//                            firstLinkSlot = j;
//                        } else {
//                            if ( j != firstLinkSlot)
//                                return false;
//                        }
//                    } else if (route.getLinks().get(n).getUsedSlots().get(j) == true){
//                        if (n == 0){
//                            lastLinkSlot = j;
//                        }
//                        finalSlot = j;
//                    }
//                }
//                if (lastLinkSlot != finalSlot)
//                    return false;
//
//                finalSlot = 0;
//            }
//        }
//        return true;
//
//    }
//
//    public static boolean notExceedMaxSlots(Solution solution, int[][]traf){
////        System.out.println("Verificar Restricciones: no supera el maximo");
//
//        int totalSlots = solution.getRoutes().get(0).getLinks().get(0).getUsedSlots().size();;
//        int countUsed = 0, j;
//
//        for (Route route : solution.getRoutes()){
//            for (Link link : route.getLinks()){
//                for (j = 0; j < link.getUsedSlots().size(); j++) {
//                    if (link.getUsedSlots().get(j) == true) {
//                        countUsed++;
//                    }
//                }
//                if (countUsed > totalSlots)
//                    return false;
//                countUsed = 0;
//            }
//        }
//        return true;
//    }
//
//    public static boolean noSuperposicion (Solution solution, int cantRanuras){
////        System.out.println("Verificar Restricciones: no superposicion");
//
//        boolean[] ranurasUsadas = new boolean[cantRanuras];
//        int i;
//
//        for (Route route : solution.getRoutes()){
//            for (i = 0; i < route.getLinks().get(0).getUsedSlots().size() ; i++){
//                if (route.getLinks().get(0).getUsedSlots().get(i)){
//                    if (ranurasUsadas[i] == true){
//                        return false;
//                    } else {
//                        ranurasUsadas[i] = true;
//                    }
//                }
//            }
//        }
//
//        return true;
//    }
//
//    public static String[][] readMatrix(String archive, int countNodos) throws IOException {
//        System.out.println("Leer matriz de archivo");
//
//        String line;
//        String[] row;
//        int i, j = 0;
//        String[][] matrix = new String[countNodos][countNodos];
//
//        try {
//            FileReader f = new FileReader(archive);
//            BufferedReader b = new BufferedReader(f);
//
//            while ((line = b.readLine()) != null) {
//                row = line.split(" ");
//
//                for (i = 0 ; i < row.length ; i++){
//                    matrix[j][i] = row[i];
//                }
//                j++;
//            }
//
//            b.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return matrix;
//
//    }
//
//    public static void generateInitialSolution(List<Solution> solutions, List<Route> demands, int countAlfa, int[][] traf, boolean[][] topology, int cantRanuras){
//        System.out.println("Generar poblacion inicial");
//
//        int countNodos = traf.length;
//        int inicioRanuras, k, k2;
//        int probDestino;
//        List<Boolean> ranurasUsadas = new ArrayList<Boolean>();
//        List<Integer> primeraRanura = new ArrayList<Integer>();
//        int ranurasSolicitadas;
//        List<Route> routes = new ArrayList<Route>();
//
//        for (k = 0; k < cantRanuras; k++){
//            ranurasUsadas.add(false);
//            primeraRanura.add(0);
//        }
//
//        while(solutions.size() < countAlfa){
//            Solution solution = new Solution();
//
//            int cantDemands = demands.size();
//
//            for (int j = 0; j < cantDemands; j++){
//                Random rnd = new Random();
//                int index = (int)(Math.random() * demands.size());
//                Route demand = demands.remove(index);
//                routes = new ArrayList<Route>();
//                boolean esRoute = false;
//                int origen = demand.getOrigin();
//                int contIntentos = 0;
//
//                // calcular ranuras
//                for (k = 0; k < cantRanuras; k++){
//                    ranurasUsadas.set(k, false);
//                    primeraRanura.set(k, 0);
//                }
//                ranurasSolicitadas = traf[demand.getOrigin()][demand.getDestiny()];
//                inicioRanuras = (int)(Math.random()*(cantRanuras-ranurasSolicitadas-1));
//                primeraRanura.set(inicioRanuras, 1);
//
//                for (k2 = 0; k2 < ranurasSolicitadas; k2++){
//                    ranurasUsadas.set(k2 + inicioRanuras, true);
//                }
//
//                probDestino = -1;
//                while(!esRoute){
//                    contIntentos++;
//                    probDestino++;
//                    if (probDestino == countNodos)
//                        probDestino = 0;
//                    if (topology[origen][probDestino]) {
//                        Link link = new Link();
//                        link.setOrigin(origen);
//                        link.setDestiny(probDestino);
//                        link.setUsedSlots(ranurasUsadas);
//                        link.setFirstSlot(primeraRanura);
//                        demand.getLinks().add(link);
//                        origen = probDestino;
//
//                        if (probDestino == demand.getDestiny()){
////                            esRoute = true;
//                            probDestino = -1;
//                        }
//                    }
//					/* Si se intenta demasiadas veces y no se encuentra una ruta, se
//						inicia de nuevo la busqueda */
//                    if(!esRoute && contIntentos > countNodos*2){
//                        origen = demand.getOrigin();
//                        demand.getLinks().clear();
//                        contIntentos = 0;
//                    }
//
//                }
//                routes.add(demand);
//            }
//
//            solution.setRoutes(routes);
//            if (verifyConstraints(solution, traf, cantRanuras)) {
//                calcularFitness(solution);
//                solutions.add(solution);
//                System.out.println("Alguna supera");
//            }
//        }
//    }
//
//    public static List<Route> getDemands(int[][] traf){
//        System.out.println("Obtener demandas de matriz");
//
//        Route route = new Route();
//        List<Route> routes = new ArrayList<Route>();
//        int j;
//
//        for (int i = 0; i < traf.length ; i++){
//            for (j = 0 ; j < traf.length ; j++){
//                if (traf[i][j] != 0){
//                    route.setOrigin(i);
//                    route.setDestiny(j);
//
//                    routes.add(route);
//                    route = new Route();
//                }
//            }
//        }
//
//        return routes;
//    }
//
//    public static void calcularFitness (Solution solution){
//        System.out.println("Calcular fitness");
//
//        int cantEnlaces = 0;
//        int cantRanuras = 0;
//        int fitness = 0;
//
//        for (Route route : solution.getRoutes()) {
//            cantEnlaces = route.getLinks().size();
//            cantRanuras = 0;
//            List<Boolean> slots = route.getLinks().get(0).getUsedSlots();
//
//            for (Boolean slot : slots) {
//                if (slot) {
//                    cantRanuras++;
//                }
//
//            }
//
//            fitness = fitness + (cantRanuras * cantEnlaces);
//        }
//
//        solution.setFitness(fitness);
//
//    }
//
//
//}

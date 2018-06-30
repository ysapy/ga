//package jmetal.metaheuristics.ga;
//import jmetal.metaheuristics.ga.objects.Enlace;
//import jmetal.metaheuristics.ga.objects.Ruteo;
//import jmetal.metaheuristics.ga.objects.Solucion;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import static java.lang.Math.floor;
//
///**
// * Created by ysapy on 23/02/16.
// */
//public class GaEon {
//
//    private static String pathArchivo = "/home/ysapy/personalProjects/tesis/JMETALHOME/src/jmetal/metaheuristics/ga/entradas/resultados/";
//
//    public static void main(String [] args) throws IOException {
//        List<List<Solucion>> todosLosConjuntos = new ArrayList<List<Solucion>>();
//        List<List<Boolean>> topologia = AGHelper.leerTopologia();
//        int cantLlamadasGA = AGHelper.leerParametro("cantidad de corridas independientes");
//        int cantSolucionesIniciales = AGHelper.leerParametro("cantidad de cromosomas");
//        int totalRanuras = AGHelper.leerParametro("cantidad de longitudes de onda por fibra");
//        int criterioParada = AGHelper.leerParametro("criterio de parada");
//        double probabMutacion = AGHelper.leerProbabMutacion("probabilidad de mutacion");
//        Long tiempoInicial;
//        List<String> archivos = new ArrayList<String>();
//        archivos.add("baja");
////        archivos.add("media");
////        archivos.add("alta");
////        archivos.add("saturada");
//
//        List<DemandaInfo> demandaInfoList = new ArrayList<DemandaInfo>();
//        tiempoInicial = System.currentTimeMillis();
//        for (String archivo : archivos) {
////            demandaInfoList.addAll(llenarDemandInfo("info_d_" + archivo + ".txt"));
//            demandaInfoList.addAll(llenarDemandInfo("demandaInfopaperTopology.txt"));
//            for (int i = 0; i < cantLlamadasGA; i++) {
//                todosLosConjuntos.add(ga(topologia, cantSolucionesIniciales, totalRanuras, criterioParada, probabMutacion, demandaInfoList));
//                guardarEnArchivo(todosLosConjuntos.get(i), i, demandaInfoList, "solucion_" + i, tiempoInicial);
//            }
//            demandaInfoList = new ArrayList<DemandaInfo>();
//            todosLosConjuntos = new ArrayList<List<Solucion>>();
//        }
//
//    }
//
//    public static List<Solucion> generarPoblacionInicial (List<List<Boolean>> topologia, Integer cantSolucionesIniciales, Integer totalRanuras, List<DemandaInfo> demandaInfoList) throws FileNotFoundException {
//
//        List<Solucion> poblacionInicial = new ArrayList<Solucion>();
//
//        List<DemandaInfo> demandasMayores = new ArrayList<DemandaInfo>();
//        demandasMayores.addAll(definirOrdenDemandas(demandaInfoList));
//
//        for (int i = 0; i < cantSolucionesIniciales; i++) {
//            poblacionInicial.add(generarSolucion(totalRanuras, topologia, demandasMayores, demandaInfoList));
//        }
//
//        return poblacionInicial;
//    }
//
//    public static List<DemandaInfo> definirOrdenDemandas (List<DemandaInfo> demandasInfo) {
//        int i, r;
//
//        List<DemandaInfo> mayores = new ArrayList<DemandaInfo>();
//        List<DemandaInfo> aux;
//        List<DemandaInfo> infoCopy = new ArrayList<DemandaInfo>();
//
//        // traer la lista de demandasInfo con las filas que tienen la mayor X de cada demanda
//        aux = obtenerDemandasMayores(demandasInfo);
//
//        // ordenar aux de mayor a menor
//        ordenar(aux);
//        infoCopy.addAll(aux);
//
//        // copio el 30% de los mayores a mayores
//        for (i = 0; i < floor(aux.size() * 0.3); i++){
//            mayores.add(aux.get(i));
//            infoCopy.remove(aux.get(i));
//        }
//
//        // copio el 70% restante en orden aleatorio
//        for (i = 0; i < aux.size() - floor(aux.size() * 0.3); i++){
//            r = (int)(Math.random()*infoCopy.size());
//            mayores.add(infoCopy.get(r));
//            infoCopy.remove(r);
//        }
//
//        return mayores;
//    }
//
//    public static Solucion generarSolucion(int cantRanuras, List<List<Boolean>> topologia, List<DemandaInfo> mayores, List<DemandaInfo> demandasInfo) {
//        List<Enlace> enlacesIniciales = generarListaInicialRanuras (cantRanuras, topologia);
//        Solucion solucion = new Solucion(enlacesIniciales); // inicializar con una lista de enlaces en la cual todas sus ranuras estan libres
//        int j;
//        String demandaNro;
//        boolean bloqueado;
//
//        // selecciono demanda por demanda en el orden ya establecido del 30% y 70%
//        // de cada demanda, traigo la ruta mas corta de demandasInfo e intento asignarle la cantidad de ranuras que quiere
//        // si no puede con esa ruta, intenta con la siguiente ruta mas corta, etc
//        // y si no puede asignar las ranuras con ninguna de sus rutas, suma una demanda bloqueada a solucion
//        for (DemandaInfo demanda : mayores) {
//
//
//            demandaNro = demanda.getDemandaId();
//            bloqueado = true;
//
//            for (j = 0; j < demandasInfo.size(); j++){
//                if (demandasInfo.get(j).getDemandaId().equals(demandaNro)){
//                    if (asignarRanuras(solucion, j, demandasInfo)){
//                        j = demandasInfo.size();
//                        bloqueado = false;
//                    }
//                }
//            }
//
//            if (bloqueado){
//                agregarBloqueado(solucion, demanda);
//                solucion.setCantBloq(solucion.getCantBloq() + 1);
//            }
//        }
//
//        calcularFitness(solucion, demandasInfo);
//        solucion.setCosto(solucion.getSaltos() * demandasInfo.get(0).getTraf());
//        solucion.setSaltos(solucion.getSaltos());
//        return solucion;
//
//    }
//
//    public static List<DemandaInfo> llenarDemandInfo (String archivo) throws FileNotFoundException {
//
//        List<DemandaInfo> demandasInfo = new ArrayList<DemandaInfo>();
//        demandasInfo.addAll(AGHelper.leerDemandasInfo(archivo));
//
//        return demandasInfo;
//
//    }
//
//    public static void ordenar(List<DemandaInfo> demandasInfo){
//
//        DemandaInfo aux;
//        int j;
//
//        for (int i = 0; i < demandasInfo.size(); i++) {
//            for (j = i + 1; j < demandasInfo.size(); j++) {
//                if (demandasInfo.get(i).getX() < demandasInfo.get(j).getX()){
//                    aux = demandasInfo.get(i);
//                    demandasInfo.set(i, demandasInfo.get(j));
//                    demandasInfo.set(j, aux);
//                }
//            }
//        }
//
//    }
//
//    public static boolean asignarRanuras (Solucion solucion, int rutaNro, List<DemandaInfo> demandasInfo){
//
//        boolean aplica = false;
//        List<Boolean> ranuras = new ArrayList<Boolean>();
//        List<Integer> posicionesLibres = new ArrayList<Integer>();
//        int r, cantRanurasLibres;
//
//        DemandaInfo demandaInfo = demandasInfo.get(rutaNro);
//        int nodoPrimero = demandaInfo.getRuta().get(0);
//        int nodoSegundo = demandaInfo.getRuta().get(1);
//
//        // recorrer todos los enlaces, y guardar en una lista de listas las ranuras que correspondan a los enlaces de mi ruta.
//        // en la lista lo que voy a guardar es si mi ranura R == 1, elimino la posicion que contenga
//        // si es R == 0, agrego a mi map un
//        for (int i = 0; i < solucion.getEnlaces().size(); i++) {
//            if (solucion.getEnlaces().get(i).getInicio() == nodoPrimero && solucion.getEnlaces().get(i).getFin() == nodoSegundo){
//                ranuras = solucion.getEnlaces().get(i).getRanuras();
//                break;
//            }
//        }
//
//        // Una vez que tenga la lista, del primer enlace busco su primera ranura libre, verifico que las X siguientes esten libres.
//        // Si es asi, agrego esa primera ranura a una una lista de posibles soluciones. Aplico esto para todas las siguientes ranuras libres.
//        // Para el siguiente enlace, obtengo de la lista de posibles soluciones la primera ranura, verifico si esa ranura existe en mi lista de ranuras,
//
//        obtenerRanurasLibres(ranuras, posicionesLibres);
//        cantRanurasLibres = posicionesLibres.size();
//
//        for (int i = 0; i < cantRanurasLibres; i++) {
//            r = (int)(Math.random() * posicionesLibres.size());
//            aplica = aplicaRanuras(posicionesLibres.get(r), rutaNro, solucion.getEnlaces(), demandasInfo);
//
//            if (aplica){
//                agregarRanurasASolucion(solucion, rutaNro, posicionesLibres.get(r), demandasInfo);
//                return true;
//            }
//            posicionesLibres.remove(r);
//            cantRanurasLibres--;
//        }
//
//        return false;
//
//    }
//
//    public static void agregarRanurasASolucion(Solucion solucion, int rutaNro, int primeraRanura, List<DemandaInfo> demandasInfo) {
//
//        DemandaInfo demandaInfo = demandasInfo.get(rutaNro);
//        int ranurasSolicitadas = demandaInfo.getTraf();
//        int nodoInicio, nodoFin, ubicacion, j;
//        boolean existe = false;
//        int posicion = -1;
//
//        Ruteo ruteo = new Ruteo();
//        List<Integer> ranurasUsadas = new ArrayList<Integer>();
//
//        // crea una lista de la posicion de las ranuras que usa cada enlace
//        for (int i = primeraRanura; i < ranurasSolicitadas + primeraRanura; i++) {
//            ranurasUsadas.add(i);
//        }
//
//        // marca en la solucion cuales son las ranuras que van a ser ocupadas por esta demanda
//        for (int i = 0; i < demandaInfo.getRuta().size() - 1; i++) {
//            nodoInicio = demandaInfo.getRuta().get(i);
//            nodoFin = demandaInfo.getRuta().get(i + 1);
//            ubicacion = ubicarEnlace(nodoInicio, nodoFin, solucion.getEnlaces());
//
//            for (j = primeraRanura; j < ranurasSolicitadas + primeraRanura; j++) {
//                solucion.getEnlaces().get(ubicacion).getRanuras().set(j, true);
//            }
//        }
//
//        // crea el objeto que cubre esta demanda y agrega a solucion
//        for (int i = 0; i < solucion.getRuteos().size(); i++) {
//            if (solucion.getRuteos().get(i).getDemandaId() == demandaInfo.getDemandaId()) {
//                existe = true;
//                posicion = i;
//                break;
//            }
//        }
//
//        if (existe){
//            solucion.getRuteos().get(posicion).setRanurasUsadas(ranurasUsadas);
//        } else {
//            ruteo.setDemandaId(demandaInfo.getDemandaId());
//            ruteo.setOriden(demandaInfo.getOrigen());
//            ruteo.setDestino(demandaInfo.getDestino());
//            ruteo.setRanurasUsadas(ranurasUsadas);
//            ruteo.setRutaNro(rutaNro);
//
//            solucion.getRuteos().add(ruteo);
//        }
//    }
//
//    public static boolean aplicaRanuras(int primeraRanura, int rutaNro, List<Enlace> enlaces, List<DemandaInfo> demandasInfo) {
//
//        DemandaInfo demandaInfo = demandasInfo.get(rutaNro);
//        int ranurasSolicitadas = demandaInfo.getTraf();
//        int nodoInicio, nodoFin, posicion, j;
//        int limite = 0;
//
//        for (int i = 0; i < demandaInfo.getRuta().size() - 1; i++) {
//            nodoInicio = demandaInfo.getRuta().get(i);
//            nodoFin = demandaInfo.getRuta().get(i + 1);
//            posicion = ubicarEnlace(nodoInicio, nodoFin, enlaces);
//            limite = ranurasSolicitadas + primeraRanura;
//
//            for (j = primeraRanura; j < limite; j++) {
//                // si recibe como primera ranura una de las ultimas y sumando las ranuras solicitadas alcanza mas del total de ranuras
//                // envia falso porque no aplica
//                if (j == enlaces.get(posicion).getRanuras().size() || enlaces.get(posicion).getRanuras().get(j)){
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }
//
//    public static int ubicarEnlace (int inicio, int fin, List<Enlace> enlaces) {
//
//        for (int i = 0; i < enlaces.size(); i++) {
//            if (enlaces.get(i).getInicio() == inicio && enlaces.get(i).getFin() == fin){
//                return i;
//            }
//        }
//
//        return -1;
//    }
//
//
//
//    /*
//    * Aca empieza el purete
//    *
//    * */
//
//    public static List<Solucion> ga(List<List<Boolean>> topologia, int cantSolucionesIniciales, int totalRanuras, int tiempoLimite, double probabilidaMutacion, List<DemandaInfo> demandasInfo) throws FileNotFoundException {
//
//        int j, reproductor1 = -1, reproductor2 = -1;
//        Solucion hijo1 = new Solucion(), hijo2 = new Solucion(); // inicializar con enlaces con ranuras vacias
//
//        List<Solucion> poblacionInicial = generarPoblacionInicial(topologia, cantSolucionesIniciales, totalRanuras, demandasInfo);
//        List<Solucion> poblacionActual = new ArrayList<Solucion>();
//        poblacionActual.addAll(poblacionInicial);
//        List<Solucion> poblacionNueva;
//        List<Solucion> aux ;
//        Random rnd = new Random();
//        int tiempoMax = tiempoLimite * 60 * 1000; // para pasar a segundos y luego a milisegundos
//
//        Long tiempoInicio = System.currentTimeMillis();
//        while ((System.currentTimeMillis() - tiempoInicio) < tiempoMax) {
//            aux = new ArrayList<Solucion>();
//            poblacionNueva = new ArrayList<Solucion>();
//
//            for (j = 0; j < poblacionInicial.size()/2; j++){
//
//                hijo1 = new Solucion();
//                hijo2 = new Solucion();
//
//                // elegir reproductores aleatoriamente
//                while(reproductor1 == reproductor2){
//                    reproductor1 = (int)(rnd.nextDouble() * poblacionActual.size());
//                    reproductor2 = (int)(rnd.nextDouble() * poblacionActual.size());
//                }
//
//                cruce (poblacionActual.get(reproductor1), poblacionActual.get(reproductor2), hijo1, hijo2, topologia, totalRanuras, demandasInfo);
//
//                // mantener poblacion actual para comparar con los hijos al final
//                // pero eliminar los reproductores ya elegidos para no repetir
//                aux.add(poblacionActual.get(reproductor1));
//                aux.add(poblacionActual.get(reproductor2));
//
//                if (reproductor1 < reproductor2){
//                    poblacionActual.remove(reproductor2);
//                    poblacionActual.remove(reproductor1);
//                } else {
//                    poblacionActual.remove(reproductor1);
//                    poblacionActual.remove(reproductor2);
//                }
//
//                mutacion (hijo1, probabilidaMutacion, topologia, totalRanuras, demandasInfo);
//                mutacion (hijo2, probabilidaMutacion, topologia, totalRanuras, demandasInfo);
//
//                poblacionNueva.add(hijo1);
//                poblacionNueva.add(hijo2);
//
//                reproductor1 = -1;
//                reproductor2 = -1;
//
//            }
//
//            elegirMejores (poblacionActual, aux, poblacionNueva);
//
//        }
//
//        return poblacionActual;
//
//    }
//
//    public static void cruce (Solucion reproductor1, Solucion reproductor2, Solucion hijo1, Solucion hijo2, List<List<Boolean>> topologia, Integer totalRanuras, List<DemandaInfo> demandasInfo) {
//        int cantRutas = reproductor1.getRuteos().size() <= reproductor2.getRuteos().size() ? reproductor1.getRuteos().size() : reproductor2.getRuteos().size();
//        List<Ruteo> ruteo1 = new ArrayList<Ruteo>();
//        List<Ruteo> ruteo2 = new ArrayList<Ruteo>();
//        Ruteo ruteo = new Ruteo();
//        Random rnd = new Random();
//        int padreSelecto;
//
//
//        for (int i = 0; i < cantRutas; i++) {
//            padreSelecto = rnd.nextInt(2) + 1;
//
//            if (padreSelecto == 1){
//                ruteo = new Ruteo();
//                reproductor1.getRuteos().get(i).clonar(ruteo);
//                ruteo1.add(ruteo);
//                ruteo = new Ruteo();
//                reproductor2.getRuteos().get(i).clonar(ruteo);
//                ruteo2.add(ruteo);
//            } else {
//                ruteo = new Ruteo();
//                reproductor2.getRuteos().get(i).clonar(ruteo);
//                ruteo1.add(ruteo);
//                ruteo = new Ruteo();
//                reproductor1.getRuteos().get(i).clonar(ruteo);
//                ruteo2.add(ruteo);
//            }
//        }
//
//        hijo1.setRuteos(ruteo1);
//        hijo2.setRuteos(ruteo2);
//
//        hijo1.setEnlaces(generarListaInicialRanuras(totalRanuras, topologia));
//        hijo2.setEnlaces(generarListaInicialRanuras(totalRanuras, topologia));
//
//        recalcularRanuras (hijo1, demandasInfo);
//        recalcularRanuras (hijo2, demandasInfo);
//
//        calcularFitness (hijo1, demandasInfo);
//        calcularFitness (hijo2, demandasInfo);
//
//    }
//
//    public static void mutacion (Solucion solucion, double probabilidadMutacion, List<List<Boolean>> topologia, Integer totalRanuras, List<DemandaInfo> demandasInfo) {
//        List<Integer> posiciones;
//        int j, r;
//
//        for (int i = 0; i < solucion.getRuteos().size(); i++) {
//            if ((Math.random()) <= probabilidadMutacion) {
//                posiciones = new ArrayList<Integer>();
//                for (j = 0; j < demandasInfo.size(); j++) {
//                    if (demandasInfo.get(j).getDemandaId() == solucion.getRuteos().get(i).getDemandaId()) {
//                        posiciones.add(j);
//                    }
//                }
//
//                r = (int)(Math.random() * posiciones.size());
//
//                solucion.getRuteos().get(i).setRutaNro(posiciones.get(r));
//            }
//        }
//
//        solucion.setEnlaces(generarListaInicialRanuras(totalRanuras, topologia));
//        solucion.setCantBloq(0);
//        solucion.setFitness(0.0);
//        recalcularRanuras(solucion, demandasInfo);
//        calcularFitness (solucion, demandasInfo);
//    }
//
//    public static void recalcularRanuras (Solucion solucion, List<DemandaInfo> demandasInfo) {
//        int j, rutaNro;
//
//        // vaciar las ranuras usadas por cada ruta
//        for (int i = 0; i < solucion.getRuteos().size(); i++) {
//            for (j = solucion.getRuteos().get(i).getRanurasUsadas().size() - 1; j >= 0; j-- ) {
//                solucion.getRuteos().get(i).getRanurasUsadas().remove(j);
//            }
//        }
//
//        for (int i = 0; i < solucion.getRuteos().size(); i++) {
//
//            rutaNro = solucion.getRuteos().get(i).getRutaNro();
//            if (rutaNro > -1) {
//                if (!asignarRanuras(solucion, rutaNro, demandasInfo)) {
//                    solucion.setCantBloq(solucion.getCantBloq() + 1);
//                    solucion.getRuteos().get(i).setRutaNro(-1);
//                }
//            } else {
//                solucion.setCantBloq(solucion.getCantBloq() + 1);
//            }
//
//        }
//    }
//
//    public static void calcularFitness (Solucion solucion, List<DemandaInfo> demandasInfo) {
//        DemandaInfo demandaInfo;
//        Double fitness = 0.0;
//
//        for (int i = 0; i < solucion.getRuteos().size(); i++) {
//            if (solucion.getRuteos().get(i).getRutaNro() > -1) {
//                demandaInfo = demandasInfo.get(solucion.getRuteos().get(i).getRutaNro());
//                fitness = fitness + demandaInfo.getX();
//            }
//        }
//
//        solucion.setFitness(fitness);
//    }
//
//    public static void elegirMejores (List<Solucion> poblacionResultado, List<Solucion> poblacionActual, List<Solucion> poblacionNueva) {
//        int cantSoluciones = poblacionActual.size();
//        List<Solucion> solucionesCompletas = new ArrayList<Solucion>();
//
//        solucionesCompletas.addAll(poblacionActual);
//        solucionesCompletas.addAll(poblacionNueva);
//        ordenarPorBloqueados(solucionesCompletas);
//
//        poblacionActual = new ArrayList<Solucion>();
//        poblacionActual.addAll(solucionesCompletas);
//
//        ordenarPorFitness (poblacionActual);
//
//        for (int i = 0; i < cantSoluciones; i++) {
//            poblacionResultado.add(poblacionActual.get(i));
//        }
//        System.out.println("Bloqueo: " + poblacionResultado.get(0).getCantBloq());
//        System.out.println("Fitness: " + poblacionResultado.get(0).getFitness());
//        System.out.println("Satos: ");
//        System.out.println("Espectro: ");
//
//    }
//
//    public static void ordenarPorBloqueados (List<Solucion> soluciones) {
//
//        Solucion aux;
//        int j;
//
//        for (int i = 0; i < soluciones.size(); i++) {
//            for (j = i + 1; j < soluciones.size(); j++) {
//                if (soluciones.get(i).getCantBloq() > soluciones.get(j).getCantBloq()){
//                    aux = soluciones.get(i);
//                    soluciones.set(i, soluciones.get(j));
//                    soluciones.set(j, aux);
//                }
//            }
//        }
//
//    }
//
//    public static void ordenarPorFitness (List<Solucion> soluciones) {
//
//        List<Solucion> gruposBloq = new ArrayList<Solucion>();
//        Solucion aux;
//        int j, cantidad = 0, inicio = -1;
//
//        if (soluciones.get(soluciones.size()/2).getCantBloq() == soluciones.get((soluciones.size()/2) -1 ).getCantBloq()) {
//            for (int i = 0; i < soluciones.size(); i++) {
//                if (soluciones.get(soluciones.size()/2).getCantBloq() == soluciones.get(i).getCantBloq()) {
//                    if (inicio < 0) {
//                        inicio = i;
//                    }
//                    cantidad++;
//                    gruposBloq.add(soluciones.get(i));
//                }
//            }
//        }
//
//        for (int i = 0; i < cantidad; i++) {
//            for (j = i + 1; j < cantidad; j++) {
//                if (gruposBloq.get(i).getFitness() > gruposBloq.get(j).getFitness()){
//                    aux = gruposBloq.get(i);
//                    gruposBloq.set(i, gruposBloq.get(j));
//                    gruposBloq.set(j, aux);
//                }
//            }
//        }
//
//        for (int i = 0; i < cantidad; i++) {
//            soluciones.set(i + inicio, gruposBloq.get(i));
//        }
//
//    }
//
//    /*
//    * Metodos auxiliares
//    * */
//
//    public static List<Enlace> generarListaInicialRanuras (int cantRanuras, List<List<Boolean>> topologia) {
//        int i, j, k;
//        Enlace enlace;
//        List<Enlace> enlaces = new ArrayList<Enlace>();
//        List<Boolean> ranuras = new ArrayList<Boolean>();
//
//        for (i = 0; i < topologia.get(0).size(); i++ ) {
//            for (j = 0; j < topologia.get(0).size(); j++ ) {
//                if (topologia.get(i).get(j)){
//                    enlace = new Enlace();
//                    ranuras = new ArrayList<Boolean>();
//                    for (k = 0; k < cantRanuras; k++) {
//                        ranuras.add(false);
//                    }
//
//                    enlace.setInicio(i);
//                    enlace.setFin(j);
//                    enlace.setRanuras(ranuras);
//
//                    enlaces.add(enlace);
//                }
//            }
//        }
//
//        return enlaces;
//    }
//
//    public static List<DemandaInfo> obtenerDemandasMayores(List<DemandaInfo> demandasInfo) {
//        List<DemandaInfo> aux = new ArrayList<DemandaInfo>();
//        DemandaInfo demandaInfo;
//        int i = 0;
//
//        while (i < demandasInfo.size()) {
//            demandaInfo = demandasInfo.get(i);
//            while (i < demandasInfo.size() && demandaInfo.getDemandaId().equals(demandasInfo.get(i).getDemandaId())) {
//                if (demandasInfo.get(i).getX() > demandaInfo.getX()) {
//                    demandaInfo = demandasInfo.get(i);
//                }
//                i++;
//            }
//            aux.add(demandaInfo);
//        }
//
//        return aux;
//    }
//
//    public static void guardarEnArchivo(List<Solucion> conjuntoSoluciones, int corridaNumero, List<DemandaInfo> demandasInfo, String archivo, Long tiempoInicial) throws IOException {
//
//        int i, j, k, solucionNumero;
//        DemandaInfo demandaInfo;
//        int corridaNro = corridaNumero + 1;
//        File f = new File(pathArchivo + archivo + "-corrida_" + corridaNro + ".txt");
//        FileWriter fw = new FileWriter(f);
//        int tiempoTotal = 0;
//
//        try{
//            BufferedWriter bw = new BufferedWriter(fw);
//            PrintWriter wr = new PrintWriter(bw);
//
//            for (i = 0; i < conjuntoSoluciones.size(); i++) {
//                solucionNumero = i + 1;
//                wr.write("\nSolucion numero: " + solucionNumero);
//                wr.write("\nCantidad de bloqueados: " + conjuntoSoluciones.get(i).getCantBloq());
//                wr.write("\nFitness: " + conjuntoSoluciones.get(i).getFitness());
//                wr.write("\nRanuras \tOrigen \tDestino \tRuta");
//
//                for (j = 0; j < conjuntoSoluciones.get(i).getRuteos().size(); j++) {
//                    wr.write("\n\t\t" + conjuntoSoluciones.get(i).getRuteos().get(j).getOriden());
//                    wr.write("\t" + conjuntoSoluciones.get(i).getRuteos().get(j).getDestino());
//
//                    if (conjuntoSoluciones.get(i).getRuteos().get(j).getRutaNro() > -1) {
//                        demandaInfo = demandasInfo.get(conjuntoSoluciones.get(i).getRuteos().get(j).getRutaNro());
//
//                        wr.write("\t\t" + demandaInfo.getRuta().get(0));
//                        for (k = 1; k < demandaInfo.getRuta().size(); k++) {
//                            wr.write(" - " + demandaInfo.getRuta().get(k));
//                        }
//
//                        wr.write("\n" + conjuntoSoluciones.get(i).getRuteos().get(j).getRanurasUsadas().get(0));
//                        for (k = 1; k < conjuntoSoluciones.get(i).getRuteos().get(j).getRanurasUsadas().size(); k++) {
//                            wr.write("\n" + conjuntoSoluciones.get(i).getRuteos().get(j).getRanurasUsadas().get(k));
//                        }
//                    } else {
//                        wr.write("\t\tBloqueado");
//                        wr.write("\t\tBloqueado");
//                    }
//
//                }
//
//                wr.write("\n\n");
//
//            }
//
//            tiempoTotal = (int) (((System.currentTimeMillis() - tiempoInicial) / 100) / 60);
//            wr.write("\nTiempo Total: " + tiempoTotal);
//            wr.close();
//            bw.close();
//        } catch (IOException e){
//
//        }
//
//
//    }
//
//    public static void obtenerRanurasLibres (List<Boolean> ranuras, List<Integer> posicionesLibres) {
//        for (int i = 0; i < ranuras.size(); i++ ) {
//            if (!ranuras.get(i)) {
//                posicionesLibres.add(i);
//            }
//        }
//
//    }
//
//    public static void agregarBloqueado (Solucion solucion, DemandaInfo demandaInfo) {
//        Ruteo ruteo = new Ruteo();
//
//        ruteo.setDemandaId(demandaInfo.getDemandaId());
//        ruteo.setOriden(demandaInfo.getOrigen());
//        ruteo.setDestino(demandaInfo.getDestino());
//        ruteo.setRutaNro(-1);
//
//        solucion.getRuteos().add(ruteo);
//    }
//
//}

//package jmetal.metaheuristics.ga;
//
//import jmetal.metaheuristics.ga.objects.Asignacion;
//import jmetal.metaheuristics.ga.objects.Opcion;
//import jmetal.metaheuristics.ga.objects.Ranuras;
//import jmetal.metaheuristics.ga.objects.Solucion;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by ysapy on 15/02/16.
// */
//public class AG_test2 {
//
//    public static int cantVueltas = 1;
//    public static int cantSoluciones = 2;
//    public static int treintaPorciento = 2;
//    public static List<Opcion> opciones =  new ArrayList<Opcion>();
//    public static Solucion solucion = new Solucion();
//    public static int cantRanuras = 30;
//
//    public static void main(String [] args) throws IOException {
//
//        int i, j, k, m, n, o, p, q, r, t;
//        int bloqueados = 0;
//        boolean funciona = false;
//        boolean paraTodos = false;
//        List<Integer> list = new ArrayList<Integer>();
//        Ranuras ranura;
//        Asignacion asignacion;
//        List<Asignacion> asignaciones = new ArrayList<Asignacion>();
////        int cantRanuras = solucion.getEnlaces().get(0).getEnlaces().size();
//
//        solucion = cargarSolucion();
//
//        for (i = 0; i < cantVueltas; i ++){
//
//            for (j = 0; j < cantSoluciones; j++){
//
//                int s = 0;
//                int d = 0;
//
//                for (k = 0; k < treintaPorciento; k ++){
//
//                    for (m = 0; m < opciones.size(); m++){
//                        if (s != opciones.get(m).getS() || d != opciones.get(m).getD()){
//                            s = opciones.get(m).getS();
//                            d = opciones.get(m).getD();
//                            paraTodos = false;
//
//                            for (n = 0; n < opciones.get(m).getRuta().size()-1; n++ ){
//
//                                for (o = 0; o < solucion.getEnlaces().size()-1; o++){
//                                    if (solucion.getEnlaces().get(o).getP() == opciones.get(m).getRuta().get(n) || solucion.getEnlaces().get(o).getS() == opciones.get(m).getRuta().get(n+1)){
//                                        ranura = solucion.getEnlaces().get(o);
//                                        funciona = false;
//
//                                        for (p = 0; p < ranura.getRanuras().size(); p++){
//                                            list = new ArrayList<Integer>();
//                                            if (!ranura.getRanuras().get(p)){
//                                                t = 0;
//                                                funciona = true;
//
//                                                while (t < opciones.get(m).getTraf() && funciona){
//                                                    if (ranura.getRanuras().get(p + t)){
//                                                        funciona = false;
//                                                    }else{
//                                                        list.add(p + t);
//                                                        t++;
//                                                    }
//                                                }
//
//                                                if (funciona){
//                                                    paraTodos = verificarPaths(list, opciones.get(m).getRuta());
//                                                }
//                                                if (paraTodos){
//                                                    asignacion = crearAsignacion(opciones.get(m).getS(), opciones.get(m).getD(), n, list);
//                                                    System.out.println(asignacion);
//                                                    asignaciones.add(asignacion);
//                                                    System.out.println(asignaciones);
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        if (!paraTodos)
//                            bloqueados++;
//                        else {
//                            for (r = 0; r < opciones.get(m).getRuta().size()-1; r++){
//                                for (q = 0; q < solucion.getEnlaces().size(); q++){
//                                    if (solucion.getEnlaces().get(q).getP() == opciones.get(m).getRuta().get(r) && solucion.getEnlaces().get(q).getS() == opciones.get(m).getRuta().get(r+1)){
//                                        for (int u = 0; u < solucion.getEnlaces().get(q).getRanuras().size(); u++){
//                                            solucion.getEnlaces().get(q).getRanuras().add(u, true);
//                                        }
//                                        q = solucion.getEnlaces().size();
//                                    }
//
//                                }
//
//
//                            }
//
//                        }
//                        System.out.println(solucion);
//                    }
//                }
//            }
//        }
//
//    }
//
//    public static boolean verificarPaths(List<Integer> list, List<Integer> ruta){
//
//        boolean funciona = false;
//        int k;
//
//        for (int p = 0; p < ruta.size(); p++){
//            for (int i = 0; i < solucion.getEnlaces().size()-1; i ++){
//                if (solucion.getEnlaces().get(i).getP() == opciones.get(ruta.get(p)).getS() && solucion.getEnlaces().get(i).getS() == opciones.get(ruta.get(p)).getD()) {
//                    funciona = true;
//                    k = 0;
//
//                    while (funciona) {
//                        if (solucion.getEnlaces().get(i).getRanuras().get(k))
//                            return false;
//                        k++;
//                    }
//                }
//            }
//        }
//
//        return true;
//
//    }
//
//    public static Asignacion crearAsignacion(int s, int d, int n, List<Integer> list){
//        Asignacion asignacion = new Asignacion();
//        List<Boolean> ranuras = new ArrayList<Boolean>();
//        int i;
//
//        asignacion.setS(s);
//        asignacion.setD(d);
//        asignacion.setRuta(n);
//
//        for (i = 0; i < cantRanuras; i++){
//            ranuras.add(false);
//        }
//
//        for (i = 0; i < list.size(); i++){
//            ranuras.add(list.get(i), true);
//        }
//
//        asignacion.setRanuras(ranuras);
//        System.out.println(asignacion);
//        return asignacion;
//    }
//
//
//}

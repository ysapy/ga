package ga.dijkstra.model;

public class Edge  {
    private String id;
    private Vertex source;
    private Vertex destination;
    private int weight;

    public Edge(String id, Vertex source, Vertex destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Edge () {
        this.id = "";
        this.source = null;
        this.destination = null;
        this.weight = -1;
    }

    public String getId() {
        return id;
    }

    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }

    public int getWeight() {
        return weight;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

  @Override
    public String toString() {
        return source + " " + destination;
    }


} 
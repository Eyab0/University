package sample;

public class Edge {
    private Vertex Source;
    private Vertex Destination;
    private final int airDistance;
    private final int walkDistance;
    private final int carDistance;

    public Edge(Vertex source, Vertex destination, int airDistance, int walkDistance, int carDistance) {
        super();
        Source = source;
        Destination = destination;
        this.airDistance = airDistance;
        this.walkDistance = walkDistance;
        this.carDistance = carDistance;
    }

    public Vertex getSource() {
        return Source;
    }

    public void setSource(Vertex source) {
        Source = source;
    }

    public Vertex getDestination() {
        return Destination;
    }

    public void setDestination(Vertex destination) {
        Destination = destination;
    }

    public int getAirDistance() {
        return airDistance;
    }

    public int getWalkDistance() {
        return walkDistance;
    }

    public int getCarDistance() {
        return carDistance;
    }
}
package sample;

import javafx.scene.shape.Circle;

import java.util.Objects;

public class Vertex implements Comparable<Vertex> {
    private String Name;
    private double X;
    private double Y;
    private Circle c;

    public Vertex(String name, double x, double y) {
        Name = name;
        X = x;
        Y = y;
    }

    public Circle getC() {
        return c;
    }

    public void setC(Circle c) {
        this.c = c;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.X, X) == 0 && Double.compare(vertex.Y, Y) == 0 && Objects.equals(Name, vertex.Name) && Objects.equals(c, vertex.c);
    }

    @Override
    public String toString() {
        return "City=" + Name;

    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, X, Y, c);
    }

    @Override
    public int compareTo(Vertex o) {
        return this.Name.compareTo(o.Name);
    }
}

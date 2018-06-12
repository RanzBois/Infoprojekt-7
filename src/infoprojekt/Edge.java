/*
 *
 */
package infoprojekt;

/**
 * Diese Klasse repräsentiert eine Kante, also eine Verbindung zwischen zwei
 * Konten, dem Start- und Zielknten.
 *
 */
public class Edge implements Comparable<Edge>{

    private final Node origin;
    private final Node target;

    // Abstand zwischen Start- und Zielknoten
    private final double cost;

    public Edge(Node a, Node b)
    {
        this.origin = a;
        this.target = b;
        
        int dx = a.getX()-b.getX();
        int dy = a.getY()-b.getY();
        cost = Math.sqrt( dx * dx + dy * dy);
        
    }
    
    /**
     * Diese Methode liefert den Sartknotwn der Kante zurück.
     *
     * @return Startknoten
     */
    Node getOrigin() {
        return origin;
    }

    /**
     * Diese Methode liefert den Zielknoten der Kante zurück.
     *
     * @return Zielknoten
     */
    Node getTarget() {
        return target;
    }

    /**
     * Diese Methode liefert die Kosten, zum Beispiel den Abstand , zwischen dem
     * Start- und Zielknten dieser Kante zurück.
     *
     * @return Kosten der Kante
     */
    public double getCost() {
        return cost;
    }

    
    @Override
    public String toString()
    {
        return "[" + origin + "->" + target + " " + cost +"]";
    }

    /**
     * Hier bringen wir der Klasse Edge bei, wie man zwei Edge Objekte
     * miteinander vergleicht. Ansonsten könnten wir die Kanten nicht nach
     * Kosten sotieren.
     * @param other die andere Kante, mit der wir uns vergleichen
     */
    @Override
    public int compareTo(Edge other) {
        return (int)(cost - other.getCost());
    }
}

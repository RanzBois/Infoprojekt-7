/**
 * Project:
 *
 *  Minimal Spanning Trees
 *  ESG Kornwestheim, KS1
 *
 * Authors:
 *
 * -´Aykon Kücük
 * - Leon Broßwitz
 *
 * (c) June 2018
 */
package infoprojekt;

/**
 * Diese Klasse repräsentiert eine Kante, also eine ungerichtete Verbindung
 * zwischen zwei Knoten. Neben denbeiden Knoten besitzt jede Kante einen
 * Kostenwert, der in unserem Fall der Kantenlänge entspricht. Diese Klass
 * implemntiert ie Schnittstelle Comparable, damit wir in einem Array Kanten
 * nach Kosten aufsteigend sortieren können.
 *
 */
public class Edge implements Comparable<Edge> {

    private final Node origin;   // Startknoten der Kante
    private final Node target;   // Zielknoten der Kante

    // Abstand zwischen Start- und Zielknoten, also Länge
    private final double cost;

    /**
     * Mit diesem Konstruktor erzeugen wir eine leere Kante, wo weder Start-
     * noch Zielknoten definiert ist.
     */
    public Edge() {
        origin = null;
        target = null;
        cost = 0.0;
    }

    /**
     * Mit diesem Konstruktor erzeugen wir eine Kante, die vom Knaoten a zum
     * Knoten b geht. Die Kosen übergeben wir nicht, da wir die Länge zwischen
     * den beien Knoten berechnen und als Kostenwert speichern.
     *
     * @param a Startknoten
     * @param b Zielnoten
     */
    public Edge(Node a, Node b) {

        origin = a;
        target = b;

        // Die Kosten setzen wir gleich dem Abstand zwischen den beiden Knoten
        final int dx = b.getX() - a.getX();
        final int dy =  b.getY() -a.getY();
        cost = Math.sqrt(dx * dx + dy * dy);

    }

    /**
     * Diese Methode liefert den ersten Knoten (Startknoten) der Kante zurück.
     *
     * @return Startknoten
     */
    Node getOrigin() {
        return origin;
    }

    /**
     * Diese Methode liefert den zweiten Konten (Zielknten) der Kante zurück.
     *
     * @return Zielknoten
     */
    Node getTarget() {
        return target;
    }

    /**
     * Diese Methode liefert die Kosten, zum Beispiel den Abstand zwischen dem
     * Start- und Zielknten dieser Kante zurück.
     *
     * @return Kosten der Kante
     */
    public double getCost() {
        return cost;
    }

    /**
     * Diese Methode implementieren wir, damit wir eine Kante schön lesbar in
     * der Kosole ausgeben können. Das hilft vor allem, wenn man im Debug Modus
     * nach Fehlern sucht.
     *
     * @return Kante in lesbarer Form "(Startknoten->Zielkonten, Kosten)"
     */
    @Override
    public String toString() {
        return "[" + origin + "->" + target + " " + cost + "]";
    }

    /**
     * Hier bringen wir der Klasse Edge bei, wie man zwei Edge Objekte
     * miteinander vergleicht. Ansonsten könnten wir ein Kanten Array nicht nach
     * Kosten sotieren.
     *
     * @param other die andere Kante, mit der wir uns vergleichen
     */
    @Override
    public int compareTo(Edge other) {
        return (int) (cost - other.getCost());
    }
}

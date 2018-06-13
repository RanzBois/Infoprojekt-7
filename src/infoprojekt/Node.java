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
 *
 * Ein Konten (node) ist das Ende oder der Anfang einer Kante (Edge). Jeder
 * Knoten hate eine X und Y Koordinate, welche die Position des Knotens in einem
 * Graphen repräsentieren. Zudem hat jeder Knoten eine eindeutige ID (positive
 * Ganzzahl).
 *
 */
public class Node {

    // Diese Konstante führen wir ein, damit die Zeichengröße (Durchmesser) eines Kontens definiert ist.
    public static int width = 10;

    // Eindeutige ID des Knotens
    private final int id;

    // X Koordinate des Knotens
    private final int x;

    // Y Koordinate des Knotens
    private final int y;

    /**
     * Mit diesem Konstruktor erzeugen wir einen neuen Konten, der neben der ID
     * noch X und Y Koordinate übergeben bekommt.
     *
     * @param id ID des neuen Knotens
     * @param x x Koordinate des neuen Knoten
     * @param y y Koordinate des neuen Knoten
     */
    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Mit dieser Methode ereugen wir einen Knoten mit der übergebenen ID, der
     * zufällig im Bereich zwischen 0 und maxX bzw. 0 und maxY platziert wird.
     * Diese Funktion wird aktuell nicht verwendet.
     *
     * @param id eindeutige ID für den zu erzeugenden Knoten
     * @param maxX maximale X Koordinate für den neuen Knoten
     * @param maxY maximale y Koordinate für den neuen Knoten
     * @return neuer zufällg platzierter Knoten
     */
    static public Node createRandomNode(int id, int maxX, int maxY) {

        // Damit unsere Knoten einen Mindestabstand (=width) haben, platzieren wir die Koordinaten auf einem Raster.
        int x = (int) (Math.random() * maxX);
        x = x / width; // abgrundet
        x = x * width; // und wieder multipliziert

        int y = (int) (Math.random() * maxY);
        y = y / width;
        y = y * width;

        return new Node(id, x, y);
    }

    ;
    
    /** 
     * Mit dieser Mthode prüfen wir, ob der übergebene Knoten sich mit diesem deckt.
     * @param other der andere Knoten
     * @return true wenn die beiden Knoten aufeinander liegen
     */
   public boolean covers(Node other) {
        return (x == other.getX()) && (y == other.getY());
    }

    /**
     * Diese Methode berechnet den Abstand zwischen diesem und dem übergebenen
     * Knoten.
     *
     * @param other
     * @return die Entfernung zwischen diesem und dem übergebenen Knoten
     */
    public double getDistance(Node other) {

        // Falls der übergebene Knoten undefiniert ist, geben wir den (Fehler-)Wert Not A Number zurück
        if (other == null) {
            return Double.NaN;
        }

        final int dx = other.getX() - x;
        final int dy = other.getY() - y;
        final double d = Math.sqrt(dx * dx + dy * dy);

        return d;

    }

    /**
     * Diese Methode gibt die ID eines Knotens zurück
     *
     * @return ID des Knotens
     */
    public int getID() {
        return id;
    }

    /**
     * Diese Methode gibt die X Koordinate eines Knotens zurück
     *
     * @return X Koordinate des Knotens
     */
    int getX() {
        return x;
    }

    /**
     * Diese Methode gibt die Y Koordinate eines Knotens zurück
     *
     * @return Y Koordinate des Knotens
     */
    int getY() {
        return y;
    }

    /**
     * Diese Methode implementieren wir, damit wir einen Knoten schön lesbar auf
     * der Konsole ausgeben können. Das hilft vor allem, wenn man im Debug Modus
     * nach Fehlern sucht.
     *
     * @return Knoten in lesbarer Form "(x,y)"
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

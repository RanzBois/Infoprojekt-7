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

import static infoprojekt.Node.width;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JProgressBar;

/**
 * Ein Graph beinhaltet die Knoten (Nodes) und die Verbindungen zwischen diesen,
 * welche wir als Kanten bezeichnen (Edges). Alle Knoten werden in einem Array
 * abgelegt. Die Katen werden ebenfalls in einm Array gespeichert.
 *
 */
public class Graph {

    private static final int EDGE_FAT_WIDTH = 4;
    private static final int EDGE_NORMAL_WIDTH = 2;

    private static DecimalFormat df = new DecimalFormat("#.00");
    /**
     * Wir speichern die Knoten eines Graphen in einem variablen Array ab, da
     * wir im nauellen Modus nicht im voraus wissen können, wieviele Knoten der
     * Benutzer anlegen wird.
     */
    private ArrayList<Node> nodes;

    /**
     * Wir speichern die Kanten eines Graphen in einem variablen Array ab, da
     * wir im nauellen Modus nicht im voraus wissen können, wieviele Konten der
     * Benutzer anlegen wird. Wir erzeugen von jedem Knoten eine Verbindung zu
     * jedem anderen Knoten. Insgesamt sind das dann (n*(n-1))/2 Kanten.
     */
    private ArrayList<Edge> edges;

    /**
     * Diese Variabe speichert die Anzahl der Knoten, die im automatischen Modus
     * erzeugt werden sollen.
     */
    private int desiredNodes;

    /**
     * In dieser Variablen speichern wir die Abmessungen unseres Zeichenfensters
     * ab.
     */
    private final Dimension dimension;

    /**
     * Die Variable gibt an, welche Zeitverzögerung wir während den einzelen
     * Simulationsschritten haben wollen.
     */
    private int delayMillis;

    /**
     * Die Variable gibt die Gesamtkosten des Minimal Aufspannenden Baumes
     * zurück
     */
    private double totalCost;

    /**
     * Dieser Konstruktor wird mit den Abmessungen unseres zeichenfensters
     * intialisiert.
     *
     * @param dimension Abmessungen des übergeordneten Panels
     */
    public Graph(Dimension dimension) {

        // wir merken uns die  Breite und Hoehe unseres Zeichenfesnters
        this.dimension = dimension;

        // In diesem Zustand sind weder Knten noch Kanten definiert
        nodes = null;
        edges = null;
    }

    /**
     * Diese Initialisierungsmethode bereitet einen Graphen mit count Konten
     * vor.
     *
     * @param count Anzahl gewünschter Knoten des graphen
     */
    public void init(int count) {

        desiredNodes = count;

        // Erzeuge das Array für unsere Knoten
        nodes = new ArrayList<Node>(desiredNodes);

        // Erzeuge das Array für alle möglichen Kanten, d.h. (n*(n-1))/2
        edges = new ArrayList<Edge>((desiredNodes * desiredNodes - 1) / 2);

    }

    /**
     * Diese Methode liefert uns die aktuelle Anzahl der Konten im Graphen
     * zurück
     *
     * @return Anzahl Knoten des Graphen
     */
    public int getNodeCount() {
        if (nodes == null)
            return 0;
        return nodes.size();
    }

    /**
     * Diese Methode liefert uns die Anzhal der gewünhscten Knoten zurück
     *
     * @return Anzahl gewünschter Knoten
     */
    public int getDesiredNodes() {
        return desiredNodes;
    }

    /**
     * Diese Methode setzt die für die Siulation gewünschte Verzögerungszeit in
     * Millisekunden.
     *
     * @param millis Verzögerungszeit zwischen zwei Simulationsschritten (in
     * Millisekunden)
     */
    public void setDelay(int millis) {
        delayMillis = millis;
    }

    /**
     * Diese Methode erzeugt einen neuen Knoten, der die übergebene ID un
     * zufällige Koordinaten bekommt.
     *
     * @param id ID des neu zu rzeugenden Knoten
     *
     */
    private Node createRandomNode(int id) {

        return Node.createRandomNode(id, (int) dimension.getWidth(), (int) dimension.getHeight());

    }

    /**
     * Diese Methode fügt einen neuen Knoten in den Graphen en.
     *
     * @param id ID des neuen Knotens
     * @param x X Koordinate des neuen Knotens
     * @param y Y Koordinate des neuen Knotens
     */
    public void addNode(int id, int x, int y) {
        // erzeuge einen neuen Knoten
        Node n = new Node(id, x, y);
        // Hänge den neuen Knoten an das Ende unseres Kontenarray
        if (nodes == null)
          init(2);
            
       
        nodes.add(n);

        // Wenn wir mindestens einen Knoten haben ...
        if (nodes.size() > 0) {
            // ... dann erzeuge eine Kante zu jedem existierenden Knoten
            for (int j = 0; j < nodes.size(); j++) {
                Edge e = new Edge(n, nodes.get(j));
                edges.add(e);
            }
        }
    }

    /**
     * Diese Methode erzeugt einen zufälligen Graphen, der genau die Anzahl der
     * eingestellten Knoten beinhaltet.
     */
    public void fillRandomly() {

        for (int idx = 0; idx < desiredNodes; idx++) {
            // erzeuge einen zufälligen Knoten
            int x = (int) (Math.random() * dimension.getWidth());
            x = (x / Node.width) * Node.width;

            int y = (int) (Math.random() * dimension.getHeight());
            y = (y / Node.width) * Node.width;

            addNode(idx, x, y);
        }
    }

    /**
     * Diese Methode zeichnet den grahen mit allen Knoten und allen Kanten vor
     * dem Auführen des MST Algorithmus
     *
     * @param g Graphik Kontext
     */
    public void draw(Graphics g, boolean withEdges) {

        Graphics2D g2d = (Graphics2D) g;

        // Zeichne alle Kanten in grau mit normaler Strichstärke
        if (withEdges) {
            g2d.setColor(Color.DARK_GRAY);

            g2d.setStroke(new BasicStroke(EDGE_NORMAL_WIDTH));

            for (int idx = 0; idx < edges.size(); idx++) {
                final Edge e = edges.get(idx);
                final Node from = e.getOrigin();
                final Node to = e.getTarget();
                g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
            }
        }

        // und erst anschliessend die Konten darüber
        g.setColor(Color.white);
        final int r = Node.width / 2;
        for (int idx = 0; idx < nodes.size(); idx++) {
            Node n = nodes.get(idx);
            Ellipse2D.Double circle = new Ellipse2D.Double(n.getX() - r, n.getY() - r, Node.width, Node.width);
            g2d.fill(circle);
        }

    }

    /**
     * Diese Methode verzögert die Ausführung im Simulationsmodus, entsprechend
     * dem igestellten delay im Millisekunden.
     */
    private void delay() {
        if (delayMillis > 0) {
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException ex) {
            }
        }
    }

    //
    /**
     * Diese lokale Klasse benötigen wir für den Kruskal Algorithmus um
     * festzustellen, ob unser Graph Zyklen hat oder nicht.
     */
    private class SubSet {

        // Verweis auf den Vater
        int parent;
        // die Gewichtung einer Kante
        int rank;
    };

    /**
     * Mit dieser (rekursiven) Hilfsfunktion bestimmen wir die Untermenge eines
     * Graphen nach der Kruskal Methode.
     *
     * @param subSets
     * @param i
     * @return
     */
    private int find(SubSet[] subSets, int i) {

        /**
         * Wir sucheh nach der Wurzel and machen iesen dann zum Vater von i
         * (Kompression des Pfades)
         */
        if (subSets[i].parent != i) {
            subSets[i].parent = find(subSets, subSets[i].parent);
        }
        return subSets[i].parent;
    }

    /**
     *
     * @param subsets
     * @param origin
     * @param target
     */
    void Union(SubSet subsets[], int origin, int target) {
        int originRoot = find(subsets, origin);
        int targetRoot = find(subsets, target);

        /**
         * Hänge den Baum mit der geringeren Gewichtung unter di Wurzel des
         * Baums mit der größeren Gewichtung
         */
        if (subsets[originRoot].rank < subsets[targetRoot].rank) {
            subsets[originRoot].parent = targetRoot;
        } else if (subsets[originRoot].rank > subsets[targetRoot].rank) {
            subsets[targetRoot].parent = originRoot;
        } /**
         * Falls beide Bäume den selben Rang (Gewichtung) haben können wir einen
         * beliebigen auswählen und diesen zur Wurzel machen und anschliessend
         * den Rang um 1 erhöhen
         */
        else {
            subsets[targetRoot].parent = originRoot;
            subsets[originRoot].rank++;
        }
    }

    /**
     * Diese Methode implementiert den Kruskal Algorithmus (veröffentlich durch
     * Joseph Kruskal im Jahr 1956) zur Bestimmung des Minimal aufspannenen
     * Baums eines Graphen. Die Grundidee des Kruskal Alorithmus besteht darin,
     * die Kanten des Graphen aufsteigender Reihenfolge, also dem ewicht oder
     * der Kosten einer Kante durchzulaufen und jede Kante zur Lösungsmenge
     * hinzuzufügen, die mit allen zuvor ausgewählten Kanten aber keinen Kreis
     * bilden. Die Kanten des Grpahen müssen ungerichtet sein, also in beide
     * Richtungen durchlaufbar sein. Der resultierende Baum ist dann minimal
     * groß.
     *
     * @param visual
     */
    public void minimalSpanningTree(Visuell visual) {

        Graphics2D g2d = (Graphics2D) visual.getPanel().getGraphics();
        /**
         * Wir setzen den "Exklusiv Oder" Zeichenmodus, damit wir beim Zeichnn
         * der Linien den Hintergrund nicht verändern, bzw. nach einem zweiten
         * Zeichenaufruf unsere Linie wieder verschwindet.
         */
        g2d.setStroke(new BasicStroke(EDGE_FAT_WIDTH));

        /**
         * Wir führen den folgenden Code in einem separeten Thread aus, damit
         * die Benutzeroberfläche nicht blockiert wird, bid diese Funktion
         * ausgeführt wurde.
         */
        new Thread(new Runnable() {

            // The main function to construct MST using Kruskal's algorithm
            @Override
            public void run() {

                // Wir sortieren die Kanten des Graphen nach den Kosten (aufsteigend)
                Collections.sort(edges);

                // Dieses Array benötigen wir, um unser Ergebnis (Kanten) zu speichern
                Edge[] mstEdges = new Edge[nodes.size()];

                // Die Gesamtkosten setzen wir hier erst einmal auf 0.
                totalCost = 0.0;
                for (int idx = 0; idx < nodes.size(); ++idx) {
                    mstEdges[idx] = new Edge();
                }

                /**
                 * Hier legen wir ein Array, welches so groß ist wie unsere
                 * Anzahl Knoten, für die zu erzuegenden Subsets an.
                 */
                SubSet[] subSets = new SubSet[nodes.size()];
                for (int idx = 0; idx < nodes.size(); ++idx) {
                    subSets[idx] = new SubSet();
                }

                // Erzeuge die Subsets 
                for (int v = 0; v < nodes.size(); ++v) {
                    subSets[v].parent = v;
                    subSets[v].rank = 0;
                }

                int pick = 0;

                int edgeIndex = 0;
                // Wir intertieren durch alle Knoten des Graphen
                while (edgeIndex < nodes.size() - 1) {

                    // Hole die nächste Kante (immer die kürzeste)
                    Edge nextEdge = edges.get(pick++);
                    // Bestimme de zugehörigen Subsets
                    int o = find(subSets, nextEdge.getOrigin().getID());
                    int t = find(subSets, nextEdge.getTarget().getID());

                    // Bestimme die Koordinaten der zugehörigen Knoten
                    final int ox = nextEdge.getOrigin().getX();
                    final int oy = nextEdge.getOrigin().getY();
                    final int tx = nextEdge.getTarget().getX();
                    final int ty = nextEdge.getTarget().getY();

                    // Färbe die gerade untersuchte Kante rot ein
                    if (ox != tx && oy != ty) {
                        g2d.setXORMode(Color.black);

                        g2d.setColor(Color.red);
                        g2d.drawLine(ox, oy, tx, ty);
                        delay();
                        // und lösche diese anschliessend wieder (exclusive or)
                        g2d.drawLine(ox, oy, tx, ty);

                    }

                    /**
                     * Falls durch das Einfügen dieser Kante ein Zyklus (Kreis)
                     * im Graph entstehen würde, fügen wir diese Kante NICHT zur
                     * Ergebnismenge hinzu.
                     */
                    if (o != t) {
                        // füge diese Kante zum Ergebnis hinzu
                        mstEdges[edgeIndex++] = nextEdge;
                        // addiere die Kosten dieser Kante zu den Gesamtkosten dazu
                        totalCost += nextEdge.getCost();
                        Union(subSets, o, t);
                        // Zeichne die gefundene MST Kante grün ein
                        g2d.setColor(Color.green);
                        g2d.setPaintMode();

                        g2d.drawLine(ox, oy, tx, ty);
                    } else {
                        /**
                         * Dieser Bereich würde ausgeführt werden, wenn der
                         * Subset einen Zyklus erzeuge würde.
                         */
                    }
                    // aktualiiere den Fortschrittsbalken
                    visual.getProgressBar().setValue(edgeIndex);
                }
                // hier geben wir in einem Textfeld die Gesamtkosten aus
                visual.getTotalEdgesLbl().setText(df.format(totalCost));
            }
        }).start();
    }
}

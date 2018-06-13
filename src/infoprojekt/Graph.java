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
     * Die Variable gibt die Gesamtkosten des Minimal Aufspannenden Baumes zurück
     */
    private double totalCost;

    /**
     * Diese lokale Klasse benötigen wir für den Kruskal Algorithmus um
     * festzustellen, ob unser graph Zyklen hat oder nicht.
     */
    private class SubSet {

        int parent;
        int rank;
    };

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
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // Zeichne alle Kanten in grau mit normaler Strichstärke
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(EDGE_NORMAL_WIDTH));

        for (int idx = 0; idx < edges.size(); idx++) {
            final Edge e = edges.get(idx);
            final Node from = e.getOrigin();
            final Node to = e.getTarget();
            g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
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

    /**
     * Mit dieser Hilfsfunktion bestimmen wir die Untermenge eines Graphen
     * nach der kruskal Methode.
     * 
     * @param subsets
     * @param i
     * @return
     */
    int find(SubSet subsets[], int i) {
        // find root and make root as parent of i (path compression)
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }

        return subsets[i].parent;
    }

    /**
     *
     * @param subsets
     * @param x
     * @param y
     */
    void Union(SubSet subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root of high rank tree
        // (Union by Rank)
        if (subsets[xroot].rank < subsets[yroot].rank) {
            subsets[xroot].parent = yroot;
        } else if (subsets[xroot].rank > subsets[yroot].rank) {
            subsets[yroot].parent = xroot;
        } // If ranks are same, then make one as root and increment
        // its rank by one
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    /**
     * Diese Methode führt den MST Algorithmus naxh Kruskal aus.
     *
     * 
     * @param visual
     */
    public void minimalSanningTree(Visuell visual) {

        Graphics2D g2d = (Graphics2D) visual.getPanel().getGraphics();
        g2d.setXORMode(Color.black);
        g2d.setStroke(new BasicStroke(EDGE_FAT_WIDTH));

        new Thread(new Runnable() {
            // The main function to construct MST using Kruskal's algorithm
            @Override
            public void run() {
                // wir sortieren unsere Kanten nach Kosten (aufsteigend)
                Collections.sort(edges);

                // brauchen wir um unser Ergebnis zu speichern
                Edge[] mstEdges = new Edge[nodes.size()];
                totalCost = 0.0;
                for (int idx = 0; idx < nodes.size(); ++idx) {
                    mstEdges[idx] = new Edge();
                }

                // 
                java.util.Arrays.sort(edges.toArray());

                // 
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

               
                int e = 0;
                while (e < nodes.size() - 1) {
                    Edge next_edge = new Edge();
                    next_edge = edges.get(pick++);

                    int x = find(subSets, next_edge.getOrigin().getID());
                    int y = find(subSets, next_edge.getTarget().getID());

                    final int ox = next_edge.getOrigin().getX();
                    final int oy = next_edge.getOrigin().getY();
                    final int tx = next_edge.getTarget().getX();
                    final int ty = next_edge.getTarget().getY();

                    // deute die gerade untersucte Kante farblich in rot an ...
                    g2d.setColor(Color.red);
                    g2d.drawLine(ox, oy, tx, ty);
                    delay();
                    // und lösche diese anschliessend wieder
                    g2d.drawLine(ox, oy, tx, ty);

                    // If including this edge does't cause cycle,
                    // include it in result and increment the index 
                    // of result for next edge
                    if (x != y) {
                        mstEdges[e++] = next_edge;
                        totalCost += next_edge.getCost();
                        Union(subSets, x, y);
                        // zeichne die gefundene MST Kante grün ein
                        g2d.setColor(Color.green);
                        g2d.drawLine(ox, oy, tx, ty);
                    }
                    // else discard the next_edge

                    visual.getProgressBar().setValue(e);
                }
             visual.getTotalEdgesLbl().setText(df.format(totalCost));

            }
        } ).start();
    }
}

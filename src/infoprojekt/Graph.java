/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoprojekt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Unser Graph beinhaltet Knoten und die Verbindungen zwischen dieses (Kanten)
 */
public class Graph {

    // Breite beim Zeichnen eines Knotens
    public static int nodeWidth = 10;

    // Die Knoten und Kanten unseres Graphen
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    private int nodeCount;
    private int edgeCount;

    private final int width;
    private final int height;
    
    HashSet<Integer> visited;
   
    private int delayMillis;

    /**
     *
     * @param width Breite des Fensters
     * @param height Höhe des Fensters
     */
    public Graph(int width, int height) {

        // wir merken uns die  Breite und Hoehe unseres Zeichenfesnters
        this.width = width;
        this.height = height;

        nodes = null;
        edges = null;
    }

    public void init(int count) {
        // Anzahl Knoten unseres Graphen
        this.nodeCount = count;

        // Erzeuge das Array für unsere Knoten
        nodes = new ArrayList<>(count);

        // Erzeuge das Array für alle möglichen Kanten, d.h. (n*(n-1))/2
        edgeCount = (nodeCount * (nodeCount - 1)) / 2;
        edges = new ArrayList<>(edgeCount);

        visited = new HashSet<>();

    }

    public void setDelay(int millis)
    {
        delayMillis = millis;
    }
    
    private Node createRandomNode(int id) {

        int x = (int) (Math.random() * width);
        x = x / nodeWidth;
        x = x * nodeWidth;

        int y = (int) (Math.random() * height);
        y = y / nodeWidth;
        y = y * nodeWidth;

        return new Node(id, x, y);
    }

    public void addNode(int id, int x, int y) {
      Node n = new Node(id, x, y);
      nodes.add(n);
        if (nodes.size() > 0) {
            // Erzeuge eine Kante zu jedem existierenden Knoten
            for (int j = 0; j < nodes.size(); j++) {
                Edge edge = new Edge(nodes.get(nodes.size()-1), nodes.get(j));
                edges.add(edge);
            }
        }
    }

    public int getNodeCount()
    {
        return nodes.size();
    }
    
    /**
     *
     */
    public void fillRandomly() {

        int idx = 0;
        while (idx < nodeCount) {
            Node node = createRandomNode(idx);
            nodes.add(node);
            if (idx > 0) {
                // Erzeuge eine Kante zu jedem existierenden Knoten
                for (int j = 0; j < idx; j++) {
                    Edge edge = new Edge(nodes.get(idx), nodes.get(j));
                    edges.add(edge);
                }
            }
            idx++;
        }
        // wir sortieren unsere Kanten nach Kosten (aufsteigend)
        Collections.sort(edges);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Zeichne alle Kanten
        
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(2));
        for (int idx = 0; idx < edges.size(); idx++) {
            Edge e = edges.get(idx);
            Node from = edges.get(idx).getOrigin();
            Node to = edges.get(idx).getTarget();
            g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
        }
        
        
        // Zeichne alle Knoten 
        g.setColor(Color.white);
        for (int idx = 0; idx < nodes.size(); idx++) {
            Node n = nodes.get(idx);

            Ellipse2D.Double circle = new Ellipse2D.Double(n.getX() - nodeWidth / 2, n.getY() - nodeWidth / 2, nodeWidth, nodeWidth);
            g2d.fill(circle);
        }

    }

    private void delay() {
        if (delayMillis > 0) {
            try {
                Thread.currentThread().sleep(delayMillis);
            } catch (InterruptedException ex) {
            }

        }
    }

    public void minimalSanningTree(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
    g2d.setXORMode(Color.black);

        int totalEdges = edges.size();
        int pos = 0;
        for (int idx = 0; idx < totalEdges; idx++) {

            // Hole die Kante mit den kleinsten Kosten
            Edge e = edges.get(pos);
            Node origin = e.getOrigin();
            Node target = e.getTarget();
            g2d.setColor(Color.red);
            g.drawLine(origin.getX(), origin.getY(), target.getX(), target.getY());
            delay();

            // wir prüfen ob die Knoten der Kante bereits besucht wurden
            if (visited.contains(origin.getID()) && visited.contains(target.getID())) {
                            g2d.setColor(Color.red);
            g.drawLine(origin.getX(), origin.getY(), target.getX(), target.getY());
                edges.remove(pos);
                delay();

            } else {
                // wir merken uns welche Knoten die Kante verbindet
                visited.add(origin.getID());
                visited.add(target.getID());

                g2d.setColor(Color.yellow);
                g2d.setStroke(new BasicStroke(2));

                g.drawLine(origin.getX(), origin.getY(), target.getX(), target.getY());

                delay();
                pos++;
            }
        }

    }
}

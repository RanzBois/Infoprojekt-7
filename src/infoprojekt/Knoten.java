/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoprojekt;

/**
 *
 * @author info
 */
public class Knoten {
    
    public static int breite = 10;
    
    private int x ;
    private int y;
    
    public Knoten(int x, int y){
        this.x = x ;
        this.y = y ;
    }
    
    // Knoten.erzeugeZufallsknoten(10,10);
    
    static Knoten erzeugeZufallsknoten(int maxX, int maxY) {
        int x = (int)(Math.random()*maxX);
        x = x / 10;
        x = x * 10;
        int y = (int)(Math.random()*maxY);
        y = y / 10;
        y = y * 10;

        return new Knoten(x, y);
    };
    
    // abstand = meinKnoten.abstand(andererKnoten)
    
    boolean deckend(Knoten b)
    {
         return (x == b.getX()) && (y == b.getY());         
    }
    
    double abstand(Knoten b){
        int dx = b.getX()-x;
        int dy = b.getY()-y;
        double d = Math.sqrt( dx * dx + dy * dy);
        return d ;
                
    }
    
    int getX()
    {
        return x;
    }
    
    int getY()
    {
        return y;
    }
    
    public String toString()
    {
        return "X=" + x + ", y=" + y;
    }
}

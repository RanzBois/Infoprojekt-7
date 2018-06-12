/*
 * 
 */
package infoprojekt;

/**
 *
 * @author info
 */
public class Node {
    
    // Breite beim Zeichnen eines Knotens
    public static int width = 10;
    
    // X Koordinate des Knotens
    private final int id ;
    
    
    // X Koordinate des Knotens
    private final int x ;
    
    // Y Koordinate des Knotens
    private final int y;
    
    //
    
    public Node(int id, int x, int y){
        this.id = x ;
        this.x = x ;
        this.y = y ;
    }
    
    //
    
    static Node createRandomNode(int id, int maxX, int maxY) {
        
        // Knoten haben einen Abstand von mindestes 10 Pixel
        int x = (int)(Math.random()*maxX);
        x = x / width;
        x = x * width;
        
        int y = (int)(Math.random()*maxY);
        y = y / width;
        y = y * width;

        return new Node(id, x, y);
    };
    
    // abstand = meinKnoten.abstand(andererKnoten)
    
    boolean covers(Node b)
    {
         return (x == b.getX()) && (y == b.getY());         
    }
    
    double getDistance(Node b){
        
        if (b == null)
            return Double.NaN;
        
        int dx = b.getX()-x;
        int dy = b.getY()-y;
        double d = Math.sqrt( dx * dx + dy * dy);
        
        return d ;
                
    }

    int getID() {
        return id;
    }

    int getX() {
        return x;
    }
    
    int getY()
    {
        return y;
    }
    
    @Override
    public String toString()
    {
        return "(" + x + "," + y +")";
    }
}

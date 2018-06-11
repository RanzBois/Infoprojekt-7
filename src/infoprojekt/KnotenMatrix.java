/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoprojekt;

import java.util.ArrayList;

/**
 *
 * @author info
 */
public class KnotenMatrix {
    
    ArrayList<Knoten> alleKnoten;
    
    
    
    public KnotenMatrix()
    {
        alleKnoten = new ArrayList<Knoten>();
    }
    
    public void addiereKonten(Knoten k)
    {
        alleKnoten.add(k);
    }

}

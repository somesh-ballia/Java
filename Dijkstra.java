/*
 * Dijkstra.java
   This class runs Dijkstra's shortest path algorithm, which determines the path with the smallest weight
   between two nodes in the graph.  
 *
*/
import java.awt.Color;
import java.util.*;

class Dijkstra {

    private NetworkXML nX;
    public Dijkstra( NetworkXML n ) {
        nX = n;
    }
    public void shortestPath ( Vector nodes, Vector edges, int startPosition, int endPosition ) {
        
        
        Vector vecPath = new Vector();
        int edgeMatrix[][] = new int[nodes.size()][nodes.size()];
        boolean notDone = true;
        boolean pathExists = true;
        boolean pathPossible = false;
        int nodeNotInS;
        int S[] = new int[nodes.size()];
        int T[][] = new int[2][nodes.size()];
        
        for (int i=0; i<nodes.size(); i++) {
            for (int j=0; j<nodes.size(); j++) {
                edgeMatrix[i][j] = -1;
            }
            S[i] = 0;
            T[0][i] = Integer.MAX_VALUE;
            T[1][i] = -1;
        }
        
        for (int k=0; k<edges.size(); k++) {
            
            int l = ((Edge) edges.get(k)).in.position;
            int m = ((Edge) edges.get(k)).out.position;
            edgeMatrix[l][m] = ((Edge) edges.get(k)).getWeight();
            edgeMatrix[m][l] = ((Edge) edges.get(k)).getWeight();
        }
        
        S[startPosition] = 1;
        T[0][startPosition] = 0;
        T[1][startPosition] = startPosition;
        for (int i=0; i<nodes.size(); i++) {
            if ((startPosition != i) && (edgeMatrix[startPosition][i] > -1)) { 
                T[0][i] = edgeMatrix[startPosition][i];
                T[1][i] = startPosition;
            }
        }
        
        for (int i=0; i<nodes.size(); i++)
            if (edgeMatrix[startPosition][i] > -1)
                pathPossible = true;
                
        while (notDone && pathExists && pathPossible) {
                
            int minT = Integer.MAX_VALUE;
            int v = -1;
            for (int i=0; i<nodes.size(); i++) {
                if ((S[i] == 0) && (T[0][i] < minT)) {
                    minT = T[0][i];
                    v = i;
                }
            }
            S[v] = 1;
            nodeNotInS = -1;
            for (int i=0; i<nodes.size(); i++) {
                if ((S[i] == 0) && (edgeMatrix[v][i] > -1)) {
                    if ((T[0][v] + edgeMatrix[v][i]) < T[0][i]) {
                        T[0][i] = T[0][v] + edgeMatrix[v][i];
                        T[1][i] = v;
                    }
                }
            }
            if (S[endPosition] == 1)  
                notDone = false;
            else {
                notDone = true;
                pathExists = false;
                for (int i=0; i<nodes.size(); i++) {
                    if ((S[i]==0) && (T[0][i] < Integer.MAX_VALUE))
                        pathExists = true;
                }
            }
                
                
        }
        if (pathExists && pathPossible) {
            int n = endPosition;
            vecPath.add(new Integer(n));
        
            n = T[1][endPosition];
            while(n != startPosition) {
                vecPath.add(new Integer(n));
                n = T[1][n];
            }
            vecPath.add(new Integer(n));
            for (int i=0; i<edges.size(); i++) {
                for (int j=0; j<(vecPath.size()-1); j++) {
                    int l = ((Edge) edges.get(i)).in.position;
                    int m = ((Edge) edges.get(i)).out.position;
                    if (((((Integer) vecPath.get(j)).intValue() == l) && (((Integer)vecPath.get(j+1)).intValue() == m)) ||
                        ((((Integer) vecPath.get(j)).intValue() == m) && (((Integer)vecPath.get(j+1)).intValue() == l)))
                        ((Edge) edges.get(i)).setLineColor( Color.blue );
                    
                }
            }
        }
        else
            nX.algoPanel.update("There is no path from node " + ((Node)nodes.get(startPosition)).getId() + " to node "
                                 + ((Node)nodes.get(endPosition)).getId());
    }
}
package deweyamoroso.roadtrip;

/**
 * Created by Tobin Dewey on 9/18/2016.
 */
public class Traversal {

    //will take array/list of nodes to process into strings to display on results page
    private Node a;
    private Node b;
    private Node[] map;



  // Djikstra's algorithm. Sets up distance and predecessor vars for every Node on the map given a source Node. Haven't tested yet in program. -T

    public static void Dijkstra(Node[] nodes, Node source, Node destination) {
        for (int i = 0; i < nodes.length; i++) { //Graph is initialized. All nodes have their values reset.
            nodes[i].setDjik_Distance(10000); //Distance is set to an arbitrarily high number to be changed later. This variable represents the shortest distance between the node and the source node.
            nodes[i].setDjik_Predecessor(null); //This variable represents the node before the current node along the path to the source. By following the chain of predecessors we can get the entire shortest path from any particular node to the source.
        }
        source.setDjik_Distance(0); //Source vertex is assigned a distance value of 0.
        Node[] S = new Node[nodes.length]; //A new array of nodes is created to store all *completed* nodes, that is, which the proper shortest distance and predecessor calculated. This is checked later on.
        int indexS = 0; //Number of values in array S.
        int tempedgelength = 10000; //Following are temporary variables used to find and store the node with the shortest distance in the graph.
        Edge tempedge = null;
        Node neighbor = null;
        Node currentNode = source;
        S[indexS] = currentNode;
        indexS++;
        Node tempNode = source;
        int shortestdistance;

        while (indexS < nodes.length) { //The meat of the method. Each loop adds one new finished node with correct distance and predecessor values to the array S. The method exits this loop once each node is accounted for.
            shortestdistance = 10000; //Some values are reset here at the beginning of the loop.
            tempedgelength = 10000;
            tempedge = null;
            neighbor = null;

            for (int j = 0; j < indexS; j++) { //Loops through each member of array S.
                tempNode = S[j];
                int edgenumber = tempNode.getNeighborCount();
                //System.out.println(edgenumber);
                for (int i = 0; i < edgenumber; i++) { //Loops through each edge of the Node.
                    tempedge = tempNode.getEdge(i);
                    tempedgelength = tempedge.getDistance();
                    neighbor = tempedge.getNeighbor(tempNode);
                    int flag = 0;
                    for (int z = 0; z < S.length; z++) { //This loop checks whether the edge being evaluated connects to another member of S.
                        if (neighbor == S[z]) {
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        if (neighbor.getDjik_Distance() >= (tempedgelength + tempNode.getDjik_Distance())) { //If not a member of S appropriate values are changed. This is the "relaxation" part of the algorithm.
                            neighbor.setDjik_Distance(tempedgelength + tempNode.getDjik_Distance());
                            neighbor.setDjik_Predecessor(tempNode);
                            if (neighbor.getDjik_Distance() < shortestdistance) { //And if this is the shortest distance of all. It is stored as a different variable.
                                shortestdistance = neighbor.getDjik_Distance();
                                currentNode = neighbor;
                            }
                        }
                    }

                }
            }
            S[indexS] = currentNode; //A completed node is added to array S. The index is incremented.
            indexS++;

        }
    }

}

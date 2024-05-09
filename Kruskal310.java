//TODO: Implement the required methods and add JavaDoc as needed.
//Remember: Do NOT add any additional instance or class variables (local variables are ok)
//and do NOT alter any provided methods or change any method signatures!

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import java.awt.Color;

import javax.swing.JPanel;


/**
 * Simulation of Kruskal algorithm.
 */
class Kruskal310 implements ThreeTenAlg {
    /**
     * The color when a node has "no color".
     */
    public static final Color COLOR_NONE_NODE = Color.WHITE;
    /**
     * The color when an edge has "no color".
     */
    public static final Color COLOR_NONE_EDGE = Color.BLACK;
    /**
     * The color when a node is inactive.
     */
    public static final Color COLOR_INACTIVE_NODE = Color.LIGHT_GRAY;
    /**
     * The color when an edge is inactive.
     */
    public static final Color COLOR_INACTIVE_EDGE = Color.LIGHT_GRAY;
    /**
     * The color when a node is highlighted.
     */
    public static final Color COLOR_HIGHLIGHT = new Color(255, 204, 51);
    /**
     * The color when a node is in warning.
     */
    public static final Color COLOR_WARNING = new Color(255, 51, 51);
    /**
     * The color when a node/edge is selected and added to MST.
     */
    public static final Color COLOR_SELECTED = Color.BLUE;
    /**
     * The graph the algorithm will run on.
     */
    Graph<GraphNode, GraphEdge> graph;
    /**
     * The priority queue of edges for the algorithm.
     */
    WeissBST<GraphEdge> pqueue;
    /**
     * The subgraph of the MST in construction.
     */
    private Graph310 markedGraph;
    /**
     * Whether or not the algorithm has been started.
     */
    private boolean started = false;

    /**
     * {@inheritDoc}
     */
    public EdgeType graphEdgeType() {
        return EdgeType.UNDIRECTED;
    }

    /**
     * {@inheritDoc}
     */
    public void reset(Graph<GraphNode, GraphEdge> graph) {
        this.graph = graph;
        started = false;
        pqueue = null;
        markedGraph = new Graph310();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * {@inheritDoc}
     */
    public void cleanUpLastStep() {
        // Unused. Required by the interface.
    }

    /**
     * {@inheritDoc}
     */
    public void start() {
        started = true;

        //----------------------------------------------------
        // Complete the missing part:
        // - add all edges into the priority queue
        //----------------------------------------------------
        pqueue = new WeissBST<>();
        for (GraphEdge e : graph.getEdges()) {
            pqueue.insert(e);
        }
        //----------------------------------------------------
        // End of missing part
        //----------------------------------------------------

        //highlight the edge with min weight
        highlightNext();

    }

    /**
     * Highlight the next min edge in the priority queue.
     */

    public void highlightNext() {

        // Find the current min edge in the priority queue
        // and change the color of the edge to be COLOR_HIGHLIGHT.
        // Note: do not dequeue the node.
        if (!pqueue.isEmpty()) {
            GraphEdge minEdge = pqueue.findMin();
            minEdge.setColor(COLOR_HIGHLIGHT);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void finish() {

        //wrapping up the algorithm
        // - mark all edges not selected to be inactive
        // - mark all nodes not in the constructed MST with COLOR_WARINING
        for (GraphEdge e : graph.getEdges()) {
            if (e.getColor() != COLOR_SELECTED) {
                e.setColor(COLOR_INACTIVE_EDGE);
            }
        }

        for (GraphNode n : graph.getVertices()) {
            if (!markedGraph.containsVertex(n)) {
                n.setColor(COLOR_WARNING);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean setupNextStep() {

        //decide whether we are done with the MST algorithm
        // return true if more steps to continue; return false if done
        // Hint: you may not always need to check all edges.
        return !pqueue.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    public void doNextStep() {

        //remove the next min edge from priority queue and check:
        // - if edge should be included in MST, add it into MST and change the color of
        //     the edge and nodes (COLOR_SELECTED)
        // - if edge should not be included, change its color to COLOR_INACTIVE_EDGE
        // - if MST is not completed, highlight next min edge
        if (!pqueue.isEmpty()) {
            GraphEdge minEdge = pqueue.findMin();
            pqueue.removeMin();
            if (minEdge != null) {
                Pair<GraphNode> nodes = graph.getEndpoints(minEdge);
                if (nodes != null && nodes.getFirst() != null && nodes.getSecond() != null) {

                    if (!markedGraph.containsVertex(nodes.getFirst())) {
                        markedGraph.addVertex(nodes.getFirst());
                    }

                    if (!markedGraph.containsVertex(nodes.getSecond())) {
                        markedGraph.addVertex(nodes.getSecond());
                    }

                    if (!markedGraph.reachableSet(nodes.getFirst()).contains(nodes.getSecond())) {
                        markedGraph.addEdge(minEdge, nodes.getFirst(), nodes.getSecond());
                        setTheColor(nodes.getFirst(), nodes.getSecond(), minEdge);
                    } else {
                        minEdge.setColor(COLOR_INACTIVE_EDGE);
                    }

                }
            }
        }
        highlightNext();
    }
    /**
     * Function that will set the color of the nodes and edges to be selected.
     * @param node1 the first node.
     * @param node2 the second node.
     * @param edge the edge between the two nodes.
     */
    private void setTheColor(GraphNode node1, GraphNode node2, GraphEdge edge){
        node1.setColor(COLOR_SELECTED);
        node2.setColor(COLOR_SELECTED);
        edge.setColor(COLOR_SELECTED);
    }
}
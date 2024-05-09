import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.graph.UndirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.Collection;

/**
 * A class that implements the UndirectedGraph interface.
 */
class Graph310 implements Graph<GraphNode, GraphEdge>, UndirectedGraph<GraphNode, GraphEdge> {

    /**
     * The maximum number of nodes allowed in the graph.
     */
    private static final int MAX_NUMBER_OF_NODES = 200;
    /**
     * The storage for the graph.
     */
    private Map310<GraphNode, Map310<GraphNode, GraphEdge>> storage;

    /**
     * Constructs an empty graph.
     */
    public Graph310() {
        storage = new Map310<>();
    }

    /**
     * Created a Set that contains edges.
     * Loop through all the nodes in the storage.
     * for each node, get the value which is the edge and add it to the set.
     *
     * @return a Collection view of all edges in this graph.
     */
    public Collection<GraphEdge> getEdges() {
        Set310<GraphEdge> edges = new Set310<>();
        for (GraphNode node : storage.keySet()) {
            edges.addAll(storage.get(node).values());
        }
        return edges;

    }

    /**
     * Create a Set that contains all the nodes in the storage.
     * Add all the keys, which is the vertices, in the storage to the set.
     *
     * @return a Collection view of all vertices in this graph.
     */
    public Collection<GraphNode> getVertices() {
        Set310<GraphNode> vertices = new Set310<>();
        vertices.addAll(storage.keySet());
        return vertices;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph.
     */
    public int getEdgeCount() {
        return getEdges().size();
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph.
     */
    public int getVertexCount() {
        return getVertices().size();
    }

    /**
     * Returns true if this graph's vertex collection contains vertex.
     * Equivalent to getVertices().contains(vertex).
     *
     * @param vertex the vertex whose presence is being queried.
     * @return true iff this graph contains a vertex vertex.
     */
    public boolean containsVertex(GraphNode vertex) {
        if (vertex == null) {
            return false;
        } else {
            return getVertices().contains(vertex);
        }
    }

    /**
     * Returns the collection of vertices which are connected to vertex.
     * via any edges in this graph.
     * If vertex is connected to itself with a self-loop, then
     * it will be included in the collection returned.
     *
     * @param vertex the vertex whose neighbors are to be returned.
     * @return the collection of vertices which are connected to vertex, or null if vertex is not present.
     */
    public Collection<GraphNode> getNeighbors(GraphNode vertex) {
        if (vertex == null) {
            return null;
        }
        if (!containsVertex(vertex)) {
            return null;
        } else {
            if (storage.get(vertex) == null) {
                return new Set310<>();
            } else {
                return storage.get(vertex).keySet();
            }
        }
    }

    /**
     * Returns the number of vertices that are adjacent to vertex.
     * (that is, the number of vertices that are incident to edges in vertex's
     * incident edge set).
     *
     * <p>Equivalent to getNeighbors(vertex).size().
     *
     * @param vertex the vertex whose neighbor count is to be returned.
     * @return the number of neighboring vertices.
     */
    public int getNeighborCount(GraphNode vertex) {
        if (vertex == null || !containsVertex(vertex) || getNeighbors(vertex) == null) {
            return 0;
        } else {
            return getNeighbors(vertex).size();
        }
    }

    /**
     * Returns an edge that connects v1 to v2.
     * If this edge is not uniquely
     * defined (that is, if the graph contains more than one edge connecting
     * v1 to v2), any of these edges
     * may be returned.  findEdgeSet(v1, v2) may be
     * used to return all such edges.
     * Returns null if either of the following is true:
     * <ul>
     * <li/>v1 is not connected to v2
     * <li/>either v1 or v2 are not present in this graph
     * </ul>
     * <b>Note</b>: for purposes of this method, v1 is only considered to be connected to
     * v2 via a given <i>directed</i> edge e if
     * v1 == e.getSource() && v2 == e.getDest() evaluates to true.
     * (v1 and v2 are connected by an undirected edge u if
     * u is incident to both v1 and v2.)
     *
     * @param v1 the first vertex to be queried.
     * @param v2 the second vertex to be queried.
     * @return an edge that connects v1 to v2, or null if no such edge exists (or either vertex is not present).
     * @see Hypergraph#findEdgeSet(Object, Object)
     */
    public GraphEdge findEdge(GraphNode v1, GraphNode v2) {
        if (v1 == null || v2 == null || storage.get(v1) == null || storage.get(v2) == null) {
            return null;
        }

        GraphEdge edge = storage.get(v1).get(v2);
        if (isIncident(v1, edge) && isIncident(v2, edge)) {
            return edge;
        } else {
            return null;
        }
    }

    /**
     * Returns true if vertex and edge.
     * are incident to each other.
     * Equivalent to getIncidentEdges(vertex).contains(edge) and to getIncidentVertices(edge).contains(vertex).
     *
     * @param vertex the vertex to be tested.
     * @param edge   the edge to be tested.
     * @return true if vertex and edge are incident to each other.
     */
    public boolean isIncident(GraphNode vertex, GraphEdge edge) {
        if (vertex == null || edge == null) {
            return false;
        } else {
            if (!containsVertex(vertex) || !containsEdge(edge)) {
                return false;
            } else {
                return getIncidentEdges(vertex).contains(edge) && getIncidentVertices(edge).contains(vertex);
            }
        }
    }

    /**
     * Returns the endpoints of edge as a Pair&lt;GraphNode&gt;.
     *
     * @param edge the edge whose endpoints are to be returned.
     * @return the endpoints (incident vertices) of edge or null if edge is not present.
     */
    public Pair<GraphNode> getEndpoints(GraphEdge edge) {
        if (edge == null) {
            return null;
        }

        GraphNode node1 = null;
        GraphNode node2 = null;

        for (GraphNode node : storage.keySet()) {
            if (storage.get(node).containsValue(edge)) {
                if (node1 == null) {
                    node1 = node;
                } else {
                    node2 = node;
                    break;
                }
            }
        }
        if (node1 == null || node2 == null) {
            return null;
        } else {
            if (node1.getId() < node2.getId()) {
                return new Pair<>(node1, node2);
            } else {
                return new Pair<>(node2, node1);
            }
        }
    }

    /**
     * Returns the collection of edges in this graph which are connected to vertex.
     *
     * @param vertex the vertex whose incident edges are to be returned.
     * @return the collection of edges which are connected to vertex, or null if vertex is not present.
     */
    public Collection<GraphEdge> getIncidentEdges(GraphNode vertex) {
        if(vertex == null || !containsVertex(vertex)){
            return null;
        }
        if (storage.get(vertex) == null) {
            return new Set310<>();
        } else {
            return storage.get(vertex).values();
        }
    }

    /**
     * Adds edge e to this graph such that it connects vertex v1 to v2.
     * Equivalent to addEdge(e, new Pair&lt;GraphNode&gt;(v1, v2).
     * If this graph does not contain v1, v2,
     * or both, implementations may choose to either silently add
     * the vertices to the graph or throw an IllegalArgumentException.
     * If this graph assigns edge types to its edges, the edge type of
     * e will be the default for this graph.
     * See Hypergraph addEdge() for a listing of possible reasons
     * for failure.
     *
     * @param e  the edge to be added
     * @param v1 the first vertex to be connected
     * @param v2 the second vertex to be connected
     * @return true if the add is successful, false otherwise
     * @see Hypergraph#addEdge(Object, Collection)
     * @see #addEdge(GraphEdge, GraphNode, GraphNode, EdgeType)
     */
    public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2) {
        if (e == null || v1 == null || v2 == null || v1.equals(v2)) {
            return false;
        }
        if (!containsVertex(v1) || !containsVertex(v2) || containsEdge(e)) {
            throw new IllegalArgumentException();
        } else {
            if (storage.get(v1) == null) {
                storage.put(v1, new Map310<>());
            }
            if (storage.get(v1).containsKey(v2)) {
                return false;
            } else {
                storage.get(v1).put(v2, e);
            }
            if (storage.get(v2) == null) {
                storage.put(v2, new Map310<>());
            }
            if (storage.get(v2).containsKey(v1)) {
                return false;
            } else {
                storage.get(v2).put(v1, e);
            }
            return true;
        }
    }


    /**
     * Adds vertex to this graph.
     * Fails if vertex is null or already in the graph.
     *
     * @param vertex the vertex to add
     * @return true if the add is successful, and false otherwise
     * @throws IllegalArgumentException if vertex is null
     */
    public boolean addVertex(GraphNode vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException();
        } else {
            if (getVertices().contains(vertex)) {
                return false;
            }
            if (storage.get(vertex) == null) {
                storage.put(vertex, new Map310<>());
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Removes edge from this graph.
     * Fails if edge is null, or is otherwise not an element of this graph.
     *
     * @param edge the edge to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeEdge(GraphEdge edge) {
        if (edge == null || !containsEdge(edge)) {
            return false;
        } else {
            Pair<GraphNode> pair = getEndpoints(edge);
            if (pair == null) {
                return false;
            } else {
                GraphNode node1 = pair.getFirst();
                GraphNode node2 = pair.getSecond();
                if (node1 == null || node2 == null || storage.get(node1) == null || storage.get(node2) == null) {
                    return false;
                } else {
                    storage.get(node1).remove(node2);
                    storage.get(node2).remove(node1);
                    return true;
                }
            }
        }
    }


    //********************************************************************************
    //   testing code goes here... edit this as much as you want!
    //********************************************************************************

    /**
     * Removes vertex from this graph.
     * As a side effect, removes any edges e incident to vertex if the
     * removal of vertex would cause e to be incident to an illegal
     * number of vertices.  (Thus, for example, incident hyperedges are not removed, but
     * incident edges--which must be connected to a vertex at both endpoints--are removed.)
     *
     * <p>Fails under the following circumstances:
     * <ul>
     * <li/>vertex is not an element of this graph
     * <li/>vertex is null
     * </ul>
     *
     * @param vertex the vertex to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeVertex(GraphNode vertex) {
        if (vertex == null || !containsVertex(vertex)) {
            return false;
        } else {
            if (storage.get(vertex) == null) {
                return false;
            } else {
                for (GraphNode node : storage.get(vertex).keySet()) {
                    storage.get(node).remove(vertex);
                }
                storage.remove(vertex);
                return true;
            }
        }

    }

    //********************************************************************************
    //   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
    //   NOTE: you do need to fix JavaDoc issues if there is any in this section.
    //********************************************************************************

    /**
     * Report a set of graph nodes that are reachable from the given vertex.
     * Do not include vertex itself in the set.
     * No particular order is required (hence a set).
     *
     * @param vertex the vertex whose reachable set is to be returned.
     * @return a set of graph nodes that are reachable from vertex, or an empty set if none, return null if vertex is not present.
     */

    public Set310<GraphNode> reachableSet(GraphNode vertex) {
        Set310<GraphNode> set = new Set310<>();
        if (vertex == null || !storage.containsKey(vertex)) {
            return null;
        } else {
            reachableSetHelper(vertex, set);
            set.remove(vertex);
            return set;
        }
    }

    /**
     * Helper method for reachableSet.
     *
     * @param vertex  the vertex whose reachable set is to be returned.
     * @param set the set of visited nodes.
     */
    private void reachableSetHelper(GraphNode vertex, Set310<GraphNode> set) {
        Collection<GraphNode> neighbors = getNeighbors(vertex);
        if(neighbors == null){
            return;
        }
        for (GraphNode neighbor : neighbors) {
            if (!set.contains(neighbor)) {
                set.add(neighbor);
                reachableSetHelper(neighbor, set);
            }
        }
    }

    /**
     * Returns a Collection view of the incoming edges incident to vertex in this graph.
     *
     * @param vertex the vertex whose incoming edges are to be returned
     * @return a Collection view of the incoming edges incident to vertex in this graph.
     */
    public Collection<GraphEdge> getInEdges(GraphNode vertex) {

        //actually, for undirected graph, this is the same as getOutEdges
        //get all edge records
        return getIncidentEdges(vertex);

    }

    /**
     * Returns a Collection view of the outgoing edges incident to vertex
     * in this graph.
     *
     * @param vertex the vertex whose outgoing edges are to be returned.
     * @return a Collection view of the outgoing edges incident to vertex in this graph.
     */
    public Collection<GraphEdge> getOutEdges(GraphNode vertex) {
        return getIncidentEdges(vertex);
    }

    /**
     * Returns the number of incoming edges incident to vertex.
     * Equivalent to getInEdges(vertex).size().
     *
     * @param vertex the vertex whose indegree is to be calculated
     * @return the number of incoming edges incident to vertex
     */
    public int inDegree(GraphNode vertex) {
        return degree(vertex);
    }

    /**
     * Returns the number of outgoing edges incident to vertex.
     * Equivalent to getOutEdges(vertex).size().
     *
     * @param vertex the vertex whose outdegree is to be calculated
     * @return the number of outgoing edges incident to vertex
     */
    public int outDegree(GraphNode vertex) {
        return degree(vertex);

    }

    /**
     * Returns a Collection view of the predecessors of vertex
     * in this graph.  A predecessor of vertex is defined as a vertex v
     * which is connected to
     * vertex by an edge e, where e is an outgoing edge of
     * v and an incoming edge of vertex.
     *
     * @param vertex the vertex whose predecessors are to be returned.
     * @return a Collection view of the predecessors of vertex in this graph.
     */
    public Collection<GraphNode> getPredecessors(GraphNode vertex) {
        //undirected graph
        return getNeighbors(vertex);

    }

    /**
     * Returns a Collection view of the successors of vertex
     * in this graph.  A successor of vertex is defined as a vertex v
     * which is connected to
     * vertex by an edge e, where e is an incoming edge of
     * v and an outgoing edge of vertex.
     *
     * @param vertex the vertex whose predecessors are to be returned
     * @return a Collection view of the successors of vertex in this graph.
     */
    public Collection<GraphNode> getSuccessors(GraphNode vertex) {
        return getNeighbors(vertex);

    }

    /**
     * Returns true if v1 is a predecessor of v2 in this graph.
     * Equivalent to v1.getPredecessors().contains(v2).
     *
     * @param v1 the first vertex to be queried
     * @param v2 the second vertex to be queried
     * @return true if v1 is a predecessor of v2, and false otherwise.
     */
    public boolean isPredecessor(GraphNode v1, GraphNode v2) {
        return isNeighbor(v1, v2);

    }

    /**
     * Returns true if v1 is a successor of v2 in this graph.
     * Equivalent to v1.getSuccessors().contains(v2).
     *
     * @param v1 the first vertex to be queried
     * @param v2 the second vertex to be queried
     * @return true if v1 is a successor of v2, and false otherwise.
     */
    public boolean isSuccessor(GraphNode v1, GraphNode v2) {
        return isNeighbor(v1, v2);

    }

    /**
     * If directed_edge is a directed edge in this graph, returns the source;
     * otherwise returns null.
     * The source of a directed edge d is defined to be the vertex for which
     * d is an outgoing edge.
     * directed_edge is guaranteed to be a directed edge if
     * its EdgeType is DIRECTED.
     *
     * @param directedEdge the edge to be queried.
     * @return the source of directed_edge if it is a directed edge in this graph, or null otherwise.
     */
    public GraphNode getSource(GraphEdge directedEdge) {
        return null; //undirected
    }

    /**
     * If directed_edge is a directed edge in this graph, returns the destination;
     * otherwise returns null.
     * The destination of a directed edge d is defined to be the vertex
     * incident to d for which
     * d is an incoming edge.
     * directed_edge is guaranteed to be a directed edge if
     * its EdgeType is DIRECTED.
     *
     * @param directedEdge the edge to be queried.
     * @return the destination of directed_edge if it is a directed edge in this graph, or null otherwise
     */
    public GraphNode getDest(GraphEdge directedEdge) {
        return null; //undirected
    }

    /**
     * Returns the number of edges incident to vertex.
     * Special cases of interest:
     * <ul>
     * <li/> Incident self-loops are counted once.
     * <li> If there is only one edge that connects this vertex to
     * each of its neighbors (and vice versa), then the value returned
     * will also be equal to the number of neighbors that this vertex has
     * (that is, the output of getNeighborCount).
     * <li> If the graph is directed, then the value returned will be
     * the sum of this vertex's indegree (the number of edges whose
     * destination is this vertex) and its outdegree (the number
     * of edges whose source is this vertex), minus the number of
     * incident self-loops (to avoid double-counting).
     * </ul>
     *
     * <p>Equivalent to getIncidentEdges(vertex).size().
     *
     * @param vertex the vertex whose degree is to be returned
     * @return the degree of this node
     * @see Hypergraph#getNeighborCount(Object)
     */
    public int degree(GraphNode vertex) {
        return getNeighborCount(vertex);
    }

    /**
     * Returns true if v1 and v2 share an incident edge.
     * Equivalent to getNeighbors(v1).contains(v2).
     *
     * @param v1 the first vertex to test
     * @param v2 the second vertex to test
     * @return true if v1 and v2 share an incident edge
     */
    public boolean isNeighbor(GraphNode v1, GraphNode v2) {
        return (findEdge(v1, v2) != null || findEdge(v2, v1) != null);
    }

    /**
     * Returns the collection of vertices in this graph which are connected to edge.
     * Note that for some graph types there are guarantees about the size of this collection
     * (i.e., some graphs contain edges that have exactly two endpoints, which may or may
     * not be distinct).  Implementations for those graph types may provide alternate methods
     * that provide more convenient access to the vertices.
     *
     * @param edge the edge whose incident vertices are to be returned
     * @return the collection of vertices which are connected to edge, or null if edge is not present.
     */
    public Collection<GraphNode> getIncidentVertices(GraphEdge edge) {
        Pair<GraphNode> p = getEndpoints(edge);
        Set310<GraphNode> ret = new Set310<>();
        ret.add(p.getFirst());
        ret.add(p.getSecond());
        return ret;
    }

    /**
     * Returns true if this graph's edge collection contains edge.
     * Equivalent to getEdges().contains(edge).
     *
     * @param edge the edge whose presence is being queried
     * @return true iff this graph contains an edge edge
     */
    public boolean containsEdge(GraphEdge edge) {
        return (getEndpoints(edge) != null);
    }

    /**
     * Returns the collection of edges in this graph which are of type edge_type.
     *
     * @param edgeType the type of edges to be returned.
     * @return the collection of edges which are of type edge_type, or null if the graph does not accept edges of this type.
     * @see EdgeType for a listing of legal edge types.
     */
    public Collection<GraphEdge> getEdges(EdgeType edgeType) {
        if (edgeType == EdgeType.UNDIRECTED) {
            return getEdges();
        }
        return null;
    }

    /**
     * Returns the number of edges of type edge_type in this graph.
     *
     * @param edgeType the type of edge for which the count is to be returned
     * @return the number of edges of type edgeType in this graph
     */
    public int getEdgeCount(EdgeType edgeType) {
        if (edgeType == EdgeType.UNDIRECTED) {
            return getEdgeCount();
        }
        return 0;
    }

    /**
     * Returns the number of predecessors that vertex has in this graph.
     * Equivalent to vertex.getPredecessors().size().
     *
     * @param vertex the vertex whose predecessor count is to be returned
     * @return the number of predecessors that vertex has in this graph
     */
    public int getPredecessorCount(GraphNode vertex) {
        return inDegree(vertex);
    }

    /**
     * Returns the number of successors that vertex has in this graph.
     * Equivalent to vertex.getSuccessors().size().
     *
     * @param vertex the vertex whose successor count is to be returned
     * @return the number of successors that vertex has in this graph
     */
    public int getSuccessorCount(GraphNode vertex) {
        return outDegree(vertex);
    }

    /**
     * Returns the vertex at the other end of edge from vertex.
     * (That is, returns the vertex incident to edge which is not vertex.)
     *
     * @param vertex the vertex to be queried
     * @param edge   the edge to be queried
     * @return the vertex at the other end of edge from vertex
     */
    public GraphNode getOpposite(GraphNode vertex, GraphEdge edge) {
        Pair<GraphNode> p = getEndpoints(edge);
        if (p.getFirst().equals(vertex)) {
            return p.getSecond();
        } else {
            return p.getFirst();
        }
    }

    /**
     * Returns all edges that connects v1 to v2.
     * If this edge is not uniquely
     * defined (that is, if the graph contains more than one edge connecting
     * v1 to v2), any of these edges
     * may be returned.  findEdgeSet(v1, v2) may be
     * used to return all such edges.
     * Returns null if v1 is not connected to v2.
     * <br/>Returns an empty collection if either v1 or v2 are not present in this graph.
     *
     * <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
     * v2 via a given <i>directed</i> edge d if
     * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
     * (v1 and v2 are connected by an undirected edge u if
     * u is incident to both v1 and v2.)
     *
     * @param v1 the first vertex to be queried.
     * @param v2 the second vertex to be queried.
     * @return a collection containing all edges that connect v1 to v2, or null if either vertex is not present.
     * @see Hypergraph#findEdge(Object, Object)
     */
    public Collection<GraphEdge> findEdgeSet(GraphNode v1, GraphNode v2) {
        GraphEdge edge = findEdge(v1, v2);
        if (edge == null) {
            return null;
        }

        Set310<GraphEdge> ret = new Set310<>();
        ret.add(edge);
        return ret;

    }

    /**
     * Returns true if vertex is the source of edge.
     * Equivalent to getSource(edge).equals(vertex).
     *
     * @param vertex the vertex to be queried
     * @param edge   the edge to be queried
     * @return true if vertex is the source of edge
     */
    public boolean isSource(GraphNode vertex, GraphEdge edge) {
        if (getSource(edge) == null) return false;
        return getSource(edge).equals(vertex);
    }

    /**
     * Returns true if vertex is the destination of edge.
     * Equivalent to getDest(edge).equals(vertex).
     *
     * @param vertex the vertex to be queried
     * @param edge   the edge to be queried
     * @return true iff vertex is the destination of edge
     */
    public boolean isDest(GraphNode vertex, GraphEdge edge) {
        if (getSource(edge) == null) return false;
        return getDest(edge).equals(vertex);
    }

    /**
     * Adds edge e to this graph such that it connects
     * vertex v1 to v2.
     * Equivalent to addEdge(e, new Pair&lt;GraphNode&gt;(v1, v2).
     * If this graph does not contain v1, v2, or both, implementations may choose to either silently add the vertices to the graph or throw an IllegalArgumentException.
     * If edgeType is not legal for this graph, this method will throw IllegalArgumentException.
     * See Hypergraph.addEdge() for a listing of possible reasons for failure.
     *
     * @param e        the edge to be added
     * @param v1       the first vertex to be connected
     * @param v2       the second vertex to be connected
     * @param edgeType the type to be assigned to the edge
     * @return true if the add is successful, false otherwise
     * @see Hypergraph#addEdge(Object, Collection)
     * @see #addEdge(GraphEdge, Collection, EdgeType)
     */
    public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2, EdgeType edgeType) {
        //NOTE: Only directed edges allowed

        if (edgeType == EdgeType.DIRECTED) {
            throw new IllegalArgumentException();
        }

        return addEdge(e, v1, v2);
    }

    /**
     * Adds edge to this graph.
     * Fails under the following circumstances:
     * <ul>
     * <li/>edge is already an element of the graph
     * <li/>either edge or vertices is null
     * <li/>vertices has the wrong number of vertices for the graph type
     * <li/>vertices are already connected by another edge in this graph,
     * and this graph does not accept parallel edges
     * </ul>
     *
     * @param edge     the edge to be added.
     * @param vertices the vertices to be connected.
     * @return true if the add is successful, and false otherwise.
     * @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge.
     */
    @SuppressWarnings("unchecked")
    public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices) {
        if (edge == null || vertices == null || vertices.size() != 2) {
            return false;
        }

        GraphNode[] vs = (GraphNode[]) vertices.toArray();
        return addEdge(edge, vs[0], vs[1]);
    }

    //********************************************************************************
    //   DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT FOR FIXING JAVADOC
    //********************************************************************************

    /**
     * Adds edge to this graph with type edge_type.
     * Fails under the following circumstances:
     * <ul>
     * <li/>edge is already an element of the graph
     * <li/>either edge or vertices is null
     * <li/>vertices has the wrong number of vertices for the graph type
     * <li/>vertices are already connected by another edge in this graph,
     * and this graph does not accept parallel edges
     * <li/>edge_type is not legal for this graph
     * </ul>
     *
     * @param edge     the edge to be added.
     * @param vertices the vertices to be connected.
     * @param edgeType the type to be assigned to the edge.
     * @return true if the add is successful, and false otherwise
     * @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge.
     */
    @SuppressWarnings("unchecked")
    public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices, EdgeType edgeType) {
        if (edge == null || vertices == null || vertices.size() != 2) {
            return false;
        }

        GraphNode[] vs = (GraphNode[]) vertices.toArray();
        return addEdge(edge, vs[0], vs[1], edgeType);
    }

    /**
     * Returns a {@code Factory} that creates an instance of this graph type.
     *
     * @param <V> the vertex type for the graph factory.
     * @param <E> the edge type for the graph factory.
     * @return the factory.
     */

    public static <V, E> Factory<UndirectedGraph<GraphNode, GraphEdge>> getFactory() {
        return new Factory<UndirectedGraph<GraphNode, GraphEdge>>() {
            @SuppressWarnings("unchecked")
            public UndirectedGraph<GraphNode, GraphEdge> create() {
                return (UndirectedGraph<GraphNode, GraphEdge>) new Graph310();
            }
        };
    }

    /**
     * Returns the edge type of edge in this graph.
     *
     * @param edge the edge whose type is to be queried.
     * @return the EdgeType of edge, or null if edge has no defined type.
     */
    public EdgeType getEdgeType(GraphEdge edge) {
        return EdgeType.UNDIRECTED;
    }

    /**
     * Returns the default edge type for this graph.
     *
     * @return the default edge type for this graph
     */
    public EdgeType getDefaultEdgeType() {
        return EdgeType.UNDIRECTED;
    }

    /**
     * Returns the number of vertices that are incident to edge.
     * For hyperedges, this can be any nonnegative integer; for edges this
     * must be 2 (or 1 if self-loops are permitted).
     *
     * <p>Equivalent to getIncidentVertices(edge).size().
     *
     * @param edge the edge whose incident vertex count is to be returned
     * @return the number of vertices that are incident to edge.
     */
    public int getIncidentCount(GraphEdge edge) {
        return 2;
    }


}

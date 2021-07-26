package sssp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

// Data structure to store graph edges
class Edge
{
    int source, destination;
    double weight;

    public Edge(int source, int destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

// Data structure to store heap nodes
class Node
{
    int vertex;
    public Node(int vertex) {
        this.vertex = vertex;
    }
}

// Class to represent a graph object
class Graph
{
    // An array of Lists to represent adjacency list
    List<List<Edge>> adjacencyList = null;
    // Constructor
    Graph(List<Edge> edges, int N)
    {
        adjacencyList = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            adjacencyList.add(i, new ArrayList<>());
        }
        // add edges to the undirected graph
        for (Edge edge: edges) {
            adjacencyList.get(edge.source).add(edge);
        }
    }
}

class AlgorithmsSP
{
    public static boolean isDAG(Graph graph, int V)
    {
        // Boolean array to keep visited arrays on check
        boolean[] visited = new boolean[V];
        // Integer array to save the departure times of the nodes
        int[] departure = new int[V];
        // Constant to save the time count
        int time = 0;
        // Use DFS traversal for all unvisited nodes
        for (int i = 0; i < V; i++)
        {
            if (!visited[i]) {
                time = DFSexplore(graph, i, visited, departure, time);
            }
        }
        // for loop to check whether a graph is DAG
        for (int u = 0; u < V; u++)
        {
            // for loop to check if back edge is formed between u and v
            for (Edge v: graph.adjacencyList.get(u))
            {
                // If departure[v]>departure[u] we have a back edge, loops exist
                // If departure[v]=departure[u] we have an edge to current node

                if (departure[u] <= departure[v.destination]) {
                    //if back edges exist return false
                    return false;
                }
            }
        }
        // if no back edges, return true
        return true;
    }

    public static void DijkstraShortestPath(Graph graph, int source, int V)
    {
        // Set distances from source equal to infinity at first
        List<Double> distance = new ArrayList<>(
                Collections.nCopies(V, Double.POSITIVE_INFINITY));
        // Set distance of source to itself equal to zero
        distance.set(source, 0.0);
        // array to store the predecessors, used to print the path
        int previous[] = new int[V];
        previous[source] = -1;
        // array to be marked as true when a vertex is visited
        boolean[] visited = new boolean[V];
        visited[source] = true;
        // creating a fibonacci heap and inserting source vertex with weight zero
        FibonacciHeap<Node> fibHeap = new FibonacciHeap();
        fibHeap.enqueue(new Node(source) , 0);
        // while loop to iterate through fibonacci heap
        while (!fibHeap.isEmpty())
        {
            // Get the minimum and remove it from fibHeap
            FibonacciHeap.Entry<Node> node = fibHeap.dequeueMin();
            // Get the value of the node
            Node nodeTemp = node.getValue();
            // Get the value of the vertex of the node and save it to u
            int u=nodeTemp.vertex;
            // for loop to iterate through neighbors v of u
            for (Edge edge: graph.adjacencyList.get(u))
            {
                int v = edge.destination;
                double weight = edge.weight;
                // if condition to update distance in case we have found a shorter one, insert to previous[] array, insert to fibHeap
                if (!visited[v] && (distance.get(u) + weight) < distance.get(v))
                {
                    distance.set(v, distance.get(u) + weight);
                    previous[v] = u;
                    fibHeap.enqueue(new Node(v), distance.get(v));
                }
            }
            // Set vertex as visited
            visited[u] = true;
        }
        // Print all required info
        printInfo(distance, source, previous, V);
    }

    private static int DFSexplore(Graph graph, int v, boolean[] visited, int[] departure, int time)
    {
        // Set the current node as visited
        visited[v] = true;
        // for loop to iterate through the edges for each node
        for (Edge u: graph.adjacencyList.get(v))
        {
            // if `u` is not yet visited
            if (!visited[u.destination]) {
                time = DFSexplore(graph, u.destination, visited, departure, time);
            }
        }
        // Set the departure time for current node
        departure[v] = time++;

        // Return the time count
        return time;
    }

    private static int topologicalSorting(Graph graph, int v, boolean[] visited, int[] departure, int time, Stack<Integer> ordering)
    {
        // Set the current node as visited
        visited[v] = true;
        // for loop to iterate through the edges for each node
        for (Edge u: graph.adjacencyList.get(v))
        {
            // if `u` is not yet visited
            if (!visited[u.destination]) {
                time = topologicalSorting(graph, u.destination, visited, departure, time, ordering);
            }
        }
        // Push the element to the stack to be used by topological sort since stack inserts are O(1)
        ordering.push(v);
        // Set the departure time for current node
        departure[v] = time++;
        // Return the time count
        return time;
    }
    
    

    private static void shortestPathDAG(Graph graph,int source, int V) {
        // Creating stack to store ordering from DFS
        Stack<Integer> ordering = new Stack<>();
        // Constant variable to save time count
        int time = 0;
        // Boolean array to store visited nodes
        boolean visited[] = new boolean[V];
        // Integer array to store departure time for the nodes
        int[] departure = new int[V];
        // for loop to perform DFS traversal
        for(int i =0; i<V; i++){
            if(!visited[i]){
                time = topologicalSorting(graph, i, visited, departure, time, ordering);
            }
        }
        // List to save the distances, initially setting all distances to infinity and source distance to itself equal to zero
        List<Double> distance = new ArrayList<>(Collections.nCopies(V, Double.POSITIVE_INFINITY));
        distance.set(source, 0.0);
        // Array to save predecessors
        int previous[] = new int[V];
        previous[source] = -1;
        // while loop to iterate through the ordering
        while (!ordering.isEmpty())
        {
            // Getting vertex from ordering
            int u = ordering.pop();

            // if condition to check if distance is not infinity
            if(distance.get(u)!=Double.POSITIVE_INFINITY){
                // for loop to iterate through the edges
                for (Edge edge: graph.adjacencyList.get(u)) {
                    int v = edge.destination;
                    double weight = edge.weight;
                    // if condition to check if new distance is less than the existing one
                    if ((distance.get(u) + weight) < distance.get(v)) {
                        // Setting new distance
                        distance.set(v, distance.get(u) + weight);
                        // Saving in the previous array in order to print route later
                        previous[v] = u;

                    }
                }
            }
        }
        // Print the required info
        printInfo(distance, source, previous, V);
    }

    private static void printInfo(List distance,int source, int previous[], int V){
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_CYAN = "\u001B[36m";
        System.out.format(ANSI_CYAN + "%15s%15s%15s%15s", "Source", "Destination", "Min Cost", "Route\n" + ANSI_RESET);
        for (int i = 0; i < V; i++)
        {
            System.out.format("%15s%15s%15s", source, i, distance.get(i));
            System.out.print("\t\t");
            printRoute(previous, i);
            System.out.println();
        }
    }

    private static void printRoute(int previous[], int i)
    {
        if (i >= 0) {
            printRoute(previous, previous[i]);
            System.out.print(i + " ");
        }else{
            return;
        }
    }

    // Run Dijkstra's algorithm on given graph

    public static void main(String[] args)
    {
        List<Edge> edges = new ArrayList<>();
        int N=0;
        int E=0;
        BufferedReader reader;
        try {
            // In order to get relative path we concatenate it to the absolute one where the user saved the project
            String filePath = new File("").getAbsolutePath();
            reader = new BufferedReader(new FileReader(
                    filePath.concat("/src/graph.txt")));
            String line = reader.readLine();
            char hashSign = '#';
            while (line != null) {

                if(line.toCharArray()[0]==hashSign) {

                }
                else if(line.split(" ").length==2){
                    // total number of nodes in the graph
                    N =  Integer.parseInt(line.split(" ")[0]);

                    // total number of edges in the graph
                    E = Integer.parseInt(line.split(" ")[1]);

                }else{
                    int source = Integer.parseInt(line.split(" ")[0]);
                    int destination = Integer.parseInt(line.split(" ")[1]);
                    double weight = Double.parseDouble(line.split(" ")[2]);

                    edges.add(new Edge(source, destination, weight));

                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // construct graph
        Graph graph = new Graph(edges, N);

        // check if the given directed graph is DAG or not
        if (isDAG(graph, N)) {
            System.out.println("The graph is a DAG");
            shortestPathDAG(graph,0, N);
        }
        else {
            System.out.println("The graph is not a DAG");
            DijkstraShortestPath(graph, 0, N);
        }
    }
}
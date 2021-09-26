# SSSP-Java (Single Source Shortest Paths)

A java implementation that introduces a way to find single source shortest paths from a given node to other nodes in the graph via the use of adjacency lists. The program works for both Directed Acyclic Graphs(DAGs) as well as for directed graphs that include cycles. The input is given via a pre-validated text file written by the user, which contains the number of vertexes, number of edges and a list of edges and their weights, as well as comments if needed which are denoted by the hash sign(#) at the beginning of line. This information is read and processed, in order to determine the type of graph and to output the shortest paths from the given source node.

## Description

After the information from user's text file is parsed, a new object of type ‘Edge’ is created which contains the 
source, destination, and weight of each edge. These objects are then added into an array list of the
same type. This arraylist and the total number of vertices are used to create a graph object. The graph is
a list of lists or otherwise called an adjacency list, where for each node(edge source) we add a list of
edges that connect it to other nodes. After the graph structure is finished, we continue testing for
cycles, and perform the needed operations to determine the shortest paths from the source nodes.

Find time complexity analysis [here](https://drive.google.com/file/d/1Atr3bqYVl7JkuF-a1AZAfn9BMQsEvYaG/view?usp=sharing).

## Getting Started

### Dependencies

Java JDK 13 or newer.
Older versions might also work.

### Executing program

After successful execution, a message is shown to provide details regarding the presence of cycles in the graph(DAG or not).
In addition to that, the output contains a table that shows source, destination, minimum cost for the shortest route and the route itself.

### Sample Output
```
The graph is not a DAG
         Source    Destination       Min Cost     Route
          0              0            0.0		0 
          0              1            3.0		0 1 
          0              2            6.5		0 5 4 2 
          0              3            4.0		0 3 
          0              4            3.5		0 5 4 
          0              5            1.5		0 5 
```

## Authors

Contributors names and contact info:

Erges Mema  
[@MemaErges](https://twitter.com/memaerges)

## Version History

* 0.1
    * Initial Release

## Acknowledgments

Pseudocode references from:

Dasgupta, S., Papadimitriou, C. H., & Vazirani, U. V. (2006). Algorithms. Boston: McGraw-Hill Higher Education.

Fibonnaci Heap implementation from Professor Schwarz's personal website:

Schwarz, K. (2021). Archive of Interesting Code. Retrieved from KeithSchwarz.com:
https://keithschwarz.com/interesting/code/?dir=fibonacci-heap

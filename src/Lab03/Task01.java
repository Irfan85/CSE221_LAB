package Lab03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Task01 {
    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("inputL3T1.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String l1 = bufferedReader.readLine();
            String[] sb = l1.split(" ", 4);
            int n = Integer.parseInt(sb[0]);
            int m = Integer.parseInt(sb[1]);
            int x = Integer.parseInt(sb[2]);
            int q = Integer.parseInt(sb[3]);

            ArrayList<Vertex> graph = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new Vertex(i));
            }

            for (int i = 0; i < m; i++) {
                String line = bufferedReader.readLine();
                String[] s = line.split(" ", 3);

                int u = Integer.parseInt(s[0]);
                int v = Integer.parseInt(s[1]);
                int w = Integer.parseInt(s[2]);

                graph.get(u - 1).addEdge(new Edge(v - 1, w));
            }

            for (int i = 0; i < q; i++) {
                String line = bufferedReader.readLine();
                String[] s = line.split(" ", 2);

                int source = Integer.parseInt(s[0]);
                int destination = Integer.parseInt(s[1]);

                int markers = 0;
                disktra(graph, source - 1);
                markers += graph.get(x - 1).distance;
                resetGraph(graph);
                disktra(graph, x - 1);
                markers += graph.get(destination - 1).distance;

                System.out.println("Case: " + (i + 1) + ": ");
                if (markers >= 0) {
                    System.out.println(markers);
                } else {
                    System.out.println("Be seeing ya, John");
                }

                resetGraph(graph);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void disktra(ArrayList<Vertex> graph, int start) {
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(new VertexComparator());
        graph.get(start).distance = 0;
        priorityQueue.add(graph.get(start));

        while (priorityQueue.size() > 0) {
            Vertex vertex = priorityQueue.poll();

            for (Edge e : vertex.edges) {
                if (graph.get(e.v).distance > (vertex.distance + e.weight)) {
                    graph.get(e.v).distance = vertex.distance + e.weight;

                    priorityQueue.add(graph.get(e.v));
                }
            }
        }
    }

    private static void resetGraph(ArrayList<Vertex> graph) {
        for (Vertex v : graph) {
            v.distance = Integer.MAX_VALUE;
        }
    }

    private static class Vertex {
        public int u, distance;
        public ArrayList<Edge> edges;

        public Vertex(int u) {
            this.u = u;
            distance = Integer.MAX_VALUE;
            edges = new ArrayList<>();
        }

        public void addEdge(Edge edge) {
            edges.add(edge);
        }
    }

    private static class Edge {
        public int v, weight;

        public Edge(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }
    }

    private static class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            return Integer.compare(v1.distance, v2.distance);
        }
    }
}

package Lab03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Task02 {
    private static final String[] places = {"Mouchack", "Panthapath", "Rampura", "Shahahbagh", "Dhanmondi", "Lalmatia","Badda", "Hatirjheel", "Nilkhet", "TSC", "Dhaka University", "BUET"};

    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("inputL3T2.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            int m = Integer.parseInt(bufferedReader.readLine());

            ArrayList<Vertex> graph = new ArrayList<>();
            for(int i = 0; i < n; i++){
                graph.add(new Vertex(i));
            }

            for (int i = 0; i < m; i++) {
                String line = bufferedReader.readLine();
                String[] s = line.split(",", 3);

                int u = Integer.parseInt(s[0]);
                int v = Integer.parseInt(s[1]);
                int w = Integer.parseInt(s[2]);

                graph.get(u).addEdge(new Edge(v, w));
            }

            int source = Integer.parseInt(bufferedReader.readLine());
            int destination = Integer.parseInt(bufferedReader.readLine());

            String yellowLine = bufferedReader.readLine();
            String[] yellows = yellowLine.split(",", 4);

            for(String s : yellows){
                int yellowIndex = Integer.parseInt(s);
                graph.get(yellowIndex).isYellow = true;
            }

            disktra(graph, source);

            ArrayList<Integer> path = new ArrayList<>();

            int x = destination;
            while (graph.get(x).parent != -1){
                path.add(x);
                x = graph.get(x).parent;
            }
            path.add(source);

            for(int i = path.size() - 1; i >= 0; i--){
                System.out.print(places[path.get(i)]);

                if(i > 0){
                    System.out.print("->");
                }
            }
            System.out.println();

            System.out.println("Path cost: " + graph.get(destination).distance);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void disktra(ArrayList<Vertex> graph, int start){
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(new VertexComparator());
        graph.get(start).distance = 0;
        priorityQueue.add(graph.get(start));

        while (priorityQueue.size() > 0){
            Vertex vertex = priorityQueue.poll();

            for(Edge e : vertex.edges){
                if(!vertex.isYellow && graph.get(e.v).distance > (vertex.distance + e.weight)){
                    graph.get(e.v).distance = vertex.distance + e.weight;
                    graph.get(e.v).parent = vertex.u;

                    priorityQueue.add(graph.get(e.v));
                }
            }
        }
    }

    private static class Vertex{
        public int u, distance, parent;
        public boolean isYellow;
        public ArrayList<Edge> edges;

        public Vertex(int u){
            this.u = u;
            distance = Integer.MAX_VALUE;
            parent = -1;
            edges = new ArrayList<>();
            isYellow = false;
        }

        public void addEdge(Edge edge){
            edges.add(edge);
        }
    }

    private static class Edge{
        public int v, weight;

        public Edge(int v, int weight){
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

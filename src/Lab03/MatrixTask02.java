package Lab03;

import java.io.*;
import java.util.LinkedList;

public class MatrixTask02 {
    private static String[] place = {"Mouchack", "Panthapath", "Rampura", "Shahahbagh", "Dhanmondi", "Lalmatia","Badda", "Hatirjheel", "Nilkhet", "TSC", "Dhaka University", "BUET"};

    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("inputL3T2.txt");
            BufferedReader br = new BufferedReader(fr);

            int n = Integer.parseInt(br.readLine());
            int m = Integer.parseInt(br.readLine());

            int[][] graph = new int[n][n];

            for (int i = 0; i < m; i++) {
                String line = br.readLine();
                String[] s = line.split(",", 3);

                int u = Integer.parseInt(s[0]);
                int v = Integer.parseInt(s[1]);
                int w = Integer.parseInt(s[2]);

                graph[u][v] = w;
            }

            int s = Integer.parseInt(br.readLine());
            int t = Integer.parseInt(br.readLine());

            String line = br.readLine();
            String[] y = line.split(",");

            disktra(graph, s, t, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int minDistance(int graph[][], int dist[], Boolean visited[]) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 0; v < graph.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    private static boolean yellow(String[] y, int v) {
        for (int c = 0; c < y.length; c++) {
            if (Integer.parseInt(y[c]) == v) {
                return true;
            }
        }
        return false;
    }

    private static void disktra(int[][] graph, int s, int t, String[] y) {
        int[] dist = new int[graph.length];
        int[] parent = new int[graph.length];
        Boolean[] visited = new Boolean[graph.length];

        for (int i = 0; i < graph.length; i++) {
            dist[i] = Integer.MAX_VALUE;
            parent[i] = -1;
            visited[i] = false;
        }

        dist[s] = 0;

        for (int count = 0; count < graph.length - 1; count++) {
            int u = minDistance(graph, dist, visited);
            visited[u] = true;

            for (int v = 0; v < graph.length; v++) {
                if (graph[u][v] != 0 && !visited[v]  && !yellow(y, v) && dist[v] > (dist[u] + graph[u][v])) {
                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        LinkedList<Integer> path = new LinkedList<>();
        path.add(t);

        int totalCost = dist[t];
        int x = parent[t];
        while (x != -1) {
            path.add(x);
            x = parent[x];
        }

        for (int p = path.size() - 1; p >= 0; p--){
            if(p > 0){
                System.out.print(place[path.get(p)] + "->");
            }else{
                System.out.println(place[path.get(p)]);
            }
        }

        System.out.println("Path Cost: " + totalCost);
    }
}

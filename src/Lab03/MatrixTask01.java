package Lab03;

import java.io.BufferedReader;
import java.io.FileReader;

public class MatrixTask01 {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("inputL3T1.txt");
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            String[] s = line.split(" ", 4);

            int n = Integer.parseInt(s[0]);
            int m = Integer.parseInt(s[1]);
            int x = Integer.parseInt(s[2]);
            int q = Integer.parseInt(s[3]);

            int[][] graph = new int[n + 1][n + 1];

            for (int i = 0; i < m; i++) {
                line = br.readLine();
                String[] s1 = line.split(" ", 3);

                int u = Integer.parseInt(s1[0]);
                int v = Integer.parseInt(s1[1]);
                int w = Integer.parseInt(s1[2]);

                graph[u][v] = w;
            }

            for (int j = 0; j < q; j++) {
                line = br.readLine();
                String[] s2 = line.split(" ", 2);
                int sc = Integer.parseInt(s2[0]);
                int ds = Integer.parseInt(s2[1]);

                int c = 0;
                int[] dist = disktra(graph, sc, x);
                c += dist[x];

                dist = disktra(graph, x, ds);
                c += dist[ds];

                System.out.println("Case " + (j + 1) + ":");
                if (c >= 0) {
                    System.out.println(c);
                } else {
                    System.out.println("Be seeing ya, John");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int minDistance(int graph[][], int dist[], Boolean visited[]) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 1; v < graph.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    private static int[] disktra(int[][] graph, int s, int t) {
        int[] dist = new int[graph.length];
        int[] parent = new int[graph.length];
        Boolean[] visited = new Boolean[graph.length];

        for (int i = 1; i < graph.length; i++) {
            dist[i] = Integer.MAX_VALUE;
            parent[i] = -1;
            visited[i] = false;
        }

        dist[s] = 0;

        for (int count = 0; count < graph.length - 1; count++) {
            int u = minDistance(graph, dist, visited);
            visited[u] = true;

            for (int v = 1; v < graph.length; v++) {
                if (graph[u][v] != 0 && !visited[v] && dist[v] > (dist[u] + graph[u][v])) {
                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        return dist;
    }
}

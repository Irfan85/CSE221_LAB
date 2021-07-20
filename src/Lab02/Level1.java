package Lab02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Level1 {
    public static void main(String[] args) {

        try {
            FileReader fileReader = new FileReader("inputL2T1.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            int m = Integer.parseInt(bufferedReader.readLine());

            ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
            }

            for (int i = 0; i < m; i++) {
                String line = bufferedReader.readLine();
                String[] s = line.split(" ", 2);

                int u = Integer.parseInt(s[0]);
                int v = Integer.parseInt(s[1]);

                graph.get(u).add(v);
                graph.get(v).add(u);
            }

            int x = Integer.parseInt(bufferedReader.readLine());

//            printGraph(graph);

            int[] dist = new int[n];
            for (int i = 0; i < n; i++) {
                dist[i] = Integer.MAX_VALUE;
            }
            dist[0] = 0;

            bfs(graph, dist, 0);

            System.out.println(dist[x]);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bfs(ArrayList<ArrayList<Integer>> graph, int[] dist, int startPos) {
        Queue<Integer> q = new LinkedList<>();
        q.add(startPos);

        while (q.size() > 0) {
            int u = q.remove();

            for(int i : graph.get(u))
            {
                if(dist[i] == Integer.MAX_VALUE){
                    dist[i] = dist[u] + 1;
                    q.add(i);
                }
            }
        }
    }

    private static void printGraph(ArrayList<ArrayList<Integer>> graph) {
        for (int i = 0; i < graph.size(); i++) {
            System.out.print(i + "-> ");
            for (int j = 0; j < graph.get(i).size(); j++) {
                if (j != (graph.get(i).size() - 1)) {
                    System.out.print(graph.get(i).get(j) + ", ");
                } else {
                    System.out.print(graph.get(i).get(j));
                }
            }
            System.out.println();
        }
    }
}


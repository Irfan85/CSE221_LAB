package Lab02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Level4 {
    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("inputL2T4.txt");
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
            }

            int[] colors = new int[n];
            Arrays.fill(colors, 0);

            Stack<Integer> topSorted = new Stack<>();

            for (int i = 0; i < n; i++) {
                if (colors[i] == 0) {
                    dfs(graph, i, colors, topSorted);
                }
            }

            System.out.println(topSorted.pop());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dfs(ArrayList<ArrayList<Integer>> graph, int s, int[] colors, Stack<Integer> topSorted) {
        colors[s] = 1;

        for (int v : graph.get(s)) {
            if (colors[v] == 0) {
                dfs(graph, v, colors, topSorted);
            }
        }

        colors[s] = 2;
        topSorted.push(s);
    }
}

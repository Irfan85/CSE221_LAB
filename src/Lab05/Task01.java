package Lab05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Task01 {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("inputL5T1.txt");
            BufferedReader bf = new BufferedReader(fr);

            int budget = Integer.parseInt(bf.readLine()) / 5;
            int n = Integer.parseInt(bf.readLine());

            ArrayList<Player> players = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                String line = bf.readLine();
                String[] playerDetails = line.split(",", 4);

                players.add(new Player(playerDetails[0].trim(), (Integer.parseInt(playerDetails[1].trim()) / 5), Integer.parseInt(playerDetails[2].trim()), playerDetails[3].trim()));
            }

            int[][] dp = new int[n + 1][budget + 1];

            for (int i = 1; i < dp.length; i++) {
                for (int j = 1; j < dp[0].length; j++) {
                    if (players.get(i - 1).price > j) {
                        dp[i][j] = dp[i - 1][j];
                    } else {
                        int taking = dp[i - 1][j - players.get(i - 1).price] + players.get(i - 1).form;
                        int notTaking = dp[i - 1][j];

                        dp[i][j] = Integer.max(taking, notTaking);
                    }
                }
            }

            int i = dp.length - 1;
            int j = dp[0].length - 1;

            Stack<String> playerNames = new Stack<>();

            while (i >= 1 && j >= 1) {
                int top = dp[i - 1][j];

                if (top == dp[i][j]) {
                    i--;
                } else {
                    Player p = players.get(i-1);
                    playerNames.add(p.name);

                    j -= p.price;
                    i--;
                }
            }

            System.out.println("Bought Players:");

            while (playerNames.size() > 0){
                if(playerNames.size() > 1){
                    System.out.print(playerNames.pop() + "-> ");
                }else{
                    System.out.println(playerNames.pop());
                }
            }

            System.out.println("Maximum summation of form: " + dp[dp.length-1][dp[0].length-1]);

//            for (int i = 0; i < dp.length; i++) {
//                for (int j = 0; j < dp[0].length; j++) {
//                    System.out.printf("%03d ", dp[i][j]);
//                }
//
//                System.out.println();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Player {
        public String name, position;
        public int price, form;

        public Player(String name, int price, int form, String position) {
            this.name = name;
            this.price = price;
            this.form = form;
            this.position = position;
        }
    }
}

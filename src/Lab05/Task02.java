package Lab05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Task02 {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("inputL5T2.txt");
            BufferedReader bf = new BufferedReader(fr);

            int capacity = Integer.parseInt(bf.readLine());
            int n = Integer.parseInt(bf.readLine());

            ArrayList<Trophy> trophies = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                String line = bf.readLine();
                String[] playerDetails = line.split(",", 4);

                trophies.add(new Trophy(playerDetails[0].trim(), Integer.parseInt(playerDetails[1].trim()), Double.parseDouble(playerDetails[2].trim()), playerDetails[3].trim()));
            }

            double[][] dpMax = new double[0][0];
            double max = 0;
            ArrayList<Trophy> validTrophies = null;

            Trophy mustTake = null;

            for (Trophy t : trophies) {
                if (t.name.equals("Champion’s League")) {
                    int W = capacity - t.weight;
                    ArrayList<Trophy> remaining = (ArrayList<Trophy>) trophies.clone();
                    remaining.remove(t);

                    double[][] dp = solveKnapsack(remaining, W);
                    if (dp[dp.length - 1][dp[0].length - 1] > max) {
                        dpMax = dp;
                        max = dp[dp.length - 1][dp[0].length - 1] + t.weight;
                        validTrophies = remaining;
                        mustTake = t;
                    }
                }
            }

            int i = dpMax.length - 1;
            int j = dpMax[0].length - 1;

            ArrayList<Trophy> rem = new ArrayList<>();

            while (i >= 1 && j >= 1) {
                double top = dpMax[i - 1][j];

                if (top == dpMax[i][j]) {
                    i--;
                } else {
                    Trophy t = validTrophies.get(i - 1);
                    rem.add(t);

                    j -= t.weight;
                    i--;
                }
            }

            ArrayList<String> clubNames = new ArrayList<>();
            for (Trophy t : trophies) {
                if (t.equals(mustTake) || rem.contains(t)) {
                    clubNames.add(t.club);
                }
            }

            System.out.println("Name of clubs whose trophies were sold:");
            for (int k = 0; k < clubNames.size(); k++) {
                if (k < (clubNames.size() - 1)) {
                    System.out.print(clubNames.get(k) + "-> ");
                } else {
                    System.out.println(clubNames.get(k));
                }
            }
            System.out.println("Maximum money he earned: " + max + " million");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double[][] solveKnapsack(ArrayList<Trophy> trophies, int capacity) {
        double[][] dp = new double[trophies.size() + 1][capacity + 1];

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (trophies.get(i - 1).weight > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    double taking = dp[i - 1][j - trophies.get(i - 1).weight] + trophies.get(i - 1).price;
                    double notTaking = dp[i - 1][j];

                    dp[i][j] = Double.max(taking, notTaking);
                }
            }
        }

//        for (int i = 0; i < dp.length; i++) {
//            for (int j = 0; j < dp[0].length; j++) {
//                System.out.printf("%.02f ", dp[i][j]);
//            }
//
//            System.out.println();
//        }
//        System.out.println();

        return dp;
    }

    private static class Trophy {
        public String name, club;
        public int weight;
        public double price;

        public Trophy(String club, int weight, double price, String name) {
            this.club = club;
            this.weight = weight;
            this.price = price;
            this.name = name;
        }
    }
}

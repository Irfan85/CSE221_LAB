package Lab04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Task01 {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("inputL4T1.txt");
            BufferedReader br = new BufferedReader(fr);

            String text = br.readLine();
            String[] textArray = text.split(" ");

            String pattern = br.readLine();
            String[] patternArray = pattern.split(" ");

            int[][] dpMatrix = new int[textArray.length + 1][patternArray.length + 1];
            String[][] direction = new String[textArray.length + 1][patternArray.length + 1];

            for (int i = 1; i < dpMatrix.length; i++) {
                for (int j = 1; j < dpMatrix[0].length; j++) {
                    if (textArray[i - 1].equals(patternArray[j - 1])) {
                        dpMatrix[i][j] = dpMatrix[i - 1][j - 1] + 1;
                        direction[i][j] = "diag";
                    } else {
                        int maximum = Integer.max(dpMatrix[i][j - 1], dpMatrix[i - 1][j]);
                        dpMatrix[i][j] = maximum;

                        if (maximum == dpMatrix[i][j - 1]) {
                            direction[i][j] = "left";
                        } else {
                            direction[i][j] = "up";
                        }
                    }
                }
            }

            int i = dpMatrix.length - 1;
            int j = dpMatrix[0].length - 1;
            Stack<String> lcsStack = new Stack<>();

            while (i >= 1 && j >= 1) {
                switch (direction[i][j]) {
                    case "diag":
                        lcsStack.push(textArray[i - 1]);
                        i--;
                        j--;
                        break;
                    case "left":
                        j--;
                        break;
                    case "up":
                        i--;
                        break;
                    default:
                        break;
                }
            }

            StringBuilder lcs = new StringBuilder();
            while (lcsStack.size() > 0) {
                lcs.append(lcsStack.pop());
            }

            System.out.println(lcs.toString());
            double ratio = (dpMatrix[textArray.length][patternArray.length] * 1.0) / textArray.length;
            int res = (int) (ratio * 100);

            if (res >= 50) {
                System.out.println(res + "% PASSED");
            } else {
                System.out.println(res + "% FAILED");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

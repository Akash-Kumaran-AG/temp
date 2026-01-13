import java.util.*;

public class Suduko_3 {

    public static boolean solve(int[][] matrix) {

        while (true) {
            boolean progress = false;

            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (matrix[r][c] == 0) {
                        List<Integer> poss = allPoss(matrix, r, c);
                        if (poss.isEmpty()) return false;
                        if (poss.size() == 1) {
                            matrix[r][c] = poss.get(0);
                            progress = true;
                        }
                    }
                }
            }
            if (!progress) break;
        }

        int[] cell = findBestCell(matrix);
        if (cell == null) return true;

        int r = cell[0];
        int c = cell[1];

        for (int num : allPoss(matrix, r, c)) {
            int[][] copy = copy(matrix);
            copy[r][c] = num;
            if (solve(copy)) {
                copyto(matrix, copy);
                return true;
            }
        }
        return false;
    }

    private static int[] findBestCell(int[][] matrix) {
        int min = 10;
        int[] best = null;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (matrix[r][c] == 0) {
                    int size = allPoss(matrix, r, c).size();
                    if (size == 0) return new int[]{-1, -1};
                    if (size < min) {
                        min = size;
                        best = new int[]{r, c};
                    }
                }
            }
        }
        return best;
    }

    private static List<Integer> allPoss(int[][] matrix, int r, int c) {
        boolean[] used = new boolean[10];

        for (int i = 0; i < 9; i++) {
            if (matrix[r][i] != 0) used[matrix[r][i]] = true;
            if (matrix[i][c] != 0) used[matrix[i][c]] = true;
        }

        int br = (r / 3) * 3;
        int bc = (c / 3) * 3;

        for (int i = br; i < br + 3; i++)
            for (int j = bc; j < bc + 3; j++)
                if (matrix[i][j] != 0)
                    used[matrix[i][j]] = true;

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 9; i++)
            if (!used[i]) list.add(i);

        return list;
    }

    private static int[][] copy(int[][] matrix) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy[i][j] = matrix[i][j];
            }
        }
            return copy;

    }

    private static void copyto(int[][] matrix, int[][] copy) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = copy[i][j];
            }

        }
    }

    public static void print(int[][] matrix) {
        for (int[] row : matrix) {
            for (int v : row)
                System.out.print(v + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
            //        int matrix[][] = {
    //                {8,0,0,0,0,5,0,0,0},
    //                {0,7,0,9,0,0,0,4,0},
    //                {0,0,9,0,7,8,3,2,5},
    //                {3,0,1,0,9,0,0,5,0},
    //                {0,0,6,0,0,0,1,0,0},
    //                {0,9,0,0,3,0,6,0,2},
    //                {2,8,3,6,5,0,7,0,0},
    //                {0,1,0,0,0,2,0,8,0},
    //                {0,0,0,1,0,0,0,0,9}
    //        };
    //        int matrix[][]= {
    //                {3, 0, 6, 5, 0, 8, 4, 0, 0},
    //                {5, 2, 0, 0, 0, 0, 0, 0, 0},
    //                {0, 8, 7, 0, 0, 0, 0, 3, 1},
    //                {0, 0, 3, 0, 1, 0, 0, 8, 0},
    //                {9, 0, 0, 8, 6, 3, 0, 0, 5},
    //                {0, 5, 0, 0, 9, 0, 6, 0, 0},
    //                {1, 3, 0, 0, 0, 0, 2, 5, 0},
    //                {0, 0, 0, 0, 0, 0, 0, 7, 4},
    //                {0, 0, 5, 2, 0, 6, 3, 0, 0}
    //        };
        int[][] matrix = {
                {0, 0, 4, 6, 0, 0, 0, 0, 0},
                {0, 3, 9, 1, 2, 0, 6, 0, 0},
                {0, 0, 0, 4, 9, 0, 0, 0, 0},
                {7, 0, 0, 0, 0, 0, 0, 0, 5},
                {0, 0, 3, 9, 0, 6, 0, 0, 0},
                {4, 0, 0, 0, 0, 1, 9, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 5, 0},
                {1, 0, 0, 7, 0, 0, 0, 0, 3},
                {0, 0, 0, 5, 3, 0, 0, 0, 8}
        };

        solve(matrix);
        print(matrix);
    }
}
import java.util.ArrayList;

public class Sudoku {
    public static ArrayList<ArrayList<ArrayList<Integer>>> possibility = new ArrayList<>();

    public static boolean solve(int matrix[][]) {
        int best[] = findbest(matrix);

        if (best == null) return true;

        if (best[0] == -1) return false;

        int i = best[0];
        int j = best[1];
printBoard(matrix);
        for (int n : allPoss(matrix, i, j)) {
            matrix[i][j] = n;
            if (solve(matrix)) {
                return true;
            }
            matrix[i][j] = 0;
        }

        return false;
    }
    public static int[] findbest(int matrix[][]) {
        int best[] = null;
        int minimumPossible = Integer.MAX_VALUE;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrix[i][j] == 0) {
                    ArrayList<Integer> poss = allPoss(matrix, i, j);

                    if (poss.size() == 0) return new int[]{-1, -1};

                    if (poss.size() < minimumPossible) {
                        minimumPossible = poss.size();
                        best = new int[]{i, j};
                    }

                    if (minimumPossible == 1) return best;
                }
            }
        }
        return best;
    }

    public static boolean rowCheck(int matrix[][], int row) {
        boolean changed = false;
        for (int i = 1; i <= 9; i++) {
            int cnt = 0;
            int pos = -1;
            for (int j = 0; j < 9; j++) {
                if (matrix[row][j] == 0 && possibility.get(row).get(j).contains(i)) {
                    cnt++;
                    pos = j;
                }
            }
            if (cnt == 1) {
                matrix[row][pos] = i;
                System.out.println(row+" "+pos+" "+" "+i);
                possibility.get(row).get(pos).clear();
                dlt(matrix, row, pos, i);
                changed = true;
            }
        }
        return changed;
    }

    public static boolean colCheck(int matrix[][], int col) {
        boolean changed = false;
        for (int i = 1; i <= 9; i++) {
            int cnt = 0;
            int pos = -1;
            for (int j = 0; j < 9; j++) {
                if (matrix[j][col] == 0 && possibility.get(j).get(col).contains(i)) {
                    cnt++;
                    pos = j;
                }
            }
            if (cnt == 1) {
                matrix[pos][col] = i;
                System.out.println(pos+" "+col+" "+" "+i);
                possibility.get(pos).get(col).clear();
                dlt(matrix, pos, col, i);
                changed = true;
            }
        }
        return changed;
    }

    public static boolean boxCheck(int matrix[][], int row, int col) {
        boolean changed = false;
        int rowb = (row / 3) * 3;
        int colb = (col / 3) * 3;
        for (int num = 1; num <= 9; num++) {
            int cnt = 0;
            int posi = -1, posj = -1;
            for (int i = rowb; i < rowb + 3; i++) {
                for (int j = colb; j < colb + 3; j++) {
                    if (matrix[i][j] == 0 && possibility.get(i).get(j).contains(num)) {
                        cnt++;
                        posi = i; posj = j;
                    }
                }
            }
            if (cnt == 1) {
                matrix[posi][posj] = num;
                System.out.println(posi+" "+posj+" "+" "+num);
                possibility.get(posi).get(posj).clear();
                dlt(matrix, posi, posj, num);
                changed = true;
            }
        }
        return changed;
    }
    public static void dlt(int matrix[][], int r, int c, int num) {
        for (int i = 0; i < 9; i++) {
            possibility.get(r).get(i).remove(Integer.valueOf(num));
            possibility.get(i).get(c).remove(Integer.valueOf(num));
        }
        int rowb = (r / 3) * 3;
        int colb = (c / 3) * 3;
        for (int i = rowb; i < rowb + 3; i++) {
            for (int j = colb; j < colb + 3; j++) {
                possibility.get(i).get(j).remove(Integer.valueOf(num));
            }
        }
    }

        public static void printBoard(int matrix[][]){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static ArrayList<Integer> allPoss(int matrix[][], int r, int c) {
        ArrayList<Integer> l = new ArrayList<>();
        if (matrix[r][c] != 0) return l;
        boolean numbers[] = new boolean[10];
        for (int i = 0; i < 9; i++) {
            numbers[matrix[i][c]] = true;
            numbers[matrix[r][i]] = true;
        }
        int startrow = 3 * (r / 3);
        int startcol = 3 * (c / 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                numbers[matrix[startrow + i][startcol + j]] = true;
            }
        }
        for (int i = 1; i <= 9; i++) {
            if (!numbers[i]) l.add(i);
        }
        return l;
    }

    public static void possibleMat(int matrix[][]) {
        for (int i = 0; i < 9; i++) {
            possibility.add(new ArrayList<>());
            for (int j = 0; j < 9; j++) {
                possibility.get(i).add(allPoss(matrix, i, j));
            }
        }
    }

    public static void main(String args[]) {
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
        int matrix[][]= {
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
        possibleMat(matrix);

        for(int i=0;i<9;i++){
//            System.out.println("going");
            for(int j=0;j<9;j++) {
                if (matrix[i][j] == 0) {
                    System.out.println("The possiblilites of " + i + " " + j + " are");
                    for (int v : possibility.get(i).get(j)) {
                        System.out.print(v + " ");
                    }
                    System.out.println();
                }
            }
        }
        boolean changed = true;
        int cnt=0;
        while (changed) {
            cnt++;
            changed = false;
            for (int i = 0; i < 9; i++) {
                if (rowCheck(matrix, i)) changed = true;
                if (colCheck(matrix, i)) changed = true;
            }
            for (int i = 0; i < 9; i += 3) {
                for (int j = 0; j < 9; j += 3) {
                    if (boxCheck(matrix, i, j)) changed = true;
                }
            }
        }

        solve(matrix);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
            {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
//        System.out.println(cnt);
    }
}

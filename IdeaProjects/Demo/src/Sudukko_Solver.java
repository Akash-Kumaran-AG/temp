import java.util.Scanner;

public class Sudukko_Solver {
    public static boolean isSafe(int matrix[][],int row, int col, int number){
        for(int i=0;i<9;i++){
            if(matrix[i][col]==number) return false;
            if(matrix[row][i]==number) return false;
        }
        int startrow=3 * (row / 3);
        int startcol=3 * (col / 3);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(matrix[startrow+i][startcol+j]==number){
                    return false;
                }
            }
        }
        return true;
    }
    public static int intcnt=0;
    public static boolean solve(int matrix[][]){
        intcnt++;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(matrix[i][j]==0){
                    for(int k=1;k<=9;k++){
                        if(isSafe(matrix,i,j,k)){
                            matrix[i][j]=k;

                            if(solve(matrix)){
                                return true;
                            }

                            matrix[i][j]=0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    public static void main(String args[]){
        Scanner in=new Scanner(System.in);
        int matrix[][]= {
                {4, 7, 9, 0, 0, 0, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {5, 0, 0, 7, 0, 0, 0, 0, 9},
                {9, 0, 0, 0, 0, 0, 5,6, 8},
                {0, 0, 7, 0, 1, 5, 0, 0, 0},
                {0, 0, 4, 0, 0, 3, 0, 9, 0},
                {0, 0, 0, 8, 0, 1, 0, 0, 6},
                {0, 2, 0, 0, 4, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 4, 7, 0}
        };
        solve(matrix);
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
//        System.out.println(intcnt);


    }
}

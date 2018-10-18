
public class array2DTest {

    public static void main(String[] args) {
        int[][] test = new int[9][9];
        for(int[] a:test){
            for (int aa:a){
                aa=0;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (i==0||i==4|i==8){
                for (int j = 0; j < 9; j++) {
                    test[i][j]=1;
                }
            }else{
                for (int j = 0; j < 9; j++) {
                    if (j==0||j==4||j==8){
                        test[i][j]=1;
                    }
                }
            }
        }

        for(int[] a:test){
            for (int aa:a){
                System.out.print(aa+" ");
            }
            System.out.println();
        }
    }
}
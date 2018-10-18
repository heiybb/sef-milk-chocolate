import gameServer.Model.AStar;
import gameServer.Model.Node;

import java.util.List;

public class Astart {

    public static void main(String[] args) {
        int[][] WALL_AREA = new int[][]{
                {1, 1}, {1, 2}, {1, 3}, {1, 5}, {1, 6}, {1, 7},
                {2, 1}, {2, 2}, {2, 3}, {2, 5}, {2, 6}, {2, 7},
                {3, 1}, {3, 2}, {3, 3}, {3, 5}, {3, 6}, {3, 7},

                {5, 1}, {5, 2}, {5, 3}, {5, 5}, {5, 6}, {5, 7},
                {6, 1}, {6, 2}, {6, 3}, {6, 5}, {6, 6}, {6, 7},
                {7, 1}, {7, 2}, {7, 3}, {7, 5}, {7, 6}, {7, 7}};
        Node mon = new Node(4,4);
        Node tar = new Node(8,8);

        AStar test = new AStar(9,9,mon,tar);
        test.setBlocks(WALL_AREA);
        test.setBlock(0,0);
        test.setBlock(8,5);
        List<Node> out = test.findPath();
        out.forEach(System.out::println);
        try{
            int x=0;
            int y=5/x;

        }catch (ArithmeticException e){
            System.err.println();
        }catch (Exception b){
            System.err.println();
        }
    }
}

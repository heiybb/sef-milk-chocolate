package gameServer.Model;

import org.smartboot.socket.transport.AioSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Bobin Yuan s3677943
 * 10/13/2018
 * <p>
 * use to find the nearest node that monster can move forward
 */
public class NearestNodeSearch {
    private static final int ROW = 9;
    private static final int COL = 9;
    //store the wall area
    private static final int[][] WALL_AREA = new int[][]{
            {1, 1}, {1, 2}, {1, 3}, {1, 5}, {1, 6}, {1, 7},
            {2, 1}, {2, 2}, {2, 3}, {2, 5}, {2, 6}, {2, 7},
            {3, 1}, {3, 2}, {3, 3}, {3, 5}, {3, 6}, {3, 7},

            {5, 1}, {5, 2}, {5, 3}, {5, 5}, {5, 6}, {5, 7},
            {6, 1}, {6, 2}, {6, 3}, {6, 5}, {6, 6}, {6, 7},
            {7, 1}, {7, 2}, {7, 3}, {7, 5}, {7, 6}, {7, 7}};

    /**
     * @param roles    roles contain all the players position
     * @param monsterX the monster X pos in the game board
     * @param monsterY the monster Y pos in the game board
     * @return 2 array include X and Y for monster move
     */
    public static int[] findNearPos(HashMap<AioSession<String>, Player> roles, int monsterX, int monsterY) {

        Node monsterPos = new Node(monsterX, monsterY);

        HashMap<Integer, List<Node>> allPossible = new HashMap<>();

        ArrayList<Node> playerNodes = new ArrayList<>();

        roles.forEach((session, player) -> {
            int targetPlayerPosX = Integer.valueOf(player.getPosition().split("-")[1]);
            int targetPlayerPosY = Integer.valueOf(player.getPosition().split("-")[2]);
            playerNodes.add(new Node(targetPlayerPosX, targetPlayerPosY));
        });

        ArrayList<List<Node>> pathList = new ArrayList<>();
        playerNodes.forEach(node -> {
            AStar map = new AStar(ROW, COL, monsterPos, node);
            //set wall area
            map.setBlocks(WALL_AREA);
            List<Node> otherPlayerNode = new ArrayList<>(playerNodes);
            otherPlayerNode.remove(node);
            //set other player pos as block
            otherPlayerNode.forEach(other -> {
                map.setBlock(other.getRow(), other.getCol());
            });
            if (map.findPath().size() != 0) {
                pathList.add(map.findPath());
            }
        });

        System.out.println(pathList.size());


        pathList.forEach(path -> {
            //if the size is the same then will overwrite the last same one in order to minimize the paths
            if (path.size() != 0) {
                //try to fix path 0 bug
                allPossible.put(path.size(), path);
            }
        });

        TreeMap<Integer, List<Node>> sortPath = new TreeMap<>(allPossible);

//        List<Node> shortestPath = sortPath.firstEntry().getValue();

        List<Node> shortestPath = getShortest(pathList);

        System.out.println(shortestPath.size());


        //the nearest Node that the monster can move to
        Node nearestNode = shortestPath.get(1);
        int[] nearest = new int[2];
        nearest[0] = nearestNode.getRow();
        nearest[1] = nearestNode.getCol();
        roles.forEach((session, player) -> {
            System.out.println(player.getXY());
        });
        System.out.println("Target Pos:" + shortestPath.get(shortestPath.size() - 1));
        System.out.println("Monster Current Position: " + monsterX + "," + monsterY);
        System.out.println("Monster is moving to:" + nearest[0] + "," + nearest[1]);
        return nearest;

    }

    private static List<Node> getShortest(ArrayList<List<Node>> pathList) {
        int pathSize = 90;
        int index = 0;
        for (int i = 0; i < pathList.size() - 1; i++) {
            if (pathList.get(i).size() < pathSize) {
                pathSize = pathList.get(i).size();
                index = i;
            }
        }
        return pathList.get(index);
    }
}

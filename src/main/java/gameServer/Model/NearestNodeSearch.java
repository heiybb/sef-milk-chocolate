package gameServer.Model;

import org.smartboot.socket.transport.AioSession;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static List<Node> getShortest(HashMap<Integer, List<Node>> pathList) {
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

    // Function to merge multiple arrays in Java 8
    private static int[][] merge(int[][]... arrays) {
        return Stream.of(arrays)
                // or use Arrays::stream
                .flatMap(Stream::of)
                .toArray(int[][]::new);

    }

    private static int[][] mergeNode(ArrayList<Node> other) {
        int[][] all = WALL_AREA;
        for (Node a : other) {
            all = merge(all, new int[][]{{a.getCol(), a.getRow()}});
        }
        return all;
    }

    /**
     * @param hashMap HashMap type require
     * @param <K>     any key
     * @param <V>     any value
     * @return TreeMap that sorted
     */
    //construct a new TreeMap from HashMap
    private static <K, V> Map<K, V> getTreeMap(Map<K, V> hashMap) {
        Map<K, V> treeMap = hashMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> newValue,
                        TreeMap::new));

        return treeMap;
    }

    /**
     * @param roles    roles contain all the players position
     * @param monsterX the monster X pos in the game board
     * @param monsterY the monster Y pos in the game board
     * @return 2 array include X and Y for monster move
     */
    public int[] findNearPos(HashMap<AioSession<String>, Player> roles, int monsterX, int monsterY) {

        Node monsterPos = new Node(monsterX, monsterY);

        ArrayList<Node> playerNodes = new ArrayList<>();

        ArrayList<List<Node>> outpath = new ArrayList<>();


        HashMap<Integer, List<Node>> testHashMap = new HashMap<>();

        roles.forEach((session, player) -> {
//            int targetPlayerPosX = Integer.valueOf(player.getPosition().split("-")[1]);
//            int targetPlayerPosY = Integer.valueOf(player.getPosition().split("-")[2]);
            int targetPlayerPosX = player.getPos().getPositionX();
            int targetPlayerPosY = player.getPos().getPositionY();
            playerNodes.add(new Node(targetPlayerPosX, targetPlayerPosY));
        });

        playerNodes.forEach(node -> {
            ArrayList<Node> otherPlayerNode = new ArrayList<>(playerNodes);
            otherPlayerNode.remove(node);

            AStar map = new AStar(ROW, COL, monsterPos, node);
            map.setBlocks(WALL_AREA);
            //bug point
            otherPlayerNode.forEach(o -> {
                map.setBlock(o.getCol(), o.getRow());
            });
            List<Node> out = map.findPath();

            outpath.add(0, out);
            testHashMap.put(out.size(), out);
        });

        TreeMap<Integer, List<Node>> allPathTree = new TreeMap<>(testHashMap);

        Node nearestNode = allPathTree.firstEntry().getValue().get(1);
        int[] nearest = new int[]{nearestNode.getRow(), nearestNode.getCol()};

        roles.forEach((session, player) -> {
            System.out.println("Player Position:" + player.getXY());
        });
        System.out.println("Monster Current Position: " + monsterX + "," + monsterY);
        System.out.println("Monster Next Position:" + nearest[0] + "," + nearest[1]);
        return nearest;

    }
}

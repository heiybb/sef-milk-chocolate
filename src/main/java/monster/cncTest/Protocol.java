package monster.cncTest;

class Protocol {

    private String message = "";

    String IDPacket(int id) {
        message = "ID" + id;
        return message;
    }

    String NewClientPacket(int x, int y, int dir, int id) {
        message = "NewClient" + x + "," + y + "-" + dir + "|" + id;
        return message;
    }

}
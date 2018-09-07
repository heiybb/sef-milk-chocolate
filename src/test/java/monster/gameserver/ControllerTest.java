package monster.gameserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {

    private Controller testController;

    @Before
    public void setUp() throws Exception {
        testController = new Controller();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void startServer() {
    }

    @Test
    public void stopServer() {
    }

    @Test
    public void initBoard() {

        testController.initBoard();
        assertEquals(0,testController.getBoard(0,0));
    }

    @Test
    public void setBoard() {
        testController.setBoard(0,0);
        assertEquals(1,testController.getBoard(0,0));


    }

    @Test
    public void getBoard() {
    }
}
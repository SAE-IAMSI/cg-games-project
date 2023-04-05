package games.project.paco_mano;

import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TestsPacoMano {

    @Test
    public void testisAWall(){
        ArrayList<Rectangle> wallList = new ArrayList<>();
        Rectangle r = new Rectangle(1,1, 5,5);
        wallList.add(r);
        assertEquals(1, wallList.get(0).getX());
    }
}

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.neron.testaqa.IceBoxRoom;
import ru.neron.testaqa.Room;
import ru.neron.testaqa.RoomClosedException;

/**
 * Created by neron on 02.11.15.
 */
public class OpenMethod {

    // Для каждого теста будет создан свой объект
    private Room room = new IceBoxRoom();

    // Приводим комнату в начальное состояние для выполнения тестов, открываем
    @Before
    public void openRoom() {
        room.open();
    }

    @Test(expected = Test.None.class)
    public void isEmpty() throws RoomClosedException {
        room.isEmpty();
    }

    @Test(expected = Test.None.class)
    public void twoTimesOpen() throws RoomClosedException {
        room.open();
        room.isEmpty();
    }

    @Test(expected = Test.None.class)
    public void withClose() throws RoomClosedException {
        room.open();
        room.isEmpty();
        room.close();
        try {
            room.isEmpty();
        } catch (RoomClosedException e) { }
        room.open();
        room.isEmpty();
    }

    // Закрываем комнату после любого теста(даже зафейленного и броукен теста)
    // Метод, помеченный аннотацией @After выполнятеся всегда
    @After
    public void closeRoomAfter() {
        room.close();
    }
}

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.neron.testaqa.IceBoxRoom;
import ru.neron.testaqa.Room;
import ru.neron.testaqa.RoomClosedException;

import static org.junit.Assert.fail;

/**
 * Created by neron on 02.11.15.
 */
public class CloseMethod {

    // Для каждого теста будет создан свой объект
    private Room room = new IceBoxRoom();

    // Приводим комнату в начальное состояние
    @Before
    public void closeRoom() {
        room.close();
    }

    @Test(expected = RoomClosedException.class)
    public void isEmpty() throws RoomClosedException {
        room.isEmpty();
    }

    @Test(expected = RoomClosedException.class)
    public void twoTimesClose() throws RoomClosedException {
        room.close();
        room.isEmpty();
    }

    @Test(expected = RoomClosedException.class)
    public void withOpen() throws RoomClosedException {
        room.open();
        try {
            room.isEmpty();
        } catch (Exception e) {
            fail("Метод isEmpty() не должен был кидать исключение после метода open()");
        }
        room.close();
        room.isEmpty();
    }

    // Возвращаем начальное состояние комнате,
    // даже если какой-нибудь метод обладает сайд эффектом и открывает комнату,
    // метод closeRoomAfter Закроет комнату после выполнения теста
    @After
    public void closeRoomAfter() {
        room.close();
    }
}

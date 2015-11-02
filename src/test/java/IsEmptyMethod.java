import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.neron.testaqa.IceBoxRoom;
import ru.neron.testaqa.Room;
import ru.neron.testaqa.RoomClosedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by neron on 02.11.15.
 */
public class IsEmptyMethod {

    // Для каждого теста будет создан свой объект
    private Room room = new IceBoxRoom();

    // Открываем комнату
    @Before
    public void openRoom() {
        room.open();
    }

    @Test(expected = Test.None.class)
    public void isEmpty() throws RoomClosedException {
        room.isEmpty();
    }

    @Test(expected = RoomClosedException.class)
    public void afterClose() throws RoomClosedException {
        room.close();
        room.isEmpty();
    }

    @Test
    public void initializedRoomIsEmpty() throws RoomClosedException {
        assertThat("Изначально комната должна быть пустой", room.isEmpty(), is(true));
    }

    @Test
    public void isEmptyAfterAdd() throws RoomClosedException {
        room.addObject(null);
        assertThat("Комната не пустая", room.isEmpty(), is(false));
    }

    @After
    public void closeRoom() {
        try {
            room.removeObject();
        } catch (RoomClosedException rce) {}
        room.close();
    }


}

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.neron.testaqa.IceBoxRoom;
import ru.neron.testaqa.Room;
import ru.neron.testaqa.RoomClosedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by neron on 02.11.15.
 */
public class AddObject {

    // Для каждого теста будет создан свой объект
    private Room room = new IceBoxRoom();

    // Открываем комнату
    @Before
    public void openRoom() {
        room.open();
    }

    @Test(expected = Test.None.class)
    public void addObject() throws RoomClosedException {
        room.addObject(null);
    }

    @Test(expected = RoomClosedException.class)
    public void addWhenClosed() throws RoomClosedException {
        room.close();
        room.addObject(null);
    }

    @Test
    public void addNullAndCheck() throws RoomClosedException {
        room.addObject(null);
        assertThat("Добавленный элемент должен быть null", room.getObject(), nullValue());
    }

    @Test
    public void addObjectAndCheck() throws RoomClosedException {
        final Object objectToAdd = new Object();
        room.addObject(objectToAdd);
        assertThat("Добавленный элемент должен быть " + objectToAdd, room.getObject(), is(objectToAdd));
    }

    @Test
    public void addTwoObjectsSequentially() throws RoomClosedException {
        final Object objectToAddFirst = new Object();
        final Object objectToAddSecond = new Object();
        room.addObject(objectToAddFirst);
        room.addObject(objectToAddSecond);
        assertThat("Добавленный элемент должен быть " + objectToAddSecond, room.getObject(), is(objectToAddSecond));
    }

    @After
    public void closeRoom() {
        try {
            room.removeObject();
        } catch (RoomClosedException rce) {}
        room.close();
    }
}

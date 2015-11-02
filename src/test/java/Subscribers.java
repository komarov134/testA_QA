import org.junit.Test;
import ru.neron.testaqa.IEventListener;
import ru.neron.testaqa.IceBoxRoom;
import ru.neron.testaqa.Room;
import ru.neron.testaqa.RoomClosedException;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by neron on 02.11.15.
 */
public class Subscribers {

    // Для каждого теста будет создан свой объект
    private Room room = new IceBoxRoom();

    @Test
    public void subscribe() throws RoomClosedException {
        final List<Object> objects = new LinkedList<>();
        IEventListener listener = objects::add;
        room.open();
        room.subscribe(listener);
        Object objectToAdd = new Object();
        room.addObject(objectToAdd);
        assertThat("Количество добавленных должно быть равно 1", objects, hasSize(equalTo(1)));
        assertThat("Добавляли один объект, а пришел совсем другой", objects, hasItem(equalTo(objectToAdd)));
    }

    @Test
    public void doubleSubscribe() throws RoomClosedException {
        final List<Object> objects = new LinkedList<>();
        IEventListener listener = objects::add;
        room.open();
        room.subscribe(listener);
        room.subscribe(listener);
        room.addObject(new Object());
        assertThat("Несмотря на то, что подписались дважды, оповещение должно приходить однажды",
                objects, hasSize(equalTo(1)));
    }

    @Test
    public void subscribeAddTwoObjects() throws RoomClosedException {
        final List<Object> objects = new LinkedList<>();
        IEventListener listener = objects::add;
        room.open();
        room.subscribe(listener);
        room.addObject(new Object());
        room.addObject(new Object());
        assertThat("не пришли оповещения о каждом добавленном объекте", objects, hasSize(equalTo(2)));
    }

    @Test
    public void twoSubscribers() throws RoomClosedException {
        final List<Object> firstListenerObjects = new LinkedList<>();
        final List<Object> secondListenerObjects = new LinkedList<>();
        IEventListener firstListener = firstListenerObjects::add;
        IEventListener secondListener = secondListenerObjects::add;
        room.open();
        room.subscribe(firstListener);
        room.subscribe(secondListener);
        final Object objectToAdd = new Object();
        room.addObject(objectToAdd);
        assertThat("Должно быть одно уведомление у первого подписчика", firstListenerObjects, hasSize(equalTo(1)));
        assertThat("Пришедший объект у первого подписчика не соответсвует ожидаемому", firstListenerObjects, hasItem(objectToAdd));
        assertThat("Должно быть одно уведомление у второго подписчика", secondListenerObjects, hasSize(equalTo(1)));
        assertThat("Пришедший у второго подписчика объект не соответсвует ожидаемому", secondListenerObjects, hasItem(objectToAdd));
    }
}

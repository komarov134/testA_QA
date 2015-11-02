package ru.neron.testaqa;

/**
 * Created by neron on 02.11.15.
 */
public interface Room {

    void open();

    void close();

    boolean isEmpty() throws RoomClosedException;

    Object getObject() throws RoomClosedException;

    void addObject(Object object) throws RoomClosedException;

    void removeObject() throws RoomClosedException;

    void subscribe(IEventListener listener);
}

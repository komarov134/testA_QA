package ru.neron.testaqa;

/**
 * Created by neron on 02.11.15.
 */
public class Student implements IEventListener {

    private Room room;

    public Student(Room room) {
        this.room = room;
        room.subscribe(this);
    }

    @Override
    public void onObjectInRoom(Object object) {
        System.out.println(object.getClass());
        System.out.println(object);
    }
}

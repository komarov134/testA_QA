package ru.neron.testaqa;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by neron on 02.11.15.
 */
// Изначально комната закрыта
// Данная реализация не является потокобезопасной!
public class IceBoxRoom implements Room {

    // Изначально комната инициализируется как закрытая
    private boolean isOpened = false;
    // Дополнительный флаг для того, чтобы хранить хранить null (В спеке не сказано, что не должны хранить null)
    private boolean hasObject = false;
    // Ссылка на объект, который храним
    // поддерживаем инвариант: Если (hasObject == false), то (object == null)
    // Обратное неверно (т.к. можем хранить в комнате null)
    private Object object = null;
    // Список подписчиков(наблюдателей)
    private List<IEventListener> listeners = new ArrayList<>();

    // Открываем комнату, если она была закрыта
    @Override
    public void open() {
        isOpened = true;
    }

    // Закрываем комнату, если она была открыта
    @Override
    public void close() {
        isOpened = false;
    }

    // ПРоверяем, пустая ли комната
    @Override
    public boolean isEmpty() throws RoomClosedException {
        checkRoomIsOpened();
        return !hasObject;
    }

    // Т.к. мы можем запросить объект у пустой комнаты и мы можем хранить null,
    // то метод может кидать исключение
    // по спеке этот метод должен бросать только одно проверяемое исключение,
    // поэтому кидаем непроверяемое
    @Override
    public Object getObject() throws RoomClosedException {
        checkRoomIsOpened();
        if (!hasObject) {
            throw new NoSuchElementException("Элемент отсутствует в комнате");
        }
        return object;
    }

    // Замещаем элемент, находящийся в комнате (возможно null)
    // Оповещаем всех наблюдателей
    @Override
    public void addObject(Object object) throws RoomClosedException {
        checkRoomIsOpened();
        hasObject = true;
        this.object = object;
        listeners.forEach(l -> l.onObjectInRoom(object));
    }

    // Удаляем элемент из комнаты, если не пусто, иначе ничего не делаем
    // поддерживаем инвариант: Если (hasObject == false), то (object == null)
    @Override
    public void removeObject() throws RoomClosedException {
        checkRoomIsOpened();
        hasObject = false;
        this.object = null;
    }

    // Подписываем наблюдателя
    // Если передан null, то кидаем NPE
    @Override
    public void subscribe(IEventListener listener) {
        if (listener == null)
            throw new NullPointerException();
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    // Проверка открытости комнаты вынесена в отдельный метод,
    // чтобы не дублировался код
    private void checkRoomIsOpened() throws RoomClosedException {
        if (!isOpened) {
            throw new RoomClosedException();
        }
    }
}

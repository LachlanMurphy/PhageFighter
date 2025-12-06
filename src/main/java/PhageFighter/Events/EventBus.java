package PhageFighter.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private static EventBus instance;
    private EventBus() {}
    private final Map<EventType, List<IObserver>> observers = new HashMap<>();

    public static synchronized EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void attach(IObserver observer, EventType eventType) {
        List<IObserver> list = observers.computeIfAbsent(eventType, k -> new ArrayList<>());
        list.add(observer);
    }

    public void postMessage(EventType eventType) {
        List<IObserver> list = observers.get(eventType);
        if (list != null) {
            for (IObserver observer : list) {
                observer.update(eventType);
            }
        }

        List<IObserver> list2 = observers.get(EventType.All);
        if (list2 != null) {
            for (IObserver observer : list2) {
                observer.update(eventType);
            }
        }
    }
}

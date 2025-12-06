package PhageFighter.Observers;

import PhageFighter.Events.EventType;
import PhageFighter.Events.IObserver;

import java.util.ArrayList;
import java.util.List;

public class Observer implements IObserver {
    private List<EventType> events;
    public Observer() {
        events = new ArrayList<>();
    }

    @Override
    public void update(EventType eventType){
        events.add(eventType);
    }

    public boolean hasEvent(EventType event){
        return events.stream().anyMatch(e -> e.equals(event));
    }

}
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
        switch (eventType) {
            case HealthPlus: {
                System.out.println("Player gained health.");
            } break;
            case LevelUp: {
                System.out.println("Player leveled up.");
            } break;
            case GameStart: {
                System.out.println("Game has started.");
            } break;
            case HealthLoss: {
                System.out.println("Player has lost health");
            } break;
            case  MaxHealthPlus: {
                System.out.println("Player max health increased.");
            } break;
            case EnemyDefeated: {
                System.out.println("Player has defeated an enemy.");
            } break;
        }
    }

    public boolean hasEvent(EventType event){
        return events.stream().anyMatch(e -> e.equals(event));
    }

}
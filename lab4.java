package lab4;
import java.util.ArrayList;
import java.util.List;

// ======= ПАСАЖИРИ ========================================================
class Human {
    private final String name;

    public Human(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + name;
    }
}

class Firefighter extends Human {
    public Firefighter(String name) { super(name); }
}

class Policeman extends Human {
    public Policeman(String name) { super(name); }
}

// ======= ТРАНСПОРТ =======================================================
abstract class Vehicle<T extends Human> {
    private final int maxSeats;
    protected final List<T> passengers = new ArrayList<>();

    public Vehicle(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getMaxSeats() { return maxSeats; }

    public int getOccupiedSeats() { return passengers.size(); }

    public void addPassenger(T passenger) {
        if (passengers.size() >= maxSeats) {
            throw new IllegalStateException("Усi мiсця зайнято!");
        }
        passengers.add(passenger);
    }

    public void removePassenger(T passenger) {
        if (!passengers.remove(passenger)) {
            throw new IllegalArgumentException("Пасажир не знаходиться у транспортi!");
        }
    }

    public List<T> getPassengers() {
        return passengers;
    }
}

// ======= КОНКРЕТНІ ТИПИ ТРАНСПОРТУ =======================================
abstract class Car<T extends Human> extends Vehicle<T> {
    public Car(int maxSeats) {
        super(maxSeats);
    }
}

class Taxi extends Car<Human> {
    public Taxi(int maxSeats) { super(maxSeats); }
}

class PoliceCar extends Car<Policeman> {
    public PoliceCar(int maxSeats) { super(maxSeats); }
}

class FireTruck extends Car<Firefighter> {
    public FireTruck(int maxSeats) { super(maxSeats); }
}

class Bus extends Vehicle<Human> {
    public Bus(int maxSeats) { super(maxSeats); }
}

// ======= ДОРОГА ==========================================================
class Road {
    private final List<Vehicle<? extends Human>> carsInRoad = new ArrayList<>();

    public void addCarToRoad(Vehicle<? extends Human> car) {
        carsInRoad.add(car);
    }

    public int getCountOfHumans() {
        int count = 0;
        for (Vehicle<? extends Human> v : carsInRoad) {
            count += v.getOccupiedSeats();
        }
        return count;
    }
}

// =========================================================================
public class lab4 {

    private static void assertEquals(Object expected, Object actual, String message) {
        if ((expected == null && actual != null) || (expected != null && !expected.equals(actual))) {
            System.out.println("Тест не пройдено: " + message);
        } else {
            System.out.println("Тест успiшний: " + message);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Тестування програми ===");

        System.out.println("--- Тест 1: Таксі може перевозити будь-яких пасажирів ---");
        Taxi taxi = new Taxi(2);
        taxi.addPassenger(new Human("Iван"));
        taxi.addPassenger(new Policeman("Петро"));
        assertEquals(2, taxi.getOccupiedSeats(), "Таксi може брати будь-яких пасажирiв");

        System.out.println("--- Тест 2: Перевірка переповнення місць ---");
        Taxi taxi2 = new Taxi(1);
        taxi2.addPassenger(new Human("Олег"));
        try {
            taxi2.addPassenger(new Human("Марiя"));
            System.out.println("Тест не пройдено");
        } catch (IllegalStateException e) {
            System.out.println("Тест успiшний");
        }

        System.out.println("--- Тест 3: Видалення пасажира ---");
        Taxi taxi3 = new Taxi(2);
        Human h1 = new Human("Катя");
        taxi3.addPassenger(h1);
        taxi3.removePassenger(h1);
        assertEquals(0, taxi3.getOccupiedSeats(), "Пасажира успiшно видалено");

        System.out.println("--- Тест 4: Видалення неіснуючого пасажира ---");
        try {
            taxi3.removePassenger(new Human("Невiдомий"));
            System.out.println("Тест не пройдено");
        } catch (IllegalArgumentException e) {
            System.out.println("Тест успiшний");
        }

        System.out.println("--- Тест 5: Поліцейська машина бере тільки поліцейських ---");
        PoliceCar policeCar = new PoliceCar(2);
        policeCar.addPassenger(new Policeman("Сергiй"));
        assertEquals(1, policeCar.getOccupiedSeats(), "Полiцейська машина з полiцiантами");

        System.out.println("--- Тест 6: Пожежна машина бере тільки пожежників ---");
        FireTruck fireTruck = new FireTruck(2);
        fireTruck.addPassenger(new Firefighter("Дмитро"));
        assertEquals(1, fireTruck.getOccupiedSeats(), "Пожежна машина перевозить пожежникiв");

        System.out.println("--- Тест 7: Перевірка підрахунку людей на дорозі ---");
        Road road = new Road();
        road.addCarToRoad(taxi);
        road.addCarToRoad(policeCar);
        road.addCarToRoad(fireTruck);
        assertEquals(4, road.getCountOfHumans(), "Пiдрахунок кiлькостi людей на дорозi");

        System.out.println("=== Тестування завершено ===");
    }
}

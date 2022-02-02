package my.garage;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MyGarageTest {

    private final MyGarage garage = new MyGarage();
    private final Owner dima = new Owner(1, "Dmitry", "Kravtsov", 25);
    private final Owner alex = new Owner(2, "Alex", "Petrov", 29);
    private final Owner oleg = new Owner(3, "Oleg", "Sokolov", 58);
    private final Car car1 = new Car(1, "BMW", "X5", 200, 350, 1);
    private final Car car2 = new Car(2, "BMW", "X6", 300, 350, 2);
    private final Car car3 = new Car(3, "VW", "Passat", 190, 200, 3);
    private final Car car4 = new Car(4, "Audi", "A8", 320, 250, 1);
    private final Car car5 = new Car(5, "Lada", "Kalina", 100, 150, 3);

    public MyGarageTest() {
        garage.addCar(car1, dima);
        garage.addCar(car2, alex);
        garage.addCar(car3, oleg);
        garage.addCar(car4, dima);
        garage.addCar(car5, oleg);
    }


    @Test
    public void allCarsUniqueOwners() throws Exception {
        assertEquals(new HashSet<>(Arrays.asList(alex, dima, oleg)),
                new HashSet<>(garage.allCarsUniqueOwners()));
    }

    @Test
    public void topThreeCarsByMaxVelocity() throws Exception {
        assertEquals(new TreeSet<>(Arrays.asList(car2, car4, car1)),
                new TreeSet<>(garage.topThreeCarsByMaxVelocity()));
    }

    @Test
    public void allCarsOfBrand() throws Exception {
        assertEquals(new TreeSet<>(Arrays.asList(car5)),
                new TreeSet<>(garage.allCarsOfBrand("Lada")));
        assertEquals(new TreeSet<>(Arrays.asList(car2, car1)),
                new TreeSet<>(garage.allCarsOfBrand("BMW")));
    }

    @Test
    public void carsWithPowerMoreThan() throws Exception {
        assertEquals(new TreeSet<>(Arrays.asList(car4, car1, car2)),
                new TreeSet<>(garage.carsWithPowerMoreThan(200)));
    }

    @Test
    public void allCarsOfOwner() throws Exception {
        assertEquals(new TreeSet<>(Arrays.asList(car4, car1)),
                new TreeSet<>(garage.allCarsOfOwner(dima)));
        assertEquals(new TreeSet<>(Arrays.asList(car2)),
                new TreeSet<>(garage.allCarsOfOwner(alex)));
    }

    @Test
    public void meanOwnersAgeOfCarBrand() throws Exception {
        assertEquals(27, garage.meanOwnersAgeOfCarBrand("BMW"));
        assertEquals(58, garage.meanOwnersAgeOfCarBrand("Lada"));
    }

    @Test
    public void meanCarNumberForEachOwner() throws Exception {
        assertEquals(2, garage.meanCarNumberForEachOwner());
    }

    @Test
    public void removeCar() throws Exception {
        assertEquals(2, new TreeSet<>(garage.allCarsOfOwner(dima)).size());
        assertEquals(car4, garage.removeCar(4));
        assertEquals(1, new TreeSet<>(garage.allCarsOfOwner(dima)).size());
    }

    @Test
    public void addCar() throws Exception {
        int carId = 10;
        Car addedCar = new Car(carId, "Lada", "Malina", 130, 120, 1);
        garage.addCar(addedCar, dima);
        assertEquals(new TreeSet<>(Arrays.asList(addedCar, car1, car4)),
                new TreeSet<>(garage.allCarsOfOwner(dima)));
        garage.removeCar(carId);
    }

    @Test
    public void getAllCars() throws Exception {
        assertEquals(new TreeSet<>(Arrays.asList(car5, car4, car3, car1, car2)),
                new TreeSet<>(garage.getAllCars()));
    }

}
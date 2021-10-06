package my.garage;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyGarage garage = new MyGarage();
        Owner dima = new Owner(1, "Dmitry", "Kravtsov", 5);
        Owner alex = new Owner(2, "Alex", "Petrov", 29);
        Owner oleg = new Owner(3, "Oleg", "Sokolov", 52);
        Car car1 = new Car(1, "BMW", "X5", 300, 250, 1);
        Car car2 = new Car(2, "BMW", "X6", 300, 350, 1);
        Car car3 = new Car(3, "VW", "Passat", 200, 350, 3);
        Car car4 = new Car(4, "Audi", "A8", 300, 250, 2);
        Car car5 = new Car(5, "Lada", "Kalina", 100, 150, 3);

        garage.addCar(car1, dima);
        garage.addCar(car2, dima);
        garage.addCar(car3, oleg);
        garage.addCar(car4, oleg);
        garage.addCar(car5, oleg);

        System.out.println("allCarsUniqueOwners:");
        for (Owner owner : garage.allCarsUniqueOwners()) {
            System.out.println(owner);
        }
        System.out.println("allCarsOfBrand:");
        for (Car car : garage.allCarsOfBrand("BMW")) {
            System.out.println(car);
        }
        System.out.println("carsWithPowerMoreThan:");
        for (Car car : garage.carsWithPowerMoreThan(250)) {
            System.out.println(car);
        }
        System.out.println("allCarsOfOwner:");
        for (Car car : garage.allCarsOfOwner(dima)) {
            System.out.println(car);
        }
        System.out.println("topThreeCarsByMaxVelocity:");
        for (Car car : garage.topThreeCarsByMaxVelocity()) {
            System.out.println(car);
        }
        System.out.println("meanCarNumberForEachOwner: " + garage.meanCarNumberForEachOwner());
        System.out.println("meanOwnersAgeOfCarBrand: " + garage.meanOwnersAgeOfCarBrand("BMW"));
        System.out.println("removeCar:");
//        garage.removeCar(312);
        garage.removeCar(1);
        garage.removeCar(4);
        garage.removeCar(5);
        garage.removeCar(3);
//        garage.removeCar(5);
//        garage.removeCar(4);
//        garage.removeCar(3);
        for (Car car : garage.getAllCars()) {
            System.out.println(car);
        }
        System.out.println("topThreeCarsByMaxVelocity:");
        for (Car car : garage.topThreeCarsByMaxVelocity()) {
            System.out.println(car);
        }
    }


}

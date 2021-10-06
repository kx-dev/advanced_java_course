package my.garage;

import java.util.*;

public class MyGarage implements Garage {

    private class ComparatorByPower implements Comparator<Car> {
        @Override
        public int compare(Car car1, Car car2) {
            // сравниваем сначала по Power, потом по CarId, т.е. car1 и car2 всегда будут различаться
            return Comparator.comparingInt(Car::getPower).thenComparingLong(Car::getCarId).compare(car1, car2);
        }
    }
    private class ComparatorByVelocity implements Comparator<Car> {
        @Override
        public int compare(Car car1, Car car2) {
            // сравниваем сначала по MaxVelocity, потом по CarId, т.е. car1 и car2 всегда будут различаться
            return Comparator.comparingInt(Car::getMaxVelocity).thenComparingLong(Car::getCarId).compare(car1, car2);
        }
    }

    private final Map<Long, Car> mapIdToCar = new HashMap<>();
    private final Map<Integer, Owner> mapIdToOwner = new HashMap<>();
    private final Map<Owner, TreeSet<Car>> mapOwnerToCars = new HashMap<>();
    private final Map<String, TreeSet<Car>> mapBrandToCars = new HashMap<>();
    private final TreeSet<Car> setCarsByPower = new TreeSet<>(new ComparatorByPower());
    private final TreeSet<Car> setCarsByVelocity = new TreeSet<>(new ComparatorByVelocity());
    private final int topNCars = 3;

    @Override
    public Collection<Owner> allCarsUniqueOwners() {
//        return mapIdToOwner.values();
        return mapOwnerToCars.keySet();
    }

    @Override
    public Collection<Car> topThreeCarsByMaxVelocity() {
        Iterator<Car> iter = setCarsByVelocity.descendingIterator();
        Car fromCar = null;
        for (int j = 0; j < topNCars && iter.hasNext(); j++) {
            fromCar = iter.next();
        }
        return setCarsByVelocity.tailSet(fromCar);
    }

    @Override
    public Collection<Car> allCarsOfBrand(String brand) {
        return mapBrandToCars.get(brand);
    }

    @Override
    public Collection<Car> carsWithPowerMoreThan(int power) {
        return setCarsByPower.tailSet(new Car(Long.MAX_VALUE, "", "", 0, power, 0));
    }

    @Override
    public Collection<Car> allCarsOfOwner(Owner owner) {
        return mapOwnerToCars.get(owner);
    }

    @Override
    public int meanOwnersAgeOfCarBrand(String brand) {
        Collection<Car> cars = allCarsOfBrand(brand);
        TreeSet<Integer> ownerIds = new TreeSet<>();
        for (Car car : cars) {
            ownerIds.add(car.getOwnerId());
        }
        int age = 0;
        for (Integer ownerId : ownerIds) {
            age += mapIdToOwner.get(ownerId).getAge();
        }
        return (int)Math.round(age / (double) ownerIds.size());
    }

    @Override
    public int meanCarNumberForEachOwner() {
        return (int)Math.round(mapIdToCar.size() / (double) mapIdToOwner.size());
    }

    @Override
    public Car removeCar(int _carId) {
        Long carId = new Long(_carId);
        if (! mapIdToCar.containsKey(carId)) { // Если машина с таким carId не существует
            throw new RuntimeException("Unable to remove car, no car with ID = " + carId);
        }
        Car car = mapIdToCar.get(carId);
        Owner owner = mapIdToOwner.get(car.getOwnerId());
        String brand = car.getBrand();
        mapOwnerToCars.get(owner).remove(car);
        if (mapOwnerToCars.get(owner).isEmpty()) {// Если удалена последняя машина owner'а, удалить owner'а
            mapOwnerToCars.remove(owner);
            mapIdToOwner.remove(car.getOwnerId());
        }
        mapBrandToCars.get(brand).remove(car);
        if (mapBrandToCars.get(brand).isEmpty()) { // Удалить "пустой" бренд
            mapBrandToCars.remove(brand);
        }
        setCarsByPower.remove(car);
        setCarsByVelocity.remove(car);
        mapIdToCar.remove(carId);
        return car;
    }

    @Override
    public void addCar(Car car, Owner owner) {
        if (mapIdToCar.containsKey(car.getCarId())) {
            throw new RuntimeException("Unable to add car, car with Id = " + car.getCarId() + " already exists.");
        }
        mapIdToCar.put(car.getCarId(), car);
        mapIdToOwner.put(car.getOwnerId(), owner);
        if (mapOwnerToCars.get(owner) == null) {
            mapOwnerToCars.put(owner, new TreeSet<>(Arrays.asList(car)));
        } else {
            mapOwnerToCars.get(owner).add(car);
        }
        String brand = car.getBrand();
        if (mapBrandToCars.get(brand) == null) {
            mapBrandToCars.put(brand, new TreeSet<>(Arrays.asList(car)));
        } else {
            mapBrandToCars.get(brand).add(car);
        }

        setCarsByPower.add(car);
        setCarsByVelocity.add(car);

    }

    public Collection<Car> getAllCars() {
        return mapIdToCar.values();
    }
}

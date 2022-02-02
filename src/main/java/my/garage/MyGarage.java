package my.garage;

import java.util.*;

import static java.util.Comparator.comparingInt;

public class MyGarage implements Garage {
    // Все используемые структуры данных - 4 мапы и 2 сета
    private final Map<Long, Car> mapIdToCar = new HashMap<>();
    private final Map<Integer, Owner> mapIdToOwner = new HashMap<>();
    private final Map<Owner, TreeSet<Car>> mapOwnerToCars = new HashMap<>();
    private final Map<String, TreeSet<Car>> mapBrandToCars = new HashMap<>();
    private final Comparator<Car> comparatorByPower = comparingInt(Car::getPower).thenComparingLong(Car::getCarId);
    private final SortedSet<Car> setCarsByPower = new TreeSet<>(comparatorByPower);
    private final Comparator<Car> ComparatorByVelocity = comparingInt(Car::getMaxVelocity).thenComparingLong(Car::getCarId);
    private final TreeSet<Car> setCarsByVelocity = new TreeSet<>(ComparatorByVelocity);
    private final int topNCars = 3;

    @Override
    public Collection<Owner> allCarsUniqueOwners() {
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
        return setCarsByPower.tailSet(Car.getDummyCarWithPower(power));
    }

    @Override
    public Collection<Car> allCarsOfOwner(Owner owner) {
        return mapOwnerToCars.get(owner);
    }

    @Override
    public int meanOwnersAgeOfCarBrand(String brand) {
        Collection<Car> cars = allCarsOfBrand(brand);
        List<Integer> ownerIds = new ArrayList<>();
        for (Car car : cars) {
            ownerIds.add(car.getOwnerId());
        }
        int age = 0;
        for (Integer ownerId : ownerIds) {
            age += mapIdToOwner.get(ownerId).getAge();
        }
        return (int) Math.round(age / (double) ownerIds.size());
    }

    @Override
    public int meanCarNumberForEachOwner() {
        return (int) Math.round(mapIdToCar.size() / (double) mapIdToOwner.size());
    }

    public boolean carExists(Long carId) { // Проверить существует ли машина с таким Id
        return mapIdToCar.containsKey(carId);
    }

    private boolean removeCarFromOwners(Car car) {
        Owner owner = mapIdToOwner.get(car.getOwnerId());
        boolean is_removed = mapOwnerToCars.get(owner).remove(car);
        if (mapOwnerToCars.get(owner).isEmpty()) {// Если удалена последняя машина owner'а, удалить owner'а
            mapOwnerToCars.remove(owner);
            mapIdToOwner.remove(car.getOwnerId());
        }
        return is_removed;
    }

    private boolean removeCarFromBrands(Car car) {
        String brand = car.getBrand();
        boolean is_removed = mapBrandToCars.get(brand).remove(car);
        if (mapBrandToCars.get(brand).isEmpty()) { // Удалить "пустой" бренд
            mapBrandToCars.remove(brand);
        }
        return is_removed;
    }

    @Override
    public Car removeCar(int _carId) {
        Long carId = new Long(_carId);
        if (!carExists(carId)) { // Если машина с таким carId не существует
            throw new RuntimeException("Unable to remove car, no car with ID = " + carId);
        }
        Car car = mapIdToCar.get(carId);
        removeCarFromOwners(car);
        removeCarFromBrands(car);
        setCarsByPower.remove(car);
        setCarsByVelocity.remove(car);
        mapIdToCar.remove(carId);
        return car;
    }

    @Override
    public void addCar(Car car, Owner owner) {
        final long carId = car.getCarId();
        if (carExists(carId)) {
            throw new RuntimeException("Unable to add car, car with Id = " + carId + " already exists.");
        }
        mapIdToCar.put(carId, car);
        mapIdToOwner.put(car.getOwnerId(), owner);
        mapOwnerToCars.computeIfAbsent(owner, k -> new TreeSet<Car>()).add(car);
        mapBrandToCars.computeIfAbsent(car.getBrand(), k -> new TreeSet<Car>()).add(car);
        setCarsByPower.add(car);
        setCarsByVelocity.add(car);

    }

    public Collection<Car> getAllCars() {
        return mapIdToCar.values();
    }
}

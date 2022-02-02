package my.garage;

import java.util.Objects;

public class Car  implements Comparable<Car> {
    private final long carId;
    private final String brand;
    private final String modelName;
    private final int maxVelocity;
    private final int power;
    private final int ownerId;

    public Car(long carId, String brand, String modelName, int maxVelocity, int power, int ownerId) {
        this.carId = carId;
        this.brand = brand;
        this.modelName = modelName;
        this.maxVelocity = maxVelocity;
        this.power = power;
        this.ownerId = ownerId;
    }

    public static Car getDummyCarWithPower(int power) {
        return new Car(Long.MAX_VALUE, "", "", 0, power, 0);
    }

    @Override
    public int compareTo(Car car) { // по дефолту сравниваем машины по carId, другие варианты сравнения - через отдельный компаратор
        return Long.compare(this.carId, car.carId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId == car.carId && brand.equals(car.brand) && modelName.equals(car.modelName) &&
                maxVelocity == car.maxVelocity && power == car.power && ownerId == car.ownerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, brand, modelName, maxVelocity, power, ownerId);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getBrand() {
        return brand;
    }

    public long getCarId() {
        return carId;
    }

    public String getModelName() {
        return modelName;
    }

    public int getMaxVelocity() {
        return maxVelocity;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", brand='" + brand + '\'' +
                ", modelName='" + modelName + '\'' +
                ", maxVelocity=" + maxVelocity +
                ", power=" + power +
                ", ownerId=" + ownerId +
                '}';
    }

}

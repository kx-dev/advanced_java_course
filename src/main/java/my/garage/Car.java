package my.garage;

public class Car  implements Comparable<Car> {
    private final long carId;
    private final String brand;
    private final String modelName;
    private final int maxVelocity;
    private final int power;
    private final int ownerId; // TODO: исправить на long?

    public Car(long carId, String brand, String modelName, int maxVelocity, int power, int ownerId) {
        this.carId = carId;
        this.brand = brand;
        this.modelName = modelName;
        this.maxVelocity = maxVelocity;
        this.power = power;
        this.ownerId = ownerId;
    }

    @Override
    public int compareTo(Car car) { // по дефолту сравниваем машины по carId, другие варианты сравнения - через отдельный компаратор
        return (this.carId < car.carId) ? -1 : ((this.carId == car.carId) ? 0 : 1) ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.carId == ((Car) o).carId;
    }

    @Override
    public int hashCode() {
        return (int)carId;
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

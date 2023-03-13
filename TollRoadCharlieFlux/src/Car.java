import java.sql.SQLOutput;

public class Car extends Vehicle{


    protected int numberOfSeats;

    Car(String vehicleRegistration, String vehicleMake, int numberOfSeats)
    {
        super(vehicleRegistration, vehicleMake);
        this.numberOfSeats = numberOfSeats;
    }

    int getNumberOfSeats()
    {
        return this.numberOfSeats;
    }

    public int calculateBasicTripCost()
    {
        if (this.numberOfSeats <= 5)
        {
            return 500;
        }else
        {
            return  600;
        }
    }

    public String toString()
    {
        return (this.vehicleRegistration + " " + this.vehicleMake + " " +
                this.numberOfSeats + " the basic trip cost is: "
                + calculateBasicTripCost());
    }

    public static void main(String[] args)
    {
        Vehicle smallCar = new Car("CY59KXV", "Toyota", 4);
        Vehicle bigCar = new Car("AY66TPU", "Land Rover", 7);

        System.out.println("Small car: " + smallCar.toString());
        System.out.println("Big car: " + bigCar.toString());

    }

}

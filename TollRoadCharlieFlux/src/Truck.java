public class Truck extends Vehicle{

    protected int numberOfTrailers;

    Truck(String vehicleRegistration, String vehicleMake, int numberOfTrailers)
    {
        super(vehicleRegistration, vehicleMake);
        this.numberOfTrailers = numberOfTrailers;
    }

    int getNumberOfTrailers()
    {
        return this.numberOfTrailers;
    }

    public int calculateBasicTripCost()
    {
        if (this.numberOfTrailers <= 1)
        {
            return 1250;
        }else
        {
            return 1500;
        }
    }

    public String toString()
    {
        return (this.vehicleRegistration + " " + this.vehicleMake + " " +
                this.numberOfTrailers + " the basic trip cost is: "
                + calculateBasicTripCost());
    }

    public static void main(String[] args)
    {
        Vehicle smallTruck = new Truck("CY59KXV", "Mercedes", 1);
        Vehicle bigTruck = new Truck("AY66TPU", "Scania", 2);

        System.out.println("Small truck: " + smallTruck.toString());
        System.out.println("Big truck: " + bigTruck.toString());
    }


}

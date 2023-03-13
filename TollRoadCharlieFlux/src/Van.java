public class Van extends Vehicle{

    protected int payload;

    Van(String vehicleRegistration, String vehicleMake, int payload)
    {
        super(vehicleRegistration, vehicleMake);
        this.payload = payload;
    }

    int getPayload()
    {
        return this.payload;
    }

    public int calculateBasicTripCost()
    {
        if (this.payload <= 600)
        {
            return 500;
        } else if (this.payload <= 800)
        {
            return 750;
        }else
        {
            return 1000;
        }

    }

    public String toString()
    {
        return (this.vehicleRegistration + " " + this.vehicleMake + " " +
                this.payload + " the basic trip cost is: "
                + calculateBasicTripCost());
    }

    public static void main(String[] args)
    {
        Vehicle smallVan = new Van("CY59KXV", "Ford", 200);
        Vehicle mediumVan = new Van("AY66TPU", "Renault", 700);
        Vehicle largeVan = new Van("AG70CPU", "Mercedes", 950);

        System.out.println("Small van: " + smallVan.toString());
        System.out.println("Medium van: " + mediumVan.toString());
        System.out.println("Large van: " + largeVan.toString());

    }

}

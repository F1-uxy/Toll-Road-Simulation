public abstract class Vehicle {

    protected String vehicleRegistration;
    protected String vehicleMake;
    Vehicle(String vehicleRegistration, String vehicleMake)
    {
        this.vehicleRegistration = vehicleRegistration;
        this.vehicleMake = vehicleMake;
    }

    String getVehicleRegistration()
    {
        return this.vehicleRegistration;
    }

    String getVehicleMake()
    {
        return this.vehicleMake;
    }


    public abstract int calculateBasicTripCost();



}

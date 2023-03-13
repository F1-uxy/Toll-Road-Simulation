public class CustomerAccount implements Comparable<CustomerAccount>{

    protected String firstName;
    protected String lastName;
    protected int balance;
    protected Vehicle vehicle;
    protected int discount;

    CustomerAccount(String firstName, String lastName,
                    int balance, Vehicle vehicle)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.vehicle = vehicle;
        this.discount = 0;
    }

    int getDiscount()
    {
        return discount;
    }

    int getBalance()
    {
        return balance;
    }

    void activateStaffDiscount()
    {
        this.discount = 50;
    }

    void activateFriendsAndFamilyDiscount()
    {
        this.discount = 10;
    }

    void deactivateDiscount()
    {
        this.discount = 0;
    }

    void addFunds(int additionalFunds)
    {
        this.balance += additionalFunds;
    }

    int makeTrip() throws InsufficientAccountBalanceException {
        int initialTripCost = this.vehicle.calculateBasicTripCost();
        float discount = this.getDiscount();
        float finalTripCost;

        // If discount is 0 then /100 will throw exception,
        // Therefore, check for discount before forcing calculation
        if (this.discount != 0)
        {
            float discountMultiplier = 1 - (discount/100);
            finalTripCost = initialTripCost * discountMultiplier;
        }else
        {
            finalTripCost = initialTripCost;
        }


            if(this.balance - finalTripCost < 0)
            {
                throw new InsufficientAccountBalanceException();
            }
            else
            {
                this.balance = this.balance - (int)finalTripCost;
            }


        return (int)finalTripCost;
    }

    public String toString()
    {
        String space = new String(" ");
        return (this.firstName + space + this.lastName + space + this.balance
                + space + this.vehicle + space + this.discount);
    }

    public int compareTo(CustomerAccount comparedAccount)
    {
        String x = this.vehicle.getVehicleRegistration();
        String y = comparedAccount.vehicle.getVehicleRegistration();

        return x.compareTo(y);
    }


    public static void main(String[] args)
    {
        Vehicle testCustomerCar1 = new Car("AY66TPU", "Land Rover", 7);
        Vehicle testCustomerCar2 = new Car("AY66ZPY", "Land Rover", 7);
        CustomerAccount testCustomer1 = new CustomerAccount("John", "Green",
                                                200, testCustomerCar1);
        CustomerAccount testCustomer2 = new CustomerAccount("John", "Green",
                                                1000, testCustomerCar2);

        //Customer1 doesn't have enough balance
        //System.out.println(testCustomer1.makeTrip());
        //System.out.println(testCustomer1.getBalance());

        //Customer2 has enough balance
        //System.out.println(testCustomer2.makeTrip());
        //System.out.println(testCustomer2.getBalance());

        //testCustomer2.activateFriendsAndFamilyDiscount();
        //System.out.println(testCustomer2.makeTrip());
        //System.out.println(testCustomer2.getBalance());

        //System.out.println(testCustomer1.compareTo(testCustomer2));
    }





}

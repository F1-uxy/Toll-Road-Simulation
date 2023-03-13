import java.util.ArrayList;
import java.util.HashMap;

public class TollRoad {

    protected HashMap<String, CustomerAccount> customerAccounts;
    // I have used a HashMap here as when searching for an account it always
    // uses the regNum as the search term therefore, placing the regNum with
    // the respecting object then it saves a lot of time searching through
    // all the customer account objects and instead searching for the regNum key
    protected int moneyMade;
    
    TollRoad()
    {
        this.customerAccounts = new HashMap<>();
        this.moneyMade = 0;
    }

    int getMoneyMade()
    {
        return this.moneyMade;
    }


    void addCustomer(CustomerAccount newCustomer)
    {
        String regNum = newCustomer.vehicle.vehicleRegistration;
        this.customerAccounts.put(regNum,newCustomer);
    }

    CustomerAccount findCustomer(String regNum){

        try {
            CustomerAccount outputAccount = this.customerAccounts.get(regNum);

            if (outputAccount != null)
            {
                return outputAccount;
            }

            throw new CustomerNotFoundException();

        }catch(CustomerNotFoundException e)
        {
            System.out.println(e);

        }

        return null;
    }

    void chargeCustomer(String registrationNumber)
            throws InsufficientAccountBalanceException
    {
        CustomerAccount selectedCustomer = findCustomer(registrationNumber);

            int tripCost = selectedCustomer.makeTrip();
            this.moneyMade = this.moneyMade + tripCost;


    }

    public String toString()
    {
        String finalString = new String();

        for (CustomerAccount customerAccount : this.customerAccounts.values())
        {
            System.out.println(customerAccount.toString());
            finalString.concat(customerAccount.toString());
        }
        return finalString;
    }

    public static void main(String[] args)
    {
        TollRoad tollRoad = new TollRoad();
        Vehicle testCustomerCar1 = new Car("AY66TPU", "Land Rover", 7);
        CustomerAccount testCustomer1 = new CustomerAccount("John", "Green", 1000, testCustomerCar1);

        Vehicle testCustomerCar2 = new Car("AY67TPU", "Land Rover", 7);
        CustomerAccount testCustomer2 = new CustomerAccount("Steve", "Green", 1000, testCustomerCar2);


        tollRoad.addCustomer(testCustomer1);
        tollRoad.addCustomer(testCustomer2);
        System.out.println(tollRoad.findCustomer("AY66TPU"));
        //tollRoad.chargeCustomer("AY66TPU");
        System.out.println(tollRoad.getMoneyMade());
        System.out.println(tollRoad);
    }

}

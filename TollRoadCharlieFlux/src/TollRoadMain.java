import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class TollRoadMain {

    public static void main(String[] args) throws FileNotFoundException {
        TollRoad mainTollRoad = initialiseTollRoadFromFile();
        System.out.println(simulateFromFile(mainTollRoad));
    }

    static TollRoad initialiseTollRoadFromFile(){
        TollRoad tollRoad = new TollRoad();

        try {
            File customerData = new File("customerData.txt");
            Scanner fileLineScanner = new Scanner(customerData);
            ArrayList<String> inputData = new ArrayList<>();

            fileLineScanner.useDelimiter("#");
            String customerInformationBuffer;

            while (fileLineScanner.hasNext()) {
                customerInformationBuffer = fileLineScanner.next();
                //System.out.println(customerInformationBuffer);

                Scanner customerDataScanner =
                                        new Scanner(customerInformationBuffer);
                customerDataScanner.useDelimiter(",");

                while (customerDataScanner.hasNext()) {
                    String customerInformation = customerDataScanner.next();
                    inputData.add(customerInformation);
                }

                String vehicleType = inputData.get(0);
                Vehicle tempVehicle = null;

                if (vehicleType.compareTo("Car") == 0)
                {
                    tempVehicle = new Car(inputData.get(1), inputData.get(4),
                            Integer.parseInt(inputData.get(5)));

                } else if (vehicleType.compareTo("Van") == 0)
                {
                    tempVehicle = new Van(inputData.get(1), inputData.get(4),
                            Integer.parseInt(inputData.get(5)));
                }else
                {
                    tempVehicle = new Truck(inputData.get(1), inputData.get(4),
                            Integer.parseInt(inputData.get(5)));
                }

                CustomerAccount tempCustomerAccount =
                        new CustomerAccount(inputData.get(2), inputData.get(3),
                                Integer.parseInt(inputData.get(6)),tempVehicle);

                checkDiscount(inputData.get(7), tempCustomerAccount);

                tollRoad.addCustomer(tempCustomerAccount);
                inputData.clear();
            }

        }catch(FileNotFoundException e)
        {
            System.out.println(e);
        }

        return tollRoad;
    }

    public static void checkDiscount(String discountValue,
                                     CustomerAccount customerAccount)
    {
        if(discountValue.compareTo("FRIENDS_AND_FAMILY") == 0)
        {
            customerAccount.activateFriendsAndFamilyDiscount(); 
        } else if (discountValue.compareTo("STAFF") == 0)
        {
            customerAccount.activateStaffDiscount();
        }
    }

    static int simulateFromFile(TollRoad road) throws FileNotFoundException {

        File transactions = new File("transactions.txt");
        Scanner transactionLineScanner = new Scanner(transactions);
        transactionLineScanner.useDelimiter("\\$");

        String transactionInformation;
        ArrayList<String> transactionTemp = new ArrayList<>();

        while(transactionLineScanner.hasNext())
        {
            transactionInformation = transactionLineScanner.next();
            //System.out.println(transactionInformationBuffer);

            Scanner transactionScanner = new Scanner(transactionInformation);
            transactionScanner.useDelimiter(",");
            while(transactionScanner.hasNext())
            {
                transactionTemp.add(transactionScanner.next());

            }

            //System.out.println(transactionTemp.toString());


            String transactionType = transactionTemp.get(0);
            String regNum = transactionTemp.get(1);
            CustomerAccount currentCustomerAccount;
            if(transactionType.compareTo("addFunds") == 0)
            {
                try {
                    int amount = Integer.parseInt(transactionTemp.get(2));
                    currentCustomerAccount = road.findCustomer(regNum);
                    currentCustomerAccount.addFunds(amount);
                    System.out.println(regNum + ": " + amount
                            + " added successfully");
                }catch (NullPointerException e)
                {
                    System.out.println(regNum + ": addFunds failed. " +
                                        "CustomerAccount does not exist");
                }


            }
            if(transactionType.compareTo("makeTrip") == 0)
            {
                try {
                    road.chargeCustomer(regNum);

                    System.out.println(regNum + " Trip completed successfully");
                }catch (InsufficientAccountBalanceException e) {
                    System.out.println(regNum + " makeTrip failed. " +
                                                "Insufficient funds");
                }catch(NullPointerException e) {
                    System.out.println(regNum + ": makeTrip failed. " +
                            "CustomerAccount does not exist");
                }
            }

            transactionTemp.clear();
        }
        return road.getMoneyMade();
    }
}

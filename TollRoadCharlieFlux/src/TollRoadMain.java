import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class TollRoadMain {

    public static void main(String[] args) throws FileNotFoundException {
        TollRoad mainTollRoad = initialiseTollRoadFromFile();
        System.out.println(simulateFromFile(mainTollRoad));
    }

    public enum DiscountType {FRIENDS_AND_FAMILY, STAFF, NONE}
    public enum VehicleType {Car, Van, Truck}

    static TollRoad initialiseTollRoadFromFile(){
        TollRoad tollRoad = new TollRoad();

        try(Scanner fileLineScanner = new Scanner(new File("customerData.txt"))) {
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

                switch (VehicleType.valueOf(vehicleType))
                {
                    case Car:
                        tempVehicle = new Car(inputData.get(1), inputData.get(4),
                                Integer.parseInt(inputData.get(5)));
                        break;
                    case Van:
                        tempVehicle = new Van(inputData.get(1), inputData.get(4),
                                Integer.parseInt(inputData.get(5)));
                        break;
                    case Truck:
                        tempVehicle = new Truck(inputData.get(1), inputData.get(4),
                                Integer.parseInt(inputData.get(5)));
                        break;
                    default:
                        break;

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

        switch (DiscountType.valueOf(discountValue))
        {
            case STAFF:
                customerAccount.activateStaffDiscount();
                break;
            case FRIENDS_AND_FAMILY:
                customerAccount.activateFriendsAndFamilyDiscount();
                break;
            case NONE:
                break;
        }
    }
    // Changing String to enum for maintainability and readability
    public static DiscountType getDiscountType(String discountValue)
    {
        switch (discountValue.toUpperCase())
        {
            case "FRIENDS_AND_FAMILY":
                return DiscountType.FRIENDS_AND_FAMILY;
            case "STAFF":
                return DiscountType.STAFF;
        }
        return null;
    }


    static int simulateFromFile(TollRoad road) throws FileNotFoundException {

        File transactions = new File("transactions.txt");
        Scanner transactionLineScanner = new Scanner(transactions);
        transactionLineScanner.useDelimiter("\\$");


        ArrayList<String> transactionTemp = new ArrayList<>();

        while(transactionLineScanner.hasNext())
        {
            String transactionInformation = transactionLineScanner.next();

            Scanner transactionScanner = new Scanner(transactionInformation);
            transactionScanner.useDelimiter(",");
            String transactionType = transactionScanner.next();



            switch (TransactionType.valueOf(transactionType))
            {
                case addFunds:
                    String regNum = transactionScanner.next();
                    int amount = Integer.parseInt(transactionScanner.next());
                    try
                    {
                        CustomerAccount currentCustomerAccount
                                = road.findCustomer(regNum);
                        currentCustomerAccount.addFunds(amount);
                        System.out.println(regNum + ": " + amount
                                + " added successfully");
                    }catch (NullPointerException e)
                    {
                        System.out.println(regNum + ": addFunds failed. " +
                                "CustomerAccount does not exist");
                    }
                    break;

                case makeTrip:
                    regNum = transactionScanner.next();
                    try
                    {
                        road.chargeCustomer(regNum);
                        System.out.println(regNum
                                + " Trip completed successfully");
                    } catch (InsufficientAccountBalanceException e) {
                        System.out.println(regNum +
                                " makeTrip failed. Insufficient funds");
                    } catch (NullPointerException e) {
                        System.out.println(regNum + ": makeTrip failed. " +
                                "CustomerAccount does not exist");
                    }
                    break;
            }

            transactionTemp.clear();
        }
        return road.getMoneyMade();
    }

    enum TransactionType {addFunds, makeTrip}
}

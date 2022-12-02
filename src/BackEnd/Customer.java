package BackEnd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Customer {
    public static ArrayList<Customer> allCustomer = new ArrayList<>();

    private int customerID;
    private String name;
    private String phoneNumber;
    private ArrayList<Contract> rentContract = new ArrayList<>();

    public Customer(String name, String phoneNumber){
        this.customerID = allCustomer.size();
        this.name = name;
        this.phoneNumber = phoneNumber;
        allCustomer.add(this);
    }



    public int getCustomerID() {
        return customerID;
    }
    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getContract(boolean isRenting){
        StringBuilder s = new StringBuilder();
        ArrayList<Contract> a = new ArrayList<>();
        for (Contract contract: rentContract)
            if (contract.isRenting() == isRenting) a.add(contract);
        for(Contract contract: a){
            String string = Rental.allRental.get(contract.getSsid()).getName();
            if(string.length() > 15){
                if (string.charAt(15) == ' ') s.append(string, 0, 14).append("[...]");
                else s.append(string, 0, Math.max(string.indexOf(' ',15),15)).append("[...]");
            }
            else s.append(string);
            if(a.indexOf(contract) < a.size()-1) s.append(", ");
        }
        return s.toString();
    }
    public boolean hasContractWithRentalByName(String rentalName, boolean isRenting){
        for (Contract contract: rentContract)
            if (Rental.allRental.get(contract.getSsid()).getName().contains(rentalName)
                    && contract.isRenting() == isRenting)
                return true;
        return false;
    }


    public ArrayList<Contract> getRentContract() {
        return rentContract;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setCustomerID(int customerID){
        this.customerID = customerID;
        //change customerID of all contract of this customer
        for(Contract contract: rentContract)
            contract.setCustomerID(customerID);
        //change customerID of this customer
    }

    public static void swapCustomer(Customer x, Customer y){
        int xCustomerID = x.getCustomerID();
        int yCustomerID = y.getCustomerID();

        x.setCustomerID(yCustomerID);
        y.setCustomerID(xCustomerID);

        Collections.swap(allCustomer, xCustomerID, yCustomerID);
    }

    public void deleteCustomer() throws IOException {
        //delete all contract related to this customer
        for(Contract contract: rentContract)
            contract.deleteContract();

        //swap this customer with customer at the end
        swapCustomer(this, allCustomer.get(allCustomer.size()-1));

        //remove this customer from allCustomer
        allCustomer.remove(this);
    }

}

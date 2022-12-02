package BackEnd;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Contract {
    public static ArrayList<Contract> allContract = new ArrayList<>();

    //customer field
    private int customerID;

    //product field
    private int ssid;

    //contract field
    private int contractID;
    private LocalDate timeStart;
    private LocalDate timeCompleted;
    private boolean isRenting;
    private int deposit;
    private int money = 0; // total rent money
    private int rent; // rent per day


    //constructor for running contract
    public Contract(int ssid, int customerID, int deposit, LocalDate timeStart){
        this.ssid = ssid;
        this.customerID = customerID;
        this.deposit = deposit;
        this.timeStart = timeStart;
        this.timeCompleted = LocalDate.MAX;
        this.isRenting = true;
        this.contractID = allContract.size();
        setRent();
        allContract.add(this);

        Rental.allRental.get(ssid).setAvailableNow(false);
        Rental.allRental.get(ssid).getRentContract().add(this);

        Customer.allCustomer.get(customerID).getRentContract().add(this);
    }

    //constructor for completed contract
    public Contract(int ssid, int customerID, int deposit, LocalDate timeStart, LocalDate timeCompleted){
        this.ssid = ssid;
        this.customerID = customerID;
        this.deposit = deposit;
        this.timeStart = timeStart;
        this.timeCompleted = timeCompleted;
        this.contractID = allContract.size();
        this.isRenting = false;
        setRent();
        setMoney();
        allContract.add(this);

        Rental.allRental.get(ssid).getRentContract().add(this);
        Customer.allCustomer.get(customerID).getRentContract().add(this);
    }

    //money methods
    public void setRent(){
        this.rent = Rental.allRental.get(this.ssid).getRent();
    }
    public void setMoney(){
        int numberOfRentDays = Utilities.getDateDiff(timeStart, timeCompleted) + 1;
        this.money = numberOfRentDays * rent;
    }
    public int getMoney(){
        return this.money;
    }
    public int getHaveToPay(){
        setMoney();
        return this.deposit - this.money;
    }


    //change SSID/customerID
    public void setSsid(int ssid){
        Rental.allRental.get(this.ssid).getRentContract().remove(this);
        Rental.allRental.get(ssid).getRentContract().add(this);
        setRent();
        this.ssid = ssid;
    }
    public void setCustomerID(int customerID){
        Customer.allCustomer.get(this.customerID).getRentContract().remove(this);
        Customer.allCustomer.get(customerID).getRentContract().add(this);
        this.customerID = customerID;
    }

    //change time (of a completed contract)
    public void setTime(LocalDate timeStart, LocalDate timeCompleted){
        if(Rental.allRental.get(ssid).isAvailableAtTime(timeStart, timeCompleted)){
            this.timeStart = timeStart;
            this.timeCompleted = timeCompleted;
            setMoney();
        }
    }

    //change timeStart (of a renting contract)
    public void setTime(LocalDate timeStart){
        Rental.allRental.get(ssid).isAvailableAtTime(timeStart, LocalDate.MAX);
        this.timeStart = timeStart;
    }

    //change a renting contract to a completed contract
    public void setRenting(LocalDate timeCompleted){
        this.isRenting = false;
        this.timeCompleted = timeCompleted;
        setMoney();
    }

    //change deposit
    public void setDeposit(int deposit){
        this.deposit = deposit;
    }


    //delete a contract
    public void deleteContract() {
        Customer.allCustomer.get(customerID).getRentContract().remove(this);
        Rental.allRental.get(ssid).getRentContract().remove(this);
        Collections.swap(allContract, this.contractID, allContract.size()-1);
        allContract.remove(this);
    }

    //getters
    public int getSsid() {
        return ssid;
    }

    public LocalDate getTimeStart() {
        return timeStart;
    }
    public String getStringTimeStart(){ return timeStart.toString();};
    public LocalDate getTimeCompleted() {
        return timeCompleted;
    }
    public String getStringTimeCompleted(){ return timeCompleted.toString();};
    public int getDeposit() {
        return deposit;
    }
    public boolean isRenting() {
        return isRenting;
    }
    public int getCustomerID() {
        return customerID;
    }
    public int getRent() {
        return rent;
    }


    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }
}

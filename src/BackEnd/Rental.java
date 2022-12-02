package BackEnd;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Collections;


abstract public class Rental {
    public static ArrayList<Rental> allRental = new ArrayList<>();

    private int ssid;
    private String name;
    private String author;
    private String year;
    private String genre;
    private int rent; //rent per day
    private boolean isAvailableNow;

    //storing all contract from this rental
    private final ArrayList<Contract> rentContract = new ArrayList<>();

    //constructor
    Rental(String name, String author, String year, String genre, int rent, boolean isAvailableNow) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.rent = rent;
        this.isAvailableNow = isAvailableNow;
        this.ssid = allRental.size();
        allRental.add(this);
    }



    //getter
    public int getSsid() {
        return ssid;
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String getYear() {
        return year;
    }
    public String getGenre() {
        return genre;
    }
    abstract public String getLength();
    abstract public String getSize();
    abstract public String getResolution();
    public int getRent() {
        return rent;
    }
    public boolean isAvailableNow() {
        return isAvailableNow;
    }
    public ArrayList<Contract> getRentContract() {
        return rentContract;
    }


    //setter
    public void setName(String name) {
        this.name = name;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    abstract public void setLength(String length);
    abstract public void setSize(String size);
    abstract public void setResolution(String resolution);
    public void setRent(int rent) {
        //change rent (and money) for all contracts of this product)
        for (Contract contract : rentContract) {
            contract.setRent();
            if (!contract.isRenting())
                contract.setMoney();
        }
        this.rent = rent;
    }
    public void setAvailableNow(boolean availableNow) {
        isAvailableNow = availableNow;
    }
    public void setSsid(int ssid) {
        this.ssid = ssid;
        //change ssid for all contract of this rental
        for (Contract contract : rentContract) {
            contract.setSsid(ssid);
        }
    }

    //check if product can be rent from timeStart to timeEnd
    public boolean isAvailableAtTime(LocalDate timeStart, LocalDate timeEnd) {
        if (timeStart.isAfter(timeEnd)) {
            LocalDate temp = timeEnd;
            timeEnd = timeStart;
            timeStart = temp;
        }

        //if contract is renting
        // true <-> contract.timeStart > timeEnd >= timeStart

        //if contract is completed
        // true <-> timeEnd >= timeStart > contract.timeCompleted || contract.timeStart > timeEnd >= timeStart
        for (Contract contract : rentContract) {
            if (contract.isRenting()) {
                if (!timeEnd.isBefore(contract.getTimeStart()))
                    return false;
            } else {
                if (!timeStart.isAfter(contract.getTimeCompleted())
                        && !timeEnd.isBefore(contract.getTimeStart()))
                    return false;
            }
        }

        return true;
    }

    //check if rental has any renting contract, effectively rendering it as not available now
    public Contract getRentingContract(){
        for (Contract contract: rentContract)
            if (contract.isRenting()) return contract;
        return null;
    }

    //swap 2 rental in allRental
    public static void swapRental(Rental x, Rental y) {
        int xSsid = x.getSsid();
        int ySsid = y.getSsid();

        x.setSsid(ySsid);
        y.setSsid(xSsid);

        //swap 2 references
        Collections.swap(allRental, xSsid, ySsid);
    }

    //Delete a rental
    public void deleteRental() {
        //delete all contract related to this rental
        for (Contract contract : rentContract) {
            contract.deleteContract();
        }
        //swap product ssid with last product to avoid changing ssid of other products
        swapRental(this, allRental.get(allRental.size() - 1));
        //remove this rental from allRental
        allRental.remove(this);
    }

}
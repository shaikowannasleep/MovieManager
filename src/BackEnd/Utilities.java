package BackEnd;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Utilities {
    private Utilities(){}


    public static int getDateDiff(LocalDate a, LocalDate b){
        return (int) ChronoUnit.DAYS.between(a,b);
    }

    /*
        METHODS FOR FILTERING AN ARRAYLIST<RENTAL> WITH SPECIFIED FIELD
     */

    //search for rental that has name contains required string
    public static void searchNameRental(ArrayList<Rental> originalArray, String name){
        int i = originalArray.size()-1;
        while(i>-1) {
            if (!originalArray.get(i).getName().contains(name)) {
                originalArray.remove(i);
            }
            i --;
        }
    }

    //search for rental that has author contains required string
    public static void searchAuthor(ArrayList<Rental> originalArray, String author){
        int i = originalArray.size()-1;
        while(i>-1) {
            if (!originalArray.get(i).getAuthor().contains(author)) {
                originalArray.remove(i);
            }
            i --;
        }
    }

    //search for rental that has year contains required string
    public static void searchYear(ArrayList<Rental> originalArray, String year){
        int i = originalArray.size()-1;
        while(i>-1) {
            if (!originalArray.get(i).getYear().contains(year)) {
                originalArray.remove(i);
            }
            i --;
        }
    }

    //search for rental that is book/movie
    public static void searchType(ArrayList<Rental> originalArray, String type){
        int i = originalArray.size() - 1;
        if(type.equals("Book")){
            while(i>-1) {
                if(originalArray.get(i).getClass() == Movie.class)
                    originalArray.remove(i);
                i--;
            }
        }
        else{
            while(i>-1){
                if(originalArray.get(i).getClass() == Book.class)
                    originalArray.remove(i);
                i--;
            }
        }
    }

    //search for rental that is available from timeStart to timeCompleted
    public static void searchTime(ArrayList<Rental> originalArray, LocalDate timeStart, LocalDate timeCompleted){
        int i = originalArray.size()-1;
        while(i>-1) {
            if (!originalArray.get(i).isAvailableAtTime(timeStart, timeCompleted)) {
                originalArray.remove(i);
            }
            i --;
        }
    }

    //search for rental that with required genre
    public static void searchGenre(ArrayList<Rental> originalArray, String genre){
        int i = originalArray.size()-1;
        while(i>-1) {
            if (!originalArray.get(i).getGenre().contains(genre)) {
                originalArray.remove(i);
            }
            i --;
        }
    }

    //search for rental with rentMin <= rent <= rentMax
    public static void searchRent(ArrayList<Rental> originalArray, int rentMin, int rentMax){
        int i = originalArray.size()-1;
        while(i>-1) {
            if (originalArray.get(i).getRent() < rentMin
                || originalArray.get(i).getRent() > rentMax) {
                originalArray.remove(i);
            }
            i --;
        }
    }

    //search for rental with required length/pageCount
    public static void searchLength(ArrayList<Rental> originalArray, String length){
        int i = originalArray.size() - 1;
        while (i>-1){
            if (!originalArray.get(i).getLength().contains(length))
                originalArray.remove(i);
            i--;
        }
    }

    //search for rental with required size/pageType
    public static void searchSize(ArrayList<Rental> originalArray, String size){
        int i = originalArray.size() - 1;
        while (i>-1){
            if (!originalArray.get(i).getSize().contains(size))
                originalArray.remove(i);
            i--;
        }
    }

    //search for rental with required resolution/language
    public static void searchResolution(ArrayList<Rental> originalArray, String resolution){
        int i = originalArray.size() - 1;
        while (i>-1){
            if (!originalArray.get(i).getResolution().contains(resolution))
                originalArray.remove(i);
            i--;
        }
    }

    /*
        FILTERING AN ARRAYLIST<CUSTOMER> WITH SPECIFIED FIELD
     */

    //search for customer with required name
    public static void searchNameCustomer(ArrayList<Customer> originalArray, String name){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(!originalArray.get(i).getName().contains(name))
                originalArray.remove(i);
            i--;
        }
    }

    //search for customer with required phone number
    public static void searchPhone(ArrayList<Customer> originalArray, String phone){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(!originalArray.get(i).getPhoneNumber().contains(phone))
                originalArray.remove(i);
            i--;
        }
    }

    //search for customer with contract is renting/completed with required name
    public static void searchRentingCustomer(ArrayList<Customer> originalArray, String renting, boolean isRenting){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(!originalArray.get(i).hasContractWithRentalByName(renting, isRenting))
                originalArray.remove(i);
            i--;
        }
    }


    /*
        FILTERING AN ARRAYLIST<CONTRACT> WITH SPECIFIED FIELD
     */

    //search for contract with required rental name
    public static void searchContractNameRental(ArrayList<Contract> originalArray, String name){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(!Rental.allRental.get(originalArray.get(i).getSsid()).getName().contains(name))
                originalArray.remove(i);
            i--;
        }
    }

    public static void searchContractNameCustomer(ArrayList<Contract> originalArray, String name){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(!Customer.allCustomer.get(originalArray.get(i).getCustomerID()).getName().contains(name))
                originalArray.remove(i);
            i--;
        }
    }

    public static void searchIsRenting(ArrayList<Contract> originalArray, boolean isRenting){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(originalArray.get(i).isRenting() != isRenting)
                originalArray.remove(i);
            i--;
        }
    }

    public static void searchContractTimeStart(ArrayList<Contract> originalArray, LocalDate timeStart){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(originalArray.get(i).getTimeStart().isBefore(timeStart))
                originalArray.remove(i);
            i--;
        }
    }

    public static void searchContractTimeCompleted(ArrayList<Contract> originalArray, LocalDate timeCompleted){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(originalArray.get(i).getTimeCompleted().isAfter(timeCompleted))
                originalArray.remove(i);
            i--;
        }
    }

    public static void searchContractDepositMin(ArrayList<Contract> originalArray, int deposit){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(originalArray.get(i).getDeposit() < deposit)
                originalArray.remove(i);
            i--;
        }
    }

    public static void searchContractDepositMax(ArrayList<Contract> originalArray, int deposit){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(originalArray.get(i).getDeposit() > deposit)
                originalArray.remove(i);
            i--;
        }
    }

    public static void searchContractMoneyMin(ArrayList<Contract> originalArray, int money){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(originalArray.get(i).getMoney() < money)
            originalArray.remove(i);
            i--;
        }
    }

    public static void searchContractMoneyMax(ArrayList<Contract> originalArray, int money){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(originalArray.get(i).getMoney() > money)
                originalArray.remove(i);
            i--;
        }
    }

    public static void searchContractRentalType(ArrayList<Contract> originalArray, boolean isBook){
        int i = originalArray.size() - 1;
        while(i > -1){
            if((Rental.allRental.get(originalArray.get(i).getSsid()).getClass() == Book.class) != isBook)
                originalArray.remove(i);
            i--;
        }
    }

    public static void searchContractIsRenting(ArrayList<Contract> originalArray, boolean isRenting){
        int i = originalArray.size() - 1;
        while(i > -1){
            if(originalArray.get(i).isRenting() != isRenting)
                originalArray.remove(i);
            i--;
        }
    }


    //find sumMoney of completed contract specified by timeStart and timeEnd
    public static int sumMoney(ArrayList<Contract> data){
        int sumMoney = 0;
        for(Contract contract: data){
            if(!contract.isRenting()) sumMoney += contract.getMoney();
        }
        return sumMoney;
    }


}

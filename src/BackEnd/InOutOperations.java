package BackEnd;

import java.io.*;
import java.time.LocalDate;

public final class InOutOperations {
    private InOutOperations(){}

    private static final String regex = "@@";

    /*
        RESET DATA
     */


    //use after modifying ssid/customerID/contractID
    //use after deleting a rental/customer
    public static void resetAllData() throws IOException{
        rentalOut();
        customerOut();
        contractOut();
        rentalIn();
        customerIn();
        contractIn();
    }

    public static void resetRentalData() throws IOException{
        rentalOut();
        rentalIn();
    }

    public static void resetCustomerData() throws IOException{
        customerOut();
        customerIn();
    }

    public static void resetContractData() throws IOException{
        contractOut();
        contractIn();
    }


    /*
        INPUT
     */


    //Rental section
    public static void rentalIn() throws IOException{
        BufferedReader rentalReader = new BufferedReader(new FileReader("Data/Rental.txt"));

        String line;
        while((line = rentalReader.readLine()) != null){
            String[] currentLine = line.split(regex);
            int size = currentLine.length;

            if(currentLine[size -4].equals("Movie")){
                new Movie(currentLine[1],
                        currentLine[2],
                        currentLine[3],
                        currentLine[4],
                        Integer.parseInt(currentLine[size-6]),
                        Boolean.parseBoolean(currentLine[size-5]),
                        currentLine[size-3],
                        currentLine[size-2],
                        currentLine[size-1]);


            }
            else{
                new Book(currentLine[1],
                        currentLine[2],
                        currentLine[3],
                        currentLine[4],
                        Integer.parseInt(currentLine[5]),
                        Boolean.parseBoolean(currentLine[6]),
                        currentLine[size-3],
                        currentLine[size-2],
                        currentLine[size-1]);

            }
        }
        rentalReader.close();
    }

    //Customer section
    public static void customerIn() throws IOException{
        BufferedReader customerReader = new BufferedReader(new FileReader("Data/Customer.txt"));

        String line;
        while((line = customerReader.readLine()) != null){
           String[] currentLine = line.split(regex);
            new Customer(currentLine[1],
                    currentLine[2]);

        }
        customerReader.close();
    }

    //Contract section
    public static void contractIn() throws IOException{
        BufferedReader contractReader = new BufferedReader(new FileReader("Data/Contract.txt"));

        String line;
        while ((line = contractReader.readLine()) != null){
            String[] currentLine = line.split(regex);

            if(Boolean.parseBoolean(currentLine[4])){
                new Contract(Integer.parseInt(currentLine[0]),
                        Integer.parseInt(currentLine[1]),
                        Integer.parseInt(currentLine[5]),
                        LocalDate.parse(currentLine[2]));
            }
            else{
                new Contract(Integer.parseInt(currentLine[0]),
                        Integer.parseInt(currentLine[1]),
                        Integer.parseInt(currentLine[5]),
                        LocalDate.parse(currentLine[2]),
                        LocalDate.parse(currentLine[3]));
            }
        }
        contractReader.close();
    }


    /*
        OUTPUT
     */


    //Rental section
    //output rental to a file
    public static void rentalOut() throws IOException {

        File fileRental = new File("Data/Rental.txt");
        fileRental.delete();
        fileRental.createNewFile();

        FileWriter rentalWriter = new FileWriter(fileRental);

        //output line by line
        for(Rental rental: Rental.allRental)
            rentalWriter.append(rentalToString(rental));
        Rental.allRental.clear();

        rentalWriter.close();
    }

    //Rental.ToString
    private static String rentalToString(Rental x){
        StringBuilder output = new StringBuilder(x.getSsid() + regex + x.getName() + regex + x.getAuthor() + regex
                + x.getYear() + regex + x.getGenre() + regex
                + x.getRent() + regex + x.isAvailableNow() + regex);
        if(x.getClass() == Movie.class){
            output.append("Movie").append(regex)
                    .append(x.getLength()).append(regex)
                    .append(x.getSize()).append(regex)
                    .append(x.getResolution()).append("\n");
        }
        else{
            output.append("Book").append(regex)
                    .append(((Book) x).getPageCount()).append(regex)
                    .append(((Book) x).getPageType()).append(regex)
                    .append(((Book) x).getLanguage()).append("\n");
        }
        return output.toString();
    }


    //Customer section
    //output customer to a file
    public static void customerOut() throws IOException{
        File fileCustomer = new File("Data/Customer.txt");
        fileCustomer.delete();
        fileCustomer.createNewFile();

        FileWriter customerWriter = new FileWriter(fileCustomer);

        //output line by line
        for(Customer customer: Customer.allCustomer)
            customerWriter.append(customerToString(customer));
        Customer.allCustomer.clear();

        customerWriter.close();
    }

    //Customer.ToString
    public static String customerToString(Customer x){
        return x.getCustomerID() + regex + x.getName() + regex
                + x.getPhoneNumber() + "\n";
    }


    //Contract section
    //output Contract to a file
    public static void contractOut() throws IOException{
        File fileContract = new File("Data/Contract.txt");
        fileContract.delete();
        fileContract.createNewFile();

        FileWriter contractWriter = new FileWriter(fileContract);

        for(Contract contract: Contract.allContract){
            contractWriter.append(contractToString(contract));
        }
        Contract.allContract.clear();

        contractWriter.close();
    }

    //Contract.ToString
    private static String contractToString(Contract x){
        return x.getSsid() + regex + x.getCustomerID() + regex
                + x.getStringTimeStart() + regex + x.getStringTimeCompleted() + regex
                + x.isRenting() + regex + x.getDeposit() + "\n";
    }


}

package FrontEnd;


import BackEnd.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static BackEnd.InOutOperations.*;
import static javafx.beans.binding.Bindings.createObjectBinding;

public class Controller implements Initializable {

    /*
        RENTAL TABLE
     */
    @FXML TableView<Rental> tableRental;
    @FXML TableColumn<Rental, String> type, name, author, year, genre, cost, length, size, resolution;
    @FXML TextField nameSearchRental, authorSearch, yearSearch, genreSearch, costMinSearch, costMaxSearch,
            lengthSearch, sizeSearch, resolutionSearch, depositRent;
    @FXML ComboBox<String> typeSearch;
    @FXML DatePicker timeStartSearch, timeCompleteSearch;
    @FXML Text invalidEditRental, invalidAddRental, invalidSearchRental, invalidRent;

    /*
        CUSTOMER TABLE
     */
    @FXML TableView<Customer> tableCustomer;
    @FXML TableColumn<Customer, String> nameCustomer, phone, renting, completed;
    @FXML TextField nameSearchCustomer, phoneSearch, rentingSearch, completedSearch;
    @FXML Text invalidEditCustomer, invalidAddCustomer, invalidSearchCustomer;


    /*
        CONTRACT TABLE
     */
    @FXML TableView<Contract> tableContract;
    @FXML TableColumn<Contract, String> rentalName, rentalType, customerName, isRenting, timeStart, timeCompleted,
            deposit, totalRent;
    @FXML TextField rentalNameSearch, customerNameSearch, depositMinSearch, depositMaxSearch, totalMinSearch, totalMaxSearch;
    @FXML ComboBox<String> isRentingSearch, typeSearchContract;
    @FXML DatePicker timeStartContractSearch, timeCompleteContractSearch;
    @FXML Text sumMoney, invalidComplete;

    public static int customerIDChoose = -1, depositChoosing, rentalChoosingSsid;
    public static LocalDate timeStartChoosing, timeCompleteChoosing;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
            SET UP RENTAL TAB
         */
        {
        /*
            SETTING UP SEARCH FUNCTION
         */
            ObservableList<String> types = FXCollections.observableArrayList(List.of("Book", "Movie", "Both"));
            typeSearch.setItems(types);
            typeSearch.getSelectionModel().selectLast();



        /*
            SETTING UP TABLE RENTAL - GET ROW DATA
         */
            updateRentalTable(Rental.allRental);


        /*
            SETTING UP TABLE RENTAL - GET COLUMN DATA OF EACH ROW
         */


            type.setCellValueFactory(rental ->
                    createObjectBinding(() -> (rental.getValue().getClass() == Book.class) ? "Book" : "Movie"));


            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            name.setCellFactory(TextFieldTableCell.forTableColumn());


            author.setCellValueFactory(new PropertyValueFactory<>("author"));
            author.setCellFactory(TextFieldTableCell.forTableColumn());


            year.setCellValueFactory(new PropertyValueFactory<>("year"));
            year.setCellFactory(TextFieldTableCell.forTableColumn());


            genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            genre.setCellFactory(TextFieldTableCell.forTableColumn());


            cost.setCellValueFactory(rental ->
                    createObjectBinding(() -> String.valueOf(rental.getValue().getRent())));
            cost.setCellFactory(TextFieldTableCell.forTableColumn());


            length.setCellValueFactory(new PropertyValueFactory<>("length"));
            length.setCellFactory(TextFieldTableCell.forTableColumn());


            size.setCellValueFactory(new PropertyValueFactory<>("size"));
            size.setCellFactory(TextFieldTableCell.forTableColumn());

            resolution.setCellValueFactory(new PropertyValueFactory<>("resolution"));
            resolution.setCellFactory(TextFieldTableCell.forTableColumn());


        }


        /*
            SET UP CUSTOMER TAB
         */
        {
            /*
                SETTING UP CUSTOMER TABLE - GET ROW DATA
             */
            updateCustomerTable(Customer.allCustomer);

            /*
                SETTING UP CUSTOMER TABLE - GET COLUMN DATA OF EACH ROW
             */


            nameCustomer.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameCustomer.setCellFactory(TextFieldTableCell.forTableColumn());

            phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            phone.setCellFactory(TextFieldTableCell.forTableColumn());

            renting.setCellValueFactory(customer->
                    createObjectBinding(()->customer.getValue().getContract(true)));

            completed.setCellValueFactory(customer ->
                    createObjectBinding(()-> customer.getValue().getContract(false)));

        }


        /*
            SET UP CONTRACT TAB
         */
        {
            /*
                SETTING UP SEARCH FUNCTION
             */
            ObservableList<String> types = FXCollections.observableArrayList(List.of("Book", "Movie", "Both"));
            typeSearchContract.setItems(types);
            typeSearchContract.getSelectionModel().selectLast();

            ObservableList<String> states = FXCollections.observableArrayList(List.of("Renting", "Completed", "Both"));
            isRentingSearch.setItems(states);
            isRentingSearch.getSelectionModel().selectLast();


            /*
                SETTING UP CONTRACT TABLE - GET ROW DATA
             */
            updateContractTable(Contract.allContract);

            /*
                SETTING UP CONTRACT TABLE - GET COLUMN DATA OF EACH ROW
             */

            rentalName.setCellValueFactory(contract ->
                    createObjectBinding(()-> Rental.allRental.get(contract.getValue().getSsid()).getName()));

            rentalType.setCellValueFactory(contract ->
                    createObjectBinding(() -> Rental.allRental.get(
                            contract.getValue().getSsid()).getClass() == Book.class ? "Book" : "Movie"));

            customerName.setCellValueFactory(contract ->
                    createObjectBinding(()-> Customer.allCustomer.get(contract.getValue().getCustomerID()).getName()));

            isRenting.setCellValueFactory(contract ->
                    createObjectBinding(()-> contract.getValue().isRenting()? "Renting" : "Completed"));

            timeStart.setCellValueFactory(contract ->
                    createObjectBinding( () -> contract.getValue().getTimeStart().format(formatter) ));

            timeCompleted.setCellValueFactory(contract ->
                    createObjectBinding( () -> contract.getValue().isRenting()?
                            "N/A" : contract.getValue().getTimeCompleted().format(formatter)));

            deposit.setCellValueFactory(contract ->
                    createObjectBinding( () -> Integer.toString(contract.getValue().getDeposit())));

            totalRent.setCellValueFactory(contract ->
                    createObjectBinding( () -> contract.getValue().isRenting() ?
                            "N/A" : Integer.toString(contract.getValue().getMoney())));

        }
    }


    /*
        SEARCH
     */
    public void handleRentalSearch(){
        ArrayList<Rental> tableData = new ArrayList<>(Rental.allRental);
        invalidSearchRental.setVisible(false);


        if(!nameSearchRental.getText().isBlank())
            Utilities.searchNameRental(tableData, nameSearchRental.getText());


        if(!authorSearch.getText().isBlank())
            Utilities.searchAuthor(tableData, authorSearch.getText());


        if(!yearSearch.getText().isBlank())
            if(isInteger(yearSearch.getText()))
                Utilities.searchYear(tableData, yearSearch.getText());
            else invalidSearchRental.setVisible(true);


        if(!(typeSearch.getValue() == null)){
            if(typeSearch.getValue().equals("Book"))
                Utilities.searchType(tableData,"Book");
            else if(typeSearch.getValue().equals("Movie"))
                Utilities.searchType(tableData, "Movie");
        }


        if(!genreSearch.getText().isBlank())
            Utilities.searchGenre(tableData, genreSearch.getText());


        if(costMinSearch.getText().isBlank()){
            if (!costMaxSearch.getText().isBlank())
                if (!isInteger(costMaxSearch.getText())) invalidSearchRental.setVisible(true);
                else Utilities.searchRent(tableData,
                            Integer.MIN_VALUE, Integer.parseInt(costMaxSearch.getText()));
        }
        else{
            if (!isInteger(costMinSearch.getText())) invalidSearchRental.setVisible(true);
            else if(costMaxSearch.getText().isBlank())
                Utilities.searchRent(tableData,
                        Integer.parseInt(costMinSearch.getText()), Integer.MAX_VALUE);
            else{
                if (isInteger(costMaxSearch.getText())) Utilities.searchRent(tableData,
                            Integer.parseInt(costMinSearch.getText()), Integer.parseInt(costMaxSearch.getText()));
                else invalidSearchRental.setVisible(true);
            }
        }


        if(!lengthSearch.getText().isBlank())
            Utilities.searchLength(tableData, lengthSearch.getText());


        if(!sizeSearch.getText().isBlank())
            Utilities.searchSize(tableData, sizeSearch.getText());


        if(!resolutionSearch.getText().isBlank())
            Utilities.searchResolution(tableData, resolutionSearch.getText());


        if(timeStartSearch.getValue() != null){
            if(timeCompleteSearch.getValue() != null)
                Utilities.searchTime(tableData,timeStartSearch.getValue(),timeCompleteSearch.getValue());
            else
                Utilities.searchTime(tableData,timeStartSearch.getValue(), LocalDate.MAX);
        }
        else if(timeCompleteSearch.getValue() != null)
            Utilities.searchTime(tableData, LocalDate.EPOCH, timeCompleteSearch.getValue());


        updateRentalTable(tableData);
    }

    public void handleCustomerSearch(){
        ArrayList<Customer> tableData = new ArrayList<>(Customer.allCustomer);
        invalidSearchCustomer.setVisible(false);

        if(!nameSearchCustomer.getText().isBlank())
            Utilities.searchNameCustomer(tableData, nameSearchCustomer.getText());

        if(!phoneSearch.getText().isBlank())
            if(checkValidString(phoneSearch.getText(), "Phone", true))
                Utilities.searchPhone(tableData, phoneSearch.getText());
            else invalidSearchCustomer.setVisible(true);

        if(!rentingSearch.getText().isBlank())
            Utilities.searchRentingCustomer(tableData, rentingSearch.getText(), true);

        if(!completedSearch.getText().isBlank())
            Utilities.searchRentingCustomer(tableData, completedSearch.getText(), false);

        updateCustomerTable(tableData);
    }

    public void handleContractSearch(){
        ArrayList<Contract> tableData = new ArrayList<>(Contract.allContract);

        if(!rentalNameSearch.getText().isBlank())
            Utilities.searchContractNameRental(tableData, rentalNameSearch.getText());
        if(!customerNameSearch.getText().isBlank())
            Utilities.searchContractNameCustomer(tableData, customerNameSearch.getText());
        if(timeCompleteContractSearch.getValue() != null && timeStartContractSearch.getValue() != null){
            Utilities.searchContractTimeStart(tableData, timeStartContractSearch.getValue());
            Utilities.searchContractTimeCompleted(tableData, timeCompleteContractSearch.getValue());
        }
        if(!depositMinSearch.getText().isBlank() && isInteger(depositMinSearch.getText()))
            Utilities.searchContractDepositMin(tableData, Integer.parseInt(depositMinSearch.getText()));
        if(!depositMaxSearch.getText().isBlank() && isInteger(depositMaxSearch.getText()))
            Utilities.searchContractDepositMax(tableData, Integer.parseInt(depositMaxSearch.getText()));
        if(!totalMinSearch.getText().isBlank() && isInteger(totalMinSearch.getText()))
            Utilities.searchContractMoneyMin(tableData, Integer.parseInt(totalMinSearch.getText()));
        if(!totalMaxSearch.getText().isBlank() && isInteger(totalMaxSearch.getText()))
            Utilities.searchContractMoneyMax(tableData, Integer.parseInt(totalMaxSearch.getText()));

        if(typeSearchContract.getValue().equals("Book"))
            Utilities.searchContractRentalType(tableData,true);
        if(typeSearchContract.getValue().equals("Movie"))
            Utilities.searchContractRentalType(tableData, false);


        if(isRentingSearch.getValue().equals("Renting"))
            Utilities.searchContractIsRenting(tableData, true);
        if(isRentingSearch.getValue().equals("Completed"))
            Utilities.searchContractIsRenting(tableData, false);


        updateContractTable(tableData);
    }

    /*
        EDIT
     */
    public void handleRentalEdit(TableColumn.CellEditEvent<Rental,String> event) throws IOException {
        invalidEditRental.setVisible(false);
        if (event.getNewValue().contains("@@")){
            invalidEditRental.setVisible(true);
            invalidEditRental.setText("Invalid edit: " + event.getTableColumn().getText() + " shouldn't contain \"@@\"");
            tableRental.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
            return;
        }
        switch (event.getTableColumn().getText()) {
            case "Name":
                if(checkValidString(event.getNewValue(), "Name", true)){
                        Rental.allRental.get(event.getRowValue().getSsid()).setName(event.getNewValue());
                        resetRentalData();
                }
                else {
                    invalidEditRental.setVisible(true);
                    invalidEditRental.setText("Invalid edit: First character of Name should be UPPERCASE");
                    tableRental.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
                }
                break;

            case "Author/Director":
                if (checkValidString(event.getNewValue(), "Author", true)){
                    Rental.allRental.get(event.getRowValue().getSsid()).setAuthor(event.getNewValue());
                    resetRentalData();
                }
                else {
                    invalidEditRental.setVisible(true);
                    invalidEditRental.setText("Invalid edit: First character of Author should be UPPERCASE");
                    tableRental.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
                }
                break;

            case "Year":
                if(checkValidString(event.getNewValue(), "Year", true)) {
                    Rental.allRental.get(event.getRowValue().getSsid()).setYear(event.getNewValue());
                    resetRentalData();
                }
                else {
                    invalidEditRental.setVisible(true);
                    invalidEditRental.setText("Invalid edit: questionable Year value");
                    tableRental.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
                }
                break;
            case "Genre":
                Rental.allRental.get(event.getRowValue().getSsid()).setGenre(event.getNewValue());
                resetRentalData();
                break;
            case "Cost":
                if (checkValidString(event.getNewValue(), "Cost",
                        event.getRowValue().getClass() == Book.class)){
                    Rental.allRental.get(event.getRowValue().getSsid()).setRent(Integer.parseInt(event.getNewValue()));
                    resetRentalData();
                }
                else {
                    invalidEditRental.setVisible(true);
                    invalidEditRental.setText("Invalid edit: not a possible Cost value");
                    tableRental.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
                }
                break;
            case "Length":
                if(checkValidString(event.getNewValue(),"Length",event.getRowValue().getClass() == Book.class)){
                    Rental.allRental.get(event.getRowValue().getSsid()).setLength(event.getNewValue());
                    resetRentalData();
                }
                else{
                    invalidEditRental.setVisible(true);
                    if(event.getRowValue().getClass() == Book.class){
                        invalidEditRental.setText("Invalid edit: xx pages/chapters/volumes");
                    }
                    else{
                        invalidEditRental.setText("Invalid edit: correct format is hours:minutes:seconds");
                    }

                    tableRental.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
                }
                break;
            case "Size":
                Rental.allRental.get(event.getRowValue().getSsid()).setSize(event.getNewValue());
                resetRentalData();
                break;
            case "Language/Resolution":
                if (checkValidString(event.getNewValue(), "Resolution",
                        event.getRowValue().getClass() == Book.class)){
                    Rental.allRental.get(event.getRowValue().getSsid()).setResolution(event.getNewValue());
                    resetRentalData();
                }
                else {
                    invalidEditRental.setVisible(true);
                    invalidEditRental.setText("Invalid edit: Resolution is 4K/FHD/HD/SD");
                    tableRental.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + event.getTableColumn().getText());
        }
    }

    public void handleCustomerEdit(TableColumn.CellEditEvent<Customer, String> event) throws IOException{
        invalidEditCustomer.setVisible(false);
        if (event.getNewValue().contains("@@")) {
            invalidEditCustomer.setVisible(true);
            invalidEditCustomer.setText("Invalid edit: " + event.getTableColumn().getText() + " shouldn't contain \"@@\"");
            tableCustomer.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
            return;
        }
        switch (event.getTableColumn().getText()){
            case "Name":
                if(checkValidString(event.getNewValue(), "Name", true)){
                    Customer.allCustomer.get(event.getRowValue().getCustomerID()).setName(event.getNewValue());
                    resetCustomerData();
                }
                else {
                    invalidEditCustomer.setVisible(true);
                    invalidEditCustomer.setText("Invalid edit: first character of Name should be UPPERCASE");
                    tableCustomer.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
                }
                break;
            case "Phone Number":
                if(checkValidString(event.getNewValue(), "Phone", true)){
                    Customer.allCustomer.get(event.getRowValue().getCustomerID()).setPhoneNumber(event.getNewValue());
                    resetCustomerData();
                }
                else {
                    invalidEditCustomer.setVisible(true);
                    invalidEditCustomer.setText("Invalid edit: invalid Phone Number");
                    tableCustomer.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
                }
                break;
        }

    }

    public void handleCompleteContract() throws IOException{
        if(timeCompleteContractSearch.getValue() == null){
            invalidComplete.setVisible(true);
            invalidComplete.setText("Invalid: can't specify contract completed time");
        }
        else if(timeCompleteContractSearch.getValue().isAfter(
                Contract.allContract.get(tableContract.getSelectionModel().getSelectedItem().getContractID()).getTimeStart())){
            Contract.allContract.get(tableContract.getSelectionModel().getSelectedItem().getContractID())
                    .setRenting(timeCompleteContractSearch.getValue());
            reset();
        }
        else {
            invalidComplete.setVisible(true);
            invalidComplete.setText("Invalid: end time is before start time");
        }


    }

    /*
        REMOVE
     */
    public void handleRentalRemove() throws IOException {
        Rental.allRental.get(tableRental.getSelectionModel().getSelectedItem().getSsid()).deleteRental();
        resetRentalData();
        updateRentalTable(Rental.allRental);
    }

    public void handleCustomerRemove() throws IOException {
        Customer.allCustomer.get(tableCustomer.getSelectionModel().getSelectedItem().getCustomerID()).deleteCustomer();
        resetAllData();
        updateCustomerTable(Customer.allCustomer);
    }

    public void handleContractRemove() throws IOException{
        Contract.allContract.get(tableContract.getSelectionModel().getSelectedItem().getContractID()).deleteContract();
        reset();
    }


    /*
        ADD
     */
    public void handleRentalAdd() throws IOException {
        if(typeSearch.getValue().equals("Both")){
            invalidAddRental.setVisible(true);
            invalidAddRental.setText("Invalid add: unspecified type (Book/Movie)");
        }
        else {
            int rent = -1;

            if(isInteger(costMinSearch.getText()))
                rent = Integer.parseInt(costMinSearch.getText());
            else if(isInteger(costMaxSearch.getText()))
                rent = Integer.parseInt(costMaxSearch.getText());
            else{
                invalidAddRental.setVisible(true);
                invalidAddRental.setText("Invalid add: unspecified or invalid cost");
            }


            if(typeSearch.getValue().equals("Movie")){
                if(checkValidString(nameSearchRental.getText(),"Name", false)
                        && checkValidString(authorSearch.getText(), "Author", false)
                        && checkValidString(yearSearch.getText(), "Year" , false)
                        && rent >= 0
                        && checkValidString(lengthSearch.getText(), "Length", false)
                        && checkValidString(sizeSearch.getText(), "Size", false)
                        && checkValidString(resolutionSearch.getText(), "Resolution", false)){
                    new Movie(nameSearchRental.getText(), authorSearch.getText(), yearSearch.getText(), genreSearch.getText(),
                            rent, true, lengthSearch.getText(), sizeSearch.getText(), resolutionSearch.getText());
                    resetRentalData();
                    updateRentalTable(Rental.allRental);
                }
                else {
                    invalidAddRental.setVisible(true);
                    invalidAddRental.setText("Invalid add: possibly invalid field(s)");
                }
            }
            else{
                if(checkValidString(nameSearchRental.getText(),"Name", true)
                        && checkValidString(authorSearch.getText(), "Author", true)
                        && checkValidString(yearSearch.getText(), "Year" , true)
                        && rent >= 0
                        && checkValidString(lengthSearch.getText(), "Length", true)
                        && checkValidString(sizeSearch.getText(), "Size", true)
                        && checkValidString(resolutionSearch.getText(), "Resolution", true)){
                    new Book(nameSearchRental.getText(), authorSearch.getText(), yearSearch.getText(), genreSearch.getText(),
                            rent, true, lengthSearch.getText(), sizeSearch.getText(), resolutionSearch.getText());
                    resetRentalData();
                    updateRentalTable(Rental.allRental);
                }
                else invalidAddRental.setVisible(true);
            }
        }
    }

    public void handleCustomerAdd() throws IOException{
        invalidAddRental.setVisible(false);
        if (checkValidString(nameSearchCustomer.getText(), "Name", true)
        && checkValidString(phoneSearch.getText(), "Phone", true)){
            new Customer(nameSearchCustomer.getText(), phoneSearch.getText());
            resetAllData();
            updateCustomerTable(Customer.allCustomer);
        }
        else invalidAddCustomer.setVisible(true);

    }

    public void handleRent() throws Exception {
        if(timeStartSearch.getValue() == null){
            invalidRent.setVisible(true);
            invalidRent.setText("Invalid rent: unspecified start time");
        }
        else{
            timeStartChoosing = timeStartSearch.getValue();
            timeCompleteChoosing = timeCompleteSearch.getValue();

            Rental rental = tableRental.getSelectionModel().getSelectedItem();
            if(rental == null){
                invalidRent.setVisible(true);
                invalidRent.setText("Invalid rent: unspecified product");
                return;
            }
            else rentalChoosingSsid = rental.getSsid();

            if(!isInteger(depositRent.getText())){
                invalidRent.setVisible(true);
                invalidRent.setText("Invalid rent: undecipherable deposit");
                return;
            }
            else depositChoosing = Integer.parseInt(depositRent.getText());
            new CustomerChoose().start(new Stage());
        }

    }




    /*
        REFRESH
     */
    public void reset() throws IOException{
        resetAllData();
        updateRentalTable(Rental.allRental);
        updateCustomerTable(Customer.allCustomer);
        updateContractTable(Contract.allContract);

        depositRent.setText("");
        timeStartSearch.setValue(null);
        timeCompleteSearch.setValue(null);
    }


    /*
        PRIVATE METHODS
     */


    //check if a string is valid for an edit or an add
    private static boolean checkValidString(String string, String column, boolean isBook){
        switch (column){
            case "Name":
            case "Author":
                return string.matches("[A-Z].*");
            case "Year": return string.matches("[1|2][0-9]{3}");
            case "Cost": return isInteger(string);
            case "Length":
                if(isBook)
                    return string.matches("[1-9]([0-9])*\\p{javaSpaceChar}(page|pages|chapter|chapters|volume|volumes)");
                else{
                    if(!string.matches("(([0-9]+):?)+"))
                        return false;
                    else{
                        String[] time = string.split(":");
                        if(time.length > 3)
                            return false;
                        else if(time.length == 3){
                            int minute = Integer.parseInt(time[1]);
                            int second = Integer.parseInt(time[2]);
                            return minute <= 60 && minute >= 0 && second <= 60 && second >= 0;
                        }
                        else if(time.length == 2){
                            int minute = Integer.parseInt(time[1]);
                            return minute <= 60 && minute >= 0;
                        }
                    }
                    return true;
                }
            case "Resolution": return isBook || string.matches("((4K|FHD|HD|SD)/*)+");
            case "Phone": return string.matches("[0-9]*");
            default: return true;
        }
    }


    //update rental table from an arraylist
    private void updateRentalTable(ArrayList<Rental> data){
        invalidEditRental.setVisible(false);
        invalidAddRental.setVisible(false);
        invalidRent.setVisible(false);
        tableRental.setItems(FXCollections.observableArrayList(data));
    }

    private void updateCustomerTable(ArrayList<Customer> data){
        invalidEditCustomer.setVisible(false);
        invalidAddCustomer.setVisible(false);
        tableCustomer.setItems(FXCollections.observableArrayList(data));
    }

    private void updateContractTable(ArrayList<Contract> data){
        invalidComplete.setVisible(false);
        sumMoney.setText("Total: " + Utilities.sumMoney(data));
        tableContract.setItems(FXCollections.observableArrayList(data));
    }


    //true if a string is parsable
    private static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}

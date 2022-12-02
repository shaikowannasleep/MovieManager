package FrontEnd;


import BackEnd.Contract;
import BackEnd.Customer;
import BackEnd.InOutOperations;
import BackEnd.Utilities;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.beans.binding.Bindings.createObjectBinding;

public class CustomerChooseController extends Controller implements Initializable {

    @FXML TableView<Customer> table;
    @FXML TableColumn<Customer, String> nameChoose, phoneChoose;
    @FXML TextField nameSearchChoose, phoneSearchChoose;
    @FXML Text invalidChoose;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
            SETTING UP TABLE - GET ROW DATA
         */
        updateTable(Customer.allCustomer);

        /*
            SETTING UP TABLE - GET COLUMN DATA OF EACH ROW
         */

        nameChoose.setCellValueFactory(customer ->
                createObjectBinding( () -> customer.getValue().getName()));

        phoneChoose.setCellValueFactory(customer ->
                createObjectBinding( () -> customer.getValue().getPhoneNumber()));

    }

    public void handleCustomerSearchChoose(){
        ArrayList<Customer> tableData = new ArrayList<>(Customer.allCustomer);

        if(!nameSearchChoose.getText().isBlank())
            Utilities.searchNameCustomer(tableData, nameSearchChoose.getText());

        if(!phoneSearchChoose.getText().isBlank())
            Utilities.searchPhone(tableData, phoneSearchChoose.getText());


        updateTable(tableData);
    }

    public void handleCustomerChoose() throws IOException {
        Customer customer = table.getSelectionModel().getSelectedItem();
        if(customer == null){
            invalidChoose.setVisible(true);
        }
        else{
            customerIDChoose = customer.getCustomerID();
            if(timeCompleteChoosing == null){
                new Contract(rentalChoosingSsid, customerIDChoose,
                        depositChoosing, timeStartChoosing);

            }
            else{
                new Contract(rentalChoosingSsid, customerIDChoose,
                        depositChoosing, timeStartChoosing, timeCompleteChoosing);
            }
            InOutOperations.resetAllData();
            ((Stage) table.getScene().getWindow()).close();
        }

    }

    private void updateTable(ArrayList<Customer> data){
        invalidChoose.setVisible(false);
        table.setItems(FXCollections.observableArrayList(data));
    }


}

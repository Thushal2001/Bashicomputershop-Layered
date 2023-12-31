package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.computershop.bo.BoFactory;
import lk.ijse.computershop.bo.custom.EmployeeBO;
import lk.ijse.computershop.dto.EmployeeDTO;
import lk.ijse.computershop.view.EmployeeTM;
import lk.ijse.computershop.util.Validation;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageemployeeFormController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtJobRole;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TableView tblEmployee;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colJobrole;
    @FXML
    private TableColumn colUsername;
    @FXML
    private TableColumn colPassword;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearch;

    private EmployeeBO employeeBO= BoFactory.getBoFactory().getBO(BoFactory.BOTypes.EMPLOYEE);

    private LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern name = Pattern.compile("^([A-Z a-z]{4,40})$");
    Pattern contact = Pattern.compile("^(07(0|1|2|4|5|6|7|8)|091)[0-9]{7}$");
    Pattern jobRole = Pattern.compile("^(Cashier|cashier|Technician|technician)$");
    Pattern userName = Pattern.compile("^([A-Z a-z 0-9 \\W]{4,40})$");
    Pattern password = Pattern.compile("^([A-Z a-z 0-9 \\W]{4,40})$");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        generateNextEmployeeId();
        storeValidations();
        disableButtons();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colJobrole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void disableButtons() {
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void storeValidations() {
        map.put(txtName, name);
        map.put(txtContact, contact);
        map.put(txtJobRole, jobRole);
        map.put(txtUsername, userName);
        map.put(txtPassword, password);
    }

    private void clearAllTxt() {
        txtId.clear();
        txtName.clear();
        txtContact.clear();
        txtJobRole.clear();
        txtUsername.clear();
        txtPassword.clear();

        disableButtons();
        txtName.requestFocus();
        setBorders(txtId, txtName, txtContact, txtJobRole, txtUsername, txtPassword);
    }

    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: transparent");
        }
    }

    @FXML
    private void reset(MouseEvent mouseEvent) {
        clearAllTxt();
        generateNextEmployeeId();
    }

    @FXML
    private void txtKeyRelease(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField txtnext = (TextField) response;
                txtnext.requestFocus();
            }
        }
    }

    private void generateNextEmployeeId() {
        try {
            String id = employeeBO.generateNextEmployeeId();
            txtId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void getAll() {
        try {
            ObservableList<EmployeeTM> observableList = FXCollections.observableArrayList();
            List<EmployeeDTO> employeeDTOList = employeeBO.loadAllEmployees();

            for (EmployeeDTO employeeDTO : employeeDTOList) {
                observableList.add(new EmployeeTM(
                        employeeDTO.getId(),
                        employeeDTO.getName(),
                        employeeDTO.getContact(),
                        employeeDTO.getJobRole(),
                        employeeDTO.getUsername(),
                        employeeDTO.getPassword()
                ));
            }
            tblEmployee.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            EmployeeDTO employeeDTO = null;
            if (!txtName.getText().isEmpty() && !txtContact.getText().isEmpty() && !txtJobRole.getText().isEmpty() && !txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
                employeeDTO = new EmployeeDTO(
                        txtId.getText(),
                        txtName.getText(),
                        txtContact.getText(),
                        txtJobRole.getText(),
                        txtUsername.getText(),
                        txtPassword.getText()
                );
            }

            if (employeeBO.saveEmployee(employeeDTO) > 0) {

                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                getAll();
                clearAllTxt();
                txtName.requestFocus();
                generateNextEmployeeId();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            EmployeeDTO employeeDTO = employeeBO.searchEmployee(txtSearch.getText());
            if (employeeDTO != null) {
                txtId.setText(employeeDTO.getId());
                txtName.setText(employeeDTO.getName());
                txtContact.setText(employeeDTO.getContact());
                txtJobRole.setText(employeeDTO.getJobRole());
                txtUsername.setText(employeeDTO.getUsername());
                txtPassword.setText(employeeDTO.getPassword());

                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Input...!").show();
                clearAllTxt();
                generateNextEmployeeId();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
        txtSearch.clear();
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    txtId.getText(),
                    txtName.getText(),
                    txtContact.getText(),
                    txtJobRole.getText(),
                    txtUsername.getText(),
                    txtPassword.getText()
            );

            if (employeeBO.updateEmployee(employeeDTO) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                getAll();
                clearAllTxt();
                txtName.requestFocus();
                generateNextEmployeeId();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.WARNING, "Are you sure...?", yes, no).showAndWait();

            if (buttonType.orElse(yes) == yes) {
                if (employeeBO.deletEmployeee(txtId.getText()) > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully...!").show();
                    getAll();
                    clearAllTxt();
                    txtName.requestFocus();
                    generateNextEmployeeId();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }
}

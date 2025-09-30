module org.example.elitedrivinglearnersadvancedapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.elitedrivinglearnersadvancedapp to javafx.fxml;
    exports org.example.elitedrivinglearnersadvancedapp;
}
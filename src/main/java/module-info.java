module org.example.elitedrivinglearnersadvancedapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;


    opens org.example.elitedrivinglearnersadvancedapp to javafx.fxml;
    exports org.example.elitedrivinglearnersadvancedapp;
}
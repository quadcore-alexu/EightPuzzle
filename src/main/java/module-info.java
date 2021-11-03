module quadcore.eightpuzzle.view {
    requires javafx.controls;
    requires javafx.fxml;


    opens quadcore.eightpuzzle.view to javafx.fxml;
    exports quadcore.eightpuzzle.view;
}

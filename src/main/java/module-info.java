module quadcore.eightpuzzle.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens quadcore.eightpuzzle.view to javafx.fxml;
    exports quadcore.eightpuzzle.view;
}

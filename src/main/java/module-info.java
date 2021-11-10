module quadcore.eightpuzzle.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.logging;
    requires java.desktop;
    requires javatuples;
    requires log4j;


    opens quadcore.eightpuzzle.view to javafx.fxml;
    exports quadcore.eightpuzzle.view;
}

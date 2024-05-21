module pk.wieik.matrixestest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires static lombok;

    opens pk.wieik.matrix_calculator to javafx.fxml;
    exports pk.wieik.matrix_calculator;
}
module pk.wieik.matrixestest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens pk.wieik.matrixestest to javafx.fxml;
    exports pk.wieik.matrixestest;
}
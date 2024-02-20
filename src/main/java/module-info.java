module com.example.zeldapuzzle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.zeldapuzzle to javafx.fxml;
    exports com.example.zeldapuzzle;
}
 module com.example.zeldapuzzle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
     requires com.almasb.fxgl.all;
     requires annotations;
     requires java.desktop;

     opens assets.levels;

     exports com.example.zeldapuzzle;
     exports com.example.zeldapuzzle.animation;

 }
module org.hauthfx.secureauthfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.hauthfx.secureauthfx to javafx.fxml;
    exports org.hauthfx.secureauthfx;
}
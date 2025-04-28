module org.hauthfx.secureauthfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.sql;


    opens org.hauthfx.secureauthfx to javafx.fxml;
    exports org.hauthfx.secureauthfx;
}
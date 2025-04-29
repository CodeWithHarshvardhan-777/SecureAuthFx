module org.hauthfx.secureauthfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.sql;
    requires java.desktop;


    opens org.hauthfx.secureauthfx to javafx.fxml;
    exports org.hauthfx.secureauthfx;
}
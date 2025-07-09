package org.hauthfx.secureauthfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class HelloApplication extends Application {
    private  Stage loginWindow;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setHeaderText(null);
        alert.setTitle("SecureAuthFX");
        alert.setContentText("Do You Want to Start SecureAuthFX ?\nThen Click Open Button.");
        ButtonType open = new ButtonType("Open");
        ButtonType cancel = new ButtonType("Cancel");
        alert.getButtonTypes().addAll(open, cancel);
        alert.getDialogPane().setStyle("-fx-background-color: white; -fx-font-family: arial; -fx-font-size: 15px; -fx-text-fill: #333333;");
        alert.showAndWait().ifPresent(e -> {
            if (e == cancel) {
                primaryStage.close();
            } else {
                Label SignUp = new Label("Sign Up");
                SignUp.getStyleClass().add("title-text");
                Label name_label = new Label("Enter Your Name :");
                name_label.getStyleClass().add("form-label");
                TextField name_textfield = new TextField();
                Label email_label = new Label("Enter Your Email :");
                email_label.getStyleClass().add("form-label");
                TextField email_textfield = new TextField();
                Label password_label = new Label("Enter Your Password :");
                password_label.getStyleClass().add("form-label");
                PasswordField password_textfield = new PasswordField();
                Label confirm_password_lebel = new Label("Confirm Password :");
                confirm_password_lebel.getStyleClass().add("form-label");
                PasswordField confirm_passwordfield = new PasswordField();
                Button submit = new Button("Submit");
                submit.getStyleClass().add("button-submit");
                Button cancel_form = new Button("Cancel");
                cancel_form.getStyleClass().add("button-cancel");

                //label for error
                Label error = new Label();

                GridPane gridPane = new GridPane();
                gridPane.addRow(1, name_label, name_textfield);
                gridPane.addRow(2, email_label, email_textfield);
                gridPane.addRow(3, password_label, password_textfield);
                gridPane.addRow(4, confirm_password_lebel, confirm_passwordfield);
                gridPane.setHgap(5);
                gridPane.setVgap(10);
                gridPane.setAlignment(Pos.CENTER);

                HBox hBox = new HBox(10, SignUp);
                hBox.setAlignment(Pos.CENTER);

                Hyperlink hyperlink = new Hyperlink("Login");
                hyperlink.setOnAction(a -> openLoginWindow(primaryStage));
                Label login_message = new Label("Already Have Account?");
                login_message.getStyleClass().add("message");
                HBox login_text = new HBox(1, login_message, hyperlink);
                login_text.setAlignment(Pos.CENTER);

                HBox formButton = new HBox(10, submit, cancel_form);
                formButton.setAlignment(Pos.CENTER);
                formButton.setPadding(new Insets(10, 0, 0, 0));

                cancel_form.setOnAction(e1->{
                    primaryStage.close();
                    loginWindow.close();

                });

                //progress indicator
                ProgressIndicator progressIndicator = new ProgressIndicator();
                progressIndicator.setVisible(false);

                submit.setOnAction(e2 -> {
                    String name = name_textfield.getText().trim();
                    String email = email_textfield.getText().trim();
                    String password = password_textfield.getText().trim();
                    String password1 = confirm_passwordfield.getText().trim();

                    if (name.isEmpty()) {
                        error.getStyleClass().add("error");
                        error.setText("Please Enter Name.");
                    } else if (email.isEmpty()) {
                        error.getStyleClass().add("error");
                        error.setText("Please Enter Email.");
                    } else if (!password.equals(password1)) {
                        error.getStyleClass().add("error");
                        error.setText("Passwords do not match. Please enter correct password.");
                    } else if (password.length() < 8) {
                        error.getStyleClass().add("error");
                        error.setText("Password must be at least 8 characters long.");
                    } else {

                        boolean hasLetter = false;
                        boolean hasNumber = false;
                        boolean hasSpecial = false;

                        for (char ch : password.toCharArray()) {
                            if (Character.isLetter(ch)) {
                                hasLetter = true;
                            } else if (Character.isDigit(ch)) {
                                hasNumber = true;
                            } else {
                                hasSpecial = true;
                            }
                        }

                        if (!hasLetter) {
                            error.getStyleClass().add("error");
                            error.setText("Password must contain at least one letter.");
                        } else if (!hasNumber) {
                            error.getStyleClass().add("error");
                            error.setText("Password must contain at least one number.");
                        } else if (!hasSpecial) {
                            error.getStyleClass().add("error");
                            error.setText("Password must contain at least one special character.");
                        } else {

                            progressIndicator.setVisible(true);
                            progressIndicator.setDisable(true);

                            Task<Void> task = new Task<Void>() {
                                @Override
                                protected Void call() throws Exception{
                                    if(!SendOTP.SendOtp(email))
                                    {
                                        error.getStyleClass().add("error");
                                        error.setText("Please Enter Valid Email.");
                                    }else {
                                        error.setText(null);
                                    }
                                    return null;
                                }
                            };
                            otpChecking(primaryStage,name,email,password);

                            task.setOnSucceeded(e3->
                            {
                                progressIndicator.setVisible(false);

                            });
                            new Thread(task).start();
                        }
                    }

                });




                VBox vBox = new VBox(10,error, hBox,gridPane,login_text, formButton);
                vBox.getStyleClass().add("body");
                vBox.setAlignment(Pos.CENTER);

                StackPane stackPane = new StackPane(vBox,progressIndicator);
                stackPane.setAlignment(Pos.CENTER);

                Scene scene = new Scene(stackPane,600, 400);
                scene.setFill(Color.BLACK);
                scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

                primaryStage.setTitle("SignUp Window");
                primaryStage.setScene(scene);
                primaryStage.setResizable(true);
                primaryStage.initStyle(StageStyle.UTILITY);
                primaryStage.show();
            }
        });
    }

    public void openLoginWindow(Stage signUpWindow) {
        signUpWindow.close();
        loginWindow = new Stage();
        loginWindow.setTitle("Login Window");
        loginWindow.setResizable(true);
        loginWindow.initStyle(StageStyle.UTILITY);


        Label login = new Label("Login");
        login.getStyleClass().add("title-text");
        Label email_label = new Label("Enter Your Email :");
        email_label.getStyleClass().add("form-label");
        TextField email_textfield = new TextField();
        Label password_label = new Label("Enter Your Password :");
        password_label.getStyleClass().add("form-label");
        PasswordField password_textfield = new PasswordField();
        Button submit = new Button("Submit");
        submit.getStyleClass().add("button-submit");
        Button cancel_form = new Button("Cancel");
        cancel_form.getStyleClass().add("button-cancel");

        //label for error
        Label error = new Label();
        error.setVisible(false);

        //label for successfully login or sign up
        Label success = new Label();
        success.setVisible(false);


        cancel_form.setOnAction(e->{
            loginWindow.close();

        });

        submit.setOnAction(e->{
            String email = email_textfield.getText().trim();
            String password = password_textfield.getText().trim();

            if(email.isEmpty())
            {
                success.setVisible(false);
                error.setVisible(true);
                error.getStyleClass().add("error");
                error.setText("Please Enter Email.");
            }else if (password.isEmpty())
            {
                success.setVisible(false);
                error.setVisible(true);
                error.getStyleClass().add("error");
                error.setText("Please Enter Password.");
            }else {
                if(!Store.checkEmail(email))
                {
                    success.setVisible(false);
                    error.setVisible(true);
                    error.getStyleClass().add("error");
                    error.setText("Email not Found. Please Enter Correct Email.");
                }else{
                    if(!Store.checkLogin(email,password))
                    {
                        success.setVisible(false);
                        error.setVisible(true);
                        error.getStyleClass().add("error");
                        error.setText("Wrong Password. Please Enter Correct Password.");
                    }else {
                        error.setVisible(false);
                        success.setVisible(true);
                        success.getStyleClass().add("successfully");
                        success.setText("Successfully Login. Please wait....");

                    }
                }

            }

        });

        GridPane gridPane = new GridPane();
        gridPane.addRow(1, email_label, email_textfield);
        gridPane.addRow(2, password_label, password_textfield);
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(10, login);
        hBox.setAlignment(Pos.CENTER);


        HBox formbutton = new HBox(10, submit, cancel_form);
        formbutton.setAlignment(Pos.CENTER);
        formbutton.setPadding(new Insets(10, 0, 0, 0));

        Hyperlink hyperlink = new Hyperlink("Sign Up");
        hyperlink.setOnAction(a -> signUpWindow.show());
        Label login_message = new Label("You Don't Have Account?");
        login_message.getStyleClass().add("message");
        HBox login_text = new HBox(1, login_message, hyperlink);
        login_text.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(10,success,error,hBox, gridPane, login_text, formbutton);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("body");


        Scene scene = new Scene(vBox, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        loginWindow.setScene(scene);
        loginWindow.show();
    }
    public void otpChecking(Stage signUp, String name, String email, String password) {
        signUp.close();
        Stage stage = new Stage();
        stage.setTitle("OTP Verification");

        Hyperlink mail = new Hyperlink("Gmail");
        mail.getStyleClass().add("hyperlink"); // add hyperlink styling
        mail.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://mail.google.com/mail"));
            } catch (Exception t) {
                t.printStackTrace();
            }
        });

        Label label = new Label("OTP Successfully Sent. Check");
        label.getStyleClass().add("title-text");

        Label enter = new Label("Enter OTP:");
        enter.getStyleClass().add("form-label");

        TextField textField = new TextField();
        textField.getStyleClass().add("text-field");

        Button submit = new Button("Submit");
        submit.getStyleClass().add("button-submit");

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("button-cancel");

        Label error = new Label();

        GridPane gridPane = new GridPane();
        gridPane.addRow(1, enter, textField);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(5, label, mail);
        hBox.setAlignment(Pos.CENTER);

        HBox formButton = new HBox(10, submit, cancel);
        formButton.setAlignment(Pos.CENTER);

        HBox hBox1 = new HBox(error);
        hBox1.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(20, hBox1,hBox, gridPane, formButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("body"); // apply dark background

        submit.setOnAction(e->{
            int num = Integer.parseInt(textField.getText().trim());
            GenrateOtp genrateOtp = new GenrateOtp();
            if(num != SendOTP.otp)
            {
                error.setText("Wrong OTP. Enter Correct OTP");
                error.getStyleClass().add("error");
            }else if(!Store.signUpStore(name,email,password)){
                error.setText("Error 1045");
                error.getStyleClass().add("error");
            } else{
                stage.close();
            }
        });

        cancel.setOnAction(e1 -> {
            stage.close();
        });

        Scene scene = new Scene(vBox, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/otpstyle.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
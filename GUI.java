import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicReference;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Make it so window close actually closes with exit code 0
        Platform.setImplicitExit(true);

        // Creating objects

        // GridPanes
        GridPane window = new GridPane();

        // HBoxes
        HBox h1 = new HBox();
        HBox h2 = new HBox();
        HBox buttons = new HBox();

        // VBoxes
        VBox pay1 = new VBox();
        VBox pay2 = new VBox();
        VBox loan1 = new VBox();
        VBox fin1 = new VBox();
        VBox fin2 = new VBox();
        VBox opt1 = new VBox();

        // Titles
        Label paymentTitle = new Label("Payment Information");
        Label loanTitle = new Label("Loan Term");
        Label finTitle = new Label("Financing Information");
        Label priceOptTitle = new Label("Price With Options");

        // Payment information labels
        Label amountLabel = new Label("Total Loan Amount: $");
        Label monthPaymentLabel = new Label("Monthly Payment: $");
        Label totalPaymentLabel = new Label("Total Payment: $");
        Label amount = new Label("0.0");
        Label month = new Label("0.0");
        Label total = new Label("0.0");

        // Loan Radio Buttons
        RadioButton months_24 = new RadioButton("24 Months");
        RadioButton months_36 = new RadioButton("36 Months");
        RadioButton months_48 = new RadioButton("48 Months");
        RadioButton months_60 = new RadioButton("60 Months");

        // Financing Labels and TextFields
        Label baseLabel = new Label("Base Price: $");
        Label downLabel = new Label("Down Payment: $");
        Label saleLabel = new Label("Sales Tax: %");
        TextField baseField = new TextField("0.0");
        TextField downField = new TextField("0.0");
        TextField saleField = new TextField("7.0");

        // Price with Options check boxes
        CheckBox trans = new CheckBox("Auto Transmission: $1800");
        CheckBox lock = new CheckBox("AntiLock Brake: $1200");
        CheckBox sun = new CheckBox("Sun Roof: $800");
        CheckBox navi = new CheckBox("Navigation System: $1350");
        CheckBox audio = new CheckBox("Audio Package: $1550");

        // Buttons
        Button calc = new Button("Calculate");
        Button reset = new Button("Reset");
        Button exit = new Button("Exit");

        // Default Settings setup
        months_24.setSelected(true);
        lock.setSelected(true);
        AtomicReference<Double> annualInterest = new AtomicReference<>(0.07);
        window.setHgap(10);
        window.setVgap(10);

        // Formatting

        // Bolding
        calc.setStyle("-fx-font-weight: bold");
        reset.setStyle("-fx-font-weight: bold");
        exit.setStyle("-fx-font-weight: bold");
        paymentTitle.setStyle("-fx-font-weight: bold");
        finTitle.setStyle("-fx-font-weight: bold");
        loanTitle.setStyle("-fx-font-weight: bold");
        priceOptTitle.setStyle("-fx-font-weight: bold");

        // Settings / Locations
        calc.setAlignment(Pos.CENTER);
        reset.setAlignment(Pos.CENTER);
        exit.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        calc.setPrefSize(80, 15);
        reset.setPrefSize(80, 15);
        exit.setPrefSize(80, 15);
        buttons.setAlignment(Pos.CENTER);
        pay1.setSpacing(10);
        pay2.setSpacing(10);
        h1.setSpacing(40);
        loan1.setSpacing(10);
        fin1.setSpacing(15);
        fin2.setSpacing(10);
        opt1.setSpacing(10);
        window.setStyle("-fx-background-color: black;");
        h1.setStyle("-fx-background-color: #F4F4F4;");
        loan1.setStyle("-fx-background-color: #F4F4F4;");
        h2.setStyle("-fx-background-color: #F4F4F4;");
        opt1.setStyle("-fx-background-color: #F4F4F4;");
        buttons.setStyle("-fx-background-color: #F4F4F4;");
        window.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        window.setPadding(new Insets(10));


        // Adding objects into others for locations
        pay1.getChildren().addAll(paymentTitle, amountLabel, monthPaymentLabel, totalPaymentLabel);
        pay2.getChildren().addAll(new Label(""), amount, month, total);
        h1.getChildren().addAll(pay1, pay2);
        loan1.getChildren().addAll(loanTitle, months_24, months_36, months_48, months_60);
        fin1.getChildren().addAll(finTitle, baseLabel, downLabel, saleLabel);
        fin2.getChildren().addAll(new Label(""), baseField, downField, saleField);
        h2.getChildren().addAll(fin1, fin2);
        opt1.getChildren().addAll(priceOptTitle, trans, lock, sun, navi, audio);
        buttons.getChildren().addAll(calc, reset, exit);
        window.add(h1, 0, 0, 1, 1);
        window.add(loan1, 1, 0, 1, 1);
        window.add(h2, 0, 1, 1, 1);
        window.add(opt1, 1, 1, 1, 1);
        window.add(buttons, 0, 2, 2, 1);


        // Listeners
        calc.setOnAction((EventHandler) event -> {
            // Fetching input data from textfields
            double base = Double.parseDouble(baseField.getText());
            double down = Double.parseDouble(downField.getText());
            double sale = Double.parseDouble(saleField.getText()) / 100;
            double optionCost = 0.0;
            if(trans.isSelected()) {
                optionCost += 1800;
            }
            if(lock.isSelected()) {
                optionCost += 1200;
            }
            if(sun.isSelected()) {
                optionCost += 800;
            }
            if(navi.isSelected()) {
                optionCost += 1350;
            }
            if(audio.isSelected()) {
                optionCost += 1550;
            }
            // We change it to a string so we can remove all decimals past 2 places.
            String taxString = ("" + ((base - down + optionCost) * sale));
            double tax = Double.parseDouble(taxString.substring(0, taxString.indexOf(".") + 2)) + 0.00;
            double loanAmount = base - down + optionCost + tax;
            // We change it to a string so we can remove all decimals past 2 places.
            double monthlyInterest = annualInterest.get() / 12;
            int months = 0;
            if(months_24.isSelected()) {
                months = 24;
            }
            if(months_36.isSelected()) {
                months = 36;
            }
            if(months_48.isSelected()) {
                months = 48;
            }
            if(months_60.isSelected()) {
                months = 60;
            }
            double monthlyPayment = loanAmount * (monthlyInterest * Math.pow(1 + monthlyInterest, months))
                    / (Math.pow(1 + monthlyInterest, months) - 1);
            double totalPayment = monthlyPayment * months + down;

            // Rounding all payments to two places
            DecimalFormat df = new DecimalFormat("0.00");
            amount.setText(df.format(loanAmount));
            month.setText(df.format(monthlyPayment));
            total.setText(df.format(totalPayment));

        });
        reset.setOnAction((EventHandler) event -> {
            baseField.setText("0.0");
            downField.setText("0.0");
            saleField.setText("7.0");
            amount.setText("0.0");
            month.setText("0.0");
            total.setText("0.0");
            trans.setSelected(false);
            lock.setSelected(false);
            sun.setSelected(false);
            navi.setSelected(false);
            audio.setSelected(false);
            months_24.setSelected(true);
            months_36.setSelected(false);
            months_48.setSelected(false);
            months_60.setSelected(false);

        });
        exit.setOnAction((EventHandler) event -> {
            primaryStage.close();
        });
        months_24.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if(isNowSelected) {
                months_36.setSelected(false);
                months_48.setSelected(false);
                months_60.setSelected(false);
                annualInterest.set(0.07);
            }
        });
        months_36.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if(isNowSelected) {
                months_24.setSelected(false);
                months_48.setSelected(false);
                months_60.setSelected(false);
                annualInterest.set(0.06);
            }
        });
        months_48.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if(isNowSelected) {
                months_36.setSelected(false);
                months_24.setSelected(false);
                months_60.setSelected(false);
                annualInterest.set(0.055);
            }
        });
        months_60.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if(isNowSelected) {
                months_36.setSelected(false);
                months_48.setSelected(false);
                months_24.setSelected(false);
                annualInterest.set(0.05);
            }
        });

        // Displaying to screen
        Scene scene = new Scene(window);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.collabs;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setTitle("Winning pair");
        stage.setWidth(355);
        stage.setHeight(500);

        final Label label = new Label("All common projects");
        label.setFont(new Font("Arial", 20));

        //Creating the table
        TableView table = new TableView();
        TableColumn emp1ID = new TableColumn("Emp ID #1");
        emp1ID.setCellValueFactory(new PropertyValueFactory<>("emp1ID"));
        TableColumn emp2ID = new TableColumn("Emp ID #2");
        emp2ID.setCellValueFactory(new PropertyValueFactory<>("emp2ID"));
        TableColumn projID = new TableColumn("Project ID");
        projID.setCellValueFactory(new PropertyValueFactory<>("projID"));
        TableColumn days = new TableColumn("Days Worked");
        days.setCellValueFactory(new PropertyValueFactory<>("days"));
        table.getColumns().addAll(emp1ID, emp2ID, projID, days);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        AllCollaborations collab = new AllCollaborations(openFile());
        addInfoToTable(collab, table);
        stage.setScene(scene);
        stage.show();
    }

    //Open a file with FileChooser
    private String openFile() {
        Alert mb = new Alert(Alert.AlertType.CONFIRMATION);
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("."));
        File file = fc.showOpenDialog(mb.getOwner());
        if (file == null || file.getName().equals("")) return null;
        return file.toString();
    }

    //Load data into the table
    private void addInfoToTable(AllCollaborations collab, TableView table) {
        Pair<Integer, Integer> empIDs = collab.mostTimeTogether();
        for (int i = 0; i < collab.getCollaborations().size(); i++) {
            if (collab.getCollaborations().get(i).getEmp1ID() == empIDs.getKey() &&
                    collab.getCollaborations().get(i).getEmp2ID() == empIDs.getValue()) {
                table.getItems().add(collab.getCollaborations().get(i));

            }
        }
    }
}



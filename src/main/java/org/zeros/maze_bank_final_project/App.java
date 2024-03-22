package org.zeros.maze_bank_final_project;

import javafx.application.Application;
import javafx.stage.Stage;
import org.zeros.maze_bank_final_project.Models.Model;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }

}
module org.zeros.maze_bank_final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.xerial.sqlitejdbc;

    opens org.zeros.maze_bank_final_project to javafx.fxml;
    exports org.zeros.maze_bank_final_project;
    exports org.zeros.maze_bank_final_project.Controllers.Admin;
    exports org.zeros.maze_bank_final_project.Controllers.Client;
    exports org.zeros.maze_bank_final_project.Controllers;
    exports org.zeros.maze_bank_final_project.Models;
    exports org.zeros.maze_bank_final_project.Views;
}
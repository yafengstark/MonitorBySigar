package com;

/**
 * @author tony
 * @date 2019/4/26 20:48
 */


import com.menu.EfficacyMenu;
import com.tab.EfficacyTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * @author com *
 */
public class JavaFxFirst extends Application {

    public static void main(String[] args) {

        System.out.println("");

        // Start the JavaFX application by calling launch().
        launch(args);
    }

    // Override the init() method.
    @Override
    public void init() {
        System.out.println("Inside the init() method.");
    }

    // Override the start() method.
    @Override
    public void start(Stage myStage) {

        System.out.println("Inside the start() method.");

        // Give the stage a title.
        myStage.setTitle("我的windows工具");

        // Create a root node. In this case, a flow layout
        // is used, but several alternatives exist.
        BorderPane borderPane = new BorderPane();

        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(
                new EfficacyMenu()
        );
        borderPane.setTop(menuBar);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
          new EfficacyTab()
        );
        borderPane.setCenter(tabPane);


        // Create a scene.
        Scene myScene = new Scene(borderPane, 800, 600);



        // Set the scene on the stage.
        myStage.setScene(myScene);

        // Show the stage and its scene.
        myStage.show();
    }

    // Override the stop() method.
    @Override
    public void stop() {
        System.out.println("Inside the stop() method.");
    }
}
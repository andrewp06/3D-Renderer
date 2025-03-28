package code.renderer;



import javafx.event.ActionEvent;

import javafx.scene.control.Button;


import javafx.application.*;
import javafx.scene.Scene;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageGUI extends Application {

    private Screen screenSetUp(){
        Screen screen = new Screen();
        screen.addSphere(new Sphere(1f,new Vector(1.5f, -.5f, 2),new Color(0, .75f, 0)));
        screen.addSphere(new Sphere(1f,new Vector(0, 0, 3),new Color(.75f,0,0 )));
        screen.addSphere(new Sphere(1f,new Vector(-4, .5f, 6), new Color(0, 0, .75f)));
        
        Light fillLight = new Light(new Vector(-3, 2, -3), new Color(.4f),new Color(.4f));
        screen.lights.add(fillLight);

        Light keyLight = new Light(new Vector(10, 7, 3), new Color(.7f),new Color(.7f));
        screen.lights.add(keyLight);

        screen.ambientLight = new Color(.3f);
        return screen;
    }

    @SuppressWarnings("unused")
    @Override
    public void start(Stage stage) throws Exception {
        Screen screen = screenSetUp();
        Button rerender = new Button();
        rerender.setOnAction((ActionEvent event)->{
            screen.shapeTest(3,0);
        });
        rerender.setText("Re-Render");
        rerender.setMinWidth(screen.image.width);



        screen.shapeTest(3,0);

        ImageView imageView = screen.image.toImageView();

        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(imageView,rerender);

        
        
        Scene scene = new Scene(vBox);
        stage.setTitle("Image test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

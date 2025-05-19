package code.renderer;



import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageGUI extends Application {

    private Screen screenSetUp(){
        Screen screen = new Screen();

        screen.addSphere(new Sphere(1f,new Vector(2f, -.75f, 2f),new Color(.1f, .75f, .1f)));
        screen.addSphere(new Sphere(1f,new Vector(-.25f, 1.5f, 2.5f),new Color(.8f,.2f,0 )));
        
        screen.addLight(new Light(
            new Vector(2, 5, -5),
            new Color(.8f),
            new Color(.8f)
        ));

        // screen.sPlusA();

        screen.ambientLight = new Color(.05f);
        return screen;
    }

    private void updateImage(Screen screen, ImageView imageView){
        screen.shapeTestMultiThread(1,1);
        screen.image.updateImage();
        imageView.setImage(screen.image.toImageView());
    }


    @Override
    public void start(Stage stage) throws Exception {

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        ImageViewPane imageViewPane = new ImageViewPane(imageView);
        
        Button rerender = new Button();

        HBox mainControl = new HBox();
        HBox main = new HBox();
        VBox rightPanel = new VBox();
        VBox objects = new VBox();
        rightPanel.getChildren().addAll(mainControl,objects);
        

        main.getChildren().addAll(imageViewPane,rightPanel );
        HBox.setHgrow(imageViewPane, Priority.ALWAYS);


        Screen screen = screenSetUp();
        rerender.setOnAction((@SuppressWarnings("unused") ActionEvent event)->{
            rerender.setDisable(true);
            new Thread(()->{
                screen.shapeTest(1,0);
               
                Platform.runLater(()->{
                    updateImage(screen, imageView);
                    rerender.setDisable(false);
                });

            }).start();
            
            
        });


        rerender.setText("Re-Render");
        rerender.setAlignment(Pos.CENTER);
        rerender.setMinWidth(stage.getWidth());

        

        updateImage(screen, imageView);

        Button addSphere = new Button("Add Sphere");
        addSphere.setOnAction((@SuppressWarnings("unused") ActionEvent event)->{
            screen.addSphere(new Sphere());
            TitledPane tp = new TitledPane();
            tp.setText("Shpere");
            tp.setContent(new Button("PlaceHolder"));
            objects.getChildren().add(tp);
        });

        mainControl.getChildren().addAll(rerender,addSphere);

        
        
        Scene scene = new Scene(main);
        stage.setTitle("Image test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package code.renderer;



import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;



import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
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

    private void updateImage(Screen screen,VBox vBox,ImageView imageView){
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

        VBox vBox = new VBox();
        vBox.getChildren().addAll(rerender, imageViewPane);
        VBox.setVgrow(imageViewPane, Priority.ALWAYS);


        Screen screen = screenSetUp();
        rerender.setOnAction((@SuppressWarnings("unused") ActionEvent event)->{
            rerender.setDisable(true);
            new Thread(()->{
                screen.shapeTest(1,0);
               
                Platform.runLater(()->{
                    updateImage(screen, vBox,imageView);
                    rerender.setDisable(false);
                });

            }).start();
            
            
        });


        rerender.setText("Re-Render");
        rerender.setAlignment(Pos.CENTER);
        rerender.setMinWidth(stage.getWidth());

        

        updateImage(screen, vBox, imageView);

        // vBox.getChildren().add(new ImageViewPane(new ImageView(new Image("photos/RenderedImage0.png"))));
        // VBox.setVgrow(vBox.getChildren().get(2), null);
        // ((ImageViewPane)vBox.getChildren().get(2)).getImageView().setPreserveRatio(true);

        
        
        Scene scene = new Scene(vBox);
        stage.setTitle("Image test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package code.renderer;



import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageGUI extends Application {

    private Screen screenSetUp(){
        Screen screen = new Screen();

        
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

    @SuppressWarnings("unused")
    private void makeSphereTitledPane(Sphere sphere, VBox objects){
        TitledPane tp = new TitledPane();
        tp.setText("Shpere");
        GridPane posGrid = makeTextFieldsForVector(sphere.getCenter());
        GridPane nameGrid = new GridPane();
        nameGrid.setVgap(4);
        nameGrid.setPadding(new Insets(5, 5, 5, 5));
        nameGrid.add(new Label("Name: "), 0, 0);
        TextField name = new TextField("Sphere");
        name.setOnAction((ActionEvent event)->{
            tp.setText(name.getText());
        }); 
        nameGrid.add(name, 1,0);

        posGrid.add(new Label("Radius: "), 0, 3);
        TextField radius = new TextField("1");
        radius.setOnAction((ActionEvent event)->{
            try{
                sphere.radius = Float.parseFloat(radius.getText());
            }catch(NumberFormatException e){}
        }); 
        posGrid.add(radius, 1, 3);

        TitledPane position = new TitledPane();
        position.setText("Position & Size");
        position.setContent(posGrid);

        TitledPane color = new TitledPane();
        color.setText("Color");
        color.setContent(makeTextFieldsForColor(sphere.getColor()));

    

        VBox vbox = new VBox();
        vbox.getChildren().addAll(nameGrid,position,color, makeMaterialPane(sphere));
        tp.setContent(vbox);
        objects.getChildren().add(tp);
    }



    @SuppressWarnings("unused")
    private GridPane makeTextFieldsForVector(Vector vector){
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("X-POS: "), 0, 0);
        TextField xpos = new TextField(vector.x+"");
        xpos.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(xpos.getText());
                vector.x=value;
            }catch(NumberFormatException e){}
        });
        grid.add(xpos, 1, 0);
        grid.add(new Label("Y-POS: "), 0, 1);

        TextField ypos = new TextField(vector.y+"");
        ypos.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(ypos.getText());
                vector.y=value;
            }catch(NumberFormatException e){}
        });
        grid.add(ypos, 1, 1);
        grid.add(new Label("Z-POS: "), 0, 2);
        TextField zpos = new TextField(vector.z+"");
        zpos.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(zpos.getText());
                vector.z=value;
            }catch(NumberFormatException e){}
        });
        grid.add(zpos, 1, 2);
        return grid;
    }

    @SuppressWarnings("unused")
    private GridPane makeTextFieldsForColor(Color color){
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Red: "), 0, 0);
        TextField xpos = new TextField(color.getR()+"");
        xpos.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(xpos.getText());
                value = Math.min(1, value);
                value = Math.max(0,value);
                color.setR(value);
            }catch(NumberFormatException e){}
        });
        xpos.setMaxWidth(40);
        grid.add(xpos, 1, 0);
        grid.add(new Label("Green: "), 2, 0);

        TextField ypos = new TextField(color.getG()+"");
        ypos.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(ypos.getText());
                value = Math.min(1, value);
                value = Math.max(0,value);
                color.setG(value);
            }catch(NumberFormatException e){}
        });
        ypos.setMaxWidth(40);
        grid.add(ypos, 3, 0);
        grid.add(new Label("Blue: "), 4, 0);
        TextField zpos = new TextField(color.getB()+"");
        zpos.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(zpos.getText());
                value = Math.min(1, value);
                value = Math.max(0,value);
                color.setB(value);
            }catch(NumberFormatException e){}
        });
        zpos.setMaxWidth(40);
        grid.add(zpos, 5, 0);
        return grid;
    }

    private TitledPane makeMaterialPane(Sphere sphere){
        TitledPane tp = new TitledPane();
        tp.setText("Material");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(new Label("Ambient Constant:"), makeTextFieldsForColor(sphere.material.ambiantConstant));
        vBox.getChildren().addAll(new Label("Reflectivity:"), makeTextFieldsForColor(sphere.material.reflectivity));
        GridPane grid = new GridPane();
        grid.add(new Label("Diffuse Constant: "),0,0);
        TextField diffConst = new TextField(sphere.material.diffuseConstant+"");
        diffConst.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(diffConst.getText());
                value = Math.min(1, value);
                value = Math.max(0,value);
                sphere.material.diffuseConstant = value;
            }catch(NumberFormatException e){}
        });

        grid.add(diffConst,1,0);


        grid.add(new Label("Specular Constant: "),0,1);
        TextField specConst = new TextField(sphere.material.diffuseConstant+"");
        specConst.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(specConst.getText());
                value = Math.min(1, value);
                value = Math.max(0,value);
                sphere.material.specularConstant = value;
            }catch(NumberFormatException e){}
        });

        grid.add(specConst,1,1);


        grid.add(new Label("Shininess: "),0,2);
        TextField shiny = new TextField(sphere.material.diffuseConstant+"");
        shiny.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(shiny.getText());
                value = Math.min(1, value);
                value = Math.max(0,value);
                sphere.material.specularConstant = value;
            }catch(NumberFormatException e){}
        });

        grid.add(shiny,1,2);

        vBox.getChildren().add(grid);

        tp.setContent(vBox);

        return tp;

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
            Sphere sphere = new Sphere();
            screen.addSphere(sphere);
            makeSphereTitledPane(sphere, objects);
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

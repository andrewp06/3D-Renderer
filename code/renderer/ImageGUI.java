package code.renderer;



import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.*;
import javafx.scene.Node;
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

        
        // screen.addLight(new Light(
        //     new Vector(2, 5, -5),
        //     new Color(.8f),
        //     new Color(.8f)
        // ));

        // // screen.sPlusA();

        screen.ambientLight = new Color(.05f);
        return screen;
    }

    private void updateImage(Screen screen, ImageView imageView){
        screen.shapeTestMultiThread();
        screen.image.updateImage();
        imageView.setImage(screen.image.toImageView());
    }

    @SuppressWarnings("unused")
    private void makeSphereTitledPane(Sphere sphere, VBox objects, Screen screen){
        TitledPane tp = new TitledPane();
        tp.setText("Sphere");
        GridPane posGrid = makeTextFieldsForVector(sphere.getCenter());
        GridPane nameGrid = new GridPane();
        nameGrid.setVgap(4);
        nameGrid.setPadding(new Insets(5, 5, 5, 5));
        nameGrid.add(new Label("Name: "), 0, 0);
        TextField name = new TextField("Sphere");
        name.setOnAction((ActionEvent event)->{
            tp.setText(name.getText());
        }); 
        name.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
        
                if (name.getOnAction() != null) {
                    name.getOnAction().handle(new ActionEvent());
                }
            }
        });
        nameGrid.add(name, 1,0);

        posGrid.add(new Label("Radius: "), 0, 3);
        TextField radius = new TextField("1");
        radius.setOnAction((ActionEvent event)->{
            try{
                sphere.radius = Float.parseFloat(radius.getText());
            }catch(NumberFormatException e){
                radius.setText(sphere.radius+"");
            }
        }); 
        posGrid.add(radius, 1, 3);

        TitledPane position = new TitledPane();
        position.setText("Position & Size");
        position.setContent(posGrid);

        TitledPane color = new TitledPane();
        color.setText("Color");
        color.setContent(makeTextFieldsForColor(sphere.getColor()));

    

        VBox vbox = new VBox();
        Button removeButton = new Button("Remove Sphere");
        removeButton.setOnAction((ActionEvent event)->{
            screen.getShperes().remove(sphere);
            objects.getChildren().remove(tp);
        });
        vbox.getChildren().addAll(nameGrid,position,color, makeMaterialPane(sphere),removeButton);
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
                xpos.setText(value+"");
            }catch(NumberFormatException e){
                xpos.setText(vector.x+"");
            }
        });
        grid.add(xpos, 1, 0);
        grid.add(new Label("Y-POS: "), 0, 1);

        TextField ypos = new TextField(vector.y+"");
        ypos.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(ypos.getText());
                vector.y=value;
                ypos.setText(value+"");
            }catch(NumberFormatException e){
                ypos.setText(vector.y+"");
            }
        });
        grid.add(ypos, 1, 1);
        grid.add(new Label("Z-POS: "), 0, 2);
        TextField zpos = new TextField(vector.z+"");
        zpos.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(zpos.getText());
                vector.z=value;
                zpos.setText(value+"");
            }catch(NumberFormatException e){
                zpos.setText(vector.z+"");
            }
        });
        grid.add(zpos, 1, 2);

        for (Node node : grid.getChildren()) {
            if (node instanceof TextField textField) {
                textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) {
                        
                        if (textField.getOnAction() != null) {
                            textField.getOnAction().handle(new ActionEvent());
                        }
                    }
                });
            }
        }

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
                xpos.setText(value+"");
            }catch(NumberFormatException e){
                xpos.setText(color.getR()+"");
            }
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
                ypos.setText(value+"");
            }catch(NumberFormatException e){
                ypos.setText(color.getG()+"");
            }
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
                zpos.setText(value+"");
            }catch(NumberFormatException e){
                zpos.setText(color.getB()+"");
            }
        });
        zpos.setMaxWidth(40);
        grid.add(zpos, 5, 0);

        for (Node node : grid.getChildren()) {
            if (node instanceof TextField textField) {
                textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) {
                        
                        if (textField.getOnAction() != null) {
                            textField.getOnAction().handle(new ActionEvent());
                        }
                    }
                });
            }
        }

        return grid;
    }

    @SuppressWarnings("unused")
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
                diffConst.setText(value+"");

            }catch(NumberFormatException e){
                diffConst.setText(sphere.material.diffuseConstant+"");

            }
        });

        grid.add(diffConst,1,0);


        grid.add(new Label("Specular Constant: "),0,1);
        TextField specConst = new TextField(sphere.material.specularConstant+"");
        specConst.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(specConst.getText());
                value = Math.min(1, value);
                value = Math.max(0,value);
                sphere.material.specularConstant = value;
                specConst.setText(value+"");
            }catch(NumberFormatException e){
                specConst.setText(sphere.material.specularConstant+"");
            }
        });

        specConst.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
        
                if (specConst.getOnAction() != null) {
                    specConst.getOnAction().handle(new ActionEvent());
                }
            }
        });

        grid.add(specConst,1,1);


        grid.add(new Label("Shininess: "),0,2);
        TextField shiny = new TextField(sphere.material.shininess+"");
        shiny.setOnAction((ActionEvent event )->{
            try{
                float value = Float.parseFloat(shiny.getText());
                value = Math.max(0,value);
                sphere.material.shininess = value;
                shiny.setText(value+"");
            }catch(NumberFormatException e){
                shiny.setText(sphere.material.shininess+"");
            }
        });


        shiny.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
        
                if (shiny.getOnAction() != null) {
                    shiny.getOnAction().handle(new ActionEvent());
                }
            }
        });

        grid.add(shiny,1,2);

        vBox.getChildren().add(grid);

        tp.setContent(vBox);

        return tp;

    }

    @SuppressWarnings("unused")
    private void makeLightTitledPane(Light light, VBox lights,Screen screen){
        GridPane position = makeTextFieldsForVector(light.location);
        VBox vBox = new VBox();
        
        TitledPane tp = new TitledPane();
        tp.setText("Light");
        tp.setContent(vBox);

        GridPane nameGrid = new GridPane();

        nameGrid.add(new Label("Name: "), 0, 0);
        TextField name = new TextField("Light");
        name.setOnAction(( ActionEvent event)->{
            tp.setText(name.getText());
        }); 
        name.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
        
                if (name.getOnAction() != null) {
                    name.getOnAction().handle(new ActionEvent());
                }
            }
        });
        nameGrid.add(name, 1,0);

        Button removeButton = new Button("Remove Light");
        removeButton.setOnAction((ActionEvent event)->{
            screen.getLights().remove(light);
            lights.getChildren().remove(tp);
        });

        vBox.getChildren().addAll(nameGrid, position,new Label("Diffuse Intensity:"), makeTextFieldsForColor(light.diffuseIntensity));
        vBox.getChildren().addAll(new Label("Specular Intenisty:"), makeTextFieldsForColor(light.specularIntensity),removeButton);
        lights.getChildren().add(tp);
    }



    @SuppressWarnings("unused")
    @Override
    public void start(Stage stage) throws Exception {

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        ImageViewPane imageViewPane = new ImageViewPane(imageView);
        
        Button rerender = new Button();

        HBox mainControl = new HBox();
        HBox main = new HBox();
        VBox rightPanel = new VBox();

        Screen screen = screenSetUp();

        TitledPane objectsTP = new TitledPane();
        objectsTP.setText("Objects");
        VBox objects = new VBox();
        objectsTP.setContent(objects);
        
        TitledPane lightsTP = new TitledPane();
        lightsTP.setText("Lights");
        VBox lights = new VBox();
        lightsTP.setContent(lights);

        ScrollPane scrollPane = new ScrollPane();

        VBox items = new VBox();


        GridPane settingsGrid = new GridPane();
        settingsGrid.add(new Label("  Number of SSAA Samples: "), 0, 0);
        TextField SSAAsamplesFeild = new TextField(screen.SSAAsamples+"");
        SSAAsamplesFeild.setOnAction(( ActionEvent event)->{
            try{
                int value = Integer.parseInt(SSAAsamplesFeild.getText());
                value = Math.max(0,value);
                screen.SSAAsamples=value;
                SSAAsamplesFeild.setText(value+"");
            }catch(NumberFormatException e){
                SSAAsamplesFeild.setText(screen.SSAAsamples+"");
            }
        }); 
        SSAAsamplesFeild.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
        
                if (SSAAsamplesFeild.getOnAction() != null) {
                    SSAAsamplesFeild.getOnAction().handle(new ActionEvent());
                }
            }
        });
        settingsGrid.add(SSAAsamplesFeild, 1,0);

        settingsGrid.add(new Label("  Reflection Recursion Depth: "), 0, 1);
        TextField recursionDepthField = new TextField(screen.recursionDepth+"");
        recursionDepthField.setOnAction(( ActionEvent event)->{
            try{
                int value = Integer.parseInt(recursionDepthField.getText());
                value = Math.max(0,value);
                screen.recursionDepth=value;
                recursionDepthField.setText(value+"");
            }catch(NumberFormatException e){
                recursionDepthField.setText(screen.recursionDepth+"");
            }
        }); 
        recursionDepthField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
        
                if (recursionDepthField.getOnAction() != null) {
                    recursionDepthField.getOnAction().handle(new ActionEvent());
                }
            }
        });
        settingsGrid.add(recursionDepthField, 1,1);



        items.getChildren().addAll(settingsGrid,lightsTP,objectsTP);

        scrollPane.setContent(scrollPane);
        scrollPane.setContent(items);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);



        lightsTP.setMinWidth(350);
        objectsTP.setMinWidth(350);

        
        rightPanel.getChildren().addAll(mainControl, scrollPane);


        MenuBar topRibbon = new MenuBar();
        Menu file = new Menu();
        file.setText("File");

        MenuItem newItem = new MenuItem("New");
        newItem.setOnAction((ActionEvent event)->{
            screen.SSAAsamples = 1;
            screen.recursionDepth = 1;
            screen.ambientLight = new Color(.05f);

            screen.spheres = new ArrayList<>();
            screen.lights = new ArrayList<>();
            
            for(Object object : objects.getChildren().toArray()){
                Node node = (Node)object;
                objects.getChildren().remove(node);
            }
            for(Object object : lights.getChildren().toArray()){
                TitledPane tp = (TitledPane)object;
                if(!tp.getText().equals("Ambient Light")){
                    lights.getChildren().remove(tp);
                }
            }

            updateImage(screen, imageView);
        });
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction((ActionEvent event)->{
            
            
            while(true){
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Import File");
                dialog.setHeaderText("Import File");
                dialog.setContentText("Please enter the filename:");

                Optional<String> result = dialog.showAndWait();
                String filename = result.get().trim();
                try {
                    screen.fromTxt(filename);
                    break;
                } catch (FileNotFoundException e) {
                    
                }
                try{
                    screen.fromTxt("txtOut/"+filename);
                    break;
                } catch (FileNotFoundException e){}
            }

            for(Object object : objects.getChildren().toArray()){
                Node node = (Node)object;
                objects.getChildren().remove(node);
            }
            for(Object object : lights.getChildren().toArray()){
                TitledPane tp = (TitledPane)object;
                if(!tp.getText().equals("Ambient Light")){
                    lights.getChildren().remove(tp);
                }
            }

            for (Sphere sphere : screen.spheres) {
                makeSphereTitledPane(sphere, objects, screen);
            }            
            for(Light light: screen.lights){
                makeLightTitledPane(light, lights, screen);
            }


        });
        MenuItem saveConfig = new MenuItem("Save Config");
        saveConfig.setOnAction((ActionEvent event)->{
            while(true){
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Save File");
                dialog.setHeaderText("Save File");
                dialog.setContentText("Please enter the filename:");

                Optional<String> result = dialog.showAndWait();
                String filename = result.get().trim();
                try {
                    screen.toTxt(filename);
                    break;
                } catch (IOException e) {
                    
                }
            }
        });
        MenuItem saveImage = new MenuItem("Save Image");
        saveImage.setOnAction((ActionEvent event)->{
            while(true){
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Save Image");
                dialog.setHeaderText("Save Image");
                dialog.setContentText("Please enter the filename:");

                Optional<String> result = dialog.showAndWait();
                String filename = result.get().trim();
                try {
                    screen.saveImage(filename);
                    break;
                } catch (IOException e) {
                    
                }
            }
        });

        MenuItem quitItem = new MenuItem("Quit");
        quitItem.setOnAction((ActionEvent event)->{
            try {
                Platform.exit();
                System.out.println("stoppped");
            } catch (Exception e) {
                
                e.printStackTrace();
            }
        });

        topRibbon.getMenus().addAll(file);

        file.getItems().addAll(newItem,openItem,saveConfig,saveImage,new SeparatorMenuItem(),quitItem);



        VBox leftSide = new VBox();
        leftSide.getChildren().addAll(topRibbon,imageViewPane);

        main.getChildren().addAll(leftSide,rightPanel);
        HBox.setHgrow(imageViewPane, Priority.ALWAYS);


        rerender.setOnAction(( ActionEvent event)->{
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
        addSphere.setOnAction(( ActionEvent event)->{
            Sphere sphere = new Sphere();
            screen.addSphere(sphere);
            makeSphereTitledPane(sphere, objects, screen);
        });

        Button addLight = new Button("Add Light");
        addLight.setOnAction(( ActionEvent event) -> {
            Light light = new Light();
            screen.addLight(light);
            makeLightTitledPane(light, lights, screen);
        });


        TitledPane ambientLightPane = new TitledPane();
        ambientLightPane.setText("Ambient Light");
        ambientLightPane.setContent(makeTextFieldsForColor(screen.ambientLight));

        lights.getChildren().add(ambientLightPane);


        mainControl.setMinWidth(350);


        mainControl.getChildren().addAll(rerender,addSphere,addLight);
        
        
        Scene scene = new Scene(main);
        stage.setTitle("Image test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

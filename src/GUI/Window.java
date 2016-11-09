package GUI;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import painting.Canvas;
import painting.Layer;
import tools.BrushTool;
import tools.SprayTool;
import tools.RecolorTool;
import tools.Tool;

/**
 * Created by dennis on 11/7/16.
 */
public class Window extends Pane
{
    private Stage stage;
    private ColorPicker colorPicker;
    private Canvas canvas;
    private Button newDrawing;
    private Button saveDrawing;
    private Button addLayer;
    private Button removeLayer;
    private Button moveUp;
    private Button moveDown;
    private ListView<Layer> layers = new ListView<>();
    private ComboBox<Integer> size = new ComboBox<>(FXCollections.observableArrayList(
            6, 8, 10, 12, 14, 16, 18, 20,
            22, 24, 26, 28, 30, 32, 34, 36,
            38, 40, 42, 44, 46, 48, 50, 52,
            54, 56, 58, 60
    ));
    private ComboBox<Tool> tools = new ComboBox<>(FXCollections.observableArrayList(
            new SprayTool(),
            new BrushTool(),
            new RecolorTool()
    ));

    public Window(int width, int height, Stage stage)
    {
        this.stage = stage;
        stage.setTitle("Simple Drawing");

        canvas = new Canvas(width, height, this);
        canvas.setLayoutX(20);
        canvas.setLayoutY(20);

        layers.getItems().add(new Layer(400, 400, canvas, "Layer"+canvas.getLayers().size()));
        layers.getSelectionModel().select(0);

        newDrawing = new Button("New");
        newDrawing.setOnAction(e-> {
            Stage popup = new NewPictureWindow(this);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(stage);
            popup.show();

        });

        saveDrawing = new Button("Save");
        saveDrawing.setOnAction(e->
        {
            SavePictureWindow popup = new SavePictureWindow(canvas);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(stage);
            popup.show();
            popup.fixLayout();
        });

        addLayer = new Button("Add");
        addLayer.setOnAction(e->createLayer());

        removeLayer = new Button("Remove");
        removeLayer.setOnAction(e->
                layers.setItems(FXCollections.observableArrayList(canvas.destroyLayer(layers.getSelectionModel().getSelectedIndex()))));

        moveDown = new Button("\\/");
        moveDown.setOnAction(e->
        {
            canvas.moveDown(layers.getSelectionModel().getSelectedIndex());
            layers.setItems(FXCollections.observableArrayList(canvas.getLayers()));
        });

        moveUp = new Button("/\\");
        moveUp.setOnAction(e->
        {
            canvas.moveUp(layers.getSelectionModel().getSelectedIndex());
            layers.setItems(FXCollections.observableArrayList(canvas.getLayers()));
        });

        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);

        size.setValue(24);
        tools.setValue(new SprayTool());

        this.getChildren().addAll(canvas, colorPicker, size, newDrawing, layers, addLayer, tools, moveDown, moveUp, removeLayer, saveDrawing);

        stage.widthProperty().addListener(e->updateLayout(width, height));
        stage.heightProperty().addListener(e->updateLayout(width, height));
    }

    public Color getColor()
    {
        return colorPicker.getValue();
    }

    public int getSize()
    {
        return size.getValue();
    }

    public void newPicture(int width, int height)
    {
        canvas = new Canvas(width, height, this);
        this.getChildren().set(0, canvas);

        updateLayout(width, height);

        layers.setItems(FXCollections.observableArrayList(canvas.getLayers()));
        layers.getSelectionModel().select(0);
    }

    private void updateLayout(int width, int height)
    {
        double margin = 20;

        stage.setWidth(margin * 3 + layers.getWidth() + width);
        stage.setHeight(height + newDrawing.getHeight() + margin * 4);

        this.getChildren().get(0).setLayoutX(margin);
        this.getChildren().get(0).setLayoutY(margin);

        addLayer.setLayoutX(margin * 2 + width);
        addLayer.setLayoutY(height - addLayer.getHeight() + margin);

        removeLayer.setLayoutX(margin * 3 + width + addLayer.getWidth());
        removeLayer.setLayoutY(height - removeLayer.getHeight() + margin);

        moveUp.setLayoutX(margin * 4 + width + addLayer.getWidth() + removeLayer.getWidth());
        moveUp.setLayoutY(height - moveUp.getHeight() + margin);

        moveDown.setLayoutX(margin * 4.25 + width + addLayer.getWidth() + moveUp.getWidth() + removeLayer.getWidth());
        moveDown.setLayoutY(height - moveDown.getHeight() + margin);


        colorPicker.setLayoutX(margin * 2 + tools.getWidth());
        colorPicker.setLayoutY(margin * 2 + height);

        tools.setLayoutX(margin);
        tools.setLayoutY(margin * 2 + height);

        layers.setLayoutX(margin * 2 + width);
        layers.setLayoutY(margin);
        layers.setPrefHeight(height - addLayer.getHeight() - margin);

        size.setLayoutX(margin * 3 + tools.getWidth() + colorPicker.getWidth());
        size.setLayoutY(margin * 2 + height);

        newDrawing.setLayoutX(margin * 4 + + tools.getWidth() + colorPicker.getWidth() + size.getWidth());
        newDrawing.setLayoutY(margin * 2 + height);

        saveDrawing.setLayoutX(margin * 5 + + tools.getWidth() + colorPicker.getWidth() + size.getWidth() + newDrawing.getWidth());
        saveDrawing.setLayoutY(margin * 2 + height);
    }

    private void createLayer()
    {
        canvas.getLayers().add(new Layer(canvas.getWidth(), canvas.getHeight(), canvas, "Layer"+(canvas.getLayers().size() + 1)));
        layers.setItems(FXCollections.observableArrayList(canvas.getLayers()));
    }

    public int getLayer()
    {
        return layers.getSelectionModel().getSelectedIndex();
    }

    public void setGlow(Effect effect)
    {
        layers.setEffect(effect);
    }

    public Tool getTool()
    {
        return tools.getValue();
    }
}

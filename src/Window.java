import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by dennis on 11/7/16.
 */
public class Window extends Pane
{
    private Stage stage;
    private ColorPicker colorPicker;
    private Canvas canvas;
    private Button button;
    private Button addLayer;
    private ListView<String> layers = new ListView<>();
    private ComboBox<Integer> size = new ComboBox<>(FXCollections.observableArrayList(
            6,
            8,
            10,
            12,
            14,
            16,
            18,
            20,
            22,
            24,
            26,
            28,
            30,
            32,
            34,
            36,
            38,
            40,
            42,
            44,
            46,
            48,
            50,
            52,
            54,
            56,
            58,
            60
    ));

    public Window(int width, int height, Stage stage)
    {
        this.stage = stage;
        stage.setTitle("Paint some shit");
        stage.setWidth(160 + width);
        stage.setHeight(height + 120);
        canvas = new Canvas(width, height, this);
        canvas.setLayoutX(20);
        canvas.setLayoutY(20);

        layers.getItems().add("Layer1");
        layers.setLayoutX(40 + width);
        layers.setLayoutY(20);
        layers.setPrefWidth(100);
        layers.setPrefHeight(height - 40);

        button = new Button("new");
        button.setOnAction(e->newPicture(width, height));

        addLayer = new Button("add Layer");
        addLayer.setLayoutX(40 + width);
        addLayer.setLayoutY(height - 15);
        addLayer.setOnAction(e->createLayer());

        colorPicker = new ColorPicker();
        colorPicker.setLayoutX(20);
        colorPicker.setLayoutY(40 + height);

        size.setLayoutX(190);
        size.setLayoutY(40 + height);
        size.setValue(12);

        button.setLayoutX(280);
        button.setLayoutY(40 + height);

        this.getChildren().addAll(canvas, colorPicker, size, button, layers, addLayer);
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
        this.getChildren().set(0, new Canvas(width, height, this));
        this.getChildren().get(0).setLayoutX(20);
        this.getChildren().get(0).setLayoutY(20);

        colorPicker.setLayoutX(20);
        colorPicker.setLayoutY(40 + height);

        size.setLayoutX(190);
        size.setLayoutY(40 + height);
        size.setValue(12);

        button.setLayoutX(280);
        button.setLayoutY(40 + height);

        layers.setItems(FXCollections.observableArrayList("Layer1"));
    }

    private void createLayer()
    {
        canvas.getLayers().add(0, new Layer(canvas.getWidth(), canvas.getHeight(), canvas));
        layers.getItems().add(0, "xD");
    }

    public int getLayer()
    {
        return layers.getSelectionModel().getSelectedIndex();
    }
}

package gui;

import effects.Pixelate;
import effects.Ripples;
import effects.BoxWarp;
import effects.Warp;
import effects.base.LayerEffect;
import files.FileLoader;
import files.ImageExporter;
import files.formats.CanvasFile;
import files.formats.javafxworkarounds.LayerConverter;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import painting.Canvas;
import painting.layers.Layer;
import tools.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by dennis on 11/7/16.
 */
public class Window extends Pane
{
    private Stage stage;
    private WritableImage pointerImage = new WritableImage(5, 5);
    private WritableImage showColorImage = new WritableImage(10, 10);
    private ImageView showColor = new ImageView(showColorImage);
    private ImageView pointer = new ImageView(pointerImage);
    private ColorPicker colorPicker;
    private Canvas canvas;
    private MenuBar menuBar = new MenuBar();
    private Button addLayer;
    private Button removeLayer;
    private Button moveUp;
    private Button moveDown;
    private Label cursorInfo = new Label();
    private int windowWidth;
    private int windowHeight;
    private ListView<Layer> layers = new ListView()
    {
        @Override
        public void requestFocus(){}
    };
    private ComboBox<Integer> size = new ComboBox<>(FXCollections.observableArrayList(
            6, 8, 10, 12, 14, 16, 18, 20,
            22, 24, 26, 28, 30, 32, 34, 36,
            38, 40, 42, 44, 46, 48, 50, 52,
            54, 56, 58, 60
    ));
    private ComboBox<Tool> tools = new ComboBox<>(FXCollections.observableArrayList(
            new SprayTool(),
            new BrushTool(),
            new RecolorTool(),
            new EraserTool(),
            new ColorEraserTool(),
            new EdgeTool(),
            new FillTool()
    ));

    public Window(int width, int height, Stage stage)
    {
        this.stage = stage;
        stage.setTitle("Simple Drawing");

        PixelWriter pi = pointerImage.getPixelWriter();
        pi.setColor(2, 0, Color.WHITE);
        pi.setColor(2, 1, Color.WHITE);
        pi.setColor(0, 2, Color.WHITE);
        pi.setColor(1, 2, Color.WHITE);
        pi.setColor(2, 2, Color.WHITE);
        pi.setColor(3, 2, Color.WHITE);
        pi.setColor(4, 2, Color.WHITE);
        pi.setColor(2, 3, Color.WHITE);
        pi.setColor(2, 4, Color.WHITE);
        pointer.setEffect(new DropShadow(2, Color.BLACK));
        pointer.setLayoutX(-100);
        pointer.setLayoutY(-100);

        Menu fileMenu = new Menu("File");

        Menu newMenu = new Menu("new");

        MenuItem newProject = new MenuItem("Project");
        MenuItem newProjectFromImage = new MenuItem("Project From Image");

        newMenu.getItems().addAll(newProject, newProjectFromImage);

        MenuItem loadDrawing = new MenuItem("Load Project");
        MenuItem saveDrawing = new MenuItem("Save Project");
        MenuItem exportDrawing = new MenuItem("Export to PNG");

        Menu effectsMenu = new Menu("Effects");

        MenuItem pixelate = new MenuItem("Pixelate");
        MenuItem warp = new MenuItem("Warp");
        MenuItem stretch = new MenuItem("Box Warp");
        MenuItem ripples = new MenuItem("Ripples");

        menuBar.getMenus().addAll(fileMenu, effectsMenu);

        fileMenu.getItems().addAll(newMenu, loadDrawing, saveDrawing, exportDrawing);
        effectsMenu.getItems().addAll(pixelate, warp, stretch, ripples);

        canvas = new Canvas(width, height, this, null);
        canvas.setLayoutX(20);
        canvas.setLayoutY(20);
        canvas.setEffect(new DropShadow(5, Color.BLACK));
        canvas.setOnMouseMoved(this::setShowColor);

        layers.getItems().add(new Layer(400, 400, canvas, "Layer"+canvas.getLayers().size()));
        layers.getSelectionModel().select(0);
        layers.setPrefWidth(250);

        layers.setOnMouseClicked(e->
        {
            if(e.getClickCount() == 2)
            {
                if(layers.getSelectionModel().getSelectedItem() != null)
                {
                    LayerWindow layerWindow = new LayerWindow(layers.getSelectionModel().getSelectedIndex(), this, layers.getSelectionModel().getSelectedItem().getName());
                    layerWindow.show();
                }
            }
        });

        newProject.setOnAction(e-> {
            Stage popup = new NewPictureWindow(this);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(stage);
            popup.show();

        });

        newProjectFromImage.setOnAction(e->
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg");
            fileChooser.getExtensionFilters().addAll(filter);

            File file = fileChooser.showOpenDialog(stage);

            Image image = new Image(file.toURI().toString());

            Color[][] colors = new Color[(int) image.getWidth()][(int) image.getHeight()];

            PixelReader pixelReader = image.getPixelReader();

            for (int i = 0; i < image.getWidth(); i++)
            {
                for (int j = 0; j < image.getHeight(); j++)
                {
                    colors[i][j] = pixelReader.getColor(i, j);
                }
            }
            Layer imageLayer = new Layer(colors, (int) image.getWidth(), (int) image.getHeight(), "Layer1");

            ArrayList<Layer> layerList = new ArrayList<>();
            layerList.add(imageLayer);

            Canvas newCanvas = new Canvas((int) image.getWidth(), (int) image.getHeight(), this, layerList);

            imageLayer.setCanvas(newCanvas);

            newPicture((int) image.getWidth(), (int) image.getHeight(), newCanvas);

        });

        saveDrawing.setOnAction(e->
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Canvas file", "*.can");
            fileChooser.getExtensionFilters().addAll(filter);

            File file = fileChooser.showSaveDialog(stage);

            if(FileLoader.saveFile(new CanvasFile(LayerConverter.layerToSerializable(canvas), file.getName(), width, height), file))
            {
                new MessageWindow("Project saved", "Project saved successfully!", null);
            }
            else
            {
                new MessageWindow("Error", "Project could not be saved!", null);
            }
        });

        loadDrawing.setOnAction(e->
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Canvas file", "*.can");
            fileChooser.getExtensionFilters().addAll(filter);

            try
            {
                ArrayList<Layer> layers = FileLoader.loadFile(fileChooser.showOpenDialog(stage));

                int newWidth = layers.get(0).getDimensions()[0];
                int newHeight = layers.get(0).getDimensions()[1];

                Canvas canvas = new Canvas(newWidth, newHeight, this, layers);
                canvas.fixCanvasPointer(canvas);
                newPicture(newWidth, newHeight, canvas);
            }
            catch (Exception e1)
            {
                MessageWindow messageWindow = new MessageWindow("Error", "Invalid or corrupt file!", null);
            }

        });

        exportDrawing.setOnAction(e->
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PNG Image", "*.png");
            fileChooser.getExtensionFilters().addAll(filter);

            File file = fileChooser.showSaveDialog(stage);

            if(ImageExporter.exportImage(file, canvas.getJavafxWorkaroundnumber324923847289()))
            {
                new MessageWindow("Image exported", "Image exported successfully!", null);
            }
            else
            {
                new MessageWindow("Error", "Image could not be exported!", null);
            }
        });

        //EFFECTS

        pixelate.setOnAction(e->
        {
            Pixelate effect = new Pixelate("Pixelate");

            EffectWindow effectWindow = new EffectWindow(effect, this);
        });

        warp.setOnAction(e->
        {
            Warp effect = new Warp("Warp", canvas.getWidth(), canvas.getHeight());

            EffectWindow effectWindow = new EffectWindow(effect, this);
        });

        stretch.setOnAction(e->
        {
            BoxWarp effect = new BoxWarp("Box Warp");

            EffectWindow effectWindow = new EffectWindow(effect, this);
        });

        ripples.setOnAction(e->
        {
            Ripples effect = new Ripples("Ripples", canvas.getWidth(), canvas.getHeight());

            EffectWindow effectWindow = new EffectWindow(effect, this);
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

        this.getChildren().addAll(menuBar, canvas, colorPicker, size, layers, addLayer, tools, moveDown, moveUp, removeLayer, cursorInfo, pointer, showColor);

        stage.setWidth(100 + 250 + width);
        stage.setHeight(height + 145);
    }

    public Color getColor()
    {
        return colorPicker.getValue();
    }

    public int getSize()
    {
        return size.getValue();
    }

    public void newPicture(int width, int height, Canvas newCanvas)
    {
        if(newCanvas != null)
        {
            canvas = newCanvas;
        }
        else
        {
            canvas = new Canvas(width, height, this, null);
        }
        this.getChildren().remove(1);
        this.getChildren().add(1, canvas);

        updateLayout(windowWidth, windowHeight);

        layers.setItems(FXCollections.observableArrayList(canvas.getLayers()));
        layers.getSelectionModel().select(0);
        canvas.setEffect(new DropShadow(5, Color.BLACK));
        canvas.setOnMouseMoved(this::setShowColor);
        canvas.updateCanvas(0, 0, width, height, true);
    }

    public boolean applyEffect(LayerEffect effect)
    {
        try
        {
            canvas.applyEffect(effect);
            layers.setEffect(null);
            return true;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            layers.setEffect(new DropShadow(5, Color.RED));
            return false;
        }
    }

    public void updateLayout(int windowWidth, int windowHeight)
    {
        double margin = 20;

        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        menuBar.setPrefWidth(windowWidth);

        addLayer.setLayoutX(windowWidth - 250 - margin);
        addLayer.setLayoutY(windowHeight - colorPicker.getHeight() - margin);

        removeLayer.setLayoutX(windowWidth - 250 + addLayer.getWidth());
        removeLayer.setLayoutY(windowHeight - colorPicker.getHeight() - margin);

        moveUp.setLayoutX(margin + windowWidth - 250 + addLayer.getWidth() + removeLayer.getWidth());
        moveUp.setLayoutY(windowHeight - colorPicker.getHeight() - margin);

        moveDown.setLayoutX(margin * 1.5 + windowWidth - 250 + addLayer.getWidth() + moveUp.getWidth() + removeLayer.getWidth());
        moveDown.setLayoutY(windowHeight - colorPicker.getHeight() - margin);

        this.getChildren().get(1).setLayoutX(Math.floor((windowWidth - 250 - margin * 2) / 2 - canvas.getWidth() / 2));
        this.getChildren().get(1).setLayoutY(Math.floor((windowHeight - margin - colorPicker.getHeight() + menuBar.getHeight()) / 2 - canvas.getHeight() / 2));

        colorPicker.setLayoutX(margin * 2 + tools.getWidth());
        colorPicker.setLayoutY(windowHeight - colorPicker.getHeight() - margin);

        tools.setLayoutX(margin);
        tools.setLayoutY(windowHeight - tools.getHeight() - margin);

        layers.setLayoutX(windowWidth - 250 - margin);
        layers.setLayoutY(margin + menuBar.getHeight());
        layers.setPrefHeight(windowHeight - addLayer.getHeight() - menuBar.getHeight() - margin * 3);

        size.setLayoutX(margin * 3 + tools.getWidth() + colorPicker.getWidth());
        size.setLayoutY(windowHeight - size.getHeight() - margin);

        cursorInfo.setLayoutX(margin * 4 + tools.getWidth() + colorPicker.getWidth() + size.getWidth());
        cursorInfo.setLayoutY(windowHeight - size.getHeight() - margin + 5);

        showColor.setLayoutX(margin * 5 + cursorInfo.getWidth() + tools.getWidth() + colorPicker.getWidth() + size.getWidth());
        showColor.setLayoutY(windowHeight - size.getHeight() - margin + 8);
    }

    public void setShowColor(MouseEvent e)
    {
        cursorInfo.setText("X: " + (int) e.getX() + " Y: " + (int) e.getY());
        Color color = canvas.getLayers().get(getLayer()).getColor((int) e.getX(), (int) e.getY());

        PixelWriter pixelWriter = showColorImage.getPixelWriter();

        if(color == null)
        {
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    if (i == j || i == -j + 9)
                    {
                        pixelWriter.setColor(i, j, Color.RED);
                    } else
                    {
                        pixelWriter.setColor(i, j, Color.WHITE);
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    pixelWriter.setColor(i, j, color);
                }
            }
        }
    }

    public void setPaintColor(Color color)
    {
        if(color != null)
        {
            colorPicker.valueProperty().setValue(color);
        }
    }

    public void createBindings()
    {
        stage.getScene().setOnKeyPressed(e->
        {
            canvas.getLayers().get(getLayer()).shiftLayer(e, e.isShiftDown());
            canvas.updateCanvas(0, 0, canvas.getWidth(), canvas.getHeight(), true);
        });
    }

    private void createLayer()
    {
        canvas.getLayers().add(new Layer(canvas.getWidth(), canvas.getHeight(), canvas, "Layer"+(canvas.getLayers().size() + 1)));
        layers.setItems(FXCollections.observableArrayList(canvas.getLayers()));
    }

    public void setLayerName(String name, int index)
    {
        canvas.getLayers().get(index).setName(name);
        layers.setItems(null);
        layers.setItems(FXCollections.observableArrayList(canvas.getLayers()));
        layers.getSelectionModel().select(index);
    }

    public void setPointer(int x, int y)
    {
        pointer.setLayoutX(canvas.getLayoutX() + x);
        pointer.setLayoutY(canvas.getLayoutY() + y);
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

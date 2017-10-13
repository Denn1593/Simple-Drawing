package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Dennis on 20-06-2017.
 */
public class LayerWindow extends Stage
{
    private Label label = new Label("Name:");
    private TextField nameField = new TextField();
    private Button accept = new Button("Accept");
    private Button cancel = new Button("Cancel");

    public LayerWindow(int index, Window window, String layername)
    {
        Pane pane = new Pane();
        setScene(new Scene(pane, 230, 100));
        setAlwaysOnTop(true);

        label.setLayoutX(20);
        label.setLayoutY(20);

        nameField.setLayoutX(70);
        nameField.setLayoutY(20);
        nameField.setPrefWidth(140);
        nameField.setText(layername);
        nameField.selectAll();

        accept.setLayoutX(35);
        accept.setLayoutY(60);
        accept.setPrefWidth(75);

        cancel.setLayoutX(120);
        cancel.setLayoutY(60);
        cancel.setPrefWidth(75);

        cancel.setOnAction(e -> close());

        nameField.setOnKeyPressed(e->
        {
            if(e.getCode() == KeyCode.ENTER)
            {
                close();
                window.setLayerName(nameField.getText(), index);
            }
        });

        accept.setOnAction(e->
        {
            close();
            window.setLayerName(nameField.getText(), index);
        });
        pane.getChildren().addAll(label, nameField, accept, cancel);
    }
}

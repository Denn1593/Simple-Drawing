package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Dennis on 07-11-2016.
 */
public class NewPictureWindow extends Stage
{
    private Label labelX = new Label("Width:");
    private Label labelY = new Label("Height:");

    private TextField x = new TextField();
    private TextField y = new TextField();

    private Button accept = new Button("Accept");
    private Button cancel = new Button("Cancel");

    private Window window;

    public NewPictureWindow(Window window)
    {
        this.window = window;
        this.setTitle("Enter Size");
        x.setLayoutX(80);
        x.setLayoutY(20);
        x.setPrefWidth(60);

        y.setLayoutX(80);
        y.setLayoutY(60);
        y.setPrefWidth(60);

        labelX.setLayoutX(20);
        labelX.setLayoutY(20);

        labelY.setLayoutX(20);
        labelY.setLayoutY(60);

        accept.setLayoutX(20);
        accept.setLayoutY(100);
        accept.setPrefWidth(75);

        cancel.setLayoutX(105);
        cancel.setLayoutY(100);
        cancel.setPrefWidth(75);

        Pane pane = new Pane();

        accept.setOnAction(e->newPicture());

        cancel.setOnAction(e->this.close());

        pane.getChildren().addAll(x, y, labelX, labelY, accept, cancel);

        this.setScene(new Scene(pane, 200, 150));
    }

    private void newPicture()
    {
        int X = 0;
        int Y = 0;

        boolean valid = true;

        try
        {
            X = Integer.parseInt(x.getText());
            x.setEffect(null);
        }
        catch (Exception e)
        {
            x.setEffect(new DropShadow(5, Color.RED));
            valid = false;
        }
        try
        {
            Y = Integer.parseInt(y.getText());
            y.setEffect(null);
        }
        catch (Exception e)
        {
            y.setEffect(new DropShadow(5, Color.RED));
            valid = false;
        }

        if(valid)
        {
            window.newPicture(X, Y);
            this.close();
        }
    }
}

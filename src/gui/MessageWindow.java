package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by dennis on 11/28/16.
 */
public class MessageWindow extends Stage
{
    private Label message;
    private Button button;
    private ImageView imageView;

    public MessageWindow(String title, String messageString, Image image)
    {
        message = new Label(messageString);
        message.setTextAlignment(TextAlignment.CENTER);
        button = new Button("Ok");

        Pane pane = new Pane();

        VBox vbox = new VBox();
        vbox.getChildren().addAll(message);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMargin(message, new Insets(10, 10, 10, 10));
        vbox.setMargin(button, new Insets(0, 0, 10, 0));

        if(image != null)
        {
            imageView = new ImageView(image);
            vbox.getChildren().addAll(imageView);
            vbox.setMargin(imageView, new Insets(10, 10, 10, 10));
        }

        vbox.getChildren().addAll(button);

        pane.getChildren().addAll(vbox);

        this.setScene(new Scene(pane));
        this.setTitle(title);
        this.initModality(Modality.APPLICATION_MODAL);
        this.show();

        button.setOnAction(e-> this.close());
    }
}

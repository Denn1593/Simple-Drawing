package GUI;

import files.ImageExporter;
import javafx.scene.Scene;
import javafx.stage.Stage;
import painting.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Created by dennis on 11/8/16.
 */
public class SavePictureWindow extends Stage
{
    private TextField name;
    private Button save;
    private Button export;

    public SavePictureWindow(Canvas canvas)
    {
        name = new TextField();

        save = new Button("Save");
        save.setOnAction(e->
        {

        });

        export = new Button("export to png");
        export.setOnAction(e->
        {
            if(ImageExporter.exportImage(name.getText(), canvas.getImage()))
            {
                this.close();
            }
        });

        Pane pane = new Pane();
        pane.getChildren().addAll(name, save, export);

        this.setScene(new Scene(pane));
    }

    public void fixLayout()
    {
        name.setLayoutX(20);
        name.setLayoutY(20);

        save.setLayoutX(name.getWidth() + 40);
        save.setLayoutY(20);

        export.setLayoutX(name.getWidth() + save.getWidth() + 60);
        export.setLayoutY(20);

        this.setWidth(80 + name.getWidth() + save.getWidth() + export.getWidth());
        this.setHeight(75 + name.getHeight());
    }
}

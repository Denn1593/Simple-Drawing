import gui.MessageWindow;
import gui.Window;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by dennis on 11/7/16.
 */
public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setScene(new Scene(new Window(400, 400, primaryStage)));
        primaryStage.show();

        MessageWindow messageWindow = new MessageWindow("Welcome", "Welcome to Spr4yT00lsens\nart creation tool.", new Image("splash.png"));

    }
}

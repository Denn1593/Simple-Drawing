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
        Window window = new Window(640, 480, primaryStage);
        primaryStage.setScene(new Scene(window));
        primaryStage.widthProperty().addListener(e-> window.updateLayout((int) (primaryStage.getWidth() - primaryStage.getScene().getX() * 2), (int) (primaryStage.getHeight() - primaryStage.getScene().getY())));
        primaryStage.heightProperty().addListener(e-> window.updateLayout((int) (primaryStage.getWidth() - primaryStage.getScene().getX() * 2), (int) (primaryStage.getHeight() - primaryStage.getScene().getY())));
        primaryStage.getIcons().add(new Image("splash.png"));
        window.createBindings();
        primaryStage.show();
        window.updateLayout((int) (primaryStage.getWidth() - primaryStage.getScene().getX() * 2), (int) (primaryStage.getHeight() - primaryStage.getScene().getY()));

        //MessageWindow messageWindow = new MessageWindow("Welcome", "Welcome to Spr4yT00lsens\nart creation tool.", new Image("splash.png"));

    }
}

package gui;

import effects.Ripples;
import effects.Warp;
import effects.base.LayerEffect;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Dennis on 29-06-2017.
 */
public class EffectWindow extends Stage
{
    public EffectWindow(LayerEffect effect, Window window)
    {
        setTitle(effect.getName());
        setAlwaysOnTop(true);

        VBox vBox = new VBox();

        for (int i = 0; i < effect.getAmountOfParameters(); i++)
        {
            Label label = new Label(effect.getParameter(i).getName());
            Slider slider = new Slider(effect.getParameter(i).getLower(), effect.getParameter(i).getUpper(), effect.getParameter(i).getLower());
            Label value = new Label("" + effect.getParameter(i).getLower());

            final int index = i;

            slider.valueProperty().addListener(e->
                    {
                        value.setText(""+slider.getValue());
                        effect.getParameter(index).setValue(slider.getValue());

                        if(effect.usesCursor())
                        {
                            window.setPointer((int) effect.getParameter(0).getValue() - 1, (int) effect.getParameter(1).getValue() - 1);
                        }
                    });

            vBox.getChildren().addAll(label, slider, value);
        }

        HBox hBox = new HBox();

        Button apply = new Button("Apply effect");
        apply.setOnAction(e->
        {
            window.setPointer(-100, -100);

            if(window.applyEffect(effect))
            {
                close();
            }
        });

        setOnCloseRequest(e-> window.setPointer(-100, -100));

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e->
        {
            window.setPointer(-100, -100);
            close();
        });

        hBox.getChildren().addAll(apply, cancel);
        vBox.getChildren().addAll(hBox);

        for (int i = 0; i < vBox.getChildren().size(); i++)
        {
            VBox.setMargin(vBox.getChildren().get(i), new Insets(2, 10, 2, 10));
        }

        HBox.setMargin(cancel, new Insets(2, 10, 2, 10));
        HBox.setMargin(apply, new Insets(2, 10, 10, 10));

        setScene(new Scene(vBox));

        show();
    }
}

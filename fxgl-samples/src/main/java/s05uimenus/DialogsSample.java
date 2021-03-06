/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package s05uimenus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shows all FXGL dialogs.
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class DialogsSample extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("DialogsSample");
        settings.setVersion("0.1");






    }

    @Override
    protected void initUI() {
        Map<String, Runnable> dialogs = new LinkedHashMap<>();
        dialogs.put("Message", () -> getDisplay().showMessageBox("This is a simple message box"));
        dialogs.put("Error", () -> getDisplay().showErrorBox("This is a scary error box!", () -> {}));
        dialogs.put("Confirmation", () -> getDisplay().showConfirmationBox("This is a confirmation box. Agree?", answer -> System.out.println("You pressed yes? " + answer)));
        dialogs.put("Input", () -> getDisplay().showInputBox("This is an input box. You can type stuff...", answer -> System.out.println("You typed: "+ answer)));
        dialogs.put("Custom", () -> {
            VBox content = new VBox(
                    getUIFactory().newText("Line 1"),
                    getUIFactory().newText("Line 2"),
                    getAssetLoader().loadTexture("brick.png"),
                    getUIFactory().newText("Line 3"),
                    getUIFactory().newText("Line 4")
            );

            Button btnClose = getUIFactory().newButton("Press me to close");
            btnClose.setPrefWidth(300);

            getDisplay().showBox("This is a customizable box", content, btnClose);
        });

        ChoiceBox<String> cbDialogs = getUIFactory().newChoiceBox(FXCollections.observableArrayList(dialogs.keySet()));

        cbDialogs.getSelectionModel().selectFirst();

        Button btn = getUIFactory().newButton("Open");
        btn.setOnAction(e -> {
            String dialogType = cbDialogs.getSelectionModel().getSelectedItem();
            if (dialogs.containsKey(dialogType)) {
                dialogs.get(dialogType).run();
            } else {
                System.out.println("Unknown dialog type");
            }
        });

        VBox vbox = new VBox(10);
        vbox.setTranslateX(600);
        vbox.getChildren().addAll(
                new Text("Dialog Types"),
                cbDialogs,
                btn
        );

        getGameScene().addUINode(vbox);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

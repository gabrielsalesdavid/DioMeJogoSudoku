import br.com.ui.custom.panel.MainPanel;
import br.com.ui.custom.frame.MainFrame;
import br.com.ui.custom.screen.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UiMain {

    public static void main(String[] args) {

        final Map<String, String> gameConfig = Stream.of(args)
                .collect(toMap(k -> k.split(";")[0],
                        v -> v.split(";")[1]));

        MainScreen mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();
    }
}
package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.view.PlayerView;
import it.polimi.ingsw.view.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.util.Comparator;
import java.util.List;

public class FinalSceneController {
    @FXML
    private Label Player1;
    @FXML
    private Label Player2;
    @FXML
    private Label Player3;
    @FXML
    private Label Player4;
    @FXML
    private Label Points1;
    @FXML
    private Label Points2;
    @FXML
    private Label Points3;
    @FXML
    private Label Points4;
    private GraphicalUI mainGraphicalUI;
    private Scene scene;

    public void setMainGui(GraphicalUI graphicalUI) {
        this.mainGraphicalUI = graphicalUI;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void showResult(List<PlayerView> players) {
        for (int i = 0; i < 4; i++) {
            String labelPlayerName = "#Player" + (i + 1);
            String labelPlayerPoints = "#Points" + (i + 1);

            Label labelName = (Label) scene.lookup(labelPlayerName);
            Label labelPoints = (Label) scene.lookup(labelPlayerPoints);

            labelName.setText(" ");
            labelPoints.setText(" ");
        }

        List<PlayerView> scorePlayer = players.stream().sorted(Comparator.comparingInt(PlayerView::score).reversed()).toList();

        for (int i = 0; i < players.size(); i++) {
            String labelPlayerName = "#Player" + (i + 1);
            String labelPlayerPoints = "#Points" + (i + 1);

            Label labelName = (Label) scene.lookup(labelPlayerName);
            Label labelPoints = (Label) scene.lookup(labelPlayerPoints);

            labelName.setText(scorePlayer.get(i).getNickname());
            labelPoints.setText(String.valueOf(scorePlayer.get(i).score()));
        }
    }
}

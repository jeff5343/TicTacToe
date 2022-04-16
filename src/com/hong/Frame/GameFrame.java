package com.hong.Frame;

import com.hong.Constants;
import com.hong.Enums.SpotState;
import com.hong.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {

    private final int width = 400;
    private final int height = 400;

    private GameBoard gameBoard;
    private SpotButton[][] spotButtons = new SpotButton[3][3];

    public GameFrame(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Tic Tac Toe");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(3,3,20,20));

        setUpSpotButtons();

        this.setVisible(true);

    }

    public void disableButtons() {
        for(JButton[] jBS: spotButtons) {
            for(JButton jB : jBS) {
                jB.setEnabled(false);
            }
        }
    }

    public void changeButtonTexts(SpotState[][] spotStates) {
        for(int i=0; i<spotButtons.length; i++) {
            for(int j=0; j<spotButtons[i].length; j++) {
                spotButtons[i][j].setText(spotStates[i][j].value);
            }
        }
    }

    private void setUpSpotButtons() {
        for(int i=0; i<spotButtons.length; i++) {
            for(int j=0; j<spotButtons[i].length; j++) {
                spotButtons[i][j] = new SpotButton();
                spotButtons[i][j].addActionListener(this);
                this.add(spotButtons[i][j]);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0; i<spotButtons.length; i++) {
            for(int j=0; j<spotButtons[i].length; j++) {
                if(e.getSource()==spotButtons[i][j]) {
                    if(gameBoard.getSpotOccupied(i,j)) {
                        return;
                    }
                    gameBoard.changeSpot(i,j,Constants.playerSpot);
                    spotButtons[i][j].setText(Constants.playerSpot.value);
                    FrameGameController.frameGameController.startNextTurn();
                }
            }
        }
    }
}

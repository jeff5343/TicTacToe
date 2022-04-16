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
    private final int height = 450;

    private GameBoard gameBoard;
    private JPanel ticTacToePanel;
    private JPanel labelPanel;
    private SpotButton[][] spotButtons = new SpotButton[Constants.widthAndHeight][Constants.widthAndHeight];
    private JLabel label;

    public GameFrame(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Tic Tac Toe");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        setUpTicTacToePanel();
        setUpLabelPanel();

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

    public void changeBottomLabel(String text) {
        label.setText(text);
    }

    private void setUpTicTacToePanel() {
        ticTacToePanel = new JPanel();
        ticTacToePanel.setLayout(new GridLayout(Constants.widthAndHeight,Constants.widthAndHeight,20,20));

        for(int i=0; i<spotButtons.length; i++) {
            for(int j=0; j<spotButtons[i].length; j++) {
                spotButtons[i][j] = new SpotButton();
                spotButtons[i][j].addActionListener(this);
                ticTacToePanel.add(spotButtons[i][j]);
            }
        }

        this.add(ticTacToePanel, BorderLayout.CENTER);
    }

    private void setUpLabelPanel() {
        labelPanel = new JPanel();
        labelPanel.setPreferredSize(new Dimension(100, 50));
        
        label = new JLabel(" ");
        label.setFont(new Font("Console", Font.PLAIN, 15));
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        labelPanel.add(label);

        this.add(labelPanel, BorderLayout.SOUTH);
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

/* Ben Costas
Connect 4 Menu
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Connect4 extends JFrame implements ActionListener{
    //Create Panels
    private JPanel introductionPanel = new JPanel();
    private JPanel instructionsPanel = new JPanel();
    private JPanel pvpPanel = new JPanel();
    private JPanel pvcPanel = new JPanel();

    //Create Labels
    private JLabel introToConnect4 = new JLabel("Welcome to Connect 4! Click the buttons below to progress", JLabel.CENTER);

    //Create buttons
    private JButton instructionsButton = new JButton("Instructions");
    private JButton pvpButton = new JButton("Player vs. Player");
    private JButton pvcButton = new JButton("Player vs. Computer");

    public Connect4() { // This class will simply display the menu of the game
        //Create frame:
        setTitle("Connect 4");
        setSize(500,500);
        setResizable(false); //Cannot readjust window size
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //Create Layouts
        BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        FlowLayout flowLayout = new FlowLayout();
        //Set the frame and panels to box layout
        setLayout(boxLayout);

        introductionPanel.setLayout(flowLayout);
        pvpPanel.setLayout(flowLayout);
        pvcPanel.setLayout(flowLayout);
        
        //Add respective components to the panels
        introductionPanel.add(introToConnect4);
        instructionsPanel.add(instructionsButton);
        pvpPanel.add(pvpButton);
        pvcPanel.add(pvcButton);

        //Add action listener for buttons
        instructionsButton.addActionListener(this);
        pvpButton.addActionListener(this);
        pvcButton.addActionListener(this);
        
        //Add the panels to the frame
        add(introductionPanel);
        add(instructionsPanel);
        add(pvpPanel);
        add(pvcPanel);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command.equals("Instructions")) {
            InstructionsConnect4 instructionsConnect4 = new InstructionsConnect4();
            dispose();
        }

        else if(command.equals("Player vs. Player")) {
            GameConnect4 gameConnect41 = new GameConnect4(1);
            dispose();
        }
        
        else if(command.equals("Player vs. Computer")) {
            GameConnect4 gameConnect42 = new GameConnect4(2);
        }
    }
    public static void main(String[] args) {
        Connect4 connect4 = new Connect4();
    }
}
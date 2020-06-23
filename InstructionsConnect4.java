/*Ben Costas
Connect 4 Instructions
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InstructionsConnect4 extends JFrame implements ActionListener{
    //Create Panels
    private JPanel instructionsPanel = new JPanel();
    private JPanel exitPanel = new JPanel();

    //Create Label
    private JLabel howToPlay = new JLabel("Connect 4 of your squares horizontally, vertically, or diagonally before your opponent can to win!", JLabel.CENTER);

    //Create Button
    private JButton exitButton = new JButton("Go back to Main Menu");

    public InstructionsConnect4() {
        setTitle("Instructions");
        setSize(600,200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //Set Layouts
        BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        FlowLayout flowLayout = new FlowLayout();
        //Set frame layout
        setLayout(boxLayout);
        //Set panel layouts
        instructionsPanel.setLayout(flowLayout);
        exitPanel.setLayout(flowLayout);
        //Add components to panels
        instructionsPanel.add(howToPlay);
        exitPanel.add(exitButton);

        exitButton.addActionListener(this);
        //Add panels to frame
        add(instructionsPanel);
        add(exitPanel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if(command.equals("Go back to Main Menu")) {
            Connect4 connect4 = new Connect4();//Go back to menu
            dispose();
        }
    }
}
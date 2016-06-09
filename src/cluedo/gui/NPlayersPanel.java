package cluedo.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cluedo.main.GUIClient;

/**
 *Used at the start of a game so the user can pick how many players
 */
public class NPlayersPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JLabel howManyPlayers;
	private int nPlayers;
	private GUIClient client;
	
	public NPlayersPanel(GUIClient client){
		super(new BorderLayout());
		nPlayers = 2;
		this.client = client;
		
		JRadioButton two = new JRadioButton("2");
        two.setActionCommand("two");
        two.setSelected(true);
 
        JRadioButton three = new JRadioButton("3");
        three.setActionCommand("three");
        
        JRadioButton four = new JRadioButton("4");
        four.setActionCommand("four");
        
        JRadioButton five = new JRadioButton("5");
        five.setActionCommand("five");
        
        JRadioButton six = new JRadioButton("6");
        six.setActionCommand("six");
	
        //Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(two);
        group.add(three);
        group.add(four);
        group.add(five);
        group.add(six);
 
        //add listeners to the buttons
        two.addActionListener(this);
        three.addActionListener(this);
        four.addActionListener(this);
        five.addActionListener(this);
        six.addActionListener(this);
        
        howManyPlayers = new JLabel("How many players?");
        ok = new JButton("Start Game");
        ok.addActionListener(this);
        ok.setActionCommand("ok");
        
        JPanel radioPanel = new JPanel(new GridLayout(1, 0));
        radioPanel.add(two);
        radioPanel.add(three);
        radioPanel.add(four);
        radioPanel.add(five);
        radioPanel.add(six);
        radioPanel.setBorder(BorderFactory.createEtchedBorder(1));
        
        add(radioPanel, BorderLayout.CENTER);
        add(ok, BorderLayout.SOUTH);
        add(howManyPlayers, BorderLayout.NORTH);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if("two".equals(e.getActionCommand())){nPlayers = 2;}
		else if("three".equals(e.getActionCommand())){nPlayers = 3;}
		else if("four".equals(e.getActionCommand())){nPlayers = 4;}
		else if("five".equals(e.getActionCommand())){nPlayers = 5;}
		else if("six".equals(e.getActionCommand())){nPlayers = 6;}
		else if("ok".equals(e.getActionCommand())){
			client.setPlayers(nPlayers);
			try {
				client.startGame();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
}


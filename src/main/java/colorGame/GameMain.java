package colorGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Pelin pääluokka mistä peli aloitetaan
 */

public class GameMain {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Colorgame");
		frame.setVisible(true);
		frame.setSize(900,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		JButton button = new JButton("Press this to start the ColorGame");
		  
		button.setBounds(300,100,150,100);	//napin sijainti
		button.setFont(new Font("SANS_SERIF",Font.BOLD,30));
		button.setBackground(new Color(0,51,200));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		  
		panel.add(button);
		button.addActionListener(
				new ActionListener() { //"press this to..." -napin toiminta
					@Override
		        	public void actionPerformed(ActionEvent e) {
						frame.dispose(); //Poistetaan aloitussivu
		        		new GameUI(); //Aloittaa pelin
		        	}
		        }
		  );
		  
		  JLabel otsikko = new JLabel("Instructions");
		  otsikko.setFont(new Font("SANS_SERIF",Font.BOLD,45));
		  panel.add(otsikko);
		  otsikko.setHorizontalTextPosition(JLabel.CENTER);
		  otsikko.setVerticalTextPosition(JLabel.CENTER);
		  
		  JLabel label = new JLabel("<html>The user needs to guess the colors and their exact order<br>" //ohjeteksti
		    		+ "User guesses 4 colors.<br> "
		    		+ "The game shows what colors are in the correct spot and what colors are somewhere in the field."
		    		+ "<br> The game ends when user has made the correct input or runs out of guesses.</html> ");
		  label.setHorizontalTextPosition(JLabel.CENTER);
		  label.setVerticalTextPosition(JLabel.CENTER);
		  panel.add(label); 
	}
}

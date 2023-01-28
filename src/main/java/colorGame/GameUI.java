package colorGame;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.JOptionPane;

/**
 * Luokka vastaa pelin käyttöliittymästä
 */

public class GameUI extends JFrame implements ActionListener {
	
	private int pelikentan_koko = 6; 			// Mahdollisten veikkausten määrä
	private int veikkaus_counter = 0; 			// Kuinka monta väriä veikkausrivillä
	private int vari_rivit = pelikentan_koko-1; // Kuinka monta veikkausta jäljellä
	private int koko = 4;  						// Veikkausten koko
	
	private Shape[][] ympyrat = new Shape[pelikentan_koko][koko];
	private Shape[][] vinkki_pallot = new Shape[pelikentan_koko][koko];
	private Shape[] veikkaus_rivi = new Shape[koko];
	private Shape[] target_rivi = new Shape[koko];
	private Color[] veikkaus_rivi_varit = new Color[koko];
	
	private Graphics2D grafiikka;
	private JPanel panel;
	private JFrame frame = new JFrame();
	private GameLogic peli = new GameLogic();
	private static final long serialVersionUID = 1L;
	
	public GameUI() {
		frame.setSize(900, 800);
		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(3);
		
		peli.generoiVarit();
		veikkaaNappi();
		kumitaNappi();
	    newgameNappi();
	    variNapit();
	    
		JPanel pn = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				
				Graphics2D g2d = (Graphics2D)g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				// Pelialueen pallojen luonti
				for (int i=0; i<pelikentan_koko; i++) {
		        	for (int j=0; j<koko; j++) {
		        		Shape circleShape = new Ellipse2D.Double((j+1)*80, 50+(i+1)*80, 50, 50);
		        		ympyrat[i][j] = circleShape;
		        		g2d.setColor(new Color(0,0,0));
		        		g2d.draw(circleShape);
		        	}
		        }
				
				// Veikausrivin luonti
				for (int i=0; i<koko; i++) {
					Shape circleShape = new Ellipse2D.Double(((i+1)*80)-8, 620, 66, 66);
					veikkaus_rivi[i] = circleShape;
					g2d.draw(circleShape);
				}
				
				// Vastausrivin luonti
				for (int i=0; i<koko; i++) {
					Shape circleShape = new Ellipse2D.Double(((i+1)*80)-8, 30, 66, 66);
					target_rivi[i] = circleShape;
					g2d.setColor(new Color(0,0,0));
					g2d.draw(circleShape);
				}
				grafiikka = g2d;
			
			}
		};
		
		panel = pn;
		frame.add(pn);
		frame.setVisible(true);
	}
	
	/**
	 * Luo napit joita painamalla nappia vastaava väri 
	 * lisätään veikkausriville
	 */
	public void variNapit(){
		int count = 0;
	    	for (int i=0; i<2; i++) {
            	for (int j=0; j<3; j++) {
            		JButton button = new JButton();
        	        button.addActionListener(
        	        	new ActionListener() {
        	        		@Override
        	        	    public void actionPerformed(ActionEvent e) {
        	        	    	if (veikkaus_counter < koko) {
        	        	    		veikkaus_rivi_varit[veikkaus_counter] = button.getBackground();
        	        	    		grafiikka.setColor(button.getBackground());
        	        	    		grafiikka.fill(veikkaus_rivi[veikkaus_counter]);
            	        	    	panel.repaint();
            	        	    	
            	        	    	peli.lisaaVeikkaukseen(button.getBackground());
            	        	    	
            	        	    	veikkaus_counter++;
        	        	    	}
        	        		}
        	        	}
        	        );
        	        button.setBounds(500+(j+1)*80,240+(i+1)*80,50,50);
        	        button.setBackground(peli.getColor(count));
        	        count++;
        	        frame.add(button);
            	}
	    	} 
	}
	
	/**
	 * Luo veikaus napin jota painamalla pelaaja tekee veikkauksen.
	 * Pelin voitto ja häviö tapahtuu täällä.
	 */
	public void veikkaaNappi() {
		JButton veikkaa = new JButton("Guess"); 
		veikkaa.addActionListener(
				new ActionListener() {
	        		@Override
	        	    public void actionPerformed(ActionEvent e) {
	        	    	if (veikkaus_counter == koko && vari_rivit >= 0) {
	        	    		for (int i=0; i<koko; i++) {
	        	    			grafiikka.setColor(veikkaus_rivi_varit[i]);
	        	    			grafiikka.fill(ympyrat[vari_rivit][i]);
	        	    		}
	        	    		resetVeikkausRivi();
	        	    		vinkkiPallot();
    	        	    	
    	        	    	// Voitetaanko peli
    	        	    	if (peli.onkoVeikkausOikein()) {
    	        	    		for (int i=0; i<koko; i++) {
    	        	    			grafiikka.setColor(peli.getTarget().get(i));
    	        	    			grafiikka.fill(target_rivi[i]);
    	        	    		}
    	        	    		panel.repaint();
    	        	    		JOptionPane.showMessageDialog(frame, "You won!", "Victory", JOptionPane.INFORMATION_MESSAGE);
    	        	    		uusiPeli();
    	        	    	}
    	        	    	else {
    	        	    		peli.getVeikkaus().clear();
    	        	    		// Onko peli hävitty
    	        	    		if (vari_rivit==0) {
    	        	    			for (int i=0; i<koko; i++) {
    	        	    				grafiikka.setColor(peli.getTarget().get(i));
    	        	    				grafiikka.fill(target_rivi[i]);
    	        	    			}
    	        	    		}
    	        	    		vari_rivit--;
    	        	    		panel.repaint();
    	        	    	}
	        	    	}
	        	  }
	        	}
	    );
	    veikkaa.setBounds(540,550,150,100);
	    veikkaa.setFont(new Font("Serif",Font.BOLD,30));
	    veikkaa.setBackground(new Color(0,51,10));
	    veikkaa.setForeground(Color.WHITE);
	    veikkaa.setFocusPainted(false);
	    veikkaa.setBorderPainted(false);
	    frame.add(veikkaa);
	}
	
	/**
	 * Luo kumitus napin jota painamalla veikkausriviltä 
	 * poistetaan edellinen väri.
	 */
	public void kumitaNappi() {
		JButton kumita = new JButton("Delete");  
	    kumita.addActionListener(
	        	new ActionListener() {
	        		@Override
	        	    public void actionPerformed(ActionEvent e) {
	        	    	if (veikkaus_counter > 0) {
	        	    		grafiikka.setColor(Color.white);
	        	    		veikkaus_counter--;
	        	    		grafiikka.fill(veikkaus_rivi[veikkaus_counter]);
    	        	    	panel.repaint();
    	        	    	peli.poistaVeikkauksesta();
	        	    	}
	        	  }
	        	}
	        );
	    kumita.setBounds(690,550,150,100);
	    kumita.setFont(new Font("Serif",Font.BOLD,30));
	    kumita.setBackground(new Color(0,51,200));
	    kumita.setForeground(Color.WHITE);
	    kumita.setFocusPainted(false);
	    kumita.setBorderPainted(false);
	    frame.add(kumita);
	}
	
	/**
	 * Luo newgame napin jota painamalla aloitetaan uusi peli
	 */
	public void newgameNappi() {
		JButton new_game = new JButton("New Game");  
	    new_game.addActionListener(
	        	new ActionListener() {
	        		@Override
	        	    public void actionPerformed(ActionEvent e) {
	        			for (int i=0; i<koko; i++) {
	        				grafiikka.setColor(peli.getTarget().get(i));
	        				grafiikka.fill(target_rivi[i]);
        	    		}
	        			uusiPeli();
	        	  }
	        	}
	        );
	    new_game.setBounds(570,100,250,100);
	    new_game.setFont(new Font("Serif",Font.BOLD,30));
	    new_game.setBackground(new Color(60,51,160));
	    new_game.setForeground(Color.WHITE);
	    new_game.setFocusPainted(false);
	    new_game.setBorderPainted(false);
	    
	    frame.add(new_game); 
	}
	 
	/**
	 * Aloittaa uuden pelin
	 */
	public void uusiPeli() {
		peli.uusiPeli();
		
		for (int i=0; i<koko; i++) {
        	for (int j=0; j<pelikentan_koko; j++) {
        		grafiikka.setColor(Color.white);
        		grafiikka.fill(ympyrat[j][i]);
        		if (vinkki_pallot[j][i]!=null) {
        			grafiikka.draw(vinkki_pallot[j][i]);
        			grafiikka.fill(vinkki_pallot[j][i]);
        		}
        	}
        	grafiikka.fill(target_rivi[i]);
        }
		resetVeikkausRivi();
		panel.repaint();
		vari_rivit = pelikentan_koko-1;
	}
	
	/**
	 * Tyhjentää veikkausrivin
	 */
	public void resetVeikkausRivi() {
		grafiikka.setColor(Color.white);
		for (int i=0; i<koko; i++) {
			grafiikka.fill(veikkaus_rivi[i]);
		}
		veikkaus_counter = 0;
	}
	
	/**
	 * Piirtää vinkkipallot
	 */
	public void vinkkiPallot() {
		int oikein = peli.montakoVariaTaysinOikein();
		int puoli_oikein = peli.montakoVariaPuoliOikein();
		
		// Piirtää vihreät pallot jokaiselle oikealle veikkaukselle
		for (int i=0; i<oikein; i++) {
			Shape circleShape = new Ellipse2D.Double(350+(i+1)*30, 65+(vari_rivit+1)*80, 25, 25);
    		vinkki_pallot[vari_rivit][i] = circleShape;
    		grafiikka.setColor(Color.green);
    		grafiikka.fill(circleShape);
		}
		
		// Piirtää keltaiset pallot jokaiselle puoli-oikealle veikkaukselle
		for (int i=oikein; i<oikein+puoli_oikein; i++) {
			Shape circleShape = new Ellipse2D.Double(350+(i+1)*30, 65+(vari_rivit+1)*80, 25, 25);
    		vinkki_pallot[vari_rivit][i] = circleShape;
    		grafiikka.setColor(Color.yellow);
    		grafiikka.fill(circleShape);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
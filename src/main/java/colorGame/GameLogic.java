package colorGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random; 

/**
 * Luokka vastaa pelin logiikasta
 */

public class GameLogic {
	
	List<Color> target_varit = new ArrayList<Color>();
	List<Color> pelaajan_veikkaus = new ArrayList<Color>();
	
	private Color[] colors = {Color.red, Color.blue, Color.green, Color.yellow, Color.MAGENTA, 
			Color.orange}; // Pelin käyttämät värit
	private int koko = 4;  // Veikkausten koko
	
	/**
	 * Tyhjentää target ja veikkaus listat ja 
	 * generoi uudet target listan värit
	 */
	public void uusiPeli() {
		target_varit.clear();
		pelaajan_veikkaus.clear();
		generoiVarit();
	}
	
	/**
	 * Generoi satunnaiset target värit 
	 */
	public void generoiVarit() {
		Random rand = new Random();  
		
		for (int i = 0; i<koko; i++) {
			target_varit.add(colors[rand.nextInt(6)]);
		}
	}
	
	/**
	 * Lisää parametrinä annetun värin pelaajan veikkaukseen
	 * @param vari	lisätty väri
	 */
	public void lisaaVeikkaukseen(Color vari) {
		if (pelaajan_veikkaus.size() < koko) {
			pelaajan_veikkaus.add(vari);
		}
	}
	
	/**
	 * Poistaa viimeksi lisätyn värin pelaajan veikkauksesta
	 */
	public void poistaVeikkauksesta() {
		int veik_koko = pelaajan_veikkaus.size();
		if (veik_koko > 0) {
			pelaajan_veikkaus.remove(veik_koko-1);
		}
	}
	
	/**
	 * Testaa onko veikkaus ja target samat
	 * @return true jos veikkaus on oikein
	 */
	public boolean onkoVeikkausOikein() {
		boolean oikein = false;
		
		if (montakoVariaTaysinOikein() == koko) {
			oikein = true;
		}
		return oikein;
	}
	
	/**
	 * Katsoo montako väriä on oikein ja oikealla paikalla
	 * @return kuinka monta väriä oikealla paikalla
	 */
	public int montakoVariaTaysinOikein() {
		int count = 0;
		
		for (int i=0; i<koko; i++) {
			if (pelaajan_veikkaus.get(i) == target_varit.get(i)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Katsoo montako väriä on oikein, mutta väärällä paikalla
	 * @return kuinka monta oikeaa väriä väärällä paikalla 
	 */
	public int montakoVariaPuoliOikein() {
		int count = 0;
		List<Color> vastaus_varit = new ArrayList<Color>();
		List<Color> veik_varit = new ArrayList<Color>();
		
		// Lisätään vastaus_varit ja veik_varit listoihin target_varit ja pelaajan_veikaus listojen alkiot,
		// paitsi jos ne ovat samat, milloin lisätään valkoiset alkiot
		for (int i=0; i<koko; i++) {
			if (pelaajan_veikkaus.get(i) == target_varit.get(i)) {
				vastaus_varit.add(Color.white);
				veik_varit.add(Color.white);
			}
			else {
				vastaus_varit.add(target_varit.get(i));
				veik_varit.add(pelaajan_veikkaus.get(i));
			}
		}
		
		// Jos väri ei ole valkoinen, etsitään puolioikeita värejä.
		// Kun puolioikea alkio löydetään, muutetaan se valkoiseksi, jotta sitä ei uudestaan laskettaisi
		for (int i=0; i<koko; i++) {
			if (vastaus_varit.contains(veik_varit.get(i)) && veik_varit.get(i) != Color.white) {
				for (int j=0; j<koko; j++) {
					if (veik_varit.get(i) == vastaus_varit.get(j)) {
						vastaus_varit.set(j, Color.white);
						j = koko;
					}
				}
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Palauttaa oikeiden veikkausten määrät ja tyhjentää listan pelaajan_veikkaus
	 * @return List<Integer> ensimmäinen alkio on täysin oikeat veikkaukset
	 * toinen alkio on puoli oikeat veikkaukset
	 */
	public List<Integer> teeVeikkaus() {
		List<Integer> oikeat_varit = new ArrayList<Integer>();
		oikeat_varit.add(montakoVariaTaysinOikein());
		oikeat_varit.add(montakoVariaPuoliOikein());
		pelaajan_veikkaus.clear();
		return oikeat_varit;
	}
	
	/**
	 * Palauttaa pelaajan_veikkaus.
	 * @return pelaajan_veikkaus
	 */
	public List<Color> getVeikkaus() {
		return pelaajan_veikkaus;
	}
	
	/**
	 * Palauttaa target_varit.
	 * @return target_varit
	 */
	public List<Color> getTarget() {
		return target_varit;
	}
	
	/**
	 * Palauttaa listan colors parametrina annetun alkion.
	 * @param vari on listan colors haluttu alkio
	 * @return Color listan colors kohdassa vari
	 */
	public Color getColor(int vari) {
		return colors[vari];
	}
	
}
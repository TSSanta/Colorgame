package colorGame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import net.jqwik.api.*;

public class GameTests {
	
	@Test
	void veikkauksenVaritTesti() {
		GameLogic peli = new GameLogic();
		
		peli.lisaaVeikkaukseen(Color.blue);
		assertEquals(peli.getVeikkaus().get(0), Color.blue);
		

		peli.lisaaVeikkaukseen(Color.red);
		assertEquals(peli.getVeikkaus().get(1), Color.red);
		
	}
	
	@Property
	boolean veikkausJaTargetSama() {
		GameLogic peli = new GameLogic();
		
		peli.lisaaVeikkaukseen(Color.red);
		peli.lisaaVeikkaukseen(Color.blue);
		peli.lisaaVeikkaukseen(Color.pink);
		peli.lisaaVeikkaukseen(Color.green);
		
		peli.getTarget().add(Color.red);
		peli.getTarget().add(Color.blue);
		peli.getTarget().add(Color.pink);
		peli.getTarget().add(Color.green);
		
		return peli.onkoVeikkausOikein();
		
	}
	
	@Test
	void veikkausJaTargetVertailu() {
		GameLogic peli = new GameLogic();
		
		peli.lisaaVeikkaukseen(Color.red);
		peli.lisaaVeikkaukseen(Color.blue);
		peli.lisaaVeikkaukseen(Color.pink);
		peli.lisaaVeikkaukseen(Color.green);
		
		peli.getTarget().add(Color.red);
		peli.getTarget().add(Color.pink);
		peli.getTarget().add(Color.green);
		peli.getTarget().add(Color.magenta);
		
		assertEquals(peli.montakoVariaTaysinOikein(), 1);
		assertEquals(peli.montakoVariaPuoliOikein(), 2);
		
	}
	

}

package it.localpc.stringModify;

import static org.junit.Assert.*;

import org.junit.Test;

import it.localpc.stringModify.service.Modifier;

public class ModifierTest {
	
	@Test
	public void testTextModify(){
//		String defaultStr = "cacao and coffee";
		String defaultStr = "the table";
		String newStr;
		
		newStr = Modifier.textModify(defaultStr);
		
//		assertEquals("kakao and kofi", newStr);
		assertEquals("tabl", newStr);
	}
	
	@Test
	public void testRemoveSymbol(){
		
		assertEquals("suksess suksess suksess", Modifier.changeSymbol("suCCess success sukCess"));
		
	}
	
	@Test
	public void testRemoveDouble(){
		
		assertEquals("uo", Modifier.removeDouble("ooooo"));
		assertEquals("u", Modifier.removeDouble("oou"));
		assertEquals("i", Modifier.removeDouble("iee"));
	}
	
	@Test
	public void testRemoveSymbolEnd(){
		
		assertEquals("Th", Modifier.removeSymbolEndWord("The"));
	}
	
}

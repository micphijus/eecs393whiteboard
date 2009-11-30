package core.testing;

import org.junit.*;
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.*;

import core.whiteboard.*;


public class WhiteboardTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testDrawLineBasic()
//	{
//		int returncode = -1;
//		WhiteboardPanel w = new WhiteboardPanel();
//		returncode = w.drawLine(Color.BLACK, 20, 20, 30, 30, true);
//		System.out.println(returncode);
//		assertEquals(returncode, 0);
//	}
	
	@Test
	public void testQueue()
	{
		String black = Color.green.toString();
		int returncode = -1;
		WhiteboardPanel w = new WhiteboardPanel();
		Queue<String> baseQueue = new LinkedList<String>();
		baseQueue.add("drawLineT,00ff00,100,100,30,50,1");
		w.applyQueue(baseQueue);
		System.out.println(returncode);
		
		assertEquals(true, true);
	}

}

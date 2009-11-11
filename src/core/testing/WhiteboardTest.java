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

	@Test
	public void testDrawCircleBasic()
	{
		int returncode = -1;
		WhiteboardWindow w = new WhiteboardWindow();
		returncode = w.drawCircle(Color.BLACK, 20, 20, 5);
		System.out.println(returncode);
		assertEquals(returncode, 0);
	}
	
	@Test
	public void testDrawCircleCoordsOOB()
	{
		int returncode = -1;
		WhiteboardWindow w = new WhiteboardWindow();
		returncode = w.drawCircle(Color.BLACK, -1, 20, 5);
		System.out.println(returncode);
		assertEquals(returncode, 1);
	}
	@Test
	public void testDrawCircleRadiusOOB()
	{
		int returncode = -1;
		WhiteboardWindow w = new WhiteboardWindow();
		returncode = w.drawCircle(Color.BLACK, 20, 20, -2);
		System.out.println(returncode);
		assertEquals(returncode, 2);
	}
}

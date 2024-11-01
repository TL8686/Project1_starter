import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.awt.event.*;
import org.junit.jupiter.api.*;

/**
 * Contains a set of unit tests for the Piano class.
 */
class PianoTester {
	private TestReceiver _receiver;
	private Piano _piano;
	private PianoMouseListener _mouseListener;

	private MouseEvent makeMouseEvent (int x, int y) {
		return new MouseEvent(_piano, 0, 0, 0, x, y, 0, false);
	}

	@BeforeEach
	void setup () {
		// A new TestReceiver will be created before running *each*
		// test. Hence, the "turn on" and "turn off" counts will be
		// reset to 0 before *each* test.
		_receiver = new TestReceiver();
		_piano = new Piano(_receiver);
		_mouseListener = _piano.getMouseListener();
	}

	@Test
	void testClickUpperLeftMostPixel () {
		// Pressing the mouse should cause the key to turn on.
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
	}

	@Test
	void testClickBoundry () {
		// Pressing the boundry between two keys should only turn on one of them
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH-Piano.BLACK_KEY_WIDTH/2, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH) ^ _receiver.isKeyOn(Piano.START_PITCH+1));
	}

	@Test
	void KeyBounds () {
		// corner of left white keys turns it on
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
		_mouseListener.mouseReleased(makeMouseEvent(0, 0));
		// corner of black key turns in on
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH - Piano.BLACK_KEY_WIDTH/2, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH+1));
		_mouseListener.mouseReleased(makeMouseEvent(Piano.WHITE_KEY_WIDTH - Piano.BLACK_KEY_WIDTH/2, 0));
		// corner of middle white key turns it on
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH, Piano.BLACK_KEY_HEIGHT));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH+2));
		_mouseListener.mouseReleased(makeMouseEvent(Piano.WHITE_KEY_WIDTH, Piano.BLACK_KEY_HEIGHT));
		// corner of right white key turns it on
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH*2, Piano.BLACK_KEY_HEIGHT));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH+4));
		_mouseListener.mouseReleased(makeMouseEvent(Piano.WHITE_KEY_WIDTH*2, Piano.BLACK_KEY_HEIGHT));
	}


	@Test
	void testDragWithinKey () {
		// Test that pressing and dragging the mouse *within* the same key
		// should cause the key to be turned on only once, not multiple times.
		// Use makeMouseEvent and TestReceiver.getKeyOnCount.
		// TODO complete me

		// click to turn the first key on
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		// check that the key was turned on
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
		// drag the mouse within the key
		_mouseListener.mouseDragged(makeMouseEvent(10, 10));
		// check that the key only turned on once
		assertTrue(1==_receiver.getKeyOnCount(Piano.START_PITCH));

		
		// click to turn the first key on
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH, 0));
		// check that the key was turned on
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH + 1));
		// drag the mouse within the key
		_mouseListener.mouseDragged(makeMouseEvent(Piano.WHITE_KEY_WIDTH+ 10, 10));
		// check that the key only turned on once
		assertTrue(1==_receiver.getKeyOnCount(Piano.START_PITCH + 1));
	}

	@Test
	void testMousePressAndRelease () {
		// Pressing the mouse should cause the key to turn off
		// test with the first white key
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
		_mouseListener.mouseReleased(makeMouseEvent(0, 0));
		assertTrue(! _receiver.isKeyOn(Piano.START_PITCH));
		// test with the first black key
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH + 1));
		_mouseListener.mouseReleased(makeMouseEvent(Piano.WHITE_KEY_WIDTH, 0));
		assertTrue(! _receiver.isKeyOn(Piano.START_PITCH + 1));
	}
}

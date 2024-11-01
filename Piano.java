import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.sound.midi.*;

/**
 * Implements a simulated piano with 36 keys.
 */
public class Piano extends JPanel {
	// DO NOT MODIFY THESE CONSTANTS
	public static int START_PITCH = 48;
	public static int WHITE_KEY_WIDTH = 40;
	public static int BLACK_KEY_WIDTH = WHITE_KEY_WIDTH/2;
	public static int WHITE_KEY_HEIGHT = 200;
	public static int BLACK_KEY_HEIGHT = WHITE_KEY_HEIGHT/2;
	public static int NUM_WHITE_KEYS_PER_OCTAVE = 7;
	public static int NUM_OCTAVES = 3;
	public static int NUM_WHITE_KEYS = NUM_WHITE_KEYS_PER_OCTAVE * NUM_OCTAVES;
	public static int WIDTH = NUM_WHITE_KEYS * WHITE_KEY_WIDTH;
	public static int HEIGHT = WHITE_KEY_HEIGHT;
		
	private java.util.List<Key> _keys = new ArrayList<>();
	private Receiver _receiver;
	private PianoMouseListener _mouseListener;

	/**
	 * Returns the list of keys in the piano.
	 * @return the list of keys.
	 */
	public java.util.List<Key> getKeys () {
		return _keys;
	}

	/**
	 * Sets the MIDI receiver of the piano to the specified value.
	 * @param receiver the MIDI receiver 
	 */
	public void setReceiver (Receiver receiver) {
		_receiver = receiver;
	}

	/**
	 * Returns the current MIDI receiver of the piano.
	 * @return the current MIDI receiver 
	 */
	public Receiver getReceiver () {
		return _receiver;
	}

	// DO NOT MODIFY THIS METHOD.
	/**
	 * @param receiver the MIDI receiver to use in the piano.
	 */
	public Piano (Receiver receiver) {
		// Some Swing setup stuff; don't worry too much about it.
		setFocusable(true);
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setReceiver(receiver);
		_mouseListener = new PianoMouseListener(_keys);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		makeKeys();
	}

	/**
	 * Returns the PianoMouseListener associated with the piano.
	 * @return the PianoMouseListener associated with the piano.
	 */
	public PianoMouseListener getMouseListener () {
		return _mouseListener;
	}

	// TODO: implement this method. You should create and use several helper methods to do so.
	/**
	 * Instantiate all the Key objects with their correct polygons and pitches, and
	 * add them to the _keys array.
	 */
	private void makeKeys () {
		//black key
		int[] blkXCoords = new int[] {
			WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2,
			WHITE_KEY_WIDTH + BLACK_KEY_WIDTH/2,
			WHITE_KEY_WIDTH + BLACK_KEY_WIDTH/2,
			WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2
		};
		int[] blkYCoords = new int[] {
			0,
			0,
			BLACK_KEY_HEIGHT,
			BLACK_KEY_HEIGHT
		};

		// left white key
		int[] leftWXCoords = new int[] {
			0,
			WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2,
			WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2,
			WHITE_KEY_WIDTH,
			WHITE_KEY_WIDTH,
			0
		};
		int[] leftWYCoords = new int[] {
			0,
			0,
			BLACK_KEY_HEIGHT,
			BLACK_KEY_HEIGHT,
			WHITE_KEY_HEIGHT,
			WHITE_KEY_HEIGHT
		};

		// right white key
		int[] rightWXCoords = new int[] {
			0,
			BLACK_KEY_WIDTH/2,
			BLACK_KEY_WIDTH/2,
			WHITE_KEY_WIDTH,
			WHITE_KEY_WIDTH,
			0
		};
		int[] rightWYCoords = new int[] {
			BLACK_KEY_HEIGHT,
			BLACK_KEY_HEIGHT,
			0,
			0,
			WHITE_KEY_HEIGHT,
			WHITE_KEY_HEIGHT
		};

		// middle white key
		int[] middleWXCoords = new int[] {
			0,
			0,
			BLACK_KEY_WIDTH/2,
			BLACK_KEY_WIDTH/2,
			WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2,
			WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2,
			WHITE_KEY_WIDTH,
			WHITE_KEY_WIDTH
		};
		int[] middleWYCoords = new int[] {
			WHITE_KEY_HEIGHT,
			BLACK_KEY_HEIGHT,
			BLACK_KEY_HEIGHT,
			0,
			0,
			BLACK_KEY_HEIGHT,
			BLACK_KEY_HEIGHT,
			WHITE_KEY_HEIGHT
		};
		// left, middle, right

		String[] octave = {"left", "middle", "right", "left", "middle", "middle", "right"};
		int pitch = START_PITCH;

		for (int i = 0; i < NUM_OCTAVES; i++ ){
			for(int z = 0; z < octave.length; z++ ){
				if(octave[z].equals("left")){
					_keys.add(new Key(new Polygon(shiftArray(leftWXCoords, z + i*NUM_WHITE_KEYS_PER_OCTAVE), leftWYCoords, leftWXCoords.length), pitch, this));
					_keys.add(new Key(new Polygon(shiftArray(blkXCoords, z + i*NUM_WHITE_KEYS_PER_OCTAVE), blkYCoords, blkXCoords.length), pitch+1, this));
					pitch = pitch+2;
				}
				if(octave[z].equals("middle")){
					_keys.add(new Key(new Polygon(shiftArray(middleWXCoords, z + i*NUM_WHITE_KEYS_PER_OCTAVE), middleWYCoords, middleWXCoords.length), pitch, this));
					_keys.add(new Key(new Polygon(shiftArray(blkXCoords, z + i*NUM_WHITE_KEYS_PER_OCTAVE), blkYCoords, blkXCoords.length), pitch+1, this));
					pitch = pitch+2;
				}
				if(octave[z].equals("right")){
					_keys.add(new Key(new Polygon(shiftArray(rightWXCoords, z + i*NUM_WHITE_KEYS_PER_OCTAVE), rightWYCoords, rightWXCoords.length), pitch, this));
					pitch = pitch+1;
				}
			}
		}


		
	}

	public int[] shiftArray(int[] inputArray, int key){

		int[] outputArray = new int[inputArray.length];

		for (int i = 0; i < inputArray.length; i++){
			outputArray[i] = inputArray[i] + (key*WHITE_KEY_WIDTH);
		}

		return outputArray;
	}


	// DO NOT MODIFY THIS METHOD.
	@Override
	/**
	 * Paints the piano and all its constituent keys.
	 * @param g the Graphics object to use for painting.
	 */
	public void paint (Graphics g) {
		// Delegates to all the individual keys to draw themselves.
		for (Key key: _keys) {
			key.paint(g);
		}
	}
}

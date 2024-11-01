import javax.swing.*;
import java.awt.event.*;
import javax.sound.midi.*;
import java.util.*;

/**
 * Handles mouse press, release, and drag events on the Piano.
 */
public class PianoMouseListener extends MouseAdapter {
	// You are free to add more instance variables if you wish.
	private List<Key> _keys;

	/**
	 * @param keys the list of keys in the piano.
	 */
	public PianoMouseListener (List<Key> keys) {
		_keys = keys;
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user drags the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseDragged (MouseEvent e) {
		final int x_pos = e.getX();
		final int y_pos = e.getY();
		boolean key_state = false;
		for(Key key: _keys){
			key_state = key.getPolygon().contains(x_pos, y_pos);
			if(key_state && !key.getIsOn()){
				key.play(true);
			}
			if(!key_state && key.getIsOn()){
				key.play(false);
			}
		}
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user presses the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mousePressed (MouseEvent e) {
		final int x_pos = e.getX();
		final int y_pos = e.getY();
		boolean key_state = false;
		for(Key key: _keys){
			key_state = key.getPolygon().contains(x_pos, y_pos);
			if (key_state){
				key.play(key_state);
			}
		}
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user releases the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseReleased (MouseEvent e) {
		// set all the keys to off
		for(Key key: _keys){
			if (key.getIsOn()){
				key.play(false);
			}
		}
	}
}

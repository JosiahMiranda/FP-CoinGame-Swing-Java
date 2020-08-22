package cellrenderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import model.interfaces.Player;

@SuppressWarnings("serial")
public class PlayerCellRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Object item = value;

		// So if the item is a Player, then we will display the ID and Name.
		if (item instanceof Player) {
			item = String.format("%s - %s", ((Player) item).getPlayerId(), ((Player) item).getPlayerName());
		}
		return super.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
	}
}
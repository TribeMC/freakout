package events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import v3Freak.Messages;

public class CommandManagement implements Listener {

	boolean changeMessage;
	List<String> disabledCommands = new ArrayList<>();
boolean denyDirektComands;
	Messages msg;

	public CommandManagement(String values, Messages msg) {
		String[] temp = values.split(", ");
		changeMessage = Boolean.valueOf(temp[0]);
		denyDirektComands = Boolean.valueOf(temp[2]);
		for (String s : temp[1].split(";")) {
			disabledCommands.add(s.toLowerCase());
		}
		this.msg = msg;
	}

	@EventHandler
	public void onCMDProzes(PlayerCommandPreprocessEvent e) {
		if (Bukkit.getHelpMap().getHelpTopic(
				e.getMessage().split(" ")[0].toLowerCase()) == null
				&& changeMessage) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(
					msg.getMessage("prefix") + msg.getMessage("unknowncmd"));
			return;
		}
		if (disabledCommands.contains(e.getMessage().split(" ")[0].replace("/",
				"").toLowerCase())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(
					msg.getMessage("prefix")
							+ msg.getMessage("disabledCommand"));
			return;

		}
		if(denyDirektComands && e.getMessage().split(" ")[0].contains(":")){
			e.setCancelled(true);
			e.getPlayer().sendMessage(msg.getMessage("prefix") + msg.getMessage("onlydirect"));
			
		}
	}

}

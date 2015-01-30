package events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import v3Freak.Messages;

public class Join implements Listener {

	Messages msg;
	boolean changeMessage;
	int toMax;

	public Join(String values, Messages msg) {
		// TODO Auto-generated constructor stub
		this.msg = msg;
		String temp[] = values.split(", ");
		changeMessage = Boolean.valueOf(temp[0]);
		toMax = Integer.valueOf(temp[1]);
		registerPermission();
	}

	private void registerPermission() {
		// TODO Auto-generated method stub
		List<Permission> perms = new ArrayList<>();
		String f = "join";
		String pre = "freakout." + f + ".";

		perms.add(new Permission(pre + "bypass", "FreakOut Permission fuer "
				+ f, PermissionDefault.FALSE));

		for (Permission p : perms) {
			Bukkit.getServer().getPluginManager().addPermission(p);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (changeMessage) {
			e.setJoinMessage("");
			if (Bukkit.getOnlinePlayers().length <= toMax
					|| e.getPlayer().hasPermission("freakout.join.bypass")) {
				String joinmsg = msg.getMessage("join");
				joinmsg = joinmsg.replace("PLAYER", e.getPlayer().getName());
				e.setJoinMessage(joinmsg);
			}
		}
	}

}

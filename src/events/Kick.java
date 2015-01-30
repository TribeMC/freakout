package events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import v3Freak.Messages;

public class Kick implements Listener {

	boolean changeMessage;
	int toMax;
	Messages msg;

	public Kick(String values, Messages msg) {
		registerPermission();
		this.msg = msg;
		String[] temp = values.split(", ");
		this.changeMessage = Boolean.valueOf(temp[0]);
		this.toMax = Integer.valueOf(temp[1]);
	}

	private void registerPermission() {
		// TODO Auto-generated method stub
		List<Permission> perms = new ArrayList<>();
		String f = "kick";
		String pre = "freakout." + f + ".";

		perms.add(new Permission(pre + "bypass", "FreakOut Permission fuer "
				+ f, PermissionDefault.FALSE));

		for (Permission p : perms) {
			Bukkit.getServer().getPluginManager().addPermission(p);
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		if (changeMessage) {
			e.setLeaveMessage("");
			if (Bukkit.getOnlinePlayers().length <= toMax
					|| e.getPlayer().hasPermission("freakout.join.bypass")) {
				String joinmsg = msg.getMessage("kick");
				joinmsg = joinmsg.replace("PLAYER", e.getPlayer().getName());
				e.setLeaveMessage(joinmsg);
			}
		}
	}
}

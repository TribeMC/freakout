package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import v3Freak.Messages;

public class Quit implements Listener {

	boolean changeMessage;
	int toMax;
	Messages msg;

	int teleportRadius;

	public Quit(String values, Messages msg) {
		registerPermission();
		this.msg = msg;
		String[] temp = values.split(", ");
		this.changeMessage = Boolean.valueOf(temp[0]);
		this.toMax = Integer.valueOf(temp[1]);
		this.teleportRadius = Integer.valueOf(temp[2]);
	}

	private void registerPermission() {
		// TODO Auto-generated method stub
		List<Permission> perms = new ArrayList<>();
		String f = "quit";
		String pre = "freakout." + f + ".";

		perms.add(new Permission(pre + "bypass", "FreakOut Permission fuer "
				+ f, PermissionDefault.OP));

		for (Permission p : perms) {
			Bukkit.getServer().getPluginManager().addPermission(p);
		}
	}

	Random r = new Random();

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (changeMessage) {
			e.setQuitMessage("");
			if (Bukkit.getOnlinePlayers().length <= toMax
					|| e.getPlayer().hasPermission("freakout.join.bypass")) {
				String joinmsg = msg.getMessage("quit");
				joinmsg = joinmsg.replace("PLAYER", e.getPlayer().getName());
				e.setQuitMessage(joinmsg);
			}
		}

		if (!e.getPlayer().hasPermission("freakout.quit.protect")) {
			Location loc = new Location(e.getPlayer().getWorld(),
					r.nextInt(2 * teleportRadius) - teleportRadius, 50,
					r.nextInt(2 * teleportRadius) - teleportRadius);
			while (!loc.getBlock().getType().equals(Material.AIR)
					&& !loc.add(0, 1, 0).getBlock().getType()
							.equals(Material.AIR)) {
				loc = loc.add(0, 1, 0);
			}
			e.getPlayer().teleport(loc);
		}
	}
}

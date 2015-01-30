package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class RandomFall implements Listener {

	int distance;

	public RandomFall(String values) {
		String[] temp = values.split(", ");
		this.distance = Integer.valueOf(temp[0]);
		registerPermission();
	}

	private void registerPermission() {
		// TODO Auto-generated method stub
		List<Permission> perms = new ArrayList<>();
		String f = "fall";
		String pre = "freakout." + f + ".";

		perms.add(new Permission(pre + "slow", "FreakOut Permission fuer " + f,
				PermissionDefault.FALSE));

		for (Permission p : perms) {
			Bukkit.getServer().getPluginManager().addPermission(p);
		}
	}

	@EventHandler
	public void onFall(PlayerMoveEvent e) {
		if (e.getPlayer().getFallDistance() >= 3) {
			Random r = new Random();
			e.getPlayer().setFallDistance(r.nextInt(distance + 3));
			if (e.getPlayer().hasPermission("freakout.fall.slow")) {
				e.getPlayer().setFallDistance(
						e.getPlayer().getFallDistance() / 3);
			}
		}
	}

}

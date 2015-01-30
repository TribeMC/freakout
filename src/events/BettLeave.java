package events;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import v3Freak.Messages;

public class BettLeave implements Listener {

	boolean drunkwake;
	int drunkchance;
	int drunkradius;
	Messages msg;

	public BettLeave(String values, Messages msg) {
		String[] temp = values.split(", ");
		this.msg = msg;
		drunkwake = Boolean.valueOf(temp[0]);
		drunkradius = Integer.valueOf(temp[1]);
		drunkchance = Integer.valueOf(temp[2]);
	}

	Random r = new Random();

	@EventHandler
	public void onWakeUP(PlayerBedLeaveEvent e) {
		if (drunkwake) {
			if (r.nextInt(drunkchance) == 0) {
				Location loc = e.getPlayer().getLocation();
				loc = loc.add(r.nextInt(drunkradius * 2) - drunkradius, 0,
						r.nextInt(drunkradius * 2) - drunkradius);
				while (!loc.getBlock().getType().equals(Material.AIR)
						&& !loc.add(0, 1, 0).getBlock().getType()
								.equals(Material.AIR)) {
					loc = loc.add(0, 1, 0);
				}

				e.getPlayer().teleport(loc);
			}
		}
	}

}

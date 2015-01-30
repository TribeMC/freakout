package events;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TeleportOnDemage implements Listener {

	int distance;
	boolean saveTP;

	public TeleportOnDemage(String values) {
		String[] temp = values.split(", ");
		this.distance = Integer.valueOf(temp[0]);
		this.saveTP = Boolean.valueOf(temp[1]);
	}

	@EventHandler
	public void onDemge(EntityDamageByEntityEvent e) {
		if (!e.isCancelled()) {
			Random r = new Random();

			Location loc = e.getEntity().getLocation();
			loc = loc.add(distance, 0, distance);
			loc = loc
					.add(-r.nextInt(distance * 2), 0, -r.nextInt(distance * 2));
			if (saveTP) {
				while (!loc.getBlock().getType().equals(Material.AIR)
						&& !loc.add(0, 1, 0).getBlock().getType()
								.equals(Material.AIR)) {
					loc = loc.add(0, 1, 0);
				}
			}
			e.getEntity().teleport(loc);
		}
	}
}

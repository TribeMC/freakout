package events;

import java.util.Random;

import org.bukkit.EntityEffect;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

@SuppressWarnings("deprecation")
public class BettEnter implements Listener {

	boolean makeKids;
	int kidchance;
	int radius;

	boolean badExplode;
	int explodechance;

	public BettEnter(String values) {
		String[] temp = values.split(", ");
		makeKids = Boolean.valueOf(temp[0]);
		kidchance = Integer.valueOf(temp[1]);
		radius = Integer.valueOf(temp[2]);
		badExplode = Boolean.valueOf(temp[3]);
		explodechance = Integer.valueOf(temp[4]);
	}

	Random r = new Random();

	@EventHandler
	public void onSleep(PlayerBedEnterEvent e) {
		if (makeKids) {
			if (r.nextInt(kidchance) == 0) {
				for (Entity en : e.getPlayer().getNearbyEntities(radius,
						radius, radius)) {
					if (en instanceof Player && ((Player) en).isSleeping()) {
						e.getPlayer()
								.getWorld()
								.spawnCreature(e.getPlayer().getLocation(),
										CreatureType.VILLAGER);
						e.getPlayer().playEffect(EntityEffect.VILLAGER_HEART);
						en.playEffect(EntityEffect.VILLAGER_HEART);
					}
				}
			}
		}

		if (badExplode) {
			if (r.nextInt(explodechance) == 0) {
				e.getBed().getWorld()
						.createExplosion(e.getBed().getLocation(), 5F);
			}
		}
	}

}

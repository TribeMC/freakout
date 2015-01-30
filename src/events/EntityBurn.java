package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import v3Freak.Messages;

public class EntityBurn implements Listener {

	boolean fireProtect;
	int fireProtectduration;
	int fireProtectChance;
	boolean allowWeed;
	List<PotionEffect> effekts = new ArrayList<>();
	int WeedRadius;

	public EntityBurn(String values, Messages msg) {
		String[] temp = values.split(", ");
		fireProtect = Boolean.valueOf(temp[0]);
		fireProtectChance = Integer.valueOf(temp[1]);
		fireProtectduration = Integer.valueOf(temp[2]);
		allowWeed = Boolean.valueOf(temp[3]);
		for (String s : temp[4].split(";")) {
			PotionEffect pf = new PotionEffect(PotionEffectType.getByName(s),
					Integer.valueOf(temp[6]) * 20, 1);
			effekts.add(pf);
		}
		WeedRadius = Integer.valueOf(temp[5]);
	}

	Random r = new Random();

	@EventHandler
	public void onBurn(EntityDamageEvent e) {
		if (e.getCause().equals(DamageCause.FIRE)) {
			if (e.getEntity() instanceof Player) {
				if (r.nextInt(fireProtectChance) == 0) {
					((Player) e.getEntity()).addPotionEffect(new PotionEffect(
							PotionEffectType.FIRE_RESISTANCE,
							fireProtectduration * 20, 0));
				}
			}

		}
	}

	@EventHandler
	public void onCombust(EntityCombustEvent e) {
		if (e.getEntity() instanceof Item) {
			if (allowWeed
					&& ((Item) e.getEntity()).getItemStack().getType()
							.equals(Material.GRASS)) {
				for (Entity en : e.getEntity().getNearbyEntities(WeedRadius,
						WeedRadius, WeedRadius)) {
					if (en instanceof Player) {
						for (PotionEffect pf : effekts) {
							((Player) en).addPotionEffect(pf);
						}
					}
				}
			}
		}
	}
}

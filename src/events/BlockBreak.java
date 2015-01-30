package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class BlockBreak implements Listener {

	List<Material> denied = new ArrayList<>();

	HashMap<Material, Material> changes = new HashMap<>();
	int random;
	int maxxp;

	@SuppressWarnings("deprecation")
	public BlockBreak(String values) {
		String[] temp = values.split(", ");
		for (String s : temp[0].split(";")) {
			try {
				denied.add(Material.getMaterial(Integer.valueOf(s)));
			} catch (Exception e) {
				System.err.println("Konnte Block nicht registrieren" + s);
			}
		}
		random = Integer.valueOf(temp[1]);
		for (String s : temp[2].split(";")) {
			String[] ss = s.split("-");
			try {
				changes.put(Material.getMaterial(Integer.valueOf(ss[0])),
						Material.getMaterial(Integer.valueOf(ss[1])));
			} catch (Exception e) {

			}
		}
		maxxp = Integer.valueOf(temp[3]);

		registerPermission();
	}

	private void registerPermission() {
		// TODO Auto-generated method stub
		List<Permission> perms = new ArrayList<>();
		String f = "break";
		String pre = "freakout." + f + ".";

		perms.add(new Permission(pre + "xp", "FreakOut Permission fuer " + f,
				PermissionDefault.FALSE));

		for (Permission p : perms) {
			Bukkit.getServer().getPluginManager().addPermission(p);
		}
	}

	Random r = new Random();

	@EventHandler
	public void onBreak(BlockBreakEvent e) {

		if (denied.contains(e.getBlock().getType())) {
			e.setCancelled(true);
			e.getBlock()
					.getWorld()
					.playEffect(e.getBlock().getLocation().add(0, 1, 0),
							Effect.ENDER_SIGNAL, 4);
			return;
		}
		if (changes.containsKey(e.getBlock().getType())) {

			if (r.nextInt(random) == 0) {
				e.setCancelled(true);

				e.getBlock()
						.getWorld()
						.dropItemNaturally(
								e.getBlock().getLocation(),
								new ItemStack(changes.get(e.getBlock()
										.getType()), 1));
				e.getBlock().setType(Material.AIR);
				e.getBlock()
						.getLocation()
						.getWorld()
						.playEffect(e.getBlock().getLocation().add(0, 1, 0),
								Effect.MOBSPAWNER_FLAMES, 7);
			}
		}
		if (e.getPlayer().hasPermission("freakout.break.xp")) {
			e.setExpToDrop(r.nextInt(maxxp));
		}
	}
}

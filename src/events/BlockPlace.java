package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Stairs;

public class BlockPlace implements Listener {

	List<Material> denied = new ArrayList<>();

	HashMap<Material, Material> changes = new HashMap<>();
	int random;

	@SuppressWarnings("deprecation")
	public BlockPlace(String values) {
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
	}

	Random r = new Random();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (denied.contains(e.getBlockPlaced().getType())) {
			e.setCancelled(true);
			e.getBlock()
					.getWorld()
					.playEffect(e.getBlock().getLocation(),
							Effect.ENDER_SIGNAL, 4);
			return;
		}
		if (changes.containsKey(e.getBlock().getType())) {
			if (r.nextInt(random) == 0) {
				e.getBlock().setType(changes.get(e.getBlock().getType()));
				e.getBlock()
						.getLocation()
						.getWorld()
						.playEffect(e.getBlock().getLocation().add(0, 1, 0),
								Effect.MOBSPAWNER_FLAMES, 7);
			}
		}

		if (e.getBlockPlaced().getState() instanceof Stairs) {
			Stairs s = (Stairs) e.getBlockPlaced().getState();
			s.setFacingDirection(BlockFace.values()[r.nextInt(18)]);
			s.setInverted(r.nextBoolean());
			e.getBlockPlaced().getWorld()
					.getBlockAt(e.getBlockPlaced().getLocation())
					.setTypeIdAndData(s.getItemTypeId(), s.getData(), true);
		}
	}

}

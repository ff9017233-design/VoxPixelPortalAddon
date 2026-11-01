package com.voxpixel.portal;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.plugin.Plugin;

public class PortalListener implements Listener {

    private final VoxPixelPortalAddon addon;
    private final Set<UUID> teleporting = new HashSet<>();

    public PortalListener(VoxPixelPortalAddon addon) {
        this.addon = addon;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPortalEnter(EntityPortalEnterEvent event) {

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!player.getWorld().getName().equalsIgnoreCase("bskyblock_world")) {
            return;
        }

        if (!teleporting.add(player.getUniqueId())) {
            return;
        }

        Plugin bentobox = Bukkit.getPluginManager().getPlugin("BentoBox");

        if (bentobox == null || !bentobox.isEnabled()) {
            teleporting.remove(player.getUniqueId());
            addon.logError("BentoBox plugin is unavailable!");
            return;
        }

        Bukkit.getScheduler().runTask(bentobox, () -> {
            try {
                if (!player.isOnline()) {
                    return;
                }

                World hub = Bukkit.getWorld("vox_hub");

                if (hub == null) {
                    addon.logError("VOXPIXEL Portal: vox_hub is not loaded!");
                    return;
                }

                boolean success = player.teleport(hub.getSpawnLocation());

                if (success) {
                    player.sendMessage("§aWelcome to VOXPIXEL Hub!");
                    addon.log("Portal redirect successful: " + player.getName());
                } else {
                    addon.logError("Teleport failed for " + player.getName());
                }

            } finally {
                teleporting.remove(player.getUniqueId());
            }
        });
    }
}

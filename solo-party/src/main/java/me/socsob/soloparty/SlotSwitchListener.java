package me.socsob.soloparty;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.PlayerInventory;

public class SlotSwitchListener implements Listener {

    private final SoloParty plugin;
    public SlotSwitchListener(SoloParty plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        PlayerState playerState = plugin.getPlayerStateManager().getPlayerState(player);
        PlayerInventory inventory = player.getInventory();

        if (playerState.isEditing()) {
            int slot = event.getNewSlot();
            if (slot >= 6 && slot <= 8) {
                switch (slot) {
                    case 6:
                        player.sendMessage("You selected 'Mark'");
                        break;
                    case 7:
                        if (playerState.isPaused()) {
                            playerState.setPaused(false);
                            inventory.setItem(7, plugin.createBoldItem(Material.GLOWSTONE, "Pause", ChatColor.RED));
                            player.sendMessage("Unpaused");
                        } else {
                            playerState.setPaused(true);
                            inventory.setItem(7, plugin.createBoldItem(Material.REDSTONE_LAMP, "Play", ChatColor.GREEN));
                            player.sendMessage("Paused");

                        }
                        break;
                    case 8:
                        plugin.enterEditMode(player);
                        break;
                }
                event.setCancelled(true);

            }
        }
    }
}
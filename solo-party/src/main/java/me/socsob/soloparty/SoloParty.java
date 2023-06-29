package me.socsob.soloparty;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class SoloParty extends JavaPlugin {
    private PlayerStateManager playerStateManager;

    @Override
    public void onEnable() {
        // Fired when the server enables the plugin
        playerStateManager = new PlayerStateManager();
        getServer().getPluginManager().registerEvents(new SlotSwitchListener(this), this);

        getCommand("edit").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    PlayerState playerState = playerStateManager.getPlayerState(player);
                    PlayerInventory inventory = player.getInventory();
                    if (playerState.isEditing()) {
                        enterEditMode(player);
                    } else {
                        if (args.length != 1) {
                            player.sendMessage("Please include a recording name.");
                            return false;
                        }
                        playerState.setEditing(true);
                        playerState.setPaused(true);
                        playerState.setRecordingName(args[0]);
                        // Add items for edit mode
                        inventory.setItem(8, createBoldItem(Material.BARRIER, "End", ChatColor.RED));
                        inventory.setItem(7, createBoldItem(Material.REDSTONE_LAMP, "Play", ChatColor.GREEN));
                        inventory.setItem(6, createBoldItem(Material.ARROW, "Mark", ChatColor.YELLOW));

                        player.sendMessage("Entered edit mode for recording: " + args[0]);
                    }
                    return true;
                }
                return false;
            }
        });
        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Fired when the server stops and disables all plugins
        getLogger().info("Plugin disabled!");
    }

    public PlayerStateManager getPlayerStateManager() {
        return playerStateManager;
    }

    public void enterEditMode(Player player) {
        PlayerState playerState = playerStateManager.getPlayerState(player);
        PlayerInventory inventory = player.getInventory();
        playerState.setEditing(false);
        playerState.setPaused(true);
        // Remove items from edit mode
        inventory.clear(8);
        inventory.clear(7);
        inventory.clear(6);

        player.sendMessage("Exited edit mode.");
    }
    public ItemStack createBoldItem(Material material, String name, ChatColor color) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(color + "" + ChatColor.BOLD + name);
            item.setItemMeta(meta);
        }
        return item;
    }
}
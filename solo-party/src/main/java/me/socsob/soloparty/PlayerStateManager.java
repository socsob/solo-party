package me.socsob.soloparty;

import org.bukkit.entity.Player;

import java.util.HashMap;

import java.util.Map;


public class PlayerStateManager {
    private Map<Player, PlayerState> playerStates = new HashMap<>();

    public PlayerState getPlayerState(Player player) {
        return playerStates.computeIfAbsent(player, k -> new PlayerState());
    }
}
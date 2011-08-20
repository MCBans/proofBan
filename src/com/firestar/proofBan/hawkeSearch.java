package com.firestar.proofBan;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import uk.co.oliwali.HawkEye.callbacks.BaseCallback;
import uk.co.oliwali.HawkEye.database.SearchQuery.SearchError;

public class hawkeSearch extends BaseCallback {

    private Player player;

    public hawkeSearch(Player player) {
        player.sendMessage("["+ChatColor.DARK_AQUA+"Proof"+ChatColor.WHITE+"]"+ChatColor.LIGHT_PURPLE+" Searching!");
    }

    public void execute() {
        player.sendMessage("["+ChatColor.DARK_AQUA+"Proof"+ChatColor.WHITE+"]"+ChatColor.LIGHT_PURPLE+" Finished searching, "+results.size()+" found!");
    }

    public void error(SearchError error, String message) {
        player.sendMessage(message);
    }

}
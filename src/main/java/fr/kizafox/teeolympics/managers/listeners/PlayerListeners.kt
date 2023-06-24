package fr.kizafox.teeolympics.managers.listeners

import fr.kizafox.teeolympics.core.TeeOlympicsCore
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListeners(private var instance: TeeOlympicsCore?) : Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    fun onLogin(event: PlayerJoinEvent){
        event.joinMessage(Component.text("§7[§a+§7] §f${event.player.name}"));
    }

    @EventHandler (priority = EventPriority.MONITOR)
    fun onLogout(event: PlayerQuitEvent){
        event.quitMessage(Component.text("§7[§c-§7] §f${event.player.name}"));
    }
}

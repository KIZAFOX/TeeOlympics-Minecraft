package fr.kizafox.teeolympics.managers.listeners

import fr.kizafox.teeolympics.core.TeeOlympicsCore
import fr.kizafox.teeolympics.tools.tablist.TList
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import kotlin.math.roundToInt


class PlayerListeners(private var instance: TeeOlympicsCore?) : Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    fun onLogin(event: PlayerJoinEvent){
        val player = event.player

        event.joinMessage(text("§7[§a+§7] §f${player.name}"))

        for(i in 0..2){
            player.sendMessage(text(""))
        }
        player.sendMessage(text("          Bienvenue sur §6§lTeeOlympics"))
        player.sendMessage(text(""))
        player.sendMessage(text("§8§l»§r N'oublie pas la commande §5§l/map§r pour voir la map en direct!"))
        player.sendMessage(text(""))

        TList.load(instance, player, player)
    }

    @EventHandler (priority = EventPriority.MONITOR)
    fun onLogout(event: PlayerQuitEvent){
        event.quitMessage(text("§7[§c-§7] §f${event.player.name}"))
    }

    @EventHandler (priority = EventPriority.MONITOR)
    fun onDeath(event: PlayerDeathEvent){
        val player = event.player
        val location = player.location

        val x = (location.x * 100).roundToInt() / 100.0
        val y = (location.y * 100).roundToInt() / 100.0
        val z = (location.z * 100).roundToInt() / 100.0

        event.deathMessage(text("§eCoordonnées de mort de §6${player.name}§e. §ex§7: §6$x §ey§7: §6$y §ez§7: §6$z §emonde§7: §6${location.world.name.uppercase()}"))
    }

    @EventHandler (priority = EventPriority.MONITOR)
    fun onChat(event: AsyncChatEvent){
        event.isCancelled = true

        Bukkit.getOnlinePlayers().forEach { players -> players.sendMessage(text("${event.player.name} : ${PlainTextComponentSerializer.plainText().serialize(event.message())}"))}
    }
}

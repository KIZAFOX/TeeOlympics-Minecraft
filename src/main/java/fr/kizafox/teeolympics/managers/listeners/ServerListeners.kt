package fr.kizafox.teeolympics.managers.listeners

import fr.kizafox.teeolympics.TeeOlympics
import fr.kizafox.teeolympics.core.TeeOlympicsCore
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import java.io.File

class ServerListeners(private var instance: TeeOlympicsCore?) : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onServerListPing(event: ServerListPingEvent) {
        event.motd(Component.text("               ${instance!!.getPlugin()!!.config.getString("motd.line1")!!.replace("&", "§")}\n                    ${instance!!.getPlugin()!!.config.getString("motd.line2")!!.replace("&", "§")}"))
        event.maxPlayers = instance!!.getPlugin()!!.config.getInt("slot")

        try {
            event.setServerIcon(Bukkit.loadServerIcon(File("server-icon.png")));
        }catch (e: Exception){
            TeeOlympics.tlogger.send("§cAn error occurred while processing the result set: " + e.message)
        }
    }
}

package fr.kizafox.teeolympics.core

import fr.kizafox.teeolympics.TeeOlympics
import fr.kizafox.teeolympics.managers.commands.AdminCommand
import fr.kizafox.teeolympics.managers.commands.MapCommand
import fr.kizafox.teeolympics.managers.listeners.PlayerListeners
import fr.kizafox.teeolympics.managers.listeners.ServerListeners
import fr.kizafox.teeolympics.tools.storage.TConfig
import fr.kizafox.teeolympics.tools.tps.TLag
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.Consumer

class TeeOlympicsCore(private var plugin: JavaPlugin?) : Core() {

    private var instance: TeeOlympicsCore? = null

    fun TeeOlympicsCore(plugin: JavaPlugin?) {
        instance = if (plugin != null && instance == null) {
            this
        } else {
            null
        }
        this.plugin = plugin
    }

    override fun enable() {
        println("================================================================")
        try {
            instance = this

            Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(this.plugin!!, TLag(), 100L, 1L)

            TConfig.loadDefaultConfig(plugin)

            registerListeners()
            registerCommands()

            Bukkit.getWorlds().forEach(Consumer { worlds: World ->
                run {
                    TeeOlympics.tlogger.send("${TeeOlympics.PREFIX} > world ${worlds.name} loaded!")
                }
            })
            println(" ")
            TeeOlympics.tlogger.send("${TeeOlympics.PREFIX} §7> §aPlugin successfully enabled!")
        } catch (e: Exception) {
            TeeOlympics.tlogger.send("§cAn error occurred while processing : ${e.message}")
        }
        println("================================================================")
    }

    override fun registerListeners() {
        println("===============================")
        try {
            val listeners = arrayOf(PlayerListeners(this), ServerListeners(this))

            listeners.forEach { listener ->
                run {
                    plugin?.server?.pluginManager?.registerEvents(listener, this.getPlugin()!!)
                    TeeOlympics.tlogger.send("${TeeOlympics.PREFIX} §7> §c${listener} successfully registered")
                }
            }
            println(" ")
            TeeOlympics.tlogger.send("${TeeOlympics.PREFIX} §7> §cListeners successfully registered")
        } catch (e: Exception) {
            TeeOlympics.tlogger.send("§cAn error occurred while processing : ${e.message}")
        }
        println("===============================")
    }

    override fun registerCommands() {
        println("===============================")
        try {
            AdminCommand(this).register()
            MapCommand(this).register()

            TeeOlympics.tlogger.send("${TeeOlympics.PREFIX} §7> §cCommands successfully registered")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("===============================")
    }

    fun get(): TeeOlympicsCore? {
        return instance
    }

    fun getPlugin(): Plugin? {
        return plugin
    }

    fun plugin(): Plugin? {
        return get()!!.getPlugin()
    }

    companion object {
        fun get(): TeeOlympicsCore? {
            return TeeOlympicsCore(plugin = null).get()
        }
    }
}

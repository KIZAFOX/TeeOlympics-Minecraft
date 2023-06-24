package fr.kizafox.teeolympics

import fr.kizafox.teeolympics.core.TeeOlympicsCore
import fr.kizafox.teeolympics.tools.logger.TLogger
import org.bukkit.plugin.java.JavaPlugin

/**
 * Welcome to the main class of this plugin.
 * @author: KIZAFOX
 */
class TeeOlympics : JavaPlugin() {

    companion object{
        var tlogger: TLogger.Companion = TLogger

        var PREFIX: String = "§7[§6§lTeeOlympics§7]§r §8§l»§r"
    }

    override fun onEnable() {
        TeeOlympicsCore(this).enable()
        super.onEnable()
    }
}

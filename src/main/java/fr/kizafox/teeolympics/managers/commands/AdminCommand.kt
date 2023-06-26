package fr.kizafox.teeolympics.managers.commands

import fr.kizafox.teeolympics.TeeOlympics
import fr.kizafox.teeolympics.core.TeeOlympicsCore
import fr.kizafox.teeolympics.managers.commands.handler.SubCommand
import fr.kizafox.teeolympics.tools.tps.TLag
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.roundToInt

class AdminCommand(private var instance: TeeOlympicsCore) : SubCommand("admin") {

    fun TeeOlympicsCore(instance: TeeOlympicsCore) {
        this.instance = instance;
    }

    override fun perform(sender: CommandSender?, cmd: Command?, label: String?, args: Array<String>?) {
        if (sender !is Player) {
            sender!!.sendMessage(Component.text("§cSeulement un joueur peut utiliser cette commande!"))
            return
        }

        if (!sender.isOp()) {
            sender.sendMessage(Component.text("§cTu n'as pas la permission d'utiliser cette commande!"))
            return
        }

        val gray = "§7§m=§r"
        val darkGray = "§8§m=§r"
        val line = "${gray}${darkGray}${gray}${darkGray}${gray}${darkGray}${gray}${darkGray}${gray}${darkGray}"
        val fullLine = "$line §6§lTeeOlympics§r §8(§71§8/§71§8) $line"

        if (args!!.isEmpty()) {
            sender.sendMessage(Component.text(fullLine))
            sender.sendMessage(Component.text("§7Utilise /admin pour voir la liste des sous-commandes."))
            sender.sendMessage(Component.text(" "))
            sender.sendMessage(Component.text("§8§l»§r §eMOTD: §7/admin motd <line1/line2> <motd>"))
            sender.sendMessage(Component.text("§8§l»§r §eSlot: §7/admin slot <count>"))
            sender.sendMessage(Component.text("§8§l»§r §eTPS: §7/admin tps"))
            sender.sendMessage(Component.text(" "))
            sender.sendMessage(Component.text(fullLine))
            return
        }

        when(args[0]){
            "motd" -> {
                when(args[1]){
                    "line1" -> this.setMOTD(instance, args, 1, sender)
                    "line2" -> this.setMOTD(instance, args, 2, sender)
                }
            }
            "slot" -> {
                val slot: Int = try {
                    args[1].toInt()
                } catch (e: NumberFormatException) {
                    sender.sendMessage(Component.text(TeeOlympics.PREFIX + " §cLa valeur 'slot' doit être un nombre entier !"))
                    return
                }

                instance.getPlugin()!!.config.set("slot", slot)
                instance.getPlugin()!!.saveConfig()

                sender.sendMessage(Component.text("${TeeOlympics.PREFIX} §eNombre de slot mit à jour sur §6§l${instance.getPlugin()!!.config.getInt("slot")}§r§e!"))
            }
            "tps" -> {
                sender.sendMessage(Component.text("${TeeOlympics.PREFIX} §e§oChargement de la requête... merci d'attendre 15 secondes!"))

                val stringBuilder = StringBuilder()
                val tpsList = HashMap<Int, Double>(3)

                var time: Long = 15

                object : BukkitRunnable() {
                    override fun run() {
                        try {
                            when(time){
                                15L -> tpsList[1] = TLag.getTPS()
                                10L -> tpsList[2] = TLag.getTPS()
                                5L -> tpsList[3] = TLag.getTPS()
                                0L -> {
                                    TLag.getTPS().let{
                                        for((k, v) in tpsList){
                                            stringBuilder.append("§e$k §7- §6§l" + (v * 100).roundToInt() / 100.0)
                                            stringBuilder.append(" §8§l|§r ")
                                        }
                                    }

                                    sender.sendMessage(Component.text("§eRésultat de la requête sur les TPS sur ces 15 dernières secondes :"))
                                    sender.sendMessage(Component.text(stringBuilder.substring(0, stringBuilder.length - 2)))
                                    tpsList.clear()
                                    this.cancel()
                                }
                            }
                            time--
                        } catch (e: NoSuchFieldException) {
                            e.printStackTrace()
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        }
                    }
                }.runTaskTimer(instance.getPlugin()!!, 0, 20)
            }
        }
    }

    private fun setMOTD(instance: TeeOlympicsCore, args: Array<out String>, line: Int, player: Player) {
        val stringBuilder = StringBuilder()

        for (part in args) {
            stringBuilder.append(part).append(" ")
        }

        instance.getPlugin()!!.config.set("motd.line$line", stringBuilder.toString().replace(args[0], "").replace(args[1], ""))
        instance.getPlugin()!!.saveConfig()

        player.sendMessage(Component.text("${TeeOlympics.PREFIX} §eMOTD mit à jour."))
        player.sendMessage(Component.text("${TeeOlympics.PREFIX} §eChangement de la ligne §6§l${line}§r §epar${instance.getPlugin()!!.config.getString("motd.line$line")!!.replace("&", "§")}"))
    }
}
package fr.kizafox.teeolympics.managers.commands.handler

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.*

import java.lang.reflect.Field
import java.util.HashMap
import java.util.HashSet
import java.util.Objects

abstract class SubCommand @JvmOverloads constructor(
    private var command: String,
    private var permission: String? = null,
    private var usage: String? = null,
    private var aliases: List<String>? = null
) : CommandExecutor {
    fun register() {
        val cmd = ReflectCommand(command)
        if (aliases != null) {
            cmd.setAliases(aliases!!)
        }
        if (usage != null) {
            cmd.setUsage(usage!!)
        }
        if (permission != null) {
            cmd.permission = permission
        }
        Objects.requireNonNull<CommandMap?>(commandMap).register(cmd.name, cmd)
        registeredCommands.add(cmd.name)
        cmd.setExecutor(this)
    }

    abstract fun perform(sender: CommandSender?, cmd: Command?, label: String?, args: Array<String>?)

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (permission != null && !Objects.requireNonNull<Command>(
                Objects.requireNonNull<CommandMap?>(
                    commandMap
                ).getCommand(command)
            ).testPermissionSilent(sender)
        ) sender.sendMessage(Component.text("§cTu n'as pas la permission d'utiliser cette commande!")) else perform(
            sender,
            cmd,
            label,
            args
        )
        return false
    }

    fun sendUsage(sender: CommandSender) {
        if (usage != null) sender.sendMessage(Component.text(usage!!)) else sender.sendMessage(Component.text("&cUsage non définie."))
    }

    private class ReflectCommand(command: String) : Command(command) {
        private var exe: SubCommand? = null
        fun setExecutor(exe: SubCommand?) {
            this.exe = exe
        }

        override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
            return exe != null && !exe!!.onCommand(sender, this, commandLabel, args)
        }
    }

    companion object {
        val registeredCommands = HashSet<String>()
        val commandMap: CommandMap?
            get() = try {
                val f: Field = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
                f.isAccessible = true
                f[Bukkit.getServer()] as CommandMap
            } catch (var2: Exception) {
                var2.printStackTrace()
                null
            }

        @Throws(
            SecurityException::class,
            NoSuchFieldException::class,
            IllegalArgumentException::class,
            IllegalAccessException::class
        )
        private fun getPrivateField(`object`: Any, field: String): Any {
            val clazz: Class<*> = `object`.javaClass
            val objectField = clazz.getDeclaredField(field)
            objectField.isAccessible = true
            val result = objectField[`object`]
            objectField.isAccessible = false
            return result
        }

        private fun unRegisterBukkitCommand(cmd: Command) {
            try {
                val result = getPrivateField(Bukkit.getServer().pluginManager, "commandMap")
                val commandMap: SimpleCommandMap = result as SimpleCommandMap
                val map = getPrivateField(commandMap, "knownCommands")
                val knownCommands = map as HashMap<*, *>
                knownCommands.remove(cmd.name)
                for (alias in cmd.aliases) {
                    knownCommands.remove(alias)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun unregisterCommands() {
            for (s in registeredCommands) {
                Objects.requireNonNull<CommandMap?>(commandMap).getCommand(s)?.let { unRegisterBukkitCommand(it) }
            }
        }
    }
}

package com.birthdates.warden.commands

import com.birthdates.warden.Warden
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.ocpsoft.prettytime.PrettyTime
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class WardenCommand : CommandExecutor {

    /**
     * Pretty Time API init (pretty cool)
     */
    private var p = PrettyTime()

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(!sender.hasPermission("warden.admin")) {
            sender.sendMessage("${ChatColor.RED}No permission!")
            return false
        }
        when(if(args.isEmpty()) "info" else args[0].toLowerCase()) {
            "info" -> {
                sender.sendMessage("${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}--------------------------------")
                sender.sendMessage("${ChatColor.GOLD}${ChatColor.BOLD}Warden ${ChatColor.RESET}Information")
                sender.sendMessage("")
                val tps = (Bukkit.spigot().tps[0] * 100.0).roundToInt() / 100.0
                sender.sendMessage("${ChatColor.GOLD}TPS: ${ChatColor.RESET}${if(tps > 20) "*20" else tps.toString()}")
                sender.sendMessage("${ChatColor.GOLD}Memory Usage: ${ChatColor.RESET}${Warden.instance.getCurrentRam()}MB / ${Runtime.getRuntime().totalMemory() / (1024.0 * 1024.0).roundToLong()}MB")
                sender.sendMessage("${ChatColor.GOLD}Last Flush: ${ChatColor.RESET}${if(Warden.instance.thread.lastFlush == null) "never" else "${p.format(Warden.instance.thread.lastFlush)} (${if(Warden.instance.thread.lastFlushForced) "forced" else "automatic"})"}")
                sender.sendMessage("${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}--------------------------------")

                return false
            }
            "flush" -> {
                Warden.instance.thread.lastFlushForced = true
                Warden.instance.thread.clearRam()
                sender.sendMessage("${ChatColor.GREEN}You have flushed the server's memory.")
                return false
            }
        }
        sender.sendMessage("${ChatColor.RED}Usage: /$p2 <info|flush>")
        return false
    }
}
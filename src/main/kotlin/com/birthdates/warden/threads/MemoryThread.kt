package com.birthdates.warden.threads

import com.birthdates.warden.Warden
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

class MemoryThread : Thread("Warden - Memory Thread") {

    var lastFlush: Date? = null
    var lastFlushForced: Boolean = false

    override fun run() {
        while (true) {
            try {
                sleep(600000L)
                lastFlushForced = false
                clearRam()
            } catch(exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    /**
     * Clear ram by using System GC and broadcast to ops
     */
    fun clearRam() {
        lastFlush = Date(System.currentTimeMillis())
        val oldMemory = Warden.instance.getCurrentRam()
        Runtime.getRuntime().gc()
        val newMemory = Warden.instance.getCurrentRam()
        val msg = ChatColor.GREEN.toString() + "Warden has ${if(lastFlushForced) "forcefully" else "synchronously"} flushed memory, (from " + oldMemory + "MB to " + newMemory + " MB, " + (oldMemory - newMemory) + "MB flushed)."
        Bukkit.getConsoleSender().sendMessage(msg)
        Bukkit.getOnlinePlayers().forEach { player: Player? ->
            if (player!!.isOp) {
                player.sendMessage(msg)
            }
        }
    }
}
package com.birthdates.warden

import com.birthdates.warden.commands.WardenCommand
import com.birthdates.warden.threads.MemoryThread
import org.bukkit.plugin.java.JavaPlugin
import kotlin.math.roundToLong

class Warden : JavaPlugin() {

    lateinit var thread: MemoryThread

     companion object {
         lateinit var instance: Warden
     }

     override fun onEnable() {
         instance = this
         thread = MemoryThread()
         thread.start()
         getCommand("warden").executor = WardenCommand()
    }

    /**
     * For some reason java memory variables come in bytes or some shit but this works translating it to megabytes, somehow.
     */
    fun getCurrentRam(): Long {
        return ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024.0 * 1024.0)).roundToLong()
    }
}

package fr.kizafox.teeolympics.tools.tps

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.Player
import kotlin.math.roundToLong

class TLag : Runnable{

    companion object {
        private var TICK_COUNT = 0
        private var TICKS = LongArray(600)
        private var LAST_TICK = 0L

        fun getTPS(): Double {
            return getTPS(100)
        }

        fun getTPS(ticks: Int): Double {
            if (TICK_COUNT < ticks) {
                return 20.0
            }
            val target: Int = (TICK_COUNT - 1 - ticks) % TICKS.size
            val elapsed = System.currentTimeMillis() - TICKS[target]
            return ticks / (elapsed / 1000.0)
        }

        fun getElapsed(tickID: Int): Long {
            if (TICK_COUNT - tickID >= TICKS.size) {
            }
            val time = TICKS[tickID % TICKS.size]
            return System.currentTimeMillis() - time
        }

        fun getLag(): Double {
            return ((1.0 - this.getTPS() / 20.0) * 100.0).roundToLong().toDouble()
        }

        fun getTPS(player: Player){
            player.sendMessage(Component.text((this.getTPS() * 100.0).roundToLong() / 100.0))
        }
    }

    /**
     * When an object implementing interface `Runnable` is used
     * to create a thread, starting the thread causes the object's
     * `run` method to be called in that separately executing
     * thread.
     *
     *
     * The general contract of the method `run` is that it may
     * take any action whatsoever.
     *
     * @see java.lang.Thread.run
     */
    override fun run() {
        TICKS[(TICK_COUNT% TICKS.size)] = System.currentTimeMillis();

        TICK_COUNT+= 1;
    }
}
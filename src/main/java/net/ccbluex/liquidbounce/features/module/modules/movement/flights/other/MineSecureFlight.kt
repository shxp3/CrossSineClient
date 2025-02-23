package net.ccbluex.liquidbounce.features.module.modules.movement.flights.other

import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode
import net.ccbluex.liquidbounce.features.value.FloatValue
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.minecraft.network.play.client.C03PacketPlayer
import kotlin.math.cos
import kotlin.math.sin

class MineSecureFlight : FlightMode("MineSecure") {
    private val speedValue = FloatValue("${valuePrefix}Speed", 2f, 0f, 5f)

    private val timer = MSTimer()

    override fun onUpdate(event: UpdateEvent) {
        mc.thePlayer.capabilities.isFlying = false

        if (!mc.gameSettings.keyBindSneak.isKeyDown) mc.thePlayer.motionY = -0.01

        MovementUtils.resetMotion(false)
        MovementUtils.strafe(speedValue.get())

        if (timer.hasTimePassed(150) && mc.gameSettings.keyBindJump.isKeyDown) {
            mc.netHandler.addToSendQueue(C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 5, mc.thePlayer.posZ, false))
            mc.netHandler.addToSendQueue(C03PacketPlayer.C04PacketPlayerPosition(0.5, -1000.0, 0.5, false))
            val yaw = Math.toRadians(mc.thePlayer.rotationYaw.toDouble())
            val x = -sin(yaw) * 0.4
            val z = cos(yaw) * 0.4
            mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z)
            timer.reset()
        }
    }
}
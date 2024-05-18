package net.ccbluex.liquidbounce.features.module.modules.other

import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.other.disablers.DisablerMode
import net.ccbluex.liquidbounce.features.value.BoolValue
import net.ccbluex.liquidbounce.features.value.Value
import net.ccbluex.liquidbounce.utils.ClassUtils
import java.util.LinkedList

@ModuleInfo(name = "Disabler",category = ModuleCategory.OTHER)
class Disabler : Module() {
    private val debugValue = BoolValue("Debug", false)
    private val mode = LinkedList<BoolValue>()
    private val mode2 = LinkedList<DisablerMode>()
    private val settings = arrayListOf<Value<*>>(debugValue)

    private val modeList = ClassUtils.resolvePackage("${this.javaClass.`package`.name}.disablers", DisablerMode::class.java)
        .map { it.newInstance() as DisablerMode }
        .sortedBy { it.modeName }
        .forEach {
            val modulesMode = object : BoolValue(it.modeName, false) {
                override fun onChange(oldValue: Boolean, newValue: Boolean) {
                    if (newValue && !oldValue) {
                        it.onEnable()
                    }else if (!newValue && oldValue){
                        it.onDisable()
                    }
                }
            }
            settings.add(modulesMode)
            mode.add(modulesMode)
            mode2.add(it)
        }



    private val modes = ClassUtils.resolvePackage("${this.javaClass.`package`.name}.disablers", DisablerMode::class.java)
        .map { it.newInstance() as DisablerMode }
        .sortedBy { it.modeName }



    override fun onEnable() {
        modes.forEach {
            if(getValue(it.modeName)?.value == true) {
                it.onEnable()
            }
        }
    }

    override fun onDisable() {
        modes.forEach {
            if(getValue(it.modeName)?.value == true) {
                it.onDisable()
            }
        }
        mc.timer.timerSpeed = 1F
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        modes.forEach {
            if(getValue(it.modeName)?.value == true) {
                it.onUpdate(event)
            }
        }
    }

    @EventTarget
    fun onMotion(event: MotionEvent) {
        modes.forEach {
            if(getValue(it.modeName)?.value == true) {
                it.onMotion(event)
            }
        }
    }

    @EventTarget
    fun onPacket(event: PacketEvent) {
        modes.forEach {
            if(getValue(it.modeName)?.value == true) {
                it.onPacket(event)
            }
        }
    }

    @EventTarget
    fun onMove(event: MoveEvent) {
        modes.forEach {
            if(getValue(it.modeName)?.value == true) {
                it.onMove(event)
            }
        }
    }

    @EventTarget
    fun onBlockBB(event: BlockBBEvent) {
        modes.forEach {
            if(getValue(it.modeName)?.value == true) {
                it.onBlockBB(event)
            }
        }
    }

    @EventTarget
    fun onJump(event: JumpEvent) {
        modes.forEach {
            if(getValue(it.modeName)?.value == true) {
                it.onJump(event)
            }
        }
    }

    @EventTarget
    fun onStep(event: StepEvent) {
        modes.forEach {
            if(getValue(it.modeName)?.value == true) {
                it.onStep(event)
            }
        }
    }

    @EventTarget
    fun onWorld(event: WorldEvent) {
        modes.forEach {
            if(getValue(it.modeName)?.value == true) {
                it.onWorld(event)
            }
        }
    }

    fun debugMessage(str: String) {
        if (debugValue.get()) {
            alert("§7[§c§lDisabler§7] §b$str")
        }
    }

    private val modeValue: List<Value<*>> get() = settings

    override val values = modeValue.toMutableList().also {
        modes.map { mode ->
            mode.values.forEach { value ->
                it.add(value.displayable { getValue(mode.modeName)?.value == true })
            }
        }
    }
}

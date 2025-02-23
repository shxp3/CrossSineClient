 
package net.ccbluex.liquidbounce.features.command.commands

import net.ccbluex.liquidbounce.features.command.Command
import net.ccbluex.liquidbounce.utils.misc.StringUtils

class SayCommand : Command("say", emptyArray()) {
    /**
     * Execute commands with provided [args]
     */
    override fun execute(args: Array<String>) {
        if (args.size > 1) {
            val str = StringUtils.toCompleteString(args, 1)
            mc.thePlayer.sendChatMessage(str)
            return
        }
        chatSyntax("say <message...>")
    }
}

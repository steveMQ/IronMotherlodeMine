package scripts;

import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Waiting;

public class InterfaceActions {

    public boolean openMagicTab() {
        return GameTab.MAGIC.open()  && Waiting.waitUntil(GameTab.MAGIC::isOpen);
    }

}

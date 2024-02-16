package scripts;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;

import java.util.function.BooleanSupplier;

public class StrutHandler {

    private void strutFixer(Travel travel) {

        Information info = new Information();
        MiningActions mactions = new MiningActions();

        long repairStartTime;
        BooleanSupplier brokenStrutExists = info::checkForBrokenStruts;

        if(brokenStrutExists.getAsBoolean()) {
            Log.warn("Broken strut detected");
            Log.trace("Walking to the water wheel");
            BooleanSupplier hasArrivedAtWheel = travel::walkToFirstWaterWheel;
            Waiting.waitUntil(hasArrivedAtWheel);
            Waiting.wait(1500);
        }

        while(brokenStrutExists.getAsBoolean()) {
            Log.trace("Fixing struts");
            BooleanSupplier hasClickedStruts = mactions::clickedStruts;

            Waiting.waitUntil(hasClickedStruts);
            Log.debug("clicked struts = " + hasClickedStruts.getAsBoolean());
            boolean isFixing = true;

            while(isFixing){
                isFixing = MyPlayer.isAnimating();
                Waiting.wait(600);
                if(!isFixing) {
                    Waiting.waitNormal(600, 25);
                    isFixing = MyPlayer.isAnimating();
                    if(!isFixing) {
                        Waiting.waitNormal(600, 25);
                        isFixing = MyPlayer.isAnimating();
                        if(!isFixing) {
                            Waiting.waitNormal(600, 25);
                            isFixing = MyPlayer.isAnimating();
                            if(!isFixing) {
                                Waiting.waitNormal(600, 25);
                            }
                        }
                    }
                }
            }
            Log.debug("Checking for broken struts before we leave");
            brokenStrutExists = info::checkForBrokenStruts;
        }


        Log.trace("All Struts are now functioning properly!");
    }


}

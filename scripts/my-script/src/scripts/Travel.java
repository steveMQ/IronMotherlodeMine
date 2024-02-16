package scripts;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Options;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.LocalWalking;

import java.util.function.BooleanSupplier;

public class Travel {

    Information info = new Information();
    MiningActions mactions = new MiningActions();

    void checkIfWeShouldRun() {
        if(MyPlayer.getRunEnergy() > 40 && !Options.isRunEnabled()) {
            Log.info("Energy is " + MyPlayer.getRunEnergy() + ", RUN enabled!");
            Options.setRunEnabled(true);
        }
    }
    boolean walkToFirstWaterWheel() {
        final Area FIRST_WATERWHEEL = Area.fromRadius(new WorldTile(3741, 5669, -1), 3);
        return LocalWalking.walkTo(FIRST_WATERWHEEL.getCenter()) && Waiting.waitUntil(FIRST_WATERWHEEL::containsMyPlayer);
    }

    void walkToHammerCrate() {
        final Area HAMMER_CRATE = Area.fromRadius(new WorldTile(3752, 5672, -1), 2);
        if (LocalWalking.walkTo(HAMMER_CRATE.getCenter()) && Waiting.waitUntil(HAMMER_CRATE::containsMyPlayer)) {
            Waiting.waitNormal(1000, 90);
        }
    }

    void walkToDepositBin() {
        final Area MOTHERLODE_DEPOSIT = Area.fromRadius(new WorldTile(3758, 5664, -1), 2);
        if (LocalWalking.walkTo(MOTHERLODE_DEPOSIT.getCenter()) && Waiting.waitUntil(MOTHERLODE_DEPOSIT::containsMyPlayer)) {
            Waiting.waitNormal(1000, 90);
        }
    }

    boolean getToTheOreSack() {
        final Area MOTHERLODE_SACK = Area.fromRadius(new WorldTile(3749, 5659, -1), 3);
        return LocalWalking.walkTo(MOTHERLODE_SACK.getCenter()) && Waiting.waitUntil(MOTHERLODE_SACK::containsMyPlayer);
    }

    void getToTheHopper() {
        final Area MOTHERLODE_HOPPER = Area.fromRadius(new WorldTile(3749, 5672, -1), 2);
        final Area upperLadder = Area.fromRadius(new WorldTile(3755, 5675, -1), 0);
        while (!MOTHERLODE_HOPPER.containsMyPlayer()) {
            Log.trace("Walking to the hopper...");

            // this is basically anywhere on the upper floor
            if(
                    Area.fromRadius(new WorldTile(3761, 5678, -1), 5).containsMyPlayer() ||
                            Area.fromRadius(new WorldTile(3761, 5673, -1), 5).containsMyPlayer() ||
                            Area.fromRadius(new WorldTile(3753, 5683, -1), 6).containsMyPlayer() ||
                            Area.fromRadius(new WorldTile(3755, 5676, -1), 5).containsMyPlayer() ||
                            Area.fromRadius(new WorldTile(3759, 5683, -1), 5).containsMyPlayer()
            ){
                Log.info("We are on the upper level. Be on the lockout for rockfall in the way");





                if (info.checkForRockfall() && Area.fromRadius(new WorldTile(3761, 5678, -1), 6).containsMyPlayer()) {
                    Log.debug("We found a rock closeby. Lets fuck it up.");
                    mactions.removeRockfall();
                    Waiting.wait(5000);
                }
                Log.info("walking towards the upper ladder");
                if (LocalWalking.walkTo(upperLadder.getCenter())) {
                    Waiting.waitUntil(5, upperLadder::containsMyPlayer);
                }
                else {
                    return;
                }
                BooleanSupplier hasClimbedLadder = mactions::clickLadder;
                Waiting.waitUntil(5, hasClimbedLadder);
                Waiting.waitUntil(Area.fromRadius(new WorldTile(3755, 5672, -1), 1)::containsMyPlayer);		// lower ladder
            }
            if (LocalWalking.walkTo(MOTHERLODE_HOPPER.getCenter())) {
                Waiting.waitUntil(MOTHERLODE_HOPPER::containsMyPlayer);
            }
        }
    }

}

package scripts;

import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.input.Keyboard;
import org.tribot.script.sdk.query.Query;

import java.util.function.BooleanSupplier;

public class MiningActions {

    void grabHammerFromCrate() {
        Query.gameObjects()
                .nameEquals("Crate")
                .findClosest()
                .map(crate -> crate.interact("Search"));
    }

     void mine(String oreName) {
        Query.gameObjects()
                .nameEquals(oreName)
                .findBestInteractable()
                .map(rock -> rock.interact("Mine"));
    }

    private Boolean washOresInHopper() {
        Boolean request = Query.gameObjects()
                .nameEquals("Hopper")
                .findClosest()
                .map(hopper -> hopper.interact("Deposit"))
                .orElse(false);
        Waiting.wait(2000);

        return request;
    }

    private boolean searchOreSack() {
        return Query.gameObjects()
                .nameEquals("Sack")
                .findClosest()
                .map(sack -> sack.interact("Search"))
                .orElse(false);

    }

    Boolean clickedStruts() {
        return Query.gameObjects()
                .actionEquals("Hammer")
                .findClosest()
                .map(brokenStrut -> brokenStrut.interact("Hammer"))
                .orElse(false);
    }

    private void depositOresAndGems() {

        Waiting.waitNormal(600, 5);

        if(Query.inventory().nameContains("Golden").isAny()){
            Bank.depositAll("Golden nugget");
            System.out.print("nug");
            Waiting.waitNormal(600, 5);
        }

        if(Query.inventory().nameContains("Runite").isAny()){
            Bank.depositAll("Runite ore");
            System.out.print("rune");
            Waiting.waitNormal(600, 5);
        }

        if(Query.inventory().nameContains("Adamantite").isAny()){
            Bank.depositAll("Adamantite Ore");
            System.out.print("add");
            Waiting.waitNormal(600, 5);
        }

        if(Query.inventory().nameContains("Mithril").isAny()){
            Bank.depositAll("Mithril ore");
            Waiting.waitNormal(600, 5);
        }

        if(Query.inventory().nameContains("Gold").isAny()){
            Bank.depositAll("Gold ore");
            Waiting.waitNormal(600, 5);
        }

        if(Query.inventory().nameContains("Coal").isAny()){
            Bank.depositAll("Coal");
            Waiting.waitNormal(600, 5);
        }

        if(Query.inventory().nameContains("ruby").isAny()){
            Bank.depositAll("uncut ruby");
            Waiting.waitNormal(600, 5);
        }

        if(Query.inventory().nameContains("emerald").isAny()){
            Bank.depositAll("uncut emerald");
            Waiting.waitNormal(600, 5);
        }

        if(Query.inventory().nameContains("sapphire").isAny()){
            Bank.depositAll("uncut sapphire");
            Waiting.waitNormal(600, 5);
        }

        if(Query.inventory().nameContains("diamond").isAny()){
            Bank.depositAll("uncut diamond");
            Waiting.waitNormal(600, 5);
        }
    }

    void removeRockfall() {
        Query.gameObjects()
                .nameEquals("Rockfall")
                .findClosest()
                .map(rockfall -> rockfall.interact("Mine"));
    }

    public void processOresInSack(Travel travel) {
        int hopperCount = GameState.getVarbit(5558);

        while(hopperCount != 0) {
            Log.trace("We have " + hopperCount + " ore to collect!");
            BooleanSupplier arrivedAtOreSack = travel::getToTheOreSack;
            Waiting.waitUntil(arrivedAtOreSack);
            Log.debug("arrived at ore sack");
            Waiting.waitNormal(1500, 350);

            BooleanSupplier oreSackOpened= this::searchOreSack;
            Waiting.waitUntil(oreSackOpened);

            int initialInventoryCount = Query.inventory().count();
            int currentInventoryCount = Query.inventory().count();

            // makeshift listener to detect when we actually get ore form the sack
            while(initialInventoryCount == currentInventoryCount) {
                currentInventoryCount = Query.inventory().count();
                Waiting.wait(1000);
            }

            openDepositBin();

            while (!Bank.isDepositBoxOpen()){
                Waiting.waitNormal(100, 5); // let's sleep before the next action
            }

            depositOresAndGems();

            Waiting.waitNormal(1000,67);
            Keyboard.pressEscape();
            hopperCount = GameState.getVarbit(5558);
        }
    }

    boolean clickLadder() {

        Log.debug("clicked ladder");
        return Query.gameObjects()
                .nameEquals("Ladder")
                //.idEquals(19044)
                .findBestInteractable()
                .map(ladder -> ladder.interact("Climb"))
                .orElse(false);
    }

    private boolean openDepositBin() {
        return Query.gameObjects()
                .nameEquals("Bank deposit box")
                .findClosest()
                .map(banker -> banker.interact("Deposit"))
                .orElse(false);
    }







}

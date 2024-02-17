package scripts;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.EquipmentItem;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.WorldTile;

import java.util.Arrays;
import java.util.Optional;

public class Information {

    Boolean checkForRockfall() {
        return Query.gameObjects()
                .nameEquals("Rockfall")
                .inArea(Area.fromRadius(new WorldTile(3757, 5677, -1), 1))
                .findClosest()
                .isPresent();
    }

    Optional<InventoryItem> itemExistsInSatchel(String itemName) {
        return Query.inventory().nameContains(itemName).findFirst();
    }

    Optional<InventoryItem> hasHammer(){
        return Query.inventory().nameContains("hammer").findFirst();
    }

    static boolean hasPickaxeInInventory(){
        return Query.inventory().nameContains("pickaxe").isAny();
    }

    static boolean hasPickaxeInBank(String name){ return Query.bank().nameContains(name).isAny();
    }


   static boolean hasPickaxeEquipped(){return Query.equipment().nameContains("pickaxe").isAny();}

    Boolean checkForBrokenStruts() {
        return Query.gameObjects().actionEquals("Hammer").findClosest().isPresent();
    }

    private int getBrokenStrutCount() {
        Object[] matches = Query.gameObjects().actionEquals("Hammer").stream().toArray();
        Log.info("matches length = " + Arrays.stream(matches).count());
        return (int) Arrays.stream(matches).count();
    }

    void getSessionStats(int a, int b, int c) {
        Log.info("-------------------------");
        Log.warn("Runtime: " + a + " m" );
        Log.warn("XP: " + (b/1000) + "k");
        Log.warn("XP/hr: " + (c/1000) +"k");
        Log.info("-------------------------");
    }


    String[] retrieveArgs(String args){
        return args.split(",");
    }



}

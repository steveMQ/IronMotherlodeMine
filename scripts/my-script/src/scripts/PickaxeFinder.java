package scripts;

import org.tribot.script.sdk.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickaxeFinder {

    public static String getPickaxes(int miningLevel) {
        Map<String, Integer> pickaxes = new HashMap<>();
        //pickaxes.put("Bronze", 1);
        pickaxes.put("Iron", 1);
        pickaxes.put("Steel", 6);
        pickaxes.put("Black", 11);
        pickaxes.put("Mithril", 21);
        pickaxes.put("Adamant", 31);
        pickaxes.put("Rune", 41);
        pickaxes.put("Dragon", 61);
        pickaxes.put("Infernal", 61);
        pickaxes.put("Crystal", 71);

        List<String> possiblePickaxes = new ArrayList<>(pickaxes.keySet());

        // Filter by mining level
        possiblePickaxes.removeIf(pickaxe -> pickaxes.get(pickaxe) > miningLevel);

        // Sort pickaxes by level requirement in descending order
        possiblePickaxes.sort((p1, p2) -> pickaxes.get(p2) - pickaxes.get(p1));

        String selection = "N/A";

        Log.info(possiblePickaxes.toString());


        for(String pickaxe : possiblePickaxes) {

            boolean hasPickaxe = Information.hasPickaxeInBank(pickaxe);

            Log.debug("Pickaxe = " + pickaxe);
            Log.debug("hasPickaxe = " + hasPickaxe);

            if(hasPickaxe){
                selection = pickaxe;
                break;
            }
            else {
                Log.info("no match for --> " + pickaxe);
            }
            Log.info("Possible pickaxes --> " + possiblePickaxes.toString());
        }


       // return possiblePickaxes.toArray(new String[0]);
        return selection;
    }




}

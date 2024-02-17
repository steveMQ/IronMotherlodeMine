package scripts;
import org.tribot.script.sdk.Log;
import java.sql.Array;
import java.util.*;

public class OreSelector {

    public static String getOreSelection(int miningLevel) {
        Map<String, Integer> ores = new HashMap<>();
        ores.put("Iron", 15);
        ores.put("Tin", 1);
        ores.put("Copper", 1);




        List<String> possibleOres = new ArrayList<>(ores.keySet());

        // Filter by mining level
        possibleOres.removeIf(ore -> ores.get(ore) > miningLevel);

        // Sort pickaxes by level requirement in descending order
        possibleOres.sort((p1, p2) -> ores.get(p2) - ores.get(p1));

        Log.info(possibleOres.toString());

        if (miningLevel < 15) {
            // Create a Random object
            Random random = new Random();
            // Generate a random number between 0 (inclusive) and x (exclusive)
            int randomNumber = random.nextInt(possibleOres.size() - 1);

            return possibleOres.get(1);
        }


        return possibleOres.get(0);
    }




}

package scripts;

import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

public class ImportantLocations {

    final static Area LumbridgeSpawn  = Area.fromRadius(new WorldTile(3222, 3219, 0), 5);
    final static Area VarrockEastBank = Area.fromRadius(new WorldTile(3253, 3420, 0), 2);

    final static Area VarrockEastMine = Area.fromRadius(new WorldTile(3284, 3365, 0), 2);



}

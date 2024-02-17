package scripts;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.EquipmentQuery;
import org.tribot.script.sdk.query.GrandExchangeOfferQuery;
import org.tribot.script.sdk.query.InventoryQuery;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GrandExchangeOffer;
import org.tribot.script.sdk.types.InventoryItem;

public class AccountBuilder {
    TravelLogic travel = new TravelLogic();
    MiningActions ma = new MiningActions();

    public AccountBuilder(){

        while(true){
            start();
        }

    }

    public void start() {

        while (Skill.MINING.getActualLevel() < 31) {
            // something breaks here if i have a pickaxe in inventory


            if (!Information.hasPickaxeEquipped() && !Information.hasPickaxeInInventory() || Query.inventory().count() == 28) {

                if (!ImportantLocations.VarrockEastBank.containsMyPlayer()) {
                    //Log.debug("We are not at the bank... lets go home first!");
                    //Waiting.waitUntil(travel::goHome);

                    //Log.info("Arrived in lumbridge!");
                    //Log.debug("Waiting 10 seconds in case the ui doesnt update");
                    //Waiting.wait(10000);
                    Log.debug("Off to Varrock!");

                    Waiting.waitUntil(120000, TravelLogic::walkToVarrockEastBank);
                    Waiting.wait(1000);
                }

                Log.debug("We arrived!");
                Waiting.waitUntil(MiningActions::openBankWindow);

                while (!Bank.isOpen()) {
                    Waiting.wait(600);
                }

                Log.debug("Bank interface opened");
                int miningLevel = Skill.MINING.getActualLevel();
                Log.info("mining level = " + miningLevel);

                //MiningActions.depositOresAndGems();
                // deposit entire inventory for a change
                Bank.depositInventory();

                String pickaxeSelection = PickaxeFinder.getPickaxes(miningLevel) + " pickaxe";
                if(!Equipment.contains(pickaxeSelection)){

                    Log.debug("Lets try to get my  --> " + (pickaxeSelection));
                    MiningActions.getPickaxe(pickaxeSelection);
                    Waiting.waitUntil(() -> Query.inventory().nameContains(pickaxeSelection).isAny());
                    Log.info("we got a pickaxe!");
                    Waiting.waitNormal(1500, 500);
                }

                Bank.close();
                Waiting.waitNormal(1500, 500);

            } else {
                Log.debug("we have a pickaxe!");
            }

            if(!Equipment.contains("Mithril pickaxe") && Skill.MINING.getActualLevel() >= 21){

                Log.warn("We can get a mithril pickaxe!");

                Waiting.waitUntil(TravelLogic::walkToTheGrandExchange);
                Waiting.waitNormal(1500,127);

                Waiting.waitUntil(GrandExchange::open);
                Waiting.waitNormal(1500,127);

                GrandExchange.placeOffer(
                    GrandExchange
                        .CreateOfferConfig
                            .builder()
                            .itemName("Mithril pickaxe")
                            .priceAdjustment(10)
                            .quantity(1)
                            .slot(GrandExchangeOffer.Slot.ONE)
                            .build()
                );

                Waiting.waitNormal(10000,127);

                GrandExchange.collectAll();

                Query.inventory()
                        .nameContains("Mithril pickaxe")
//                        .idEquals(12345)
                        .findFirst()
                        .map(InventoryItem::click)
                        .orElse(false);




            }

            if (!ImportantLocations.VarrockEastMine.containsMyPlayer() && Query.inventory().count() < 28) {
                Log.debug("we are not at the varrock mine.");
                Waiting.waitUntil(120000, TravelLogic::walkToVarrockEastMine);
                Log.debug("We have arrived at the mine!");
                Waiting.waitNormal(3000, 700);
            }
            this.beginMiningLoop(Skill.MINING.getActualLevel());
        }

        Log.warn("We are finally able to get into the Motherlode Mine (with an addy pick, i waited 1 level!)");
        Log.info("Lets go take care of some things first.");

    }

    private void beginMiningLoop(int lvl){
        Log.warn("We have begun the mining routine!");
        String oreSelection = OreSelector.getOreSelection(lvl);
        Log.info(oreSelection);

        while(Query.inventory().count() < 28) {


            MiningActions.mine(oreSelection+" rocks");
            Log.trace("You swing your pick at the rock...");
            Waiting.waitNormal(1000,5);

            // this is a sluice for 5 consecutive game ticks
            // animations toggle between mining and not mining, while actually mining
            // if character is not animating for 5 consecutive ticks, they are idle
            // and the process escapes the sluice
            MiningActions.playerIsMiningValidator(100);
            Log.debug("Ore depleted");
        }
        TravelLogic.checkIfWeShouldRun();
        TravelLogic.walkToVarrockEastBank();
        Waiting.waitNormal(2500, 800);
        Log.info("loop starting again");



    }
}

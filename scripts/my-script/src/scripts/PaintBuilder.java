package scripts;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.painting.Painting;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;

import java.awt.*;

public class PaintBuilder {

    public PaintBuilder(double scriptStartTime, double startingMiningXP) {
        PaintTextRow template = PaintTextRow.builder().background(Color.green.darker()).build();
        // template is optional stuff to not repeat same colors/fonts for every row

        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.scriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                //.row(template.toBuilder().label("Test").value("ing").onClick(() -> Log.log("CLICKED!")).build())
                .row(template.toBuilder().label("XP/hr").value(() -> {
                    double bignum = (3600000);
                    double divisor = (System.currentTimeMillis() - scriptStartTime) / bignum;
                    divisor = divisor * 1000;
                    String output = String.valueOf(Math.round((Skill.MINING.getXp() - startingMiningXP) / divisor));
                    return output + "k";
                }).build())
                .row(template.toBuilder().label("TTL:").value(() -> {
                    double bignum = (3600000);
                    double divisor = (System.currentTimeMillis() - scriptStartTime) / bignum;
                    divisor = divisor * 1000;
                    double hourlyXP = Math.round((Skill.MINING.getXp() - startingMiningXP) / divisor);
                    double nextLevel = Skill.MINING.getCurrentXpToNextLevel();
                    double timeToNextLevel = (nextLevel / hourlyXP) / 1000;
                    return String.format("%.2f",timeToNextLevel).concat(" hrs");
                }).build())
                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();
        Painting.addPaint(paint::render);
        Log.debug("Paint initialized");
    }

}

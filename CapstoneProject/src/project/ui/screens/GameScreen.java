package project.ui.screens;

import gameutils.math.*;
import processing.core.*;
import project.*;
import project.core.Events.Event;
import project.core.Input.*;
import project.core.UI.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.ui.*;
import project.ui.bars.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.graphics.Pal.*;

/** Contains all the UI of a game screen. */
public class GameScreen extends Screen{
    public Table playerHealth, playerShield, playerAmmo, playerExp, enemyHealth;

    public Sprite background = new Sprite().set(SpritePath.backgrounds, "space2");
    public float rot = random(0, 360);
    public Vec2 pan = new Vec2();

    @Override
    public void init(){
        playerHealth = new SmoothBar(width / 2f, 10).progress(() -> world.player.fin()).color(healthRed).alignX(AlignX.center).x(width / 2f).y(height - 55);
        playerShield = new SmoothBar(width / 2.5f, 8).progress(() -> world.player.shield.fin()).color(shieldBlue).alignX(AlignX.center).x(width / 2f).y(height - 70);
        playerAmmo = new SegmentedBar(width / 3f, 5, 3).segments(() -> world.player.weapon.charges()).progress(() -> world.player.weapon.fin()).color(expGray).alignX(AlignX.center).x(width / 2f).y(height - 80);

        playerExp = new SmoothBar(width - 100, 5).progress(() -> world.player.exp / pow(expScaling, world.player.level) / baseLevelExp).color(expGray).alignX(AlignX.center).x(width / 2f).y(8);
        enemyHealth = new SmoothBar(width / 4f, 3).progress(() -> world.player.lastHit.fin()).color(expGray).alignX(AlignX.center).x(width / 2f).y(18);

        events.on(Event.playerKilled, event -> {
            canvas.shake(100);
            canvas.screen(ui.loseScreen);
        });
    }

    @Override
    public void rebuild(){
        canvas.cursor(PConstants.CROSS);
    }

    @Override
    public void update(){
        rot += 0.025f;

        delta += (1f - delta) / 10f;

        pan.add(Tmp.v1.set(world.player.pos).sub(pan).scl(0.1f));

        world.update();

        if(input.pressed(KeyBind.upgrade)){
            input.consume(KeyBind.upgrade);
            if(3 > world.player.spent) canvas.screen(ui.upgradeScreen);
            else canvas.screen(ui.pauseScreen);
        }
    }

    @Override
    public void draw(){
//        canvas.pushMatrix();
//        canvas.translate(-pan.x / 50f, -pan.y / 50f);
//        canvas.tint(255, 255, 255);
//        background.draw(Tmp.v1.setr(rot, 1).x * 550 - 550, Tmp.v1.y * 100 - 100, (float)background.image.width * (height + 200) / background.image.height, height + 200, Color.white);
//        canvas.popMatrix();

        canvas.fill(220, 220, 220);
        canvas.rect(0, 0, width, height);

        canvas.push();
        canvas.translate(canvas.random(-1, 1) * canvas.shake * screenShake, canvas.random(-1, 1) * canvas.shake * screenShake);
        world.draw();
        canvas.pop();

        if(world.player.keep()){
            canvas.fill(100, 100, 100, 100);
            canvas.textSize(30);
            canvas.textAlign(canvas.CENTER, canvas.TOP);
            canvas.text("WAVE: " + world.waves.wave, width / 2f, 20);

            playerHealth.process();
            playerShield.process();
            if(world.player.weapon.charges() > 1) playerAmmo.process();
            playerExp.process();
            if(world.player.lastHit != null && world.player.lastHit.keep()) enemyHealth.process();
        }
    }
}

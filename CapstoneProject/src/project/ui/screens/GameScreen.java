package project.ui.screens;

import gameutils.math.*;
import project.*;
import project.core.Events.*;
import project.core.Input.*;
import project.core.UI.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.ui.*;
import project.ui.bars.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.graphics.Pal.*;

/** Contains all the UI of a game screen. */
public class GameScreen extends Screen{
    public Table playerHealth, playerShield, playerAmmo, playerExp;

    public Sprite background = new Sprite(SpritePath.backgrounds, "space2");
    public float rot = random(0, 360);
    public Vec2 pan = new Vec2();

    @Override
    public void init(){
        playerHealth = new SmoothBar(width / 2f, 10).progress(() -> world.player.fin()).color(healthRed).alignX(AlignX.center).x(width / 2f).y(height - 55);
        playerShield = new SmoothBar(width / 2.5f, 8).progress(() -> world.player.shield.fin()).color(shieldBlue).alignX(AlignX.center).x(width / 2f).y(height - 70);
        playerAmmo = new SegmentedBar(width / 3f, 5, 3).segments(() -> world.player.weapon.charges()).progress(() -> world.player.weapon.fin()).color(expGray).alignX(AlignX.center).x(width / 2f).y(height - 80);

        playerExp = new SmoothBar(width - 100, 5).progress(() -> world.player.exp / pow(expScaling, world.player.level) / baseLevelExp).color(expGray).alignX(AlignX.center).x(width / 2f).y(10);

        events.on(Event.playerKilled, event -> {
            canvas.shake(100);
            canvas.screen(ui.loseScreen);
        });
    }

    @Override
    public void update(){
        rot += 0.025f;

        pan.add(Tmp.v1.set(world.player.pos).sub(pan).scl(0.1f));

        world.update();

        if(input.pressed(KeyBind.upgrade)){
            input.consume(KeyBind.upgrade);
            if(world.player.level > world.player.spent) canvas.screen(ui.upgradeScreen);
            else canvas.screen(ui.pauseScreen);
        }
    }

    @Override
    public void draw(){
        canvas.pushMatrix();
        canvas.translate(-pan.x / 50f, -pan.y / 50f);
        canvas.tint(255, 255, 255);
        background.drawh(Tmp.v1.setr(rot, 1).x * 550 - 550, Tmp.v1.y * 100 - 100, height + 200);
        canvas.popMatrix();

        canvas.fill(0, 0, 0, 150);
        canvas.rect(0, 0, width, height);

        canvas.push();
        canvas.translate(canvas.random(-1, 1) * canvas.shake * screenShake, canvas.random(-1, 1) * canvas.shake * screenShake);
        world.draw();
        canvas.pop();

        if(world.player.keep()){
            playerHealth.process();
            playerShield.process();
            if(world.player.weapon.charges() > 1) playerAmmo.process();
            playerExp.process();
        }
    }
}

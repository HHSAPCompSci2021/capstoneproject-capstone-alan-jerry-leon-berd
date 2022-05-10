package project.ui.screens;

import project.*;
import project.core.Events.*;
import project.core.Input.*;
import project.core.UI.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.ui.bars.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.graphics.Pal.*;

/** Contains all the UI of a game screen. */
public class GameScreen extends Screen{
    public ProgressBar playerHealth;
    public ProgressBar playerShield;
    public ProgressBar playerExp;

    public Sprite background = new Sprite(SpritePath.backgrounds, "space2");
    ;
    public float rot = random(0, 360), shieldGlow = 50, healthGlow = 50;

    @Override
    public void init(){
        playerHealth = (SmoothBar)new SmoothBar(width / 2f, 10).glow(() -> healthGlow).progress(() -> world.player.fin()).color(healthRed).alignX(AlignX.center).x(width / 2f).y(height - 55).update(t -> {
            healthGlow += (50 - healthGlow) / 10;
        });
        playerShield = (SegmentedBar)new SegmentedBar(width / 2.5f, 8, 5, 5).glow(() -> shieldGlow).progress(() -> world.player.shield.fin()).color(shieldBlue).alignX(AlignX.center).x(width / 2f).y(height - 70).update(t -> {
            shieldGlow += (50 - shieldGlow) / 10;
        });
        playerExp = (SmoothBar)new SmoothBar(width - 100, 5).progress(() -> world.player.exp / pow(expScaling, world.player.level) / baseLevelExp).color(expGray).alignX(AlignX.center).x(width / 2f).y(10);

        events.on(Event.playerDamage, e -> {
            if(world.player.shield.value > 0) shieldGlow = 200;
            else healthGlow = 200;
        });
    }

    @Override
    public void update(){
        rot += 0.025f;

        world.update();

        if(input.pressed(KeyBind.pause)){
            input.consume(KeyBind.pause);
            canvas.screen(ui.pauseScreen);
        }
    }

    @Override
    public void draw(){
        canvas.pushMatrix();
        canvas.translate(-world.player.x() / 50f, -world.player.y() / 50f);
        background.drawh(Tmp.v1.setr(rot, 1).x * 550 - 550, Tmp.v1.y * 100 - 100, height + 200);
        canvas.popMatrix();

        canvas.fill(0, 0, 0, 150);
        canvas.rect(0, 0, width, height);

        canvas.push();
        canvas.translate(canvas.random(-1, 1) * canvas.shake * screenShake, canvas.random(-1, 1) * canvas.shake * screenShake);
        world.draw();
        canvas.pop();

        playerHealth.process();
        playerShield.process();
        playerExp.process();
    }
}

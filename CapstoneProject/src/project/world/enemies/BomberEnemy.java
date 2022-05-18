package project.world.enemies;

import project.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.world.bullets.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

public class BomberEnemy extends Enemy{
	private float thrustDuration = 60;
	public float shootInterval = 3;
	public float inaccuracy = 20;
	public float waveFrequency = 5;
	public float waveAmplitude = 5;
	
	public BomberEnemy() {
		super();
		
		color = new Color(255, 120, 0);
		reload = 1;
		rotate = 3;
		accel = 0.5f;
	}	
	
	@Override
	public void init(){
		if (sprite == null) sprite = new Sprite(SpritePath.enemies, "tracer");
		if (bullet == null) bullet = new Bullet() {{
			size = 2;
			speed = 5;
		}};
		super.init();
	}
	
	@Override
	public BomberEnemyEntity create() {
		return new BomberEnemyEntity(this);
	}
	public class BomberEnemyEntity extends EnemyEntity{
		public float wave;
		public BomberEnemyEntity(Enemy type) {
			super(type);
		}
		
		@Override
		public void update() {
			super.update();
			reloadt += reload();
			if (reloadt < 60) rotate(Tmp.v1.set(world.player.pos).sub(pos).ang());
			else {
				if ((reloadt - 60) % shootInterval < reload()) {
					shoot(180 + random(-inaccuracy, inaccuracy));
				}
				rotation += Math.sin(wave) * waveAmplitude;
				wave += waveFrequency;
				thrust();
				if (reloadt >= 60 + thrustDuration) reloadt = 0;
			}
			if (!world.bounds.contains(pos) && !justSpawned) {
				pos.x = mod(pos.x - world.bounds.x, world.bounds.w)+ world.bounds.x;
				pos.y = mod(pos.y - world.bounds.y, world.bounds.h) + world.bounds.y;
			}

		}
		
		@Override
		public boolean keep() {
			return life > 0;
		}
	}
}

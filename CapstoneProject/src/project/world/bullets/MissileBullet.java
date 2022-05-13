package project.world.bullets;

import project.world.ship.Ship;
import static project.Vars.*;

import gameutils.util.Mathf;
import project.Tmp;

public class MissileBullet extends Bullet{
	public float homingPower = 5;
	public float homingRange = 200;
	
	public MissileBulletEntity create() {
		return new MissileBulletEntity(this);
	}
	
	public class MissileBulletEntity extends BulletEntity{
		public Ship target;
		
		public MissileBulletEntity(MissileBullet type) {
			super(type);
		}
		
		public void update() {
			super.update();
			
			if (target == null || !target.keep()) {
				world.ships.query(pos.x, pos.y, homingRange, e -> {
					if (e.team != team) target = e;
				});
			} else {
				float wanted = Tmp.v1.set(target.pos).sub(pos).ang();
				rotation = Mathf.turn(rotation, wanted, homingPower);
			}
		}
	}
}

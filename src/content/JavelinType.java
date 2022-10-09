package content;

import arc.math.Rand;
import arc.util.Log;
import mindustry.entities.bullet.BulletType;
import mindustry.type.UnitType;

public class JavelinType extends UnitType
{
    public static final Rand random = new Rand();

    public float lightingCone = 12f;
    public float lightingDelay = 0.64f;
    public float dischargeMaximalVelocity = 7.2f;
    public float dischargeDelay = 0.64f;
    public float chargeSpeed = 0.28f;
    public int chargeLimit = 6;
    public float chargeDamageMultiplier = 0.64f;
    public BulletType emmitBullet;

    public JavelinType(String name) {
        super(name);
        constructor = JavelinEntity::new;
        Log.info(constructor);
    }
}

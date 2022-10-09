package content;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Rand;
import arc.util.Log;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;

public class JavelinShipType extends UnitType
{
    public float chargeSpeed = 0.28f;
    public int chargeLimit = 6;
    public float chargeDamageMultiplier = 0.64f;
    public float lightingCone = 12f;
    public float lightingDelay = 0.64f;
    public float dischargeMaximalVelocity = 7.2f;
    public float dischargeDelay = 0.64f;
    public Sound dischargeSound;
    public BulletType dischargeBullet;
    public TextureRegion chargeShield;
    public Color chargeShieldColor = Pal.lancerLaser;

    public JavelinShipType(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        chargeShield = Core.atlas.find(name + "-shield");
        Log.info(name + "-shield");
        Log.info(chargeShield);
    }
}

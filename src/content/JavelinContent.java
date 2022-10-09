package content;

import arc.graphics.Color;
import mindustry.ai.types.BuilderAI;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.world.blocks.storage.CoreBlock;

public class JavelinContent
{
    public static UnitType javelin;

    public static void Load()
    {
        javelin = new JavelinType("javelin")
        {{
            aiController = BuilderAI::new;
            isEnemy = false;

            flying = true;
            mineSpeed = 24f;
            mineTier = 2;
            buildSpeed = 0.75f;
            drag = 0.05f;
            speed = 14.6f;
            rotateSpeed = 2.4f;
            accel = 0.02f;
            itemCapacity = 50;
            health = 420f;
            engineOffset = 6f;
            hitSize = 9f;
            faceTarget = true;
            lowAltitude = true;

            emmitBullet = new LightningBulletType()
            {{
                damage = 48f;
                legCount = 8;
            }};

            weapons.add(new Weapon("javelin-ship-launcher"){{
                top = false;
                reload = 20f;
                x = 3f;
                y = 0.5f;
                rotate = false;
                shoot.shotDelay = 1.6f;
                ejectEffect = Fx.casing1;

                bullet = new MissileBulletType(3f, 140){{
                    width = 7f;
                    height = 9f;
                    lifetime = 60f;
                    homingRange = 48f;
                    homingPower = 0.1f;
                    shootEffect = Fx.shootSmall;
                    buildingDamageMultiplier = 0.01f;
                    backColor = Pal.lancerLaser;
                    frontColor = Color.white;
                    trailColor = Pal.lancerLaser;
                    engineColor = Pal.lancerLaser;
                }};
            }});
        }};

        ((CoreBlock)Blocks.coreNucleus).unitType = javelin;
    }
}

package content;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.graphics.Shaders;
import mindustry.logic.GlobalVars;
import mindustry.type.UnitType;

public class JavelinShipEntity extends UnitEntity {
    protected JavelinShipType javelinType;
    protected float _initialDamage;
    protected float _lightingDelay;
    protected float _dischargeSpeed;
    protected int _chargeWeight;
    protected float _effectWeight;
    protected float _effectRotation = 0;

    @Override
    public void setType(UnitType type) {
        super.setType(type);
        Log.info("Type seated: " + type);
        javelinType = (JavelinShipType) type;
        _initialDamage = javelinType.dischargeBullet.damage;
    }

    @Override
    public void update() {
        super.update();

        if (JaveLib.IsDischarging && _chargeWeight > 0) {
            ReleaseCharge(_chargeWeight);
        }

        if (vel.len2() < javelinType.dischargeMaximalVelocity)
        {
            if (_chargeWeight > 0) {
                Log.info("_dischargeSpeed: " + _dischargeSpeed);
                _dischargeSpeed += Time.delta;
                if (_dischargeSpeed > javelinType.dischargeDelay) {
                    _dischargeSpeed -= javelinType.dischargeDelay;
                    ReleaseCharge(1);
                }
            }

        } else {
            float velocity = Mathf.sqrt(vel.len2() - javelinType.dischargeMaximalVelocity);
            _lightingDelay += velocity / (javelinType.speed - velocity) * Time.delta * javelinType.chargeSpeed;
            if (_lightingDelay > javelinType.lightingDelay)
            {
                _lightingDelay -= javelinType.lightingDelay;
                if (_chargeWeight < javelinType.chargeLimit)
                    _chargeWeight++;
            }
        }

        // Updates effect params
        _effectWeight -= (_effectWeight - _chargeWeight) * Time.delta * 0.1f;
        _effectRotation += Time.delta * 2;
        if (_effectRotation > 360)
            _effectRotation -= 360;
    }
    protected void ReleaseCharge(int amount)
    {
        javelinType.dischargeSound.play(0.2f + 0.1f * amount);
        javelinType.dischargeBullet.damage = _chargeWeight * javelinType.chargeDamageMultiplier;
        for (int i = 0; i < _chargeWeight; i++) {
            javelinType.dischargeBullet.create(this, team, x, y,
                                               rotation + GlobalVars.rand.random(-javelinType.lightingCone, javelinType.lightingCone));
        }
        javelinType.dischargeBullet.damage = _initialDamage;
        _chargeWeight = 0;
    }

    @Override
    public void draw() {
        super.draw();
        if (_chargeWeight < 0.01f) return;

        Drawf.square(x, y, _effectWeight * 2.5f, _effectRotation, _chargeWeight < javelinType.chargeLimit ? Pal.lancerLaser : Pal.redLight);
        Log.info("Alpha: " + (float)_chargeWeight / javelinType.chargeLimit);
        Draw.color(javelinType.chargeShieldColor);
        float normalizedWeight = (float)_chargeWeight / javelinType.chargeLimit;
        Draw.alpha(normalizedWeight * 0.25f);
        Draw.blend(Blending.additive);
        float normalizedShake = 0.2f * normalizedWeight;
        Draw.rect(javelinType.chargeShield, x + GlobalVars.rand.random(-normalizedShake, normalizedShake), y + GlobalVars.rand.random(-normalizedShake, normalizedShake), rotation - 90);
        Draw.blend();
        Draw.color();
    }


    @SuppressWarnings("SpellCheckingInspection")
    protected boolean aprox(float value, float target) {
        return target - 0.01f < value && value < target + 0.01f;
    }
    @SuppressWarnings("SpellCheckingInspection")
    protected boolean invaprox(float value, float target) {
        return target - 0.01f > value || value > target + 0.01f;
    }
}

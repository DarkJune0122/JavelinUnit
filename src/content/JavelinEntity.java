package content;

import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;

public class JavelinEntity extends UnitEntity {
    protected JavelinType javelinType;
    protected float _lightingDelay;
    protected float _dischargeSpeed;
    protected int _chargeWeight;
    protected float _effectWeight;
    protected float _effectRotation = 0;

    @Override
    public void setType(UnitType type) {
        super.setType(type);
        Log.info("Type seated: " + type);
        javelinType = (JavelinType) type;
    }

    @Override
    public void update() {
        super.update();

        if (JaveLib.IsDischarging && _chargeWeight > 0)
            ReleaseCharge(_chargeWeight);

        if (vel.len2() < javelinType.dischargeMaximalVelocity)
        {
            if (_chargeWeight > 0) {
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
        _effectWeight -= (_effectWeight - _chargeWeight) * Time.delta;
        _effectRotation += Time.delta * 2;
        if (_effectRotation > 360)
            _effectRotation -= 360;
    }
    protected void ReleaseCharge(int amount)
    {
        for (int i = 0; i < _chargeWeight; i++) {
            javelinType.emmitBullet.create(this, team, x, y, rotation + JavelinType.random.random(-javelinType.lightingCone, javelinType.lightingCone), _chargeWeight * javelinType.chargeDamageMultiplier);
        }
        _chargeWeight = 0;
    }

    @Override
    public void draw() {
        super.draw();
        if (_chargeWeight < 0.01f) return;

        Drawf.square(x, y, _effectWeight * 2.5f, _effectRotation, _chargeWeight < javelinType.chargeLimit ? Pal.lancerLaser : Pal.redLight);
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

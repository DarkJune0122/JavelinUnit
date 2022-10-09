import arc.Core;
import arc.input.InputProcessor;
import arc.input.KeyCode;
import arc.math.Rand;
import content.JaveLib;
import content.JavelinContent;
import mindustry.mod.Mod;

public class JavelinUnitMod extends Mod
{
    public static final Rand random = new Rand();
    public final InputProcessor javelinInputs = new InputProcessor() {
        @Override
        public boolean keyDown(KeyCode keycode) {
            if (keycode == DischargeKey) {
                JaveLib.IsDischarging = true;
            }
            return false;
        }
        @Override
        public boolean keyUp(KeyCode keycode) {
            if (keycode == DischargeKey) {
                JaveLib.IsDischarging = false;
            }
            return false;
        }
    };
    public static final KeyCode DischargeKey = KeyCode.c;

    @Override
    public void loadContent(){
        Core.input.getInputMultiplexer().addProcessor(javelinInputs);
        JavelinContent.Load();
    }


    /*Groups.unit.each(Unitc::isPlayer, unit -> {
        if (unit.isPlayer() && unit instanceof JavelinEntity)
        {
            ((JavelinEntity) unit).ReleaseCharge();
        }
    });*/
}
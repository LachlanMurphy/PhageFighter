package PhageFighter.Ability;

public class AbilityFactory {

    static public Ability createDeployTurretAbility(){
        return new DeployTurret();
    }
    static public Ability createTCellDashAbility() {
        return new TCellDash();
    }
}

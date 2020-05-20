public class AI {
    public enum Behaviour {
        Attack, Escape, Follow, Search, Idle, Goto
    };

    private boolean Change = false;
    private Behaviour actBehaviour = Behaviour.Idle;

    public AI() {

    }

    public void ChangeAction(Behaviour beh)
    {
        actBehaviour = beh;
        Change = true;
    }
    public void Do(){

        if(Change)
        {
            Change = false;
            switch (actBehaviour)
            {
                case Goto:
                    break;

                case Idle:
                    break;

                case Attack:
                    break;

                case Escape:
                    break;

                case Follow:
                    break;

                case Search:
                    break;

            }

        }

    }



    public void GoTo(Player player){
     float x = player.getX();
     float y = player.getY();
     float z = player.getZ();

            // ur code here plz
    }
    public void GoTo(float x, float y, float z){
        // ur code here plz
    }
    public void GoTo(Block block){
        // ur code here plz
    }

    public void Attack (Player player) {
        //type code here
    }
    public void Attack (mob mob) {
        //type code here
    }

}

public class Game {
    World world;

    public Game() {
        world = new World();
    }

    public void update() {
        world.update();
    }

    public void render() {
        world.render();
    }
}

package engine;

import java.util.List;

public class GameObject {

    private String name;
    private List<Component> components;

    public GameObject(String name) {
        this.name = name;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {

    }
}

import java.io.Serializable;
import java.util.Set;

public class SerializableObject implements Serializable {
    private static long PROTO_ID = 3000000;
    private long id;
    private String name;
    private int age;
    private Set<String> pets;
    private boolean isSingle;
    private char sign;
    public SerializableObject(String name, int age, Set<String> pets, boolean isSingle, char sign) {
        this.id = SerializableObject.PROTO_ID++;
        this.name = name;
        this.age = age;
        this.pets = pets;
        this.isSingle = isSingle;
        this.sign = sign;
    }
    public int getAge() {
        return this.age;
    }
    public Set<String> getPets() {
        return this.pets;
    }
    public char getSign() {
        return this.sign;
    }
    public long getID() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public boolean getIsSingle() {
        return this.isSingle;
    }
}

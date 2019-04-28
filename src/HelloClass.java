public class HelloClass {

    public String name;
    public int id;

    public HelloClass(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public boolean isThisClass(int inputId) {
        return this.id == inputId;
    }

    public String getRealName() {
        return this.name;
    }

    public void setRealName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
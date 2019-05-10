package testUtil;

public class Result {
    public String name = "";
    public boolean success = true;
    public String error = "";

    public Result(String name) {
        this.name = name;
    }

    public void setError(String error) {
        this.success = false;
        this.error = error;
    }

}

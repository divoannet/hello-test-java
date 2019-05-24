package testUtil;

public class Result {
    public String name = "";
    public boolean success = true;
    public String message = "";

    public Result(String name) {
        this.name = name;
    }

    public void setError(String message) {
        this.success = false;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

public class Data {
    private String title;
    private DataResult result;

    public class DataResult {
	private String name;

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}
    }

    public DataResult getResult() {
	return result;
    }

    public void setResult(DataResult result) {
	this.result = result;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    @Override
    public String toString() {
	return "Data [title=" + title + ", result=" + result + "]";
    }
}
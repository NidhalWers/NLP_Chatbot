import java.util.ArrayList;
import java.util.List;

public class Intent {
    private String tag;

    private List<String> patterns;

    private List<String> responses;

    public Intent(String tag, List<String> patterns, List<String> responses) {
        this.tag = tag;
        this.patterns = patterns;
        this.responses = responses;
    }

    public String getTag() {
        return tag;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public List<String> getResponses() {
        return responses;
    }

    public Intent addPatterns(List<String> otherPatterns){
        for(String s : otherPatterns){
            this.patterns.add(s);
        }
        //this.patterns.addAll(otherPatterns);
        return this;
    }

    public Intent addResponses(List<String> otherResponses){
        this.responses.addAll(otherResponses);
        return this;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String tag;

        private List<String> patterns = new ArrayList<>();

        private List<String> responses = new ArrayList<>();

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder setPatterns(List<String> patterns) {
            this.patterns.addAll(patterns);
            return this;
        }

        public Builder setResponses(List<String> responses) {
            this.responses.addAll(responses);
            return this;
        }

        public Intent build(){
            return new Intent(this.tag, this.patterns, this.responses);
        }
    }
}

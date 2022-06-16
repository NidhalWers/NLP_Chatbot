import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {

        final String topicsPath =  "src/main/resources/dominant_topic.csv";
        final String csvTopic = new String(Files.readAllBytes(new File(topicsPath).toPath()));

        List<Intent> intents = csvToIntents(csvTopic);
        List<Intent> intentgroupedBy = groupByTag(intents);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonIntents = ow.writeValueAsString(intentgroupedBy);

        PrintWriter writer = new PrintWriter("src/main/resources/intents.json", StandardCharsets.UTF_8);
        writer.println(jsonIntents);
        writer.close();

    }

    private static List<Intent> csvToIntents(String csv){
        List<Intent> result = Arrays.stream(csv.split("\n"))
                .skip(1)
                .map(line -> line.split(";"))
                .map(line -> Intent.builder()
                        .setTag(line[2])
                        .setPatterns(Arrays.asList(line[5]))
                        .setResponses(Arrays.asList(line[6]))
                        .build()
                )
                .collect(Collectors.toList());

        return result;
    }

    private static List<Intent> groupByTag(List<Intent> intentsBase){
        Map<String, Intent> result = new HashMap<>();

        for(Intent intent : intentsBase){
            if(result.containsKey(intent.getTag())){
                result.get(intent.getTag())
                        .addPatterns(intent.getPatterns())
                        .addResponses(intent.getResponses());
            }else{
                result.put(intent.getTag(), intent);
            }
        }

        return List.copyOf(result.values());
    }
}

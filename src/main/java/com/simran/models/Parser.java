package com.simran.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Parser
{
    public void parseJson()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("./src/main/java/com/simran/data/modalities.json");
        try {
            ModalityResponse modalityResponse = objectMapper.readValue(file, ModalityResponse.class);
            Predicate<Modality> directlyModality = (i->i.isDirectlyModality);
            Predicate<Modality> externalModality = (i->i.isExternalLink);
            Predicate<Modality> extModality = directlyModality.and(externalModality);
            List<String> extModalities = modalityResponse.modalities.stream().filter(extModality).map(i->i.name).collect(Collectors.toList());
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            for (String s:extModalities) {
                writer.write(s+"\n");
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package org.freekode.wowbot.modules.moving;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvTools {
    public static String buildCsvFile(List<CharacterRecordModel> records) {
        StringBuilder out = new StringBuilder();

        for (CharacterRecordModel record : records) {
            out.append(record.getDate().getTime()).append(";")
                    .append(record.getCoordinates().getX()).append(";")
                    .append(record.getCoordinates().getY()).append("\n");
        }

        return out.toString();
    }

    public static List<CharacterRecordModel> parseCsvFile(File file) {
        Pattern pattern = Pattern.compile("([\\d\\.]*);([\\d\\.]*);([\\d\\.]*)");
        List<CharacterRecordModel> records = new LinkedList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {

                    Date date = new Date(new Long(matcher.group(1)));
                    Double x = new Double(matcher.group(2));
                    Double y = new Double(matcher.group(3));

                    records.add(new CharacterRecordModel(date, new Vector3D(x, y, 0)));
                }
            }

            return records;
        } catch (IOException e) {
            return new LinkedList<>();
        }
    }
}

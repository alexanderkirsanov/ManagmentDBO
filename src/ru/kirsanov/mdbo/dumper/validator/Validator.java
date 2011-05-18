package ru.kirsanov.mdbo.dumper.validator;

public class Validator {
    public static String escape(String value, char delimiter) {
        if (value == null) return "";
        if (value.length() == 0) return "\"\"";

        boolean needQuoting = value.startsWith(" ") || value.endsWith(" ") || (value.startsWith("#"));
        if (!needQuoting) {
            for (char ch : new char[]{'\"', '\\', '\r', '\n', '\t', delimiter}) {
                if (value.indexOf(ch) != -1) {
                    needQuoting = true;
                    break;
                }
            }
        }

        String result = value.replace("\"", "\"\"");
        if (needQuoting) result = "\"" + result + "\"";
        return result;
    }

    public static StringBuilder prepareData(String[] line) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String currentLine : line) {
            stringBuilder.append("'");
            stringBuilder.append(currentLine.replace("\"", "\\\""));
            stringBuilder.append("'");
            i++;
            if (i < line.length) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder;
    }
}

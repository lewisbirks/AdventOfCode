package com.lewisbirks.adventofcode.utils;

public class TerminalCommands {
    public static String getFileName(String command) {
        return command.substring(command.indexOf(' ') + 1);
    }

    public static boolean isCommand(String command) {
        return command.startsWith("$");
    }

    public static boolean changingDirectory(String command) {
        return command.startsWith("$ cd");
    }

    public static boolean isNavigatingOut(String name) {
        return name.startsWith("..");
    }

    public static String getDirectory(String command) {
        return command.substring(5);
    }

    public static boolean isDirectory(String line) {
        return line.startsWith("dir");
    }

    public static long getFileSize(String line) {
        return Long.parseLong(line.substring(0, line.indexOf(' ')));
    }
}

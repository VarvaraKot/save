package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static ArrayList<String> list = new ArrayList<>();

    public static void main(String[] args) {
        File savegame = new File("savegames");
        savegame.mkdir();
        GameProgress gameProgress1 = new GameProgress(5, 5, 5, 5.5);
        GameProgress gameProgress2 = new GameProgress(3, 3, 3, 3.5);
        GameProgress gameProgress3 = new GameProgress(1, 1, 5, 1.5);

        saveGame("C://Users//barba//IdeaProjects//Installation//Games//savegames//save1.dat", gameProgress1);
        saveGame("C://Users//barba//IdeaProjects//Installation//Games//savegames//save2.dat", gameProgress2);
        saveGame("C://Users//barba//IdeaProjects//Installation//Games//savegames//save3.dat", gameProgress3);

        list.add("C://Users//barba//IdeaProjects//Installation//Games//savegames//save1.dat");
        list.add("C://Users//barba//IdeaProjects//Installation//Games//savegames//save2.dat");
        list.add("C://Users//barba//IdeaProjects//Installation//Games//savegames//save3.dat");

        zipFiles("C://Users//barba//IdeaProjects//Installation//Games//savegames//zip.zip", list);

        deleteFile("savegames", "zip.zip");
    }

    public static void saveGame(String location, GameProgress gameProgress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(location);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void zipFiles(String location, ArrayList<String> list) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(location))) {
            for (String s : list) {
                try (FileInputStream fileInputStream = new FileInputStream(s)) {
                    ZipEntry zipEntry = new ZipEntry("packed" + s);
                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);
                    zipOutputStream.write(buffer);
                    zipOutputStream.closeEntry();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFile(String dir, String zip) {
        File directory = new File(dir);
        for (File item : directory.listFiles()) {
            if (!zip.equals(item.getName())) {
                if (item.delete()) {
                    System.out.println("Файл удален: " + item);
                }
            }
        }
    }
}

package uz.javachi;

public class Main {
    public static int FIRST_GENERATING_FISHES_COUNT = 0;
    public static int AQUARIUM_MAX_SIZE = 0;
    public static int AQUARIUM_MIN_SIZE = 0;
    public static int TOTAL_FISHES = 0;
    public static int TOTAL_FISHES_DIE = 0;
    public static int TOTAL_FISHES_MEET = 0;
    public static int TOTAL_NEW_GENERATING_FISHES_AFTER_MEET = 0;
    public static int TOTAL_SHAR_ATE_FISH = 0;
    public static void main(String[] args) {
        new Thread(new Aquarium()).start();
    }
}
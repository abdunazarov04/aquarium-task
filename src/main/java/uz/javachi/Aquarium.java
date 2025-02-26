package uz.javachi;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static uz.javachi.Main.*;

public class Aquarium implements Runnable {
    private final Queue<Fish> fishList = new ConcurrentLinkedQueue<>();
    private final Set<String> occupiedPositions = Collections.synchronizedSet(new HashSet<>());
    private final List<Thread> fishThreads = new ArrayList<>();
    private final Random random;

    public Aquarium() {
        this.random = new Random();
    }

    @Override
    public void run() {
        System.out.println("AKVARIUM YARATILDI...");
        FIRST_GENERATING_FISHES_COUNT = random.nextInt(50, 200);
        generateNewFishes(FIRST_GENERATING_FISHES_COUNT);

        AQUARIUM_MAX_SIZE = random.nextInt(200, 600);
        AQUARIUM_MIN_SIZE = 0;

        while (fishList.size() < AQUARIUM_MAX_SIZE && fishList.size() > AQUARIUM_MIN_SIZE) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            fishList.removeIf(fish -> {
                        if (!fish.isAlive()) {
                            occupiedPositions.remove(fish.getX() + "," + fish.getY() + "," + fish.getZ());
                            return true;
                        }
                        return false;
                    }
            );

            for (Fish fish1 : fishList) {
                for (Fish fish2 : fishList) {
                    if (!fish1.equals(fish2) && fish1.isAdult() && fish2.isAdult() &&
                            fish1.isCanBreed() && fish2.isCanBreed() &&
                            fish1.isMale() != fish2.isMale() &&
                            fish1.getX() == fish2.getX() &&
                            fish1.getY() == fish2.getY() &&
                            fish1.getZ() == fish2.getZ()) {
                        int fishCount = random.nextInt(1, 10);
                        TOTAL_NEW_GENERATING_FISHES_AFTER_MEET += fishCount;
                        TOTAL_FISHES_MEET += 1;
                        fish1.setLastBreedTime(System.currentTimeMillis());
                        fish2.setLastBreedTime(System.currentTimeMillis());
                        System.out.printf("Baliqlar uchrashdi va %d ta baliq yangi yaratildi!\n", fishCount);

                        generateNewFishes(fishCount);

                        fish1.setCanBreed(false);
                        fish2.setCanBreed(false);
                        break;
                    }
                }
            }
        }

        waitForFishThreadsToFinish();
        System.err.println("AKVARIUM TO'XTADI...");

        TOTAL_FISHES = TOTAL_NEW_GENERATING_FISHES_AFTER_MEET + FIRST_GENERATING_FISHES_COUNT;

        System.out.println("\n");

        System.out.printf("Akvarium max sig'imi: %d\n", AQUARIUM_MAX_SIZE);
        System.out.printf("Akvarium min sig'imi: %d\n", AQUARIUM_MIN_SIZE);
        System.out.printf("Birinchi yaratilgan baliqlar soni: %d\n", FIRST_GENERATING_FISHES_COUNT);
        System.out.printf("Barcha baliqlar soni: %d\n", TOTAL_FISHES);
        System.out.printf("Barcha o'lgan baliqlar soni: %d\n", TOTAL_FISHES_DIE);
        System.out.printf("Barcha uchrashuvlar soni: %d\n", TOTAL_FISHES_MEET);
        System.out.printf("Barcha uchrashuvlardan so'ng yaratilgan baliqlar soni: %d\n", TOTAL_NEW_GENERATING_FISHES_AFTER_MEET);
    }

    private void waitForFishThreadsToFinish() {
        for (Thread thread : fishThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateNewFishes(int fishCount) {
        for (int i = 0; i < fishCount; i++) {
            Fish newFish = new Fish();
            Thread thread = new Thread(newFish);
            String position = newFish.getX() + "," + newFish.getY() + "," + newFish.getZ();
            if (occupiedPositions.contains(position)) {
                continue;
            }
            occupiedPositions.add(position);
            thread.start();
            fishThreads.add(thread);
            System.out.printf("Yanig baliq yaratildi jinsi %s\n", (newFish.isMale()) ? "ERKAK" : "AYOL");
            fishList.add(newFish);
        }
    }
}

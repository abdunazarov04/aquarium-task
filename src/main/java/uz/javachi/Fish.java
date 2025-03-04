package uz.javachi;

import java.util.Random;
import static uz.javachi.Main.*;
public class Fish implements Runnable {
    private boolean alive;
    private boolean isShark;
    private long liveDuration;
    private long birthTime;
    private long lastBreedTime;
    private boolean male;
    private boolean adult;
    private boolean canBreed;
    private final Random random;

    private int x, y, z;

    public Fish() {
        random = new Random();
        alive = true;
        liveDuration = random.nextLong(5000, 50000);
        birthTime = System.currentTimeMillis();
        male = random.nextBoolean();
        adult = false;
        isShark = false;
        this.x = random.nextInt(1, 100);
        this.y = random.nextInt(1, 100);
        this.z = random.nextInt(1, 100);
    }


    @Override
    public void run() {
        System.out.printf("%d baliq tug'ildi %n", this.hashCode());
        while (System.currentTimeMillis() < birthTime + liveDuration) {
            if (System.currentTimeMillis() >= birthTime + (liveDuration / 10)) {
                adult = true;
            }
            if (!canBreed && System.currentTimeMillis() >= lastBreedTime + (liveDuration / 5)) {
                canBreed = true;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.x = (this.x + random.nextInt(1, 100)) % 100;
            this.y = (this.y + random.nextInt(1, 100)) % 100;
            this.z = (this.z + random.nextInt(1, 100)) % 100;
        }
        System.out.printf("%d baliq o'ldi %n", this.hashCode());
        TOTAL_FISHES_DIE += 1;
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public long getLiveDuration() {
        return liveDuration;
    }

    public void setLiveDuration(long liveDuration) {
        this.liveDuration = liveDuration;
    }

    public long getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(long birthTime) {
        this.birthTime = birthTime;
    }

    public long getLastBreedTime() {
        return lastBreedTime;
    }

    public void setLastBreedTime(long lastBreedTime) {
        this.lastBreedTime = lastBreedTime;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isCanBreed() {
        return canBreed;
    }

    public void setCanBreed(boolean canBreed) {
        this.canBreed = canBreed;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isShark() {
        return isShark;
    }

    public void setShark(boolean shark) {
        isShark = shark;
    }

    public Random getRandom() {
        return random;
    }

    @Override
    public String toString() {
        return "Fish{" +
                "alive=" + alive +
                ", liveDuration=" + liveDuration +
                ", birthTime=" + birthTime +
                ", lastBreedTime=" + lastBreedTime +
                ", male=" + male +
                ", adult=" + adult +
                ", canBreed=" + canBreed +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

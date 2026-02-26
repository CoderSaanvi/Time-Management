import components.map.Map;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Proof-of-concept for a time block scheduler components. Helps manage your
 * daily schedules.
 *
 * @author Saanvi Mishra
 */
public class TimeBlockScheduler {
    /**
     * Inner class for time blocks.
     */
    private static class TimeBlock {

        /**
         * name of the activity.
         */
        private String activity;

        /**
         * duration of activity in hours.
         */
        private int duration;

        /**
         * constructor for timeblock.
         *
         * @param activity
         *            the name of the activity.
         * @param duration
         *            how long activity takes in hours.
         */
        TimeBlock(String activity, int duration) {
            this.activity = activity;
            this.duration = duration;
        }
    }

    /**
     * Map of hour to TimeBlock.
     */
    private Map<Integer, TimeBlock> schedule;

    /**
     * no-arg constructor.
     */
    public TimeBlockScheduler() {
        this.schedule = new Map1L<>();
    }

    //kernel methods

    /**
     * Adds a block to schedule.
     *
     * @param hour
     *            starting hour for block
     * @param activity
     *            name of activity
     * @param duration
     *            how many hoirs activity takes
     */
    public void addBlock(int hour, String activity, int duration) {
        this.schedule.add(hour, new TimeBlock(activity, duration));
    }

    /**
     * Removes block at given hour.
     *
     * @param hour
     *            hour to remove from
     * @return the activity name that was removed, or null if nothing there.
     */
    public String removeBlock(int hour) {
        String block = null;
        if (this.schedule.hasKey(hour)) {
            block = this.schedule.remove(hour).value().activity;
        }
        return block;
    }

    /**
     * checks if hour is free.
     *
     * @param hour
     *            hour to check
     * @return true if nothing schedule, false otherwise
     */
    public boolean isFree(int hour) {
        return !this.schedule.hasKey(hour);
    }

    /**
     * clear schedule.
     */
    public void clear() {
        this.schedule.clear();
    }

    //secondary methods

    /**
     * finds free time slot.
     *
     * @param duration
     *            how many consecutive hours needed
     * @return starting hour of free slot, -1 if none found
     */
    public int findFreeSlot(int duration) {
        int result = -1;
        final int hourDur = 24;
        for (int i = 0; i <= hourDur - duration; i++) {
            boolean free = true;
            for (int j = 0; j < duration; j++) {
                if (!this.isFree(i + j)) {
                    free = false;
                }
            }

            if (free) {
                result = i;
                i = hourDur;
            }
        }
        return result;
    }

    /**
     * checks for scheduling conflict.
     *
     * @param hour
     *            starting hour to check
     * @param duration
     *            how many hours to check
     * @return true if conflict exists, false otherwise
     */
    public boolean hasConflict(int hour, int duration) {
        boolean conflict = false;
        for (int i = 0; i < duration; i++) {
            if (!this.isFree(hour + i)) {
                conflict = true;
            }
        }
        return conflict;
    }

    /**
     * returns total scheduled hours.
     *
     * @return number of hours schedules
     */
    public int size() {
        int total = 0;
        Map<Integer, TimeBlock> temp = this.schedule.newInstance();
        temp.transferFrom(this.schedule);

        while (temp.size() > 0) {
            total += temp.removeAny().value().duration;
        }

        this.schedule.transferFrom(temp);
        return total;
    }

    /**
     * prints schedule.
     *
     * @param out
     *            output stream
     */
    public void printSchedule(SimpleWriter out) {
        final int hoursNum = 24;
        out.println("\n    SCHEDULE    ");
        for (int hr = 0; hr < hoursNum; hr++) {
            if (this.schedule.hasKey(hr)) {
                Map.Pair<Integer, TimeBlock> pair = this.schedule.remove(hr);
                out.println(hr + ":00 - " + pair.value().activity);
                this.schedule.add(pair.key(), pair.value());
            }
        }
        out.println("        \n");
    }

    /**
     * main method demo.
     *
     * @param args
     *            command line arguments not used
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        final int three = 3;
        final int four = 4;
        final int five = 5;
        final int six = 6;
        final int seven = 7;
        final int eight = 8;

        TimeBlockScheduler day = new TimeBlockScheduler();

        out.println(" TIME BLOCK SCHEDULER \n");

        boolean run = true;
        while (run) {
            out.println("What would you like to do?");
            out.println("1 - add a block");
            out.println("2 - remove a block");
            out.println("3 - view schedule");
            out.println("4 - find free time");
            out.println("5 - check for a conflict");
            out.println("6 - view total hours");
            out.println("7 - clea schedule");
            out.println("8 - exit");
            out.print("Enter choice: ");

            int choice = in.nextInteger();
            out.println();

            if (choice == 1) {
                out.print("Enter the hour (0-23): ");
                int hour = in.nextInteger();
                out.print("Enter activity: ");
                String activity = in.nextLine();
                out.print("Enter duration (in hrs): ");
                int duration = in.nextInteger();

                if (day.hasConflict(hour, duration)) {
                    out.println(
                            "Can't add, theres a conflict with existing schedule.");
                } else {
                    day.addBlock(hour, activity, duration);
                    out.println("Block added.");
                }
            } else if (choice == 2) {
                out.print("Enter hour to remove: ");
                int hour = in.nextInteger();
                String removed = day.removeBlock(hour);
                if (removed != null) {
                    out.println("Removed: " + removed);
                } else {
                    out.println("Nothing scheduled at that hour.");
                }
            } else if (choice == three) {
                day.printSchedule(out);
            } else if (choice == four) {
                out.print("Enter duration needed (hours): ");
                int duration = in.nextInteger();
                int free = day.findFreeSlot(duration);
                if (free != 1) {
                    out.println("Free time found at " + free + ":00");
                }
            } else if (choice == five) {
                out.print("Enter starting hour: ");
                int hour = in.nextInteger();
                out.print("Enter duration: ");
                int duration = in.nextInteger();
                if (day.hasConflict(hour, duration)) {
                    out.println("There is a conflict!");
                } else {
                    out.println("No conflict!");
                }
            } else if (choice == six) {
                out.println("Total scheduled hours: " + day.size());
            } else if (choice == seven) {
                day.clear();
                out.println("Schedule cleared.");
            } else if (choice == eight) {
                run = false;
                out.println("Bye!");
            } else {
                out.println("Wrong choice, do again!");
            }

            out.println();
        }

        in.close();
        out.close();
    }
}

import components.standard.Standard;
/**
 * Time Block Scheduler kernel component with primary methods.
 *
 * @author Saanvi Mishra
 */
public interface TimeBlockSchedulerKernel extends Standard<TimeBlockScheduler> {

    /**
     * adds time block to this schedule.
     *
     * @param hour
     *      starting hour for the block
     * @param activity
     *      the name of the activity
     * @param duration
     *      how many hours activity takes
     * @updates this
     * @requires 0 <= hour < 24 and duration >0 and
     *      hour is not in DOMAIN(this)
     * @ensures this = #this union {(hour, activity, duration)}
     */
    void addBlock(int hour, String activity, int duration);

    /**
     * removes and returns the activity at the given hour.
     *
     * @param hour
     *      starting hour of the block to remove.
     * @return the activity name that was removed
     * @updates this
     * @requires hour is in DOMAIN(this)
     * @ensures removeBlock = [activity in #this at hour] and
     *      this = #this without block at hour
     */
    String removeBlock(int hour);

    /**
     * reports whether given hour is free in the schedule.
     *
     * @param hour
     *      the hour to check
     * @return true if the hour is free, and false otherwise
     * @requires 0 <= hour < 24
     * @ensures isFree = (hour is not in DOMAIN(this))
     */
    boolean isFree(int hour);

    /**
     * reports number of time blocks in this schedule.
     *
     * @return the size of this schedule
     * @ensures size = |this|
     */
    int size();
}

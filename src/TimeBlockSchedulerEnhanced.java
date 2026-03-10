/**
 * {@code TimeBlockSchedulerKernel} enhanced with secondary methods.
 *
 * @author Saanvi Mishra
 */
public interface TimeBlockSchedulerEnhanced {

    /**
     * Finds first available time slot that can fit the requested duration.
     *
     * @param duration
     *            how many consecutive free hours needed
     * @return the starting hour of the free slot or -1 if none found
     * @requires duration > 0
     * @ensures findFreeSlot is the first hour where duration consecutive hours
     *          are free or -1 if no such slot exists
     */
    int findFreeSlot(int duration);

    /**
     * checks if scheduling a block at the given hour would conflict with existing blocks.
     *
     * @param hour
     *      the starting hour to check
     * @param duration
     *      how many hours to check
     * @return true if there is conflict, false if not
     * @requires 0 <= hour < 24 and duration > 0
     * @ensures hasConflict = true if any hour from hour to hour+duration - 1
     *  is already scheduled
     */
    boolean hasConflict(int hour, int duration);
}

package com.qd8s.werewolf2.GameHandler;

/**
 * Created by Matthew on 6/18/2017.
 */

public interface FireBasePublishable {

    /**
     * Publish data to FireBase
     */
    public void publish();

    /**
     * Pull data from FireBase
     */
    public void pullData();
}

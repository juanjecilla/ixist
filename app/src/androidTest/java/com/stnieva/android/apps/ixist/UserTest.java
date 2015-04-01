package com.stnieva.android.apps.ixist;

import android.test.suitebuilder.annotation.SmallTest;

import com.stnieva.android.apps.ixist.socialnetwork.Type;
import com.stnieva.android.apps.ixist.socialnetwork.User;

import junit.framework.TestCase;

/**
 * Created by stnieva on 1/4/15.
 */
public class UserTest extends TestCase {

    private String id;

    private String username;

    private String name;

    private Type type;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        id = "123456";
        username = "johnsmith";
        name = "John Smith";
        type = Type.FACEBOOK;
    }

    @SmallTest
    public void testGetId() {
        User user = new User(id, username, name, type);

        assertEquals(user.getId(), id);
        assertNotNull(user.getId());
    }

    @SmallTest
    public void testGetUsername() {
        User user = new User(id, username, name, type);

        assertEquals(user.getUsername(), id);
        assertNotNull(user.getUsername());
    }

    @SmallTest
    public void testName() {
        User user = new User(id, username, name, type);

        assertEquals(user.getName(), name);
        assertNotNull(user.getName());
    }

    @SmallTest
    public void testType() {
        User user = new User(id, username, name, type);

        assertEquals(user.getType(), type);
        assertNotNull(user.getType());
    }
}

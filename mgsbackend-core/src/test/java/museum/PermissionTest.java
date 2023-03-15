package museum;

import com.mimiter.mgs.model.entity.Museum;
import org.junit.Assert;
import org.junit.Test;

import static com.mimiter.mgs.model.entity.Museum.PERMISSION_GPT;
import static com.mimiter.mgs.model.entity.Museum.PERMISSION_OPEN_QA;


public class PermissionTest {

    @Test
    public void permissionTest() {
        Museum museum = new Museum();
        museum.setPermission(PERMISSION_OPEN_QA, true);
        Assert.assertTrue(museum.hasPermission(PERMISSION_OPEN_QA));
        museum.setPermission(PERMISSION_GPT, true);
        Assert.assertTrue(museum.hasPermission(PERMISSION_GPT));
        Assert.assertEquals(0b0011, museum.getPermission());

        museum.setPermission(PERMISSION_OPEN_QA, false);
        Assert.assertFalse(museum.hasPermission(PERMISSION_OPEN_QA));
        Assert.assertTrue(museum.hasPermission(PERMISSION_GPT));
        Assert.assertEquals(0b0010, museum.getPermission());

        museum.setPermission(PERMISSION_GPT, false);
        Assert.assertFalse(museum.hasPermission(PERMISSION_GPT));
        Assert.assertFalse(museum.hasPermission(PERMISSION_OPEN_QA));
        Assert.assertEquals(0, museum.getPermission());
    }
}

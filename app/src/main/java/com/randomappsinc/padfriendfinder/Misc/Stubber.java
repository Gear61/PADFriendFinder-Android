package com.randomappsinc.padfriendfinder.Misc;

import com.randomappsinc.padfriendfinder.Models.Friend;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderchiou on 7/21/15.
 */
public class Stubber
{
    public static List<MonsterAttributes> getMonsterBox()
    {
        List<MonsterAttributes> monsterBox = new ArrayList<>();
        MonsterAttributes monster1 = GodMapper.getGodMapper().getMonsterAttributes("Crazed King of Purgatory, Beelzebub");
        monster1.setPlusEggs(297);
        monster1.setName("Crazed King of Purgatory, Beelzebub");
        MonsterAttributes monster2 = GodMapper.getGodMapper().getMonsterAttributes("Awoken Ceres");
        monster2.setPlusEggs(297);
        monster2.setName("Awoken Ceres");
        monsterBox.add(monster1);
        monsterBox.add(monster2);
        return monsterBox;
    }

    public static List<Friend> getFriendResults()
    {
        List<Friend> friendResults = new ArrayList<>();

        MonsterAttributes monster1 = GodMapper.getGodMapper().getMonsterAttributes("Awoken Ceres");
        monster1.setPlusEggs(297);
        Friend friend1 = new Friend("377890281", monster1);

        MonsterAttributes monster2 = GodMapper.getGodMapper().getMonsterAttributes("Awoken Ceres");
        monster2.setPlusEggs(129);
        Friend friend2 = new Friend("323791081", monster2);

        friendResults.add(friend1);
        friendResults.add(friend2);

        return friendResults;
    }
}

package com.randomappsinc.padfriendfinder.Misc;

/**
 * Created by alexanderchiou on 7/13/15.
 */

import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alex on 11/1/2014.
 */
public class GodMapper
{
    private static GodMapper instance = null;
    private static HashMap<String, MonsterAttributes> nameToAttributes = new HashMap<>();
    private static ArrayList<String> friendFinderMonsterList = new ArrayList<String>();

    private GodMapper()
    {
        setUpMapper();
        setUpFriendFinderMonsterList();
    }

    public static GodMapper getGodMapper()
    {
        if (instance == null)
        {
            instance = new GodMapper();
        }
        return instance;
    }

    private static void setUpMapper()
    {
        /*
        // FRIEND FINDER CREATURES
        nameToAttributes.put("Ancient Dragon Knight, Zeal", new MonsterAttributes(9, 5, 3, R.drawable.adk_zeal));
        nameToAttributes.put("World Tree Sprite, Alraune", new MonsterAttributes(99, 11, 3, R.drawable.alraune_green));
        nameToAttributes.put("Dancing Light, Amaterasu Ohkami", new MonsterAttributes(99, 11, 4, R.drawable.ama_light));
        nameToAttributes.put("Deathly Hell Deity Jackal, Anubis", new MonsterAttributes(99, 11, 4, R.drawable.anubis_final));
        nameToAttributes.put("Heavenly Herald, Archangel", new MonsterAttributes(99, 6, 3, R.drawable.archangel_blue));
        nameToAttributes.put("Guardian of the Sacred City, Athena", new MonsterAttributes(99, 9, 6, R.drawable.athena_final));
        nameToAttributes.put("Moonlit Feline Goddess, Bastet", new MonsterAttributes(99, 8, 4, R.drawable.bastet_dark));
        nameToAttributes.put("Feline Deity of Harmony, Bastet", new MonsterAttributes(99, 8, 4, R.drawable.bastet_light));
        nameToAttributes.put("BAO Batman+Batarang", new MonsterAttributes(50, 4, 0, R.drawable.batman_4));
        nameToAttributes.put("BAO Batman+BW Stealth", new MonsterAttributes(99, 4, 7, R.drawable.batman_blue_final));
        nameToAttributes.put("Crazed King of Purgatory, Beelzebub", new MonsterAttributes(99, 5, 8, R.drawable.beelz_final));
        nameToAttributes.put("Endless Blue Dragon Caller, Sonia", new MonsterAttributes(99, 6, 6, R.drawable.blonia_7));
        nameToAttributes.put("Keeper of Paradise, Metatron", new MonsterAttributes(99, 6, 6, R.drawable.btron));
        nameToAttributes.put("Awoken Ceres", new MonsterAttributes(99, 3, 8, R.drawable.ceres_final));
        nameToAttributes.put("Destroying Goddess of Power, Kali", new MonsterAttributes(99, 6, 6, R.drawable.dkali_6));
        nameToAttributes.put("Arbiter of Judgement, Metatron", new MonsterAttributes(99, 4, 7, R.drawable.dmeta_final));
        nameToAttributes.put("Divine Flowers, Da Qiao & Xiao Qiao", new MonsterAttributes(99, 6, 4, R.drawable.dqxq_6));
        nameToAttributes.put("Demon Slaying Goddess, Durga", new MonsterAttributes(99, 6, 4, R.drawable.durga_6));
        nameToAttributes.put("Hell-Creating Archdemon, Lucifer", new MonsterAttributes(99, 16, 4, R.drawable.fa_lucifer_dark));
        nameToAttributes.put("Goemon, the Thief", new MonsterAttributes(99, 15, 3, R.drawable.goemon_7));
        nameToAttributes.put("Shining Lance Wielder, Odin", new MonsterAttributes(99, 6, 8, R.drawable.grodin_final));
        nameToAttributes.put("Eternal Jade Dragon Caller, Sonia", new MonsterAttributes(99, 6, 6, R.drawable.gronia_7));
        nameToAttributes.put("Genius Sleeping Dragon, Zhuge Liang", new MonsterAttributes(99, 5, 5, R.drawable.gzl_6));
        nameToAttributes.put("Banishing Claw Byakko, Haku", new MonsterAttributes(99, 6, 7, R.drawable.haku_final));
        nameToAttributes.put("Sacred Life Goddess, Hathor", new MonsterAttributes(99, 6, 4, R.drawable.hathor_6));
        nameToAttributes.put("Blazing Deity Falcon, Horus", new MonsterAttributes(99, 11, 4, R.drawable.horus_final));
        nameToAttributes.put("Eternal Twin Stars, Idunn&Idunna", new MonsterAttributes(99, 9, 4, R.drawable.i_and_i_healer));
        nameToAttributes.put("Shining Sea Deity, Isis", new MonsterAttributes(99, 6, 4, R.drawable.isis_light));
        nameToAttributes.put("Kirin of the Sacred Gleam, Sakuya", new MonsterAttributes(99, 6, 7, R.drawable.kirin_final));
        nameToAttributes.put("Divine Brave General, Krishna", new MonsterAttributes(99, 6, 4, R.drawable.krishna_6));
        nameToAttributes.put("Devoted Miko Goddess, Kushinadahime", new MonsterAttributes(99, 4, 7, R.drawable.kush_final));
        nameToAttributes.put("Shining Goddess of Secrets, Kali", new MonsterAttributes(99, 6, 5, R.drawable.lkali_6));
        nameToAttributes.put("Keeper of the Sacred Texts, Metatron", new MonsterAttributes(99, 6, 6, R.drawable.lmeta_final));
        nameToAttributes.put("Divine Flying General, Lu Bu", new MonsterAttributes(99, 6, 4, R.drawable.lubu_6));
        nameToAttributes.put("Divine Law Goddess, Valkyrie Rose", new MonsterAttributes(99, 7, 5, R.drawable.lvalk_final));
        nameToAttributes.put("Celestial Life Dragon, Zhuge Liang", new MonsterAttributes(99, 6, 6, R.drawable.lzl_7));
        nameToAttributes.put("Sea God's Songstress, Siren", new MonsterAttributes(99, 7, 3, R.drawable.mermaid_final));
        nameToAttributes.put("Awoken Minerva", new MonsterAttributes(99, 3, 8, R.drawable.minerva_final));
        nameToAttributes.put("Goddess of the Dead, Nephthys", new MonsterAttributes(99, 6, 4, R.drawable.nephthys_6));
        nameToAttributes.put("Awoken Neptune", new MonsterAttributes(99, 3, 8, R.drawable.neptune_final));
        nameToAttributes.put("Roaming National Founder, Okuninushi", new MonsterAttributes(99, 4, 4, R.drawable.oku_final));
        nameToAttributes.put("God of Dark Riches, Osiris", new MonsterAttributes(99, 6, 4, R.drawable.osiris_6));
        nameToAttributes.put("Goddess of the Bleak Night, Pandora", new MonsterAttributes(99, 6, 4, R.drawable.pandora_6));
        nameToAttributes.put("Pure Light Sun Deity, Ra", new MonsterAttributes(99, 8, 4, R.drawable.ra_light));
        nameToAttributes.put("BAO Robin", new MonsterAttributes(99, 4, 0, R.drawable.robin_4));
        nameToAttributes.put("Awoken Phantom God, Odin", new MonsterAttributes(99, 6, 6, R.drawable.rodin_7));
        nameToAttributes.put("Marvelous Red Dragon Caller, Sonia", new MonsterAttributes(99, 6, 6, R.drawable.ronia_final));
        nameToAttributes.put("Holy Night Kirin Princess, Sakuya", new MonsterAttributes(99, 6, 7, R.drawable.santa_kirin));
        nameToAttributes.put("King of Hell, Satan", new MonsterAttributes(99, 16, 3, R.drawable.satan_final));
        nameToAttributes.put("Demolishing Creator, Shiva", new MonsterAttributes(99, 6, 4, R.drawable.shiva_final));
        nameToAttributes.put("Rebel Seraph Lucifer", new MonsterAttributes(99, 16, 4, R.drawable.sod_lucifer_final));
        nameToAttributes.put("Sylph", new MonsterAttributes(35, 6, 0, R.drawable.sylph_4));
        nameToAttributes.put("Divine Wardens, Umisachi&Yamasachi", new MonsterAttributes(99, 4, 7, R.drawable.u_and_y_final));
        nameToAttributes.put("Awoken Zeus Olympios", new MonsterAttributes(99, 16, 3, R.drawable.zeus_light));
        nameToAttributes.put("BAO Robin+E. Stick", new MonsterAttributes(99, 4, 3, R.drawable.robin_5));
        nameToAttributes.put("Dark Sun Deity, Ra", new MonsterAttributes(99, 8, 4, R.drawable.ra_dark));
        nameToAttributes.put("Gleaming Kouryu Emperor, Fagan", new MonsterAttributes(99, 6, 8, R.drawable.fagan_light));
        nameToAttributes.put("Dark Kouryu Emperor, Fagan", new MonsterAttributes(99, 6, 8, R.drawable.fagan_dark));
        nameToAttributes.put("Seraph of Dawn Lucifer", new MonsterAttributes(99, 16, 4, R.drawable.sod_lucifer));
        nameToAttributes.put("Dark Liege, Vampire Duke", new MonsterAttributes(99, 7, 3, R.drawable.vamp_dark));
        nameToAttributes.put("Jester Dragon, Drawn Joker", new MonsterAttributes(99, 7, 3, R.drawable.drawn_joker_final));
        nameToAttributes.put("Asuka&Eva Unit-02", new MonsterAttributes(50, 11, 0, R.drawable.asuka_eva_4));
        nameToAttributes.put("Awoken Dancing Queen Hera-Ur", new MonsterAttributes(99, 5, 6, R.drawable.hera_ur_final));
        nameToAttributes.put("Awoken Hinokagutsuchi", new MonsterAttributes(99, 5, 8, R.drawable.kagutsuchi_final));
        nameToAttributes.put("Crimson Lotus Mistress, Echidna", new MonsterAttributes(99, 6, 3, R.drawable.echidna_final));
        nameToAttributes.put("Unyielding Samurai Dragon King, Zaerog", new MonsterAttributes(99, 5, 4, R.drawable.zaerog_samurai));
        nameToAttributes.put("TAMADRApurin", new MonsterAttributes(99, 5, 4, R.drawable.tamadrapurin));
        nameToAttributes.put("Norn of the Past, Urd", new MonsterAttributes(99, 6, 5, R.drawable.urd_6));
        nameToAttributes.put("Norn of the Present, Verdandi", new MonsterAttributes(99, 6, 5, R.drawable.verdandi_6));
        nameToAttributes.put("Norn of the Future, Skuld", new MonsterAttributes(99, 6, 6, R.drawable.skuld_7));
        nameToAttributes.put("Gods of Hunt, Umisachi&Yamasachi", new MonsterAttributes(99, 4, 4, R.drawable.u_and_y_6));
        nameToAttributes.put("Awoken Odin", new MonsterAttributes(99, 6, 5, R.drawable.grodin_6));
        nameToAttributes.put("Nocturne Chanter, Tsukuyomi", new MonsterAttributes(99, 6, 4, R.drawable.yomi_dark));
        nameToAttributes.put("Hand of the Dark God, Metatron", new MonsterAttributes(99, 6, 6, R.drawable.dmeta_7));
        nameToAttributes.put("Warrior Rose, Graceful Valkyrie", new MonsterAttributes(99, 7, 3, R.drawable.valk_7));
        nameToAttributes.put("Scholarly God, Ganesha", new MonsterAttributes(99, 6, 4, R.drawable.ganesha_6));
        nameToAttributes.put("Goddess of Rice Fields, Kushinada", new MonsterAttributes(99, 4, 4, R.drawable.kush_6));
        nameToAttributes.put("Voice of God, Metatron", new MonsterAttributes(99, 6, 5, R.drawable.lmeta_6));
        nameToAttributes.put("Extant Red Dragon Caller, Sonia", new MonsterAttributes(99, 6, 5, R.drawable.ronia_6));
        nameToAttributes.put("Kirin of the Aurora, Sakuya", new MonsterAttributes(99, 6, 4, R.drawable.kirin_6));
        nameToAttributes.put("Noble Wolf King Hero, Cu Chulainn", new MonsterAttributes(99, 7, 3, R.drawable.cu_chu_dark));
        nameToAttributes.put("Earth-Rending Emperor, Siegfried", new MonsterAttributes(99, 7, 3, R.drawable.sieg_fire));
        nameToAttributes.put("Abyssal Hell Deity Jackal, Anubis", new MonsterAttributes(99, 11, 4, R.drawable.hamnubis)); */
    }

    private static void setUpFriendFinderMonsterList()
    {
        for (String key : nameToAttributes.keySet())
        {
            friendFinderMonsterList.add(key);
        }
    }

    public ArrayList<String> getFriendFinderMonsterList()
    {
        return (ArrayList<String>) friendFinderMonsterList.clone();
    }

    public MonsterAttributes getMonsterAttributes(String monsterName)
    {
        return nameToAttributes.get(monsterName);
    }
}


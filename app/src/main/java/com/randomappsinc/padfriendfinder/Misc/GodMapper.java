package com.randomappsinc.padfriendfinder.Misc;

/**
 * Created by alexanderchiou on 7/13/15.
 */

import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.R;

import java.util.ArrayList;
import java.util.Collections;
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
        // FRIEND FINDER CREATURES
        nameToAttributes.put("Ancient Dragon Knight, Zeal", new MonsterAttributes(9, 5, 3, R.mipmap.adk_zeal));
        nameToAttributes.put("World Tree Sprite, Alraune", new MonsterAttributes(99, 11, 3, R.mipmap.alraune_green));
        nameToAttributes.put("Dancing Light, Amaterasu Ohkami", new MonsterAttributes(99, 11, 4, R.mipmap.ama_light));
        nameToAttributes.put("Deathly Hell Deity Jackal, Anubis", new MonsterAttributes(99, 11, 4, R.mipmap.anubis_final));
        nameToAttributes.put("Heavenly Herald, Archangel", new MonsterAttributes(99, 6, 3, R.mipmap.archangel_blue));
        nameToAttributes.put("Guardian of the Sacred City, Athena", new MonsterAttributes(99, 9, 6, R.mipmap.athena_final));
        nameToAttributes.put("Moonlit Feline Goddess, Bastet", new MonsterAttributes(99, 8, 4, R.mipmap.bastet_dark));
        nameToAttributes.put("Feline Deity of Harmony, Bastet", new MonsterAttributes(99, 8, 4, R.mipmap.bastet_light));
        nameToAttributes.put("BAO Batman+Batarang", new MonsterAttributes(50, 4, 0, R.mipmap.batman_4));
        nameToAttributes.put("BAO Batman+BW Stealth", new MonsterAttributes(99, 4, 7, R.mipmap.batman_blue_final));
        nameToAttributes.put("Crazed King of Purgatory, Beelzebub", new MonsterAttributes(99, 5, 8, R.mipmap.beelz_final));
        nameToAttributes.put("Endless Blue Dragon Caller, Sonia", new MonsterAttributes(99, 6, 6, R.mipmap.blonia_7));
        nameToAttributes.put("Keeper of Paradise, Metatron", new MonsterAttributes(99, 6, 6, R.mipmap.btron));
        nameToAttributes.put("Awoken Ceres", new MonsterAttributes(99, 3, 8, R.mipmap.ceres_final));
        nameToAttributes.put("Destroying Goddess of Power, Kali", new MonsterAttributes(99, 6, 6, R.mipmap.dkali_6));
        nameToAttributes.put("Arbiter of Judgement, Metatron", new MonsterAttributes(99, 4, 7, R.mipmap.dmeta_final));
        nameToAttributes.put("Divine Flowers, Da Qiao & Xiao Qiao", new MonsterAttributes(99, 6, 4, R.mipmap.dqxq_6));
        nameToAttributes.put("Demon Slaying Goddess, Durga", new MonsterAttributes(99, 6, 4, R.mipmap.durga_6));
        nameToAttributes.put("Hell-Creating Archdemon, Lucifer", new MonsterAttributes(99, 16, 4, R.mipmap.fa_lucifer_dark));
        nameToAttributes.put("Goemon, the Thief", new MonsterAttributes(99, 15, 3, R.mipmap.goemon_7));
        nameToAttributes.put("Shining Lance Wielder, Odin", new MonsterAttributes(99, 6, 8, R.mipmap.grodin_final));
        nameToAttributes.put("Eternal Jade Dragon Caller, Sonia", new MonsterAttributes(99, 6, 6, R.mipmap.gronia_7));
        nameToAttributes.put("Genius Sleeping Dragon, Zhuge Liang", new MonsterAttributes(99, 5, 5, R.mipmap.gzl_6));
        nameToAttributes.put("Banishing Claw Byakko, Haku", new MonsterAttributes(99, 6, 7, R.mipmap.haku_final));
        nameToAttributes.put("Sacred Life Goddess, Hathor", new MonsterAttributes(99, 6, 4, R.mipmap.hathor_6));
        nameToAttributes.put("Blazing Deity Falcon, Horus", new MonsterAttributes(99, 11, 4, R.mipmap.horus_final));
        nameToAttributes.put("Eternal Twin Stars, Idunn&Idunna", new MonsterAttributes(99, 9, 4, R.mipmap.i_and_i_healer));
        nameToAttributes.put("Shining Sea Deity, Isis", new MonsterAttributes(99, 6, 4, R.mipmap.isis_light));
        nameToAttributes.put("Kirin of the Sacred Gleam, Sakuya", new MonsterAttributes(99, 6, 7, R.mipmap.kirin_final));
        nameToAttributes.put("Divine Brave General, Krishna", new MonsterAttributes(99, 6, 4, R.mipmap.krishna_6));
        nameToAttributes.put("Devoted Miko Goddess, Kushinadahime", new MonsterAttributes(99, 4, 7, R.mipmap.kush_final));
        nameToAttributes.put("Shining Goddess of Secrets, Kali", new MonsterAttributes(99, 6, 5, R.mipmap.lkali_6));
        nameToAttributes.put("Keeper of the Sacred Texts, Metatron", new MonsterAttributes(99, 6, 6, R.mipmap.lmeta_final));
        nameToAttributes.put("Divine Flying General, Lu Bu", new MonsterAttributes(99, 6, 4, R.mipmap.lubu_6));
        nameToAttributes.put("Divine Law Goddess, Valkyrie Rose", new MonsterAttributes(99, 7, 5, R.mipmap.lvalk_final));
        nameToAttributes.put("Celestial Life Dragon, Zhuge Liang", new MonsterAttributes(99, 6, 6, R.mipmap.lzl_7));
        nameToAttributes.put("Sea God's Songstress, Siren", new MonsterAttributes(99, 7, 3, R.mipmap.mermaid_final));
        nameToAttributes.put("Awoken Minerva", new MonsterAttributes(99, 3, 8, R.mipmap.minerva_final));
        nameToAttributes.put("Goddess of the Dead, Nephthys", new MonsterAttributes(99, 6, 4, R.mipmap.nephthys_6));
        nameToAttributes.put("Awoken Neptune", new MonsterAttributes(99, 3, 8, R.mipmap.neptune_final));
        nameToAttributes.put("Roaming National Founder, Okuninushi", new MonsterAttributes(99, 4, 4, R.mipmap.oku_final));
        nameToAttributes.put("God of Dark Riches, Osiris", new MonsterAttributes(99, 6, 4, R.mipmap.osiris_6));
        nameToAttributes.put("Goddess of the Bleak Night, Pandora", new MonsterAttributes(99, 6, 4, R.mipmap.pandora_6));
        nameToAttributes.put("Pure Light Sun Deity, Ra", new MonsterAttributes(99, 8, 4, R.mipmap.ra_light));
        nameToAttributes.put("BAO Robin", new MonsterAttributes(99, 4, 0, R.mipmap.robin_4));
        nameToAttributes.put("Awoken Phantom God, Odin", new MonsterAttributes(99, 6, 6, R.mipmap.rodin_7));
        nameToAttributes.put("Marvelous Red Dragon Caller, Sonia", new MonsterAttributes(99, 6, 6, R.mipmap.ronia_final));
        nameToAttributes.put("Holy Night Kirin Princess, Sakuya", new MonsterAttributes(99, 6, 7, R.mipmap.santa_kirin));
        nameToAttributes.put("King of Hell, Satan", new MonsterAttributes(99, 16, 3, R.mipmap.satan_final));
        nameToAttributes.put("Demolishing Creator, Shiva", new MonsterAttributes(99, 6, 4, R.mipmap.shiva_final));
        nameToAttributes.put("Rebel Seraph Lucifer", new MonsterAttributes(99, 16, 4, R.mipmap.sod_lucifer_final));
        nameToAttributes.put("Sylph", new MonsterAttributes(35, 6, 0, R.mipmap.sylph_4));
        nameToAttributes.put("Divine Wardens, Umisachi&Yamasachi", new MonsterAttributes(99, 4, 7, R.mipmap.u_and_y_final));
        nameToAttributes.put("Awoken Zeus Olympios", new MonsterAttributes(99, 16, 3, R.mipmap.zeus_light));
        nameToAttributes.put("BAO Robin+E. Stick", new MonsterAttributes(99, 4, 3, R.mipmap.robin_5));
        nameToAttributes.put("Dark Sun Deity, Ra", new MonsterAttributes(99, 8, 4, R.mipmap.ra_dark));
        nameToAttributes.put("Gleaming Kouryu Emperor, Fagan", new MonsterAttributes(99, 6, 8, R.mipmap.fagan_light));
        nameToAttributes.put("Dark Kouryu Emperor, Fagan", new MonsterAttributes(99, 6, 8, R.mipmap.fagan_dark));
        nameToAttributes.put("Seraph of Dawn Lucifer", new MonsterAttributes(99, 16, 4, R.mipmap.sod_lucifer));
        nameToAttributes.put("Dark Liege, Vampire Duke", new MonsterAttributes(99, 7, 3, R.mipmap.vamp_dark));
        nameToAttributes.put("Jester Dragon, Drawn Joker", new MonsterAttributes(99, 7, 3, R.mipmap.drawn_joker_final));
        nameToAttributes.put("Asuka&Eva Unit-02", new MonsterAttributes(50, 11, 0, R.mipmap.asuka_eva_4));
        nameToAttributes.put("Awoken Dancing Queen Hera-Ur", new MonsterAttributes(99, 5, 6, R.mipmap.hera_ur_final));
        nameToAttributes.put("Awoken Hinokagutsuchi", new MonsterAttributes(99, 5, 8, R.mipmap.kagutsuchi_final));
        nameToAttributes.put("Crimson Lotus Mistress, Echidna", new MonsterAttributes(99, 6, 3, R.mipmap.echidna_final));
        nameToAttributes.put("Unyielding Samurai Dragon King, Zaerog", new MonsterAttributes(99, 5, 4, R.mipmap.zaerog_samurai));
        nameToAttributes.put("TAMADRApurin", new MonsterAttributes(99, 5, 4, R.mipmap.tamadrapurin));
        nameToAttributes.put("Norn of the Past, Urd", new MonsterAttributes(99, 6, 5, R.mipmap.urd_6));
        nameToAttributes.put("Norn of the Present, Verdandi", new MonsterAttributes(99, 6, 5, R.mipmap.verdandi_6));
        nameToAttributes.put("Norn of the Future, Skuld", new MonsterAttributes(99, 6, 6, R.mipmap.skuld_7));
        nameToAttributes.put("Gods of Hunt, Umisachi&Yamasachi", new MonsterAttributes(99, 4, 4, R.mipmap.u_and_y_6));
        nameToAttributes.put("Awoken Odin", new MonsterAttributes(99, 6, 5, R.mipmap.grodin_6));
        nameToAttributes.put("Nocturne Chanter, Tsukuyomi", new MonsterAttributes(99, 6, 4, R.mipmap.yomi_dark));
        nameToAttributes.put("Hand of the Dark God, Metatron", new MonsterAttributes(99, 6, 6, R.mipmap.dmeta_7));
        nameToAttributes.put("Warrior Rose, Graceful Valkyrie", new MonsterAttributes(99, 7, 3, R.mipmap.valk_7));
        nameToAttributes.put("Scholarly God, Ganesha", new MonsterAttributes(99, 6, 4, R.mipmap.ganesha_6));
        nameToAttributes.put("Goddess of Rice Fields, Kushinada", new MonsterAttributes(99, 4, 4, R.mipmap.kush_6));
        nameToAttributes.put("Voice of God, Metatron", new MonsterAttributes(99, 6, 5, R.mipmap.lmeta_6));
        nameToAttributes.put("Extant Red Dragon Caller, Sonia", new MonsterAttributes(99, 6, 5, R.mipmap.ronia_6));
        nameToAttributes.put("Kirin of the Aurora, Sakuya", new MonsterAttributes(99, 6, 4, R.mipmap.kirin_6));
        nameToAttributes.put("Noble Wolf King Hero, Cu Chulainn", new MonsterAttributes(99, 7, 3, R.mipmap.cu_chu_dark));
        nameToAttributes.put("Earth-Rending Emperor, Siegfried", new MonsterAttributes(99, 7, 3, R.mipmap.sieg_fire));
        nameToAttributes.put("Abyssal Hell Deity Jackal, Anubis", new MonsterAttributes(99, 11, 4, R.mipmap.hamnubis));
        nameToAttributes.put("Sparkling Goddess of Secrets, Kali", new MonsterAttributes(99, 6, 6, R.mipmap.lkali_7));
        nameToAttributes.put("Awoken Horus", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_horus));
        nameToAttributes.put("Awoken Ra", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_ra));
        nameToAttributes.put("Awoken Bastet", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_bastet));
        nameToAttributes.put("Awoken Shiva", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_shiva));
        nameToAttributes.put("Magic Hand Slayer Goddess, Durga", new MonsterAttributes(99, 6, 4, R.mipmap.durga_7));
        nameToAttributes.put("Avowed Thief, Ishikawa Goemon", new MonsterAttributes(99, 6, 4, R.mipmap.goemon_8));
        nameToAttributes.put("Wailing Bleak Night Goddess, Pandora", new MonsterAttributes(99, 6, 7, R.mipmap.pandora_7));
        nameToAttributes.put("Chaotic Flying General, Lu Bu", new MonsterAttributes(99, 5, 7, R.mipmap.lubu_7));
        nameToAttributes.put("Awoken Meimei", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_meimei));
        nameToAttributes.put("Lightning Red Dragonbound, Gadius", new MonsterAttributes(99, 6, 5, R.mipmap.gadius_6));
        nameToAttributes.put("Dawning Dragon Caller, Sonia Gran", new MonsterAttributes(99, 11, 9, R.mipmap.sonia_gran_8));
        nameToAttributes.put("Blue Chain Starsea Goddess, Andromeda", new MonsterAttributes(99, 6, 7, R.mipmap.andromeda_7));
        nameToAttributes.put("Sacred Divine Flower, Xiao Qiao", new MonsterAttributes(99, 6, 4, R.mipmap.xq_7));
        nameToAttributes.put("Awoken Venus", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_venus));
        nameToAttributes.put("Awoken Hades", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_hades));
        nameToAttributes.put("Awoken Lakshmi", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_lakshmi));
        nameToAttributes.put("Awoken Parvati", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_parvati));
        nameToAttributes.put("Sacred Dragon Princess, Da Qiao", new MonsterAttributes(99, 6, 7, R.mipmap.dq_7));
        nameToAttributes.put("Scholarship Student, Isis", new MonsterAttributes(99, 6, 5, R.mipmap.schoolgirl_isis));
        nameToAttributes.put("Discipline Committee Chair, Athena", new MonsterAttributes(99, 9, 6, R.mipmap.schoolgirl_athena));
        nameToAttributes.put("Stormy God-Emperor, Yamato Takeru", new MonsterAttributes(99, 6, 7, R.mipmap.yamato_7));
        nameToAttributes.put("Revered Monkey King, Sun Wukong", new MonsterAttributes(99, 6, 7, R.mipmap.wukong_7));
        nameToAttributes.put("Green Star Vanquishing Deity, Perseus", new MonsterAttributes(99, 6, 7, R.mipmap.perseus_7));
        nameToAttributes.put("Dominating Warrior King, Cao Cao", new MonsterAttributes(99, 6, 7, R.mipmap.cao_cao_green));
        nameToAttributes.put("Unifying Martial Deity, Cao Cao", new MonsterAttributes(99, 6, 4, R.mipmap.cao_cao_dark));
        nameToAttributes.put("Divine General of the Sun, Krishna", new MonsterAttributes(99, 10, 4, R.mipmap.krishna_7));
        nameToAttributes.put("Wise and Moral Goddess, Sarasvati", new MonsterAttributes(99, 10, 4, R.mipmap.sarasvati_7));
        nameToAttributes.put("Awoken Isis", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_isis));
        nameToAttributes.put("Awoken Anubis", new MonsterAttributes(99, 5, 8, R.mipmap.awoken_anubis));
        nameToAttributes.put("Enraged Black Phantom Demon, Zuoh", new MonsterAttributes(99, 6, 5, R.mipmap.zuoh_6));
    }

    private static void setUpFriendFinderMonsterList()
    {
        for (String key : nameToAttributes.keySet())
        {
            friendFinderMonsterList.add(key);
        }
        Collections.sort(friendFinderMonsterList);
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


package com.randomappsinc.padfriendfinder.Misc;

/**
 * Created by alexanderchiou on 7/13/15.
 */
public class Constants {
    // API
    public static final String SERVER_URL = "https://mysterious-citadel-1245.herokuapp.com/";
    public static final String PAD_ID_KEY = "pad_ID";
    public static final String MONSTER_KEY = "monster";
    public static final String MONSTERS_KEY = "monsters/";
    public static final String NAME_KEY = "name";
    public static final String LEVEL_KEY = "level";
    public static final String SKILL_LEVEL_KEY = "skill_level";
    public static final String AWAKENINGS_KEY = "awakenings";
    public static final String PLUS_EGGS_KEY = "plus_eggs";
    public static final String FETCH_FRIENDS_KEY = "fetch";
    public static final String UPDATE_KEY = "update";
    public static final String DELETE_KEY = "delete";
    public static final String MONSTER_ID_KEY = "monsterId";
    public static final String GET_MONSTERS_KEY = "getMonsters";
    public static final String CHANGE_ID_KEY = "changeID";

    // Activity communication/broadcasts
    public static final String MODE_KEY = "mode";
    public static final String MONSTER_UPDATE_KEY = "monsterUpdate";
    public static final String MONSTER_BOX_KEY = "monsterBox";
    public static final String OTHER_BOX_KEY = "otherBox";
    public static final String REST_CALL_RESPONSE_KEY = "restCallResponse";

    // Monster form
    public static final String SEARCH_MODE = "Search";
    public static final String ADD_MODE = "Add";
    public static final String UPDATE_MODE = "Update";
    public static final int MAX_PLUS_EGGS = 297;
    public static final String MONSTER_ADD_FAILED_MESSAGE = "We were unable to add your monster to our database. " +
            "Please try again later.";
    public static final String MONSTER_UPDATE_FAILED_MESSAGE = "We were unable to update your monster in our database. " +
            "Please try again later.";
    public static final String MONSTER_ADD_SUCCESS_MESSAGE = "Your monster was successfully added to our database.";
    public static final String MONSTER_UPDATE_SUCCESS_MESSAGE = "Your monster was successfully updated in our database.";
    public static final String ADDING_MONSTER = "Adding your monster to our database...";
    public static final String UPDATING_MONSTER = "Updating your monster in our database...";

    public static final String FETCH_FRIENDS_FAILED_MESSAGE = "We were unable to find potential friends for you. " +
            "Please try again later.";

    // Monster box
    public static final String OTHERS_BOX_FETCH_FAILED_MESSAGE = "We were unable to fetch this user's monster box. " +
            "Please try again later.";
    public static final String MONSTER_DELETE_SUCCESS_MESSAGE = "Your monster was successfully deleted from our database.";
    public static final String MONSTER_DELETE_FAILED_MESSAGE = "We were unable to delete your monster from our database. " +
            "Please try again later.";

    // Intent keys
    public static final String OTHERS_ID_KEY = "ID";
    public static final String SETTINGS_KEY = "SETTINGS_MODE";
    public static final String CHOOSE_AVATAR_MODE = "chooseAvatarMode";
}

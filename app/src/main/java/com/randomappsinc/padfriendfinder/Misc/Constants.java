package com.randomappsinc.padfriendfinder.Misc;

/**
 * Created by alexanderchiou on 7/13/15.
 */
public class Constants
{
    // Welcome
    public static final String WELCOME_MESSAGE = "Welcome to PAD Friend Finder!";

    // PAD ID form
    public static final String INCOMPLETE_PAD_ID_MESSAGE = "Please enter in all 9 digits of your PAD ID.";
    public static final String INCORRECT_FIRST_DIGIT_MESSAGE = "We only support NA right now, so your PAD ID's first" +
            " digit must be 3.";
    public static final String PAD_ID_CONFIRMATION = "PAD ID Confirmation";

    // Monster search
    public static final String MONSTER_FORM_INCOMPLETE_MESSAGE = "Please fill in every field before submitting!";

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

    // Activity communication/broadcasts
    public static final String MODE_KEY = "mode";
    public static final String MONSTER_UPDATE_KEY = "monsterUpdate";
    public static final String MONSTER_BOX_KEY = "monsterBox";
    public static final String REST_CALL_RESPONSE_KEY = "restCallResponse";

    // Monster form
    public static final String HAVE_A_HINT = "I have a...";
    public static final String LEVEL_HINT = "Level";
    public static final String AWAKENINGS_HINT = "# of Awakenings";
    public static final String PLUS_EGGS_HINT = "# of + Eggs";
    public static final String SKILL_LEVEL_HINT = "Skill Level";
    public static final String SEARCH_MODE = "Search";
    public static final String ADD_MODE = "Add";
    public static final String UPDATE_MODE = "Update";
    public static final String FIND_FRIENDS_LABEL = "Find Friends";
    public static final String ADD_MONSTER_LABEL = "Add Monster";
    public static final String UPDATE_MONSTER_LABEL = "Update Monster";
    public static final int MAX_PLUS_EGGS = 297;
    public static final String LOOKING_FOR_HINT = "I am looking for...";
    public static final String INVALID_MONSTER_MESSAGE = "Please enter in a valid monster name.";
    public static final String MONSTER_ADD_FAILED_MESSAGE = "We were unable to add your monster to our database. " +
            "Please try again later.";
    public static final String MONSTER_UPDATE_FAILED_MESSAGE = "We were unable to update your monster in our database. " +
            "Please try again later.";
    public static final String MONSTER_ADD_SUCCESS_MESSAGE = "Your monster was successfully added to our database.";
    public static final String MONSTER_UPDATE_SUCCESS_MESSAGE = "Your monster was successfully updated in our database.";
    public static final String ADDING_MONSTER = "Adding your monster to our database...";
    public static final String UPDATING_MONSTER = "Updating your monster in our database...";

    // Friend results
    public static final String PAD_ID_COPIED_MESSAGE = "PAD ID copied to clipboard.";
    public static final String FETCH_FRIENDS_FAILED_MESSAGE = "We were unable to find friends for you. " +
            "Please try again later.";

    // Monster box
    public static final String BOX_FETCH_FAILED_MESSAGE = "We were unable to fetch your monster box. " +
            "Please try again later.";
    public static final String MONSTER_DELETE_SUCCESS_MESSAGE = "Your monster was successfully deleted from our database.";
    public static final String MONSTER_DELETE_FAILED_MESSAGE = "We were unable to delete your monster from our database. " +
            "Please try again later.";
    public static final String DELETING_MONSTER_MESSAGE = "Deleting your monster from our database...";
    public static final String FIND_OTHER = "Find other";
    public static final String EDIT = "Edit";
    public static final String DELETE = "Delete";
}

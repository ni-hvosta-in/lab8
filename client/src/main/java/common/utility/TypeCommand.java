package common.utility;

import java.io.Serializable;

public enum TypeCommand implements Serializable {
    HELP,
    INFO,
    SHOW,
    INSERT,
    UPDATE,
    REMOVE_KEY,
    CLEAR,
    SAVE,
    EXECUTE_SCRIPT,
    EXIT,
    REMOVE_LOWER,
    REPLACE_IF_GREATER,
    REMOVE_GREATER_KEY,
    GROUP_COUNTING_BY_SEMESTER_ENUM,
    FILTER_CONTAINS_NAME,
    FILTER_GREATER_THAN_GROUP_ADMIN;
}
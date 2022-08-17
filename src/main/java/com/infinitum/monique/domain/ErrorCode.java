package com.infinitum.monique.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    ID_DUPLICATION(400, "400", "Duplicated Id"),
    EMAIL_DUPLICATION(401, "401", "Duplicated Email"),
    NULL_EXCEPTION(402, "402", "NULL input"),
    TYPE_NOT_SELECTED(403, "403", "Member type not selected"),
    ID_NOT_FOLLOW_REGEX(404, "404", "Id not following RegExp"),
    PW_NOT_FOLLOW_REGEX(405, "405", "Pw not following RegExp"),
    EMAIL_NOT_FOLLOW_REGEX(406, "406", "Email not following RegExp"),
    EMAIL_CURRENTLY_USED(407, "407", "This e-mail is currently used"),
    ID_PW_NOT_MATCHING(408, "408", "Id and Password not matching"),
    MEMBER_NOT_FOUND(409, "409", "Member not found"),
    NO_INPUT_DATA(410, "410", "There is no input data"),
    ID_EMAIL_NOT_MATCHING(411, "411", "Id and Email not matching"),
    NO_FILE(412, "412", "There's no uploaded file"),
    CHOOSE_CORRECT_PLACE(413, "413", "Choose correct place number"),
    DELETION_FAILED(414, "414", "FILE DELETION FAILED"),
    ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

}


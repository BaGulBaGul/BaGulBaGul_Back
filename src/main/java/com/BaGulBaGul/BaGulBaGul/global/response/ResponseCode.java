package com.BaGulBaGul.BaGulBaGul.global.response;

import com.BaGulBaGul.BaGulBaGul.global.exception.GeneralException;
import java.text.MessageFormat;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.http.HttpStatus;

public class ResponseCode {

    @Getter
    @AllArgsConstructor
    public enum CodeType {

        //COMMON
        SUCCESS("C00000", HttpStatus.OK,"SUCCESS"),
        BAD_REQUEST("C10000", HttpStatus.BAD_REQUEST,"BAD REQUEST"),
        UNAUTHORIZED("C10001", HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"),
        FORBIDDEN("C10002", HttpStatus.FORBIDDEN, "FORBIDDEN"),
        NOT_FOUND("C10003", HttpStatus.NOT_FOUND, "PAGE NOT FOUND"),
        INTERNAL_SERVER_ERROR("C20000", HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),

        //EVENT
        EVENT_NOT_FOUND("E00000", HttpStatus.OK, "이벤트가 존재하지 않습니다"),
        EVENT_CATEGORY_NOT_EXIST("E00001", HttpStatus.OK, "존재하지 않는 카테고리입니다"),

        //RECRUITMENT
        RECRUITMENT_NOT_FOUND("R00000", HttpStatus.OK, "모집글이 존재하지 않습니다"),

        //POST
        POST_NOT_FOUND("P00000", HttpStatus.OK, "게시글이 존재하지 않습니다"),

        //USER
        USER_NOT_FOUND("U00000", HttpStatus.OK, "존재하지 않는 유저입니다"),

        //ALARM
        ALARM_NOT_FOUND("AL00000", HttpStatus.OK, "알람이 존재하지 않습니다"),

        //UserJoin
        UJ_JOINTOKEN_WRONG("UJ00000", HttpStatus.OK, "소셜 로그인 회원가입 인증 토큰이 잘못되었습니다."),
        UJ_JOINTOKEN_EXPIRED("UJ00001", HttpStatus.OK, "소셜 로그인 회원가입 인증 토큰이 만료되었습니다. 다시 인증해 주세요."),

        //Upload
        UPLOAD_NOT_IMAGE("UPLOAD00000", HttpStatus.OK, "이미지 파일이 아닙니다");

        private final String code;
        private final HttpStatus httpStatus;
        private final String message;
        private final MessageFormat messageFormat;

        CodeType(String code, HttpStatus httpStatus, String message) {
            this.code = code;
            this.httpStatus = httpStatus;
            this.message = message;
            this.messageFormat = new MessageFormat(message);
        }

        public static CodeType valueOf(HttpStatus httpStatus) {
            if (httpStatus == null) { throw new GeneralException(INTERNAL_SERVER_ERROR); }

            return Arrays.stream(values())
                    .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                    .findFirst()
                    .orElseGet(() -> {
                        if (httpStatus.is4xxClientError()) { return CodeType.BAD_REQUEST; }
                        else if (httpStatus.is5xxServerError()) { return CodeType.INTERNAL_SERVER_ERROR; }
                        else { return CodeType.SUCCESS; }
                    });
        }
    }
    private CodeType codeType;
    private Object[] messageParams;

    public ResponseCode(CodeType codeType) {
        this(codeType, null);
    }
    public ResponseCode(CodeType codeType, Object[] messageParams) {
        this.codeType = codeType;
        this.messageParams = messageParams;
    }

    public String getCode() {
        return this.codeType.getCode();
    }
    public HttpStatus getHttpStatus() {
        return this.codeType.getHttpStatus();
    }
    public String getMessage() {
        return codeType.getMessage();
    }
}
package edu.web.garage.dto;

public class ResponseDto {
    private String type;
    private String message;

    public ResponseDto(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }
    public String getMessage() {
        return message;
    }
}

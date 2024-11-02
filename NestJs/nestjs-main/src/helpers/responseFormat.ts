
/*
 * Copyright (c) ICANIO Technologies
 */

interface ResponseFormatType {
    statusCode: number,
    message: string,
    data: any | undefined;
}

class ResponseFormat implements ResponseFormatType {
    data: any | undefined;
    message: string;
    statusCode: number;

    constructor(data: any, message: string, statusCode: number) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }


    public static build(data: any, message: string) {
        return new ResponseFormat(data, message, 200);
    }

    public static buildWithData(data: object) {
        return new ResponseFormat(data, "success", 200);
    }

    public static buildWithMessage(message: string) {
        return new ResponseFormat(undefined, message, 200);
    }

    public static buildWithMessageAndStatusCode(message: string, statusCode: number) {
        return new ResponseFormat(undefined, message, statusCode);
    }

}

export default ResponseFormat;
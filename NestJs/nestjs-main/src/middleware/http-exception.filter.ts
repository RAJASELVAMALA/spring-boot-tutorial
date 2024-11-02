import {
  ExceptionFilter,
  Catch,
  ArgumentsHost,
  HttpException,
  Logger,
  BadRequestException,
} from '@nestjs/common';
import { Request, Response } from 'express';

@Catch(HttpException)
export class HttpExceptionFilter implements ExceptionFilter {
  // Handle exceptions of type HttpException
  catch(exception: HttpException, host: ArgumentsHost) {
    // Get the context and extract the response, request, and status
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();
    const request = ctx.getRequest<Request>();
    const status = exception.getStatus();

    let message = exception.message;
    if (
      exception instanceof BadRequestException &&
      // @ts-ignore
      exception.response?.message
    ) {
      // @ts-ignore
      message = exception.response?.message;
    }
    // Log the error message along with the requested URL
    Logger.error(`${request.url} : ${exception.message}`);

    // Set the HTTP status and send a JSON response with error details
    response.status(status).json({
      statusCode: status,
      message: message,
      timestamp: new Date().toISOString(),
      path: request.url,
    });
  }
}

/*
 * Copyright (c) ICANIO Technologies
 */

import { AuthServices } from './auth.services';
import ResponseFormat from '../../helpers/responseFormat';
import { ILogin } from './auth.interface';
import {
  Body,
  Controller,
  Get,
  Post,
  UseGuards,
  Request,
  HttpCode,
  HttpStatus,
} from '@nestjs/common';
import { AuthGuard, Public } from './auth.guard';
import { ApiBearerAuth, ApiTags } from '@nestjs/swagger';

@ApiTags('Auth')
@Controller('auth')
export class AuthController {
  constructor(private readonly authServices: AuthServices) {}

  // Public endpoint for user login
  @Public()
  @HttpCode(HttpStatus.OK)
  @Post('/login')
  public async login(@Body() payload: ILogin) {
    return ResponseFormat.buildWithData(await this.authServices.login(payload));
  }

  // Protected endpoint to get user profile
  @ApiBearerAuth()
  @UseGuards(AuthGuard) // Assuming AuthGuard is an authentication guard
  @Get('profile')
  getProfile(@Request() req) {
    return req.user; // Assuming user data is attached to the request object after authentication
  }
}

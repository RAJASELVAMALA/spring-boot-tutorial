/*
 * Copyright (c) ICANIO Technologies
 */

import ResponseFormat from '../../helpers/responseFormat';
import { UsersServices } from './users.services';
import { IUserCreate, IUserUpdate } from './users.interface';
import { Body, Controller, Get, Param, Patch, Post } from '@nestjs/common';
import { Public } from '../auth/auth.guard';
import { ApiBearerAuth, ApiTags } from '@nestjs/swagger';

@ApiTags('Users')
@Controller('users')
export class UsersController {
  constructor(private readonly usersServices: UsersServices) {}

  // Get all users
  @ApiBearerAuth()
  @Get()
  public async getUsers() {
    return ResponseFormat.build(
      await this.usersServices.getUsers(),
      'Users list',
    );
  }

  // Create a new user
  @Public() // Assuming this allows public access
  @Post()
  public async createUser(@Body() reqBody: IUserCreate) {
    return ResponseFormat.build(
      await this.usersServices.saveUser(reqBody),
      'User Created',
    );
  }

  // Update a user partially by ID
  @ApiBearerAuth()
  @Patch('/:id')
  public async patchUser(
    @Param('id') id: string,
    @Body() reqBody: IUserUpdate,
  ) {
    return ResponseFormat.build(
      await this.usersServices.patchUpdateUser(id, reqBody),
      'Updated user',
    );
  }
}

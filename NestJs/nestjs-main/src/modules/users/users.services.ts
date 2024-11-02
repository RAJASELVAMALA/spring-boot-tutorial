/*
 * Copyright (c) ICANIO Technologies
 */

import { Users } from './users.schema';
import { IUserCreate, IUserUpdate } from './users.interface';
import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';

@Injectable()
export class UsersServices {
  constructor(@InjectModel(Users.name) private usersModel: Model<Users>) {}

  // Get all users with selected fields
  public getUsers = async () => {
    return this.usersModel.find(
      {},
      { name: 1, email: 1, avatar: 1, createdAt: 1, updatedAt: 1 },
    );
  };

  // Get a user by ID with selected fields
  public getUser = async (id: string) => {
    const user = await this.usersModel.findById(id, {
      name: 1,
      email: 1,
      avatar: 1,
      createdAt: 1,
      updatedAt: 1,
    });
    if (!user) throw new NotFoundException(`User [${id}] not found`);
    return user;
  };

  // Save a new user
  public saveUser = async (userPayload: IUserCreate) => {
    return await this.usersModel.create({ ...userPayload });
  };

  // Update a user partially by ID
  public patchUpdateUser = async (id: string, userPayload: IUserUpdate) => {
    // Check if the user exists
    await this.getUser(id);

    // Update the user with the provided payload
    await this.usersModel.findByIdAndUpdate(id, { ...userPayload });

    // Return the updated user
    return this.getUser(id);
  };

  // Get a user by email
  getUserByEmail = async (email: string) => {
    const user = await this.usersModel.findOne({ email });
    if (!user) throw new NotFoundException(`User [${email}] not found`);
    return user;
  };
}

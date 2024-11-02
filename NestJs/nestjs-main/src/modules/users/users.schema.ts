/*
 * Copyright (c) ICANIO Technologies
 */

import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import * as bcrypt from 'bcryptjs';
import { Timestamps } from '../../config/database/mongoDBBaseDocument';

// Define a Mongoose schema for users with toJSON and toObject options
@Schema({ toJSON: { virtuals: true }, toObject: { virtuals: true } })
export class Users extends Timestamps {
  // Define a 'name' property of type string
  @Prop()
  name: string;

  // Define an 'email' property of type string with a unique constraint
  @Prop({ unique: true })
  email: string;

  // Define an 'avatar' property of type string (optional)
  @Prop({ required: false })
  avatar?: string;

  // Define a 'password' property of type string (optional)
  @Prop({ required: false })
  password?: string;
}

// Create a Mongoose schema based on the Users class
export const usersSchema = SchemaFactory.createForClass(Users);

// Add a pre-save hook to hash the password before saving the user
usersSchema.pre('save', { document: true }, async function (next) {
  // Skip hashing if the password is not modified
  if (!this.isModified('password')) {
    return next();
  }

  // Hash the password using bcrypt with a cost factor of 12
  // @ts-ignore
  this.password = await bcrypt.hash(this.password, 12);

  // Continue with the save operation
  next();
});

// Add a custom method to the schema to check if a given password is correct
usersSchema.method(
  'correctPassword',
  async function (typedPassword: string, originalPassword: string) {
    // Compare the typed password with the original hashed password
    return await bcrypt.compare(typedPassword, originalPassword);
  },
);

// Export the schema as UsersSchema
export const UsersSchema = usersSchema;

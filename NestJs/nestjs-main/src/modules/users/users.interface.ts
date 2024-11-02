/*
 * Copyright (c) ICANIO Technologies
 */

import { ILogin } from '../auth/auth.interface';
import { ApiProperty } from '@nestjs/swagger';

export class IUserCreate extends ILogin {
  /**
   * @minLength 3 Name must be greater than 3 character
   * */
  @ApiProperty({
    minLength: 3,
  })
  name: string;

  @ApiProperty({ required: false })
  avatar?: string;
}

export interface IUserUpdate {
  /**
   * @minLength 3 Name must be greater than 3 character
   * */
  name?: string;

  /**
   * @pattern ^(.+)@(.+)$ please provide correct email
   * @default example@mail.com
   */
  email?: string;
  avatar?: string;

  /**
   * @minLength 8 Password must be greater than 8 character
   * @pattern ^(.+)@(.+)$ please provide correct email
   */
  password?: string;
}

/*
 * Copyright (c) ICANIO Technologies
 */

import { ILogin } from './auth.interface';
import { UsersServices } from '../users/users.services';
import {
  BadRequestException,
  forwardRef,
  Inject,
  Injectable,
} from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';

@Injectable()
export class AuthServices {
  constructor(
    private readonly jwtService: JwtService,
    @Inject(forwardRef(() => UsersServices))
    private readonly usersServices: UsersServices,
    private readonly config: ConfigService,
  ) {}

  // Handle user login
  public async login(payload: ILogin) {
    const { email, password } = payload;

    // Get user by email
    const user = await this.usersServices.getUserByEmail(email);

    // Check if the user has a local password (not using external login providers)
    if (!user.password)
      throw new BadRequestException(
        'This account login from google or facebook or linkedLin login',
      );

    // Check if the provided password matches the stored password
    // @ts-ignore
    if (!(await user.correctPassword(password, user.password))) {
      throw new BadRequestException("Password don't match");
    }

    // Generate and return tokens
    return this.generateToken(email);
  }

  // Generate access and refresh tokens
  private generateToken(email: string) {
    // Generate access token
    let token = this.jwtService.sign(
      { email },
      {
        secret: this.config.get('JWT_SECRET'),
        expiresIn: this.config.get('JWT_EXPIRES_IN'),
      },
    );

    // Generate refresh token
    let refreshToken = this.jwtService.sign(
      { email },
      {
        secret: this.config.get('JWT_SECRET_REF'),
        expiresIn: this.config.get('JWT_EXPIRES_REF_IN'),
      },
    );

    // Return tokens
    return {
      accessToken: token,
      refreshToken,
    };
  }
}

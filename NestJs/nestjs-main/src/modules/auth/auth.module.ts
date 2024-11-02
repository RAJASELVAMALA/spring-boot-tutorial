import { forwardRef, Module } from '@nestjs/common';
import { AuthController } from './auth.controller';
import { AuthServices } from './auth.services';
import { JwtModule } from '@nestjs/jwt';
import { UsersModule } from '../users/users.module';

@Module({
  // Import the UsersModule and JwtModule with global configuration
  imports: [
    forwardRef(() => UsersModule),
    JwtModule.register({
      global: true,
    }),
  ],
  // Specify the controller(s) for this module
  controllers: [AuthController],
  // Specify the service(s) for this module
  providers: [AuthServices],
  // Export the AuthServices to make it available for other modules
  exports: [AuthServices],
})
export class AuthModule {}

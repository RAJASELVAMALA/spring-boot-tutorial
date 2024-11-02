import { forwardRef, Module } from '@nestjs/common';
import { UsersServices } from './users.services';
import { UsersController } from './users.controller';
import { MongooseModule } from '@nestjs/mongoose';
import { Users, UsersSchema } from './users.schema';
import { AuthServices } from '../auth/auth.services';

@Module({
  // Import the Mongoose module and define the Users model and schema
  imports: [
    MongooseModule.forFeature([
      {
        name: Users.name,
        schema: UsersSchema,
      },
    ]),
  ],
  // Specify the controller(s) for this module
  controllers: [UsersController],
  // Specify the service(s) for this module
  providers: [UsersServices],
  // Export the UsersServices to make it available for other modules
  exports: [UsersServices],
})
export class UsersModule {}

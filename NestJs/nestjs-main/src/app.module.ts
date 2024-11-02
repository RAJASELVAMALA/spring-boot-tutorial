import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { ConfigModule, ConfigService } from '@nestjs/config';
import * as paginate from 'mongoose-paginate-v2';
import { idPropertyPlugin } from './config/database/mongoDBPlugins';
import { AuthModule } from './modules/auth/auth.module';
import { UsersModule } from './modules/users/users.module';
import { AuthGuard } from './modules/auth/auth.guard';
import { APP_GUARD } from '@nestjs/core';

@Module({
  // Import global configuration settings
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    // Configure and connect to the MongoDB database
    MongooseModule.forRootAsync({
      imports: [],
      useFactory: async (configService: ConfigService) => ({
        uri: configService.get('DB_URL'),

        // Apply plugins to the Mongoose connection
        connectionFactory: (connection) => {
          connection.plugin(paginate);
          connection.plugin(idPropertyPlugin);
          return connection;
        },
        autoIndex: true,
        useUnifiedTopology: true,
      }),
      inject: [ConfigService],
    }),
    // Import the AuthModule and UsersModule
    AuthModule,
    UsersModule,
  ],
  // No controllers specified in this module
  controllers: [],
  // Specify providers for this module
  providers: [
    // Provide the AuthGuard as the global guard for the entire application
    {
      provide: APP_GUARD,
      useClass: AuthGuard,
    },
  ],
})
export class AppModule {}

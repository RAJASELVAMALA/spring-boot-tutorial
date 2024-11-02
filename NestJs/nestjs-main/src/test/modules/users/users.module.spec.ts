import { Test, TestingModule } from '@nestjs/testing';
import { getModelToken, MongooseModule } from '@nestjs/mongoose';
import { UsersModule } from '../../../modules/users/users.module';
import { Users, UsersSchema } from '../../../modules/users/users.schema';
import { UsersServices } from '../../../modules/users/users.services';
import { UsersController } from '../../../modules/users/users.controller';

describe('UsersModule', () => {
  let module: TestingModule;

  beforeEach(async () => {
    module = await Test.createTestingModule({
      imports: [
        MongooseModule.forRoot('mongodb://localhost/test'),
        UsersModule,
      ],
    }).compile();
  });

  it('should be defined', () => {
    expect(module).toBeDefined();
  });

  it('should provide the Users model', () => {
    const userModelProvider = module.get(getModelToken(Users.name));
    expect(userModelProvider).toBeDefined();
    expect(userModelProvider.modelName).toBe(Users.name);
    expect(userModelProvider.schema).toBe(UsersSchema);
  });

  it('should provide the UsersServices', () => {
    const usersService = module.get(UsersServices);
    expect(usersService).toBeDefined();
    expect(usersService).toBeInstanceOf(UsersServices);
  });

  it('should provide the UsersController', () => {
    const usersController = module.get(UsersController);
    expect(usersController).toBeDefined();
    expect(usersController).toBeInstanceOf(UsersController);
  });

  it('should export the UsersServices', () => {
    const exportedServices = module.get(UsersServices, { strict: false });
    expect(exportedServices).toBeDefined();
    expect(exportedServices).toBeInstanceOf(UsersServices);
  });
});

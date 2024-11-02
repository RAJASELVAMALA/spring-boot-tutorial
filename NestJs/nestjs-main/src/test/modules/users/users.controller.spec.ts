import { Test, TestingModule } from '@nestjs/testing';
import { UsersController } from '../../../modules/users/users.controller';
import { UsersServices } from '../../../modules/users/users.services';
import { Model } from 'mongoose';
import { Users } from '../../../modules/users/users.schema';
import { getModelToken } from '@nestjs/mongoose';

const mockModel = {
  find: jest.fn(),
  findById: jest.fn(),
  create: jest.fn(),
  findByIdAndUpdate: jest.fn(),
  findOne: jest.fn(),
  // Add other methods used by UsersServices
};
describe('UsersController', () => {
  let usersController: UsersController;
  let usersServices: UsersServices;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [UsersController],
      providers: [
        UsersServices,
        {
          provide: getModelToken(Users.name),
          // @ts-ignore
          useValue: mockModel as Model<Users>,
        },
      ],
    }).compile();

    usersController = module.get<UsersController>(UsersController);
    usersServices = module.get<UsersServices>(UsersServices);
  });

  describe('getUsers', () => {
    it('should return an array of users', async () => {
      const usersList = [{ id: '1', name: 'John Doe' }];
      // @ts-ignore
      jest.spyOn(usersServices, 'getUsers').mockResolvedValue(usersList);

      const result = await usersController.getUsers();

      expect(result).toEqual({
        data: usersList,
        message: 'Users list',
        statusCode: 200,
      });
    });
  });

  describe('createUser', () => {
    it('should create a new user', async () => {
      const newUser = {
        _id: '098764567890',
        avatar: '',
        password: 'Test@1345',
        name: 'Alice',
        email: 'alice@example.com',
      };
      // @ts-ignore
      jest.spyOn(usersServices, 'saveUser').mockResolvedValue(newUser);

      const result = await usersController.createUser(newUser);

      expect(result).toEqual({
        data: newUser,
        message: 'User Created',
        statusCode: 200,
      });
    });
  });

  describe('patchUser', () => {
    it('should partially update a user by ID', async () => {
      const userId = '1';
      const updatedUserData = { name: 'Updated Name' };

      jest
        .spyOn(usersServices, 'patchUpdateUser')
        // @ts-ignore
        .mockResolvedValue(updatedUserData);

      const result = await usersController.patchUser(userId, updatedUserData);

      expect(result).toEqual({
        data: updatedUserData,
        message: 'Updated user',
        statusCode: 200,
      });
    });
  });
});

import { Test, TestingModule } from '@nestjs/testing';
import { getModelToken } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { NotFoundException } from '@nestjs/common';
import { UsersServices } from '../../../modules/users/users.services';
import { Users } from '../../../modules/users/users.schema';
import {
  IUserCreate,
  IUserUpdate,
} from '../../../modules/users/users.interface';

const mockModel = {
  find: jest.fn(),
  findById: jest.fn(),
  create: jest.fn(),
  findOne: jest.fn(),
  update: jest.fn(),
  findByIdAndUpdate: jest.fn(),
};

describe('UsersServices', () => {
  let usersServices: UsersServices;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        UsersServices,
        {
          provide: getModelToken(Users.name),
          // @ts-ignore
          useValue: mockModel as Model<Users>,
        },
      ],
    }).compile();

    usersServices = module.get<UsersServices>(UsersServices);
  });

  afterEach(() => {
    jest.resetAllMocks();
  });

  describe('getUsers', () => {
    it('should return an array of users with selected fields', async () => {
      // Arrange
      const expectedUsers = [{ name: 'John', email: 'john@example.com' }];
      mockModel.find.mockResolvedValue(expectedUsers);

      // Act
      const result = await usersServices.getUsers();

      // Assert
      expect(result).toEqual(expectedUsers);
      expect(mockModel.find).toHaveBeenCalledWith(
        {},
        { name: 1, email: 1, avatar: 1, createdAt: 1, updatedAt: 1 },
      );
    });
  });

  describe('getUser', () => {
    it('should return a user by ID with selected fields', async () => {
      // Arrange
      const userId = '123';
      const expectedUser = { name: 'John', email: 'john@example.com' };
      mockModel.findById.mockResolvedValue(expectedUser);

      // Act
      const result = await usersServices.getUser(userId);

      // Assert
      expect(result).toEqual(expectedUser);
      expect(mockModel.findById).toHaveBeenCalledWith(userId, {
        name: 1,
        email: 1,
        avatar: 1,
        createdAt: 1,
        updatedAt: 1,
      });
    });

    it('should throw NotFoundException if user is not found', async () => {
      // Arrange
      const userId = '123';
      mockModel.findById.mockResolvedValue(null);

      // Act & Assert
      await expect(usersServices.getUser(userId)).rejects.toThrowError(
        NotFoundException,
      );
    });
  });

  describe('saveUser', () => {
    it('should save a new user', async () => {
      // Arrange
      // @ts-ignore
      const newUser: IUserCreate = {
        name: 'Alice',
        email: 'alice@example.com',
      };
      mockModel.create.mockResolvedValue(newUser);

      // Act
      const result = await usersServices.saveUser(newUser);

      // Assert
      expect(result).toEqual(newUser);
      expect(mockModel.create).toHaveBeenCalledWith(newUser);
    });
  });

  describe('patchUpdateUser', () => {
    it('should update a user partially by ID', async () => {
      // Arrange
      const userId = '123';
      const updatedUserData: IUserUpdate = { name: 'Updated Name' };
      const existingUser = { name: 'John', email: 'john@example.com' };
      mockModel.findById.mockResolvedValue(existingUser);
      mockModel.findByIdAndUpdate.mockResolvedValue(existingUser);

      // Act
      const result = await usersServices.patchUpdateUser(
        userId,
        updatedUserData,
      );

      // Assert
      expect(result).toEqual(existingUser);
      expect(mockModel.findById).toHaveBeenCalledWith(userId, {
        name: 1,
        email: 1,
        avatar: 1,
        createdAt: 1,
        updatedAt: 1,
      });
      expect(mockModel.findByIdAndUpdate).toHaveBeenCalledWith(
        userId,
        updatedUserData,
      );
      expect(mockModel.findByIdAndUpdate).toHaveBeenCalledWith(
        expect.any(String),
        expect.any(Object),
      );
    });

    it('should throw NotFoundException if user is not found', async () => {
      // Arrange
      const userId = '123';
      const updatedUserData: IUserUpdate = { name: 'Updated Name' };
      mockModel.findById.mockResolvedValue(null);

      // Act & Assert
      await expect(
        usersServices.patchUpdateUser(userId, updatedUserData),
      ).rejects.toThrowError(NotFoundException);
    });
  });

  describe('getUserByEmail', () => {
    it('should return a user by email', async () => {
      // Arrange
      const userEmail = 'john@example.com';
      const expectedUser = { name: 'John', email: userEmail };
      mockModel.findOne.mockResolvedValue(expectedUser);

      // Act
      const result = await usersServices.getUserByEmail(userEmail);

      // Assert
      expect(result).toEqual(expectedUser);
      expect(mockModel.findOne).toHaveBeenCalledWith({ email: userEmail });
    });

    it('should throw NotFoundException if user is not found', async () => {
      // Arrange
      const userEmail = 'john@example.com';
      mockModel.findOne.mockResolvedValue(null);

      // Act & Assert
      await expect(
        usersServices.getUserByEmail(userEmail),
      ).rejects.toThrowError(NotFoundException);
    });
  });
});

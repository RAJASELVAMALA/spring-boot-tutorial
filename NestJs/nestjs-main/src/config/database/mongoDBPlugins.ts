import { Schema } from 'mongoose';

// Define a Mongoose plugin to add a virtual 'id' property to each document
export const idPropertyPlugin = (schema: Schema) => {
  // Add a virtual 'id' property using the 'get' function
  schema.virtual('id').get(function () {
    // Return the '_id' property as the virtual 'id'
    return this._id;
  });
};

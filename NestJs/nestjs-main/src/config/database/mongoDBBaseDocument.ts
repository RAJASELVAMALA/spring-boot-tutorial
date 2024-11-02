import { Prop, Schema } from '@nestjs/mongoose';
import { Document as MongooseDocument } from 'mongoose';
import { now } from '../../helpers/utils';

// Define a Mongoose schema for timestamps with toJSON and toObject options
@Schema({ toJSON: { virtuals: true }, toObject: { virtuals: true } })
export class Timestamps extends MongooseDocument {
  // Define a 'createdAt' property with a default value set to the current timestamp
  @Prop({ default: now })
  createdAt?: number;

  // Define an 'updatedAt' property with a default value set to the current timestamp
  @Prop({ default: now })
  updatedAt?: number;
}

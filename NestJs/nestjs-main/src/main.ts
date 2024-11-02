import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import * as morgan from 'morgan';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';
import { HttpExceptionFilter } from './middleware/http-exception.filter';
import { ValidationPipe } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';

// Bootstrap the Nest.js application
async function bootstrap() {
  // Create an instance of the Nest application
  const app = await NestFactory.create(AppModule);

  // Use the 'tiny' format for logging HTTP requests
  app.use(morgan('tiny'));

  // Configure Swagger documentation
  const config = new DocumentBuilder()
    .setTitle('ICANIO NestJs Framework')
    .setDescription('The NestJs API description')
    .setVersion('1.0')
    .addBearerAuth() // Add Bearer token authentication to Swagger
    .build();
  const document = SwaggerModule.createDocument(app, config);
  SwaggerModule.setup('api', app, document);

  // Apply the global exception filter to handle HTTP exceptions
  app.useGlobalFilters(new HttpExceptionFilter());
  app.useGlobalPipes(new ValidationPipe());

  const configService = app.get(ConfigService);
  const port = configService.get('PORT', 3005);
  // Start the application and listen on port 3000
  await app.listen(port);
}
// Call the bootstrap function to start the application
bootstrap();

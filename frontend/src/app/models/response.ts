export class ResponseDto {
  timestamp?: Date;
  status?: number;
}

export class ResponseDtoSuccess extends Response {
  message?: object;
}

export class ResponseDtoError extends Response {
  error?: object;
}

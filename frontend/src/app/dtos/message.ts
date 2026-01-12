export class MessageDto {
  constructor(
    public id: string,
    public content: string,
    public createdAt: string,
    public sentByMe: boolean
  ) {}
}

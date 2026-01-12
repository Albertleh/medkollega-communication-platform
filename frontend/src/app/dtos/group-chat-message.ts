export class GroupChatMessageDto {
  constructor(
    public id: string,
    public content: string,
    public createdAt: string, // kommt i.d.R. als ISO-String
    public senderFirstName: string | null,
    public senderLastName: string | null,
    public sentByMe: boolean
  ) {}
}

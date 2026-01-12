export class ChatDto {
  constructor(
    public id: string,
    public partnerFirstName: string,
    public partnerLastName: string,
    public partnerRole: string,
    public unreadMessages: number,
    public accepted: boolean,
    public createdByMe: boolean
  ) {}
}

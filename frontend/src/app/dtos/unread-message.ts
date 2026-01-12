export class UnreadMessageFrontendDto {
  constructor(
    public id: string,
    public chatId: string,
    public patientId: string, 
    public senderName: string,
    public initials: string,
    public preview: string,
    public createdAt: string,
    public unread: boolean = true,
    public time?: string,
    public color?: string,
    public patientName?: string
  ) {}
}

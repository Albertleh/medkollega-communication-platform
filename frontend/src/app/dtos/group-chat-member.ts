export class GroupChatMemberDto {
  constructor(
    public partnerFirstName: string,
    public partnerLastName: string,
    public partnerRole: string,
    public accepted: boolean
  ) {}
}

import { GroupChatMemberDto } from './group-chat-member';

export class GroupChatDto {
  constructor(
    public id: string,
    public name: string,
    public members: GroupChatMemberDto[],
    public unreadMessages: number,
    public createdByMe: boolean,
    public accepted: boolean
  ) {}
}

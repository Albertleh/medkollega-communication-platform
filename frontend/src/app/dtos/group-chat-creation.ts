export class GroupChatCreationDto {
  constructor(
    public patientId: string,
    public professionalIds: string[],
    public groupName: string
  ) {}
}
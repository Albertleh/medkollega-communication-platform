export class ChatRequestDto {
  constructor(
    public chatId: string,
    public patientInsuranceNumber: number,
    public patientDateOfBirth: string,
    public patientCreated: boolean,
    public senderFirstName: string,
    public senderLastName: string,
    public senderProfessionalRoleUser: string,
    public patientId: string,
    public groupChat: boolean,
    public groupChatName: string
  ) {}
}

export class ContactDto {
  constructor(
    public userId: string,
    public firstName: string,
    public lastName: string,
    public professionalRoleUser: string,
    public zipCode: string,
    public city: string,
    public placesAvailable: boolean
  ) {}
}

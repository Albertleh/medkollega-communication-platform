export class ContactUpdateDto {
  constructor(
    public firstName: string,
    public lastName: string,
    public zipCode: string,
    public city: string,
    public placesAvailable: boolean
  ) {}
}

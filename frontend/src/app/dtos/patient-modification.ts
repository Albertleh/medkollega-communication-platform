export class PatientModificationDto {
  constructor(
    public insuranceNumber: number,
    public dateOfBirth: string,  
    public firstName: string,
    public lastName: string,
    public zipCode: string,
    public city: string,
    public streetName: string,
    public streetNumber: string,
    public description: string
  ) {}
}


export class PatientDto {
    constructor (
        public id: string,
        public socNr: string,
        public firstName: string,
        public lastName: string
    ) {}
}
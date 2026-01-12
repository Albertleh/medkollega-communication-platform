export class MedicationDto {
  constructor(
    public id: string,
    public createdAt: string,     // LocalDate
    public medication: string,
    public morning: string | null,
    public midday: string | null,
    public evening: string | null,
    public night: string | null,
    public note: string | null
  ) {}
}

export class MedicationCreationDto {
  constructor(
    public medication: string,
    public morning: number | null,
    public midday: number | null,
    public evening: number | null,
    public night: number | null,
    public note: string | null
  ) {}
}
